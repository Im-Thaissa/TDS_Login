# Trilha do Saber (TDS)

Sistema de login seguro desenvolvido por Thaíssa Victória Bianchini de Oliveira (RGM 11231102992) para a disciplina de Aplicativos Web. Implementa cadastro, autenticação, autorização por perfil e persistência de dados, servindo como base para uso no PFC "Trilha do Saber".

## Tecnologias

- Java 17
- Spring Boot 4.1.1
- Spring Security
- Spring Data MongoDB
- Spring Session (mongodb-spring-session)
- Thymeleaf
- MongoDB Atlas

## Funcionalidades

- Cadastro, login e logout com senha criptografada (BCrypt)
- Quatro perfis de usuário: **Aluno**, **Professor**, **Coordenador** e **Administrador**
- Escolha do perfil (Aluno, Professor ou Coordenador) no momento do cadastro (o perfil Admin nunca pode ser escolhido pelo formulário público)
- Fluxo de aprovação: Alunos e Professores são aprovados por um Coordenador; Coordenadores são aprovados por um Administrador. Uma conta pendente não consegue fazer login até ser aprovada
- Controle de acesso por rota, de acordo com o perfil do usuário
- Sessão HTTP persistida no MongoDB Atlas (sobrevive a reinícios do servidor)
- Temas visuais configuráveis por propriedade, sem necessidade de alterar código ou templates

## Pré-requisitos

- JDK 17 Temurin
- Maven (ou a integração de Maven já embutida no IntelliJ)
- Uma conta no [MongoDB Atlas](https://www.mongodb.com/atlas) (camada gratuita é suficiente)
- IntelliJ IDEA.

## Configurando o banco de dados

1. Crie um cluster no MongoDB Atlas (ou use um já existente).
2. Em **Database Access**, crie um usuário de banco (anote o nome de usuário exatamente como aparece  ⚠️ Ele diferencia maiúsculas de minúsculas).
3. Em **Network Access**, libere o seu IP (ou `0.0.0.0/0` para liberar de qualquer lugar, só em ambiente de estudo/desenvolvimento).
4. Em **Database Access**, gere ou copie a senha do usuário.
5. Monte a string de conexão neste formato, substituindo usuário, senha e nome do banco:

   ```
   mongodb+srv://USUARIO:SENHA@SEU_CLUSTER.mongodb.net/NOME_DO_BANCO?appName=Cluster0
   ```


## Configurando a variável de ambiente

No IntelliJ:

1. Na barra superior, clique nas **três bolinhas**.
2. Na seção Configuration, clique em **Edit**.
   <img width="1056" height="220" alt="image" src="https://github.com/user-attachments/assets/d0843b94-ad9c-4faf-884b-6604dc8b618f" />

4. Seleciona a configuração da aplicação principal (`TDSApplication`).
5. Em **Modify options**, habilita **Environment variables**.
   <img width="1332" height="915" alt="image" src="https://github.com/user-attachments/assets/6544360d-a8d4-439c-bf30-b25a89d75772" />

   
7. Adiciona:

   ```
   MONGODB_URI=mongodb+srv://USUARIO:SENHA@SEU_CLUSTER.mongodb.net/NOME_DO_BANCO?appName=Cluster0
   ```

8. Clica em **OK** / **Apply**.

## Rodando o projeto

1. Clona o repositório e abre no IntelliJ.
2. Confirma que o Maven reconheceu o `pom.xml` (painel Maven, lateral direita, deve mostrar o projeto)
   Caso o projeto não seja reconhecido pelo Maven, clica com o botão direito no `pom.xml` → **Add as Maven Project**).
   <img width="500" height="450" alt="image" src="https://github.com/user-attachments/assets/0deea3c3-54b7-4d68-928b-1b7c7fbc061e" />

4. Configura a variável de ambiente `MONGODB_URI` (passo acima).
5. Roda a classe `TDSApplication`.
6. Acessa [http://localhost:8080/cadastro](http://localhost:8080/cadastro) para criar a primeira conta.

## Criando o primeiro Administrador

Todo cadastro feito pelo formulário nasce como Aluno, Professor ou Coordenador (nunca Administrador) e começa **pendente de aprovação**. Como não existe, de início, nenhum Administrador para aprovar ninguém, o primeiro usuário ADMIN precisa ser criado manualmente:

1. Cadastre-se normalmente pelo formulário (qualquer perfil).
2. No MongoDB Atlas, abra o **Data Explorer** → banco de dados do projeto → coleção `users`.
3. Localize o documento do seu usuário e edite dois campos:
   - `roles`: adicione `"ADMIN"` à lista (ex.: `["ALUNO", "ADMIN"]`)
   - `aprovado`: altere para `true`
4. Salve o documento.
   <img width="1400" height="577" alt="image" src="https://github.com/user-attachments/assets/9680ca3a-2d3b-4718-9eae-a2dc0e33c256" />

6. Faça login novamente com esse usuário — ele já deve acessar `/admin`.

A partir daí, esse Administrador pode aprovar Coordenadores pelo painel em `/admin/aprovacoes`, e os Coordenadores aprovados passam a aprovar Alunos e Professores em `/coordenador/aprovacoes`.

## Perfis e rotas

| Perfil | Rota principal | Quem aprova o cadastro |
|---|---|---|
| Aluno | `/` | Coordenador |
| Professor | `/professor` | Coordenador |
| Coordenador | `/coordenador` | Administrador |
| Administrador | `/admin` | Criado manualmente no banco |

## Temas visuais

O tema ativo é definido no `application.yaml`:

```yaml
app:
  theme: classico
```

Valores disponíveis: `classico` (tons de verde-tinta e âmbar) ou `noturno` (tons de lilás). Para trocar o visual de toda a aplicação, basta alterar esse valor e reiniciar o projeto — nenhum código ou template precisa ser modificado.

## Estrutura do projeto

```
src/main/java/br/umc/tds/
├── TDSApplication.java
└── core/
    ├── auth/        → cadastro e login (AuthController, RegisterForm)
    ├── config/      → SecurityConfig, SessionConfig, ThemeAdvice
    ├── home/        → páginas internas e aprovações (HomeController, AdminController, CoordenadorController, ProfessorController)
    └── user/        → domínio (User, Role, UserRepository, UserService, UserDetailsServiceImpl)

src/main/resources/
├── templates/       → telas Thymeleaf
├── static/css/      → folhas de estilo dos temas
└── application.yaml
```

## Gitflow

O projeto segue o fluxo de trabalho gitflow: as funcionalidades são desenvolvidas em branches `feature/*`, integradas à `develop` via pull request, e a `main` recebe apenas versões estáveis.
