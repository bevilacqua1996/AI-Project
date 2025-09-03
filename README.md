# AI-Project
Introdução de projeto AI

# Guia de Inicialização do Ambiente com Docker Compose

Este projeto utiliza Docker Compose para subir dois serviços principais:

- **Ollama**: Plataforma para execução de modelos de IA.
- **Qdrant**: Banco de dados vetorial para armazenamento e busca eficiente.

## Pré-requisitos

- [Docker](https://docs.docker.com/get-docker/) instalado
- [Docker Compose](https://docs.docker.com/compose/install/) instalado

## Como subir os serviços

1. **Clone o repositório (se necessário):**
   ```bash
   git clone <URL-do-repositório>
   cd AI-Project
   ```

2. **Suba os containers:**
   ```bash
   docker-compose up -d
   ```

   Isso irá iniciar os serviços `ollama` e `qdrant` em segundo plano.

## Como baixar o modelo Llama3 no Ollama

Após os containers estarem rodando, execute o comando abaixo para baixar o modelo mistral dentro do container Ollama e também o modelo embedding nomic-embed-text:

```bash
docker exec -it ollama ollama pull mistral
```

```bash
docker exec -it ollama ollama pull nomic-embed-text
```

Esse comando faz o download do modelo Llama3, tornando-o disponível para uso no serviço Ollama.

## Parar os serviços

Para parar e remover os containers, execute:

```bash
docker-compose down
```

---

Pronto! Agora seu ambiente está configurado para iniciar o desenvolvimento
