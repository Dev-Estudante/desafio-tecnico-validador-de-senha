
---

# 🔐 Password Validator

Um serviço simples, mas poderoso, para validar senhas seguindo boas práticas de segurança.
Ideal para cenários em que você precisa garantir que o usuário crie senhas fortes e seguras.

---

## 🚀 Como rodar o projeto

### 1) Subir o MongoDB (via Docker)

Se você já tem Mongo instalado, pode pular essa parte.
Caso contrário, basta rodar:

```bash
docker run -d --name mongo \
  -p 27017:27017 \
  -e MONGO_INITDB_ROOT_USERNAME=admin \
  -e MONGO_INITDB_ROOT_PASSWORD=123456 \
  mongo:6
```

Isso vai levantar um container com usuário `admin` e senha `123456`.

---

### 2) Rodar a aplicação

Certifique-se de estar com o Java 21 configurado. Depois, basta:

```bash
chmod +x ./mvnw
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
./mvnw spring-boot:run
```

A aplicação ficará disponível em:
👉 [http://localhost:8099](http://localhost:8099)

---

### 3) Testar a validação de senha

* **Caso válido (retorna 204 No Content):**

```bash
curl -i -X POST \
  -H "Content-Type: application/json" \
  -d '{
    "user": "joao",
    "password": "ABCDEFGH1u@Java",
    "confirmPassword": "ABCDEFGH1u@Java"
  }' \
  http://localhost:8099/password-validations
```

* **Caso inválido (senha contém o nome do usuário):**

```bash
curl -i -X POST \
  -H "Content-Type: application/json" \
  -d '{
    "user": "joao",
    "password": "joaoJava1!",
    "confirmPassword": "joaoJava1!"
  }' \
  http://localhost:8099/password-validations
```

O retorno será `400 Bad Request` com a mensagem de erro.

---

## ⚙️ Configuração

* Porta padrão: **8099**
* URI do MongoDB (já configurada em `application.yml`):

  ```
  mongodb://admin:123456@localhost:27017/userCredentials?authSource=admin
  ```

---

## 🧪 Rodando testes

```bash
./mvnw test
```

---

## 📋 Regras de validação da senha

1. Ter pelo menos **8 caracteres**
2. Conter **1 letra maiúscula** (A-Z)
3. Conter **1 letra minúscula** (a-z)
4. Conter **1 número** (0-9)
5. Conter **1 caractere especial** (!@#\$%^&\*()-\_+=)
6. Não repetir nenhuma das últimas **5 senhas usadas**
7. Não ser uma senha comum (`123456`, `password`, etc.)
8. Não conter o nome do usuário

---

## 🔎 Exemplos de senhas

| Senha     | Resultado  | Motivo da reprovação                   |
| --------- | ---------- | -------------------------------------- |
| Abc123!@# | ✅ válido   | Cumpre todas as regras                 |
| abcdef    | ❌ inválido | Curta, sem maiúscula e sem número      |
| ABCDEFGH1 | ❌ inválido | Sem minúscula e sem caractere especial |
| Abcdefgh  | ❌ inválido | Sem número e sem caractere especial    |

---

## 🌟 Extras implementados

* Endpoint para histórico de senhas: `GET /password-histories`
* Senhas são **ofuscadas** (exibidas como `******`) no histórico

---

## 🔍 O que avaliamos nesse desafio

* Código limpo e organizado
* Boas práticas de design (DDD, SOLID, Clean Code)
* Testes cobrindo os cenários principais
* Commits descritivos (ex.: `feat: criar endpoint POST de validação de senha`)

---
