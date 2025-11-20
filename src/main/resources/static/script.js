/* ====== CONFIGURAZIONE ====== */

const API = "/api/utenti";
let editId = null;

// Paginazione e sorting
let allUsers = [];
let currentPage = 1;
const pageSize = 5;
let currentSort = { key: null, asc: true };

// Flag: mostra tabella solo se richiesto
let showTableEnabled = false;

const usersTable = document.getElementById("usersTable");
const tbody = document.querySelector("#usersTable tbody");
const noResults = document.getElementById("noResults");
const searchInput = document.getElementById("search");
const showAllBtn = document.getElementById("showAllBtn");



/* ====== RENDER BASE DELLA TABELLA (SINGOLA PAGINA) ====== */

function renderUsers(data, showNoResultsMessage = true) {
    tbody.innerHTML = "";

    if (!data || data.length === 0) {
        usersTable.style.display = "none";

        if (showNoResultsMessage) {
            noResults.textContent = "Nessun utente trovato.";
            noResults.style.display = "block";
        } else {
            noResults.style.display = "none";
        }
        return;
    }

    data.forEach(u => {
        const tr = document.createElement("tr");

        tr.innerHTML = `
            <td>${u.id}</td>
            <td>${u.nome}</td>
            <td>${u.cognome}</td>
            <td>${u.codiceFiscale}</td>
            <td>${u.dataNascita}</td>
            <td>
                <button class="edit" onclick="editUser(${u.id})">Modifica</button>
                <button class="delete" onclick="deleteUser(${u.id})">Elimina</button>
            </td>
        `;

        tbody.appendChild(tr);
    });

    // MOSTRO LA TABELLA SOLO SE È STATO ABILITATO
    if (showTableEnabled) {
        usersTable.style.display = "table";
    }

    noResults.style.display = "none";
}



/* ====== PAGINAZIONE + SORTING  ====== */

function applySorting(data) {
    if (!currentSort.key) return data;

    return data.sort((a, b) => {
        let v1 = a[currentSort.key] ?? "";
        let v2 = b[currentSort.key] ?? "";

        if (typeof v1 === "string") v1 = v1.toLowerCase();
        if (typeof v2 === "string") v2 = v2.toLowerCase();

        if (v1 < v2) return currentSort.asc ? -1 : 1;
        if (v1 > v2) return currentSort.asc ? 1 : -1;
        return 0;
    });
}

function renderPaginationControls() {
    const totalPages = Math.ceil(allUsers.length / pageSize);

    let paginationDiv = document.getElementById("pagination");
    if (!paginationDiv) {
        paginationDiv = document.createElement("div");
        paginationDiv.id = "pagination";
        paginationDiv.style.marginTop = "20px";
        document.body.appendChild(paginationDiv);
    }

    paginationDiv.innerHTML = "";

    for (let p = 1; p <= totalPages; p++) {
        const btn = document.createElement("button");
        btn.className = "page-btn";
        btn.textContent = p;
        btn.dataset.page = p;

        if (p === currentPage) {
            btn.style.background = "#d0d0d0";
            btn.style.fontWeight = "600";
        }

        btn.onclick = () => {
            currentPage = p;
            renderSortedAndPaginatedUsers();
        };

        paginationDiv.appendChild(btn);
    }
}

function renderSortedAndPaginatedUsers() {
    let data = [...allUsers];

    // 1. Sorting
    data = applySorting(data);

    // 2. Paginazione
    const start = (currentPage - 1) * pageSize;
    const paginated = data.slice(start, start + pageSize);

    // 3. Render tabella
    renderUsers(paginated, false);

    // 4. Render bottoni paginazione
    renderPaginationControls();
}



/* ====== GET FULL LIST (MOSTRA TUTTI) ====== */

