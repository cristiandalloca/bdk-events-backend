# BDK Gestão de Eventos

O **BDK Gestão de Eventos** é um sistema focado inicialmente na gestão de **formaturas**, mas com estrutura pensada para ser facilmente adaptável a outros tipos de eventos como **casamentos**, **festas corporativas** ou **eventos sociais em geral**.

> ⚠️ Este projeto está em desenvolvimento.

---

## 📌 Objetivo

Migrar um sistema legado feito em PHP (cerca de 2010) para uma arquitetura moderna utilizando Java com Spring Framework, oferecendo uma base sólida e escalável para gerenciamento de eventos.

---

## 🛠 Tecnologias Utilizadas

- **Java 21**
- **Spring Framework**
  - Spring Boot
  - Spring Web (REST APIs)
  - Spring Security (Autenticação com JWT)
- **Jakarta Validation** (Validação e tratamento de erros)
- **Swagger** (Documentação das APIs)
- **MySQL** (Banco de Dados Relacional)
- **Docker** (Ambiente de desenvolvimento)
- **Flyway** (Versionamento do banco de dados)
- **Google Cloud Storage** (Bucket para arquivos)

---

## 🚀 Como rodar o projeto localmente

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/bdk-gestao-eventos.git
   ```

2. Suba o banco de dados com Docker:
   ```bash
   cd tools/docker/db
   docker-compose up -d
   ```

3. Com o banco rodando, execute o projeto (via sua IDE ou terminal):
   ```bash
   ./mvnw spring-boot:run
   ```

4. Acesse a documentação da API:
   - [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## ✨ Funcionalidades

- Integração com busca de CEPs (via API externa)
- CRUD completo para:
  - Cidades
  - Estados
  - Países
  - Empresas e suas parametrizações
  - Instituições educacionais
  - Cursos
  - Eventos
  - Convidados
  - Fornecedores
  - Serviços e serviços por evento
  - Usuários
  - Perfis e Privilégios
- Autenticação com JWT
- Versionamento de banco de dados com Flyway
- Armazenamento de arquivos em bucket do Google Cloud

---

## 📸 Prints do Projeto

![image](https://github.com/user-attachments/assets/f48770ca-a37a-46a6-8984-986e08f8d156)


---

## 🔗 Link de Demonstração

- Swagger: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🙋‍♂️ Autor

Desenvolvido por [Cristian Berti](https://github.com/cristiandalloca)  
Entre em contato: cristiandalloca@gmail.com
