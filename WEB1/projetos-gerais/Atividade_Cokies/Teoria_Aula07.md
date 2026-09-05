# Resolução – Questões Teóricas

## 1. Qual das alternativas descreve corretamente a principal função de um cookie?

**Resposta:** c)

**Justificativa:** Cookies armazenam pequenas informações no navegador do usuário para que possam ser reutilizadas em requisições futuras, permitindo a manutenção de preferências e identificação do usuário.

---

## 2. Considere o seguinte cenário: um usuário acessa uma loja virtual, adiciona produtos ao carrinho e navega por diversas páginas antes de finalizar a compra.

**Resposta:** c)

**Justificativa:** A Sessão HTTP é o mecanismo mais adequado para armazenar temporariamente os itens do carrinho, pois os dados permanecem no servidor e podem ser acessados durante toda a navegação do usuário.

---

## 3. Sobre o protocolo HTTP, assinale a alternativa correta.

**Resposta:** c)

**Justificativa:** O HTTP é um protocolo stateless (sem estado), ou seja, cada requisição é tratada de forma independente e o servidor não mantém automaticamente informações sobre requisições anteriores.

---

## 4. Ao criar um cookie em uma Servlet utilizando a API Java, qual método é utilizado para enviar o cookie ao navegador do cliente?

**Resposta:** b)

**Justificativa:** O método `response.addCookie(cookie)` adiciona o cookie à resposta HTTP para que seja armazenado pelo navegador.

---

## 5. Uma plataforma de cursos online deseja lembrar o idioma preferido do usuário mesmo após ele fechar o navegador e retornar dias depois.

**Resposta:** c)

**Justificativa:** Um cookie persistente permite armazenar a preferência do usuário por um período determinado, mantendo a informação mesmo após o encerramento do navegador. Sessões HTTP normalmente expiram quando o usuário permanece inativo ou encerra a sessão.

---

## 6. Analise as afirmações a seguir sobre sessões HTTP:

I. Cada usuário normalmente possui sua própria sessão.

II. Os dados da sessão são armazenados no servidor.

III. A sessão pode ser encerrada automaticamente após um período de inatividade.

**Resposta:** d)

**Justificativa:** Todas as afirmações estão corretas. Cada usuário possui uma sessão própria, seus dados são armazenados no servidor e a sessão pode expirar automaticamente após determinado tempo de inatividade.

---

## 7. Uma empresa desenvolveu um sistema acadêmico onde cada aluno, ao realizar login, recebe um identificador de sessão único.

**Resposta:** c)

**Justificativa:** O identificador de sessão permite que o servidor reconheça o usuário e mantenha seu contexto durante várias requisições, preservando informações como autenticação e permissões.

---

## 8. Quando um navegador realiza uma nova requisição para um servidor, o que normalmente acontece com os cookies previamente armazenados para aquele domínio?

**Resposta:** b)

**Justificativa:** Os cookies válidos são enviados automaticamente junto à requisição, desde que atendam às regras de domínio, caminho e validade definidas em sua criação.

---

## 9. Um desenvolvedor decide armazenar em um cookie informações sensíveis, como CPF, senha e nível de acesso do usuário, em texto puro.

**Resposta:** c)

**Justificativa:** Cookies são armazenados no navegador do usuário e podem ser visualizados ou manipulados. Armazenar dados sensíveis em texto puro representa um risco significativo para a segurança da aplicação.

---

## 10. Gerenciamento de Estado em uma Plataforma de Eventos

Para manter o usuário autenticado durante a navegação entre páginas, o mecanismo mais adequado é a **Sessão HTTP**. A sessão armazena os dados no servidor, permitindo identificar o usuário por meio de um identificador de sessão (normalmente o cookie JSESSIONID). Essa abordagem é mais segura para armazenar informações relacionadas à autenticação.

Para lembrar a preferência de tema (claro ou escuro) mesmo após o usuário fechar o navegador e retornar dias depois, o mecanismo mais adequado é um **cookie persistente**. Como a preferência precisa permanecer disponível por um longo período, o cookie pode ser configurado com um tempo de expiração adequado.

### Diferença entre Cookies e Sessões

* **Cookies:** armazenam dados no navegador do cliente.
* **Sessões HTTP:** armazenam dados no servidor.

### Local de Armazenamento

* Cookies → Cliente (navegador).
* Sessões → Servidor.

### Aplicação dos Requisitos

* Autenticação do usuário → Sessão HTTP.
* Preferência de tema → Cookie persistente.

### Aspectos de Segurança

Para aumentar a segurança da aplicação, recomenda-se:

* Utilizar cookies com atributo **HttpOnly** para impedir acesso via JavaScript.
* Utilizar **Secure** para envio apenas por HTTPS.
* Invalidar a sessão durante o logout.
* Evitar armazenar informações sensíveis diretamente em cookies.
* Utilizar tempo de expiração adequado para sessões e cookies.

Dessa forma, a aplicação mantém a autenticação de forma segura por meio de sessões HTTP e preserva preferências do usuário utilizando cookies persistentes.
