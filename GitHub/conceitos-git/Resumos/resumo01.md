# 📚 Aprendendo alguns conceitos no Git

## 📑 Índice

-   🔄 [Navegando entre versões do
    código](#navegando-entre-versões-do-código)
-   🌿 [Trabalhando com Branches](#trabalhando-com-branches)
-   🚀 [Levando alterações para a branch
    principal](#levando-alterações-para-a-branch-principal)

------------------------------------------------------------------------

# 🔄 Navegando entre versões do código

Navegar entre versões do projeto é uma das funcionalidades mais úteis do
Git. Cada alteração salva gera um **commit**, permitindo retornar a
qualquer versão anterior do código.

> ⚠️ **Atenção:** alguns comandos, como `git reset --hard`, descartam
> alterações que ainda não foram salvas em um commit. Utilize-os com
> cuidado.

  -----------------------------------------------------------------------------
  💻 Comando                          📖 Significado
  ----------------------------------- -----------------------------------------
  `git log`                           Exibe o histórico de commits do projeto.
                                      Cada commit possui um identificador
                                      (Hash/ID).

  `git reset --hard <ID_DO_COMMIT>`   Retorna o projeto exatamente para o
                                      commit informado, descartando alterações
                                      posteriores que não forem recuperáveis.
  -----------------------------------------------------------------------------

✅ Com esses comandos, é possível voltar para qualquer versão já
registrada no histórico do projeto.

------------------------------------------------------------------------

# 🌿 Trabalhando com Branches

Uma **branch** é uma linha de desenvolvimento independente. Ela permite
desenvolver novas funcionalidades, corrigir erros ou realizar testes sem
modificar diretamente a branch principal (`main` ou `master`).

👥 Esse fluxo é muito utilizado em equipes, pois cada desenvolvedor pode
trabalhar em sua própria branch antes de integrar suas alterações ao
projeto principal.

  -----------------------------------------------------------------------
  💻 Comando                    📖 Significado
  ----------------------------- -----------------------------------------
  `git branch`                  Lista todas as branches existentes no
                                repositório.

  `git branch teste`            Cria uma nova branch chamada `teste`.

  `git checkout teste`          Alterna para a branch `teste`, tornando-a
                                sua área de trabalho atual.
  -----------------------------------------------------------------------

💡 Atualmente também é comum utilizar:

``` bash
git switch teste
```

------------------------------------------------------------------------

# 🚀 Levando alterações para a branch principal

📥 Antes de realizar o merge, atualize sua branch principal:

``` bash
git pull
```

  -----------------------------------------------------------------------
  💻 Comando                    📖 Significado
  ----------------------------- -----------------------------------------
  `git merge teste`             Mescla as alterações da branch `teste` na
                                branch atual.

  `git push`                    Envia as alterações da branch local para
                                o repositório remoto.
  -----------------------------------------------------------------------

## 📋 Fluxo completo

``` bash
git checkout main
git pull
git merge teste
git push
```

## 💡 Resumo

-   📜 `git log` → Visualiza o histórico de commits.
-   ⏪ `git reset --hard` → Retorna para um commit específico.
-   🌿 `git branch` → Lista ou cria branches.
-   🔄 `git checkout` / `git switch` → Troca de branch.
-   🤝 `git merge` → Une duas branches.
-   📥 `git pull` → Baixa e integra alterações do remoto.
-   📤 `git push` → Envia alterações para o remoto.
