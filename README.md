# Spring Bloglab

Projeto de estudo, com o objetivo de criar um pequeno blog, desenvolvido com as seguintes tecnologias:

 - Java 8
 - Spring 4.3
 - Spring Boot 1.4
 - Spring Data JPA
 - Lombok
 - Liquibase
 - PostgreSQL

# Instalação

Breve...

# Funcionalidades e Regras

As funcionalidades abaixo, traz uma descrição da responsabilidade de cada um, e as suas necessidades.

## Autor

Usuário responsável pelo blog, e os posts criados. Ele é composto por:

 - Nome
 - Sobrenome
 - E-mail


### Considerações

 - O *nome* e *sobrenome* devem ter no máximo **50 caracteres**
 - O *e-mail* deve ter no máximo **60 caracteres**
 - O formato do e-mail **deverá ser validado**
 - Todos os campos são **obrigatórios**


## Post

Essa funcionalidade representa os posts do blog em que é composto por:

 - Título
 - Slug (URL representando o post)
 - Autor do Post
 - Resumo geral
 - Conteúdo do post
 - Data de criação
 - Data de atualização
 - Status (ativo / inativo)
 - Palavras-chave


### Considerações

 - O *título* deve ter no máximo **50 caracteres**
 - O *slug* deve ter no máximo **70 caracteres**
 - O *resumo geral* deve ter no máximo **150 caracteres**
 - O *conteúdo do post* deve aceitar textos grandes
 - A *data de criação* é registrada ao salvar o post
 - A *data de atualização* é registrado ao atualizar o post
 - Os posts com *status* **inativo**, não devem ser exibidos (remoção lógica)
 - Os posts com *status* **ativo**, devem ser exibidos
 - As *palavras-chave* **não tem limite** e são **opcionais**
 - O restante dos campos são **obrigatórios**
