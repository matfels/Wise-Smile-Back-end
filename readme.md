# 🦷 WiseSmile API

O **WiseSmile** é um sistema de gestão odontológica desenvolvido para otimizar o fluxo de atendimento em clínicas, centralizando o agendamento de consultas, cadastro de pacientes e especialistas, além de oferecer controle de acesso administrativo seguro.

---

## 🚀 Tecnologias Utilizadas

Este projeto utiliza uma arquitetura moderna e escalável:

* **Backend:** Java 17, Spring Boot 3
* **Segurança:** Spring Security com autenticação via JWT (JSON Web Tokens)
* **Persistência:** Spring Data JPA / Hibernate
* **Banco de Dados:** MySQL
* **Frontend:** Angular 17+

---

## ⚙️ Funcionalidades Principais

* **Autenticação Segura:** Login via JWT para proteção de rotas e segurança dos dados dos pacientes.
* **Gestão Clínica:** CRUD completo de Pacientes, Dentistas e Especialidades.
* **Controle de Consultas:** Agendamento, listagem e cancelamento com registro de justificativa.
* **Dashboard:** Resumo estatístico para acompanhamento da produtividade e movimento da clínica.

---

## 📋 Pré-requisitos

Para rodar este projeto em sua máquina local, certifique-se de ter instalado:

* [Java JDK 17+](https://adoptium.net/)
* [MySQL Server](https://dev.mysql.com/downloads/mysql/)
* [Maven](https://maven.apache.org/)

---

## 🔧 Configuração e Instalação

**1. Clone o repositório:**

git clone [https://github.com/matfels/wise-smile-back-end.git](https://github.com/matfels/wise-smile-back-end.git)
cd wise-smile-back-end 

## 🔧 Configuração e Execução

**2. Configuração do Banco de Dados:**
* Crie um banco de dados chamado `clinica` no seu MySQL.
* Abra o arquivo `src/main/resources/application.properties`.
* Ajuste as credenciais de acesso (`spring.datasource.username` e `password`) conforme o seu ambiente local.

**3. Execute o projeto:**
./mvnw spring-boot:run


## 🛠️ Endpoints da API

Abaixo estão listadas as principais rotas disponíveis na aplicação:

| Método | Endpoint | Descrição |
| :---: | :--- | :--- |
| `POST` | `/login` | Autenticação do usuário (obtenção do Token) |
| `GET` | `/consultas` | Listar todas as consultas |
| `POST` | `/consultas` | Criar novo agendamento |
| `PUT` | `/consultas/{id}/cancelar` | Cancelar consulta |
| `GET` | `/consultas/dashboard` | Resumo estatístico do sistema |
| `CRUD` | `/pacientes` | Gerenciamento de pacientes |
| `CRUD` | `/dentistas` | Gerenciamento de especialistas |

---

## 🛡️ Segurança (Fluxo de Autenticação)

O sistema utiliza o padrão **Bearer Token** no cabeçalho `Authorization` das requisições HTTP para garantir que apenas usuários devidamente autenticados tenham acesso aos endpoints protegidos.

---

## ✒️ Autor

Desenvolvido por **Matheus Melo**.

[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/matfels)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/matheus-melo-dev/)
