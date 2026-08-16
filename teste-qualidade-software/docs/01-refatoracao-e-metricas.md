
# 📊 1. Refatoração e Métricas

> Registro completo das análises de Code Smells, métricas obtidas e refatorações aplicadas.

| Nº | Classe | Método / Trecho | Problema Identificado | Métrica Antes | Refatoração Aplicada | Métrica Depois | Commit |
| :---: | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **1** | `SecurityConfig` | `securityFilterChain` | *Duplicate Code* (`csrf` e rotas redundantes), *Long Parameter List* (5 parâmetros), *Violation of SRP* (CORS e Handlers inline) | **LOC:** 85<br>**CC:** 12<br>**Params:** 5<br>**Dup:** 3 | **Extract Method** (CORS Bean e Handlers), **Constructor Injection**, Consolidação de Rotas | **LOC:** 35<br>**CC:** 3<br>**Params:** 2<br>**Dup:** 0 | `a1b2c3d` |
| **2** | | | | | | | |
| **3** | | | | | | | |