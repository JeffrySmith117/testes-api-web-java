# Automação de Testes - API e Web

Projeto de automação de testes cobrindo testes de API REST e testes Web E2E, desenvolvido em Java.



## 📁 Estrutura do Projeto

automacao-testes/
├── .github/
│   └── workflows/
│       └── ci.yml                  # Pipeline CI (API + Web)
├── api-tests/
│   ├── src/test/java/com/testes/
│   │   ├── PetTest.java            # Endpoints /pet
│   │   ├── StoreTest.java          # Endpoints /store
│   │   └── UserTest.java           # Endpoints /user
│   └── pom.xml
├── web-tests/
│   ├── src/test/java/com/testes/
│   │   ├── pages/
│   │   │   ├── LoginPage.java
│   │   │   ├── InventoryPage.java
│   │   │   ├── CartPage.java
│   │   │   └── CheckoutPage.java
│   │   └── CompraE2ETest.java
│   └── pom.xml
└── README.md


## 🛠️ Tecnologias

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 17 |
| Framework de testes | JUnit 5 |
| Automação Web | Selenium WebDriver 4 |
| Requisições HTTP | RestAssured |
| Gerenciamento de driver | WebDriverManager |
| Build | Maven |
| CI/CD | GitHub Actions |


## ⚙️ Pré-requisitos

- Java 17+
- Maven 3.8+
- Google Chrome instalado

## 🚀 Instalação e Execução

### 1. Clone o repositório

```bash
git clone https://github.com/JeffrySmith117/testes-api-web-java.git
cd testes-api-web-java
```

### 2. Testes de API

```bash
cd api-tests
mvn test
```

### 3. Testes Web

```bash
cd web-tests
mvn test
```

## ⚙️ Pipeline CI/CD

A pipeline é disparada automaticamente em todo **push** ou **pull request** para a branch `main`.

**Jobs:**
- `api-tests` → executa os testes de API contra o Petstore Swagger
- `web-tests` → instala Chrome e executa os testes E2E no SauceDemo

Arquivo: `.github/workflows/ci.yml`

### ✅ Pipeline passando

![Pipeline](https://github.com/JeffrySmith117/testes-api-web-java/actions/workflows/ci.yml/badge.svg)


## 🧪 Cenários Cobertos

### API – Petstore (https://petstore.swagger.io/v2)

| Módulo | Cenários |
|---|---|
| Pet | Criar, buscar por ID, atualizar status, deletar |
| Store | Criar pedido, buscar pedido, consultar inventário, deletar |
| User | Criar usuário, buscar, fazer login, deletar |

### Web – SauceDemo (https://www.saucedemo.com)

| Cenário | Descrição |
|---|---|
| Fluxo E2E completo | Login → adicionar produto → carrinho → checkout → confirmação |


## 🎨 Design Pattern - Page Object Model

O projeto web utiliza o padrão **Page Object Model (POM)**:

- `LoginPage` → encapsula ações da página de login
- `InventoryPage` → encapsula ações da página de produtos
- `CartPage` → encapsula ações da página do carrinho
- `CheckoutPage` → encapsula ações da página de checkout

## 🔑 Credenciais SauceDemo
Usuário: standard_user
Senha:   secret_sauce

## ✅ Boas Práticas Adotadas

- Page Object Model para separação de responsabilidades
- Esperas explícitas com `WebDriverWait`
- Headless Chrome para execução em CI
- Asserções explícitas com mensagens claras
- Commits descritivos seguindo padrão `feat/fix/chore/refactor/docs`