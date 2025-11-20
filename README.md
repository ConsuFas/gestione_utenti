# Gestione Utenti – Spring Boot REST API

Applicazione Spring Boot progettata per la gestione degli utenti tramite API REST e dotata di un’interfaccia web statica per la gestione delle operazioni CRUD.  
Il progetto utilizza un database **H2 in-memory**.

---

## Tecnologie Utilizzate

### Backend
- Java 17  
- Spring Boot  
- Spring Web  
- Spring Data JPA  
- Validation  
- Hibernate  
- H2 Database  
- Maven  

### Frontend
- HTML5  
- CSS3  
- JavaScript Vanilla  

---

## Configurazione Database (H2)

Il progetto utilizza un database **H2 in-memory**, che viene creato automaticamente all’avvio dell’applicazione.

### Configurazione reale (`application.properties`)

```properties
spring.application.name=gestione_utenti
server.port=8080

# H2 database in-memory
spring.datasource.url=jdbc:h2:mem:gestione_utenti
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=exi
spring.datasource.password=admin

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Console H2
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

---

## Accesso alla Console H2

Una volta avviata l’applicazione, è possibile accedere alla console del database tramite:

```
http://localhost:8080/h2-console
```

### Credenziali

- **JDBC URL:**  
  ```
  jdbc:h2:mem:gestione_utenti
  ```
- **User Name:**  
  ```
  exi
  ```
- **Password:**  
  ```
  admin
  ```
  
## Nota sul popolamento del database (H2)

Per facilitare i test dell’applicazione è consigliato inserire alcuni record di esempio direttamente dalla H2 Console prima di aprire la pagina web.
In questo modo il database risulterà già popolato e sarà possibile verificare più facilmente le funzionalità di visualizzazione, ricerca, modifica ed eliminazione degli utenti.

## Query SQL utili per la Console H2

Queste query possono essere eseguite direttamente dalla console H2 (`http://localhost:8080/h2-console`) per verificare, modificare o popolare il database durante le operazioni di test. 
###  Creare utenti (INSERT)

```sql
INSERT INTO utente (nome, cognome, codice_fiscale, data_nascita)
VALUES ( 'Marco', 'Rossi', 'RSSMRC90A01H501U', DATE '1990-01-01');

INSERT INTO utente (nome, cognome, codice_fiscale, data_nascita)
VALUES ('Giulia', 'Verdi', 'VRDGLI95C10H501B', DATE '1995-03-10');

INSERT INTO utente (nome, cognome, codice_fiscale, data_nascita)
VALUES ('Luca', 'Bianchi', 'BNCGLC88D15H501C', DATE '1988-04-15');

INSERT INTO utente (nome, cognome, codice_fiscale, data_nascita)
VALUES ('Sara', 'Neri', 'NRESRA92E20H501F', DATE '1992-05-20');
```

---

###  Visualizzare tutta la tabella

```sql
SELECT * FROM UTENTE;
```

---

###  Recuperare un utente tramite ID

```sql
SELECT * FROM UTENTE WHERE ID = 1;
```

---

###  Eliminare un utente tramite ID

```sql
DELETE FROM UTENTE WHERE ID = 1;
```


---

## Avvio dell’Applicazione (Build & Run)

Importare il progetto in un IDE compatibile con Spring Boot  
(IntelliJ IDEA, Eclipse, VS Code con estensioni Spring).

Eseguire la classe principale:

```
GestioneUtentiApplication
```

L’applicazione sarà disponibile all’indirizzo:

```
http://localhost:8080
```

---


All'interno della pagina web, tramite ui sono disponibili le seguenti funzionalità:

- Visualizzazione della lista utenti  
- Ricerca dinamica dell'utente(full text e match esatto)
- Creazione di un nuovo utente  
- Modifica dei dati utente  
- Eliminazione utenti  

---

## Endpoints API

Tutte le API espongono il prefisso:

```
/api/utenti
```

### 1. Crea utente
```
POST /api/utenti/createUtente
```

### 2. Ottieni tutti gli utenti
```
GET /api/utenti/getAllUtenti
```

### 3. Ottieni utente per ID
```
GET /api/utenti/getUtenteById/{id}
```

### 4. Aggiorna utente
```
PUT /api/utenti/updateUtente/{id}
```

### 5. Elimina utente
```
DELETE /api/utenti/deleteUtente/{id}
```

### 6. Ricerca utenti
```
GET /api/utenti/ricercaUtente?q=valore
```

---


## JSON di esempio per testare le API

Di seguito alcuni JSON pronti all’uso per testare rapidamente le API tramite Postman, o strumenti analoghi.

---

### 1. Creare un nuovo utente (POST /createUtente)

```json
{
  "nome": "Elena",
  "cognome": "Marchetti",
  "codiceFiscale": "MRCELN85A41H501G",
  "dataNascita": "1985-01-01"
}
```

**Alternativa:**

```json
{
  "nome": "Davide",
  "cognome": "Conti",
  "codiceFiscale": "CNTDVD90B12H501L",
  "dataNascita": "1990-02-12"
}
```

---

### 2. Aggiornare un utente esistente (PUT /updateUtente/{id})

> Nota: sostituire `{id}` nell’URL con l’ID dell’utente da aggiornare.  
> E mantenere lo stesso codice fiscale dell’utente per evitare l’errore di duplicazione.

```json
{
  "nome": "Giulia Modificata",
  "cognome": "Verdi",
  "codiceFiscale": "VRDGLI95C10H501B",
  "dataNascita": "1995-03-10"
}
```
>Questo esempio aggiorna l’utente con ID 2 presente negli INSERT iniziali.
---

### 3. Ricerca utenti (GET /ricercaUtente?q=valore)

Esempi di query string:

```
/api/utenti/ricercaUtente?q=giu
/api/utenti/ricercaUtente?q=Verdi
/api/utenti/ricercaUtente?q=VRDGLI

```

---




## Autore
Sviluppato da **Consuelo Fasano**  

## Repository GitHub

Il codice sorgente del progetto è disponibile alla seguente repository GitHub:

https://github.com/ConsuFas/gestione_utenti