async function loadAllUsers() {
    try {
        const res = await fetch(`${API}/getAllUtenti`);
        if (!res.ok) {
            console.error("Errore:", res.status);
            return;
        }

        allUsers = await res.json();
        currentPage = 1;

        // Abilito la visualizzazione della tabella
        showTableEnabled = true;

        renderSortedAndPaginatedUsers();

    } catch (err) {
        console.error("Errore di rete:", err);
    }
}

showAllBtn.addEventListener("click", loadAllUsers);



/* ====== CREATE / UPDATE UTENTE ====== */

document.getElementById("userForm").onsubmit = async function (e) {
    e.preventDefault();

    const form = new FormData(e.target);
    const obj = Object.fromEntries(form.entries());

    const isEdit = !!editId;
    const method = isEdit ? "PUT" : "POST";
    const url = isEdit
        ? `${API}/updateUtente/${editId}`
        : `${API}/createUtente`;

    try {
        const res = await fetch(url, {
            method: method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(obj)
        });

        if (!res.ok) {
            alert("Errore: " + await res.text());
            return;
        }

        alert(isEdit ? "Utente aggiornato" : "Utente creato");

        editId = null;
        e.target.reset();
        searchInput.value = "";

        // DOPO CREATE/UPDATE: NON MOSTRARE TABELLA
        showTableEnabled = false;
        usersTable.style.display = "none";
        tbody.innerHTML = "";
        noResults.style.display = "none";

    } catch (err) {
        console.error("Errore rete:", err);
    }
};



/* ====== EDIT UTENTE ====== */

async function editUser(id) {
    try {
        const res = await fetch(`${API}/getUtenteById/${id}`);

        if (!res.ok) {
            alert("Errore nel recupero dell'utente.");
            return;
        }

        const u = await res.json();
        const form = document.getElementById("userForm");

        form.nome.value = u.nome;
        form.cognome.value = u.cognome;
        form.codiceFiscale.value = u.codiceFiscale;
        form.dataNascita.value = u.dataNascita;

        editId = id;

    } catch (err) {
        console.error("Errore di rete:", err);
    }
}



/* ====== DELETE UTENTE ====== */

async function deleteUser(id) {
    try {
        const res = await fetch(`${API}/deleteUtente/${id}`, { method: "DELETE" });

        if (!res.ok) {
            alert("Errore durante l'eliminazione.");
            return;
        }

        // Dopo delete: NON mostrare lista completa
        showTableEnabled = false;
        usersTable.style.display = "none";
        tbody.innerHTML = "";
        noResults.style.display = "none";

    } catch (err) {
        console.error("Errore rete:", err);
    }
}



/* ====== SEARCH UTENTI ====== */

async function searchUsers(q) {
    const query = q.trim();

    if (query === "") {
        tbody.innerHTML = "";
        usersTable.style.display = "none";
        noResults.style.display = "none";
        return;
    }

    try {
        const res = await fetch(`${API}/ricercaUtente?q=${encodeURIComponent(query)}`);

        if (!res.ok) {
            noResults.textContent = "Errore durante la ricerca.";
            noResults.style.display = "block";
            usersTable.style.display = "none";
            return;
        }

        const data = await res.json();

        // IN SEARCH LA TABELLA SI MOSTRA, MA SENZA ABILITARE SHOW ALL
        showTableEnabled = true;

        renderUsers(data, true);

    } catch (err) {
        console.error("Errore di rete:", err);
    }
}

searchInput.oninput = e => searchUsers(e.target.value);



/* ====== SORTING COLONNE ====== */

document.getElementById("th-id").onclick = () => sortTable("id");
document.getElementById("th-nome").onclick = () => sortTable("nome");
document.getElementById("th-cognome").onclick = () => sortTable("cognome");
document.getElementById("th-cf").onclick = () => sortTable("codiceFiscale");
document.getElementById("th-data").onclick = () => sortTable("dataNascita");

function sortTable(key) {
    if (currentSort.key === key) {
        currentSort.asc = !currentSort.asc;
    } else {
        currentSort.key = key;
        currentSort.asc = true;
    }
    renderSortedAndPaginatedUsers();
}
