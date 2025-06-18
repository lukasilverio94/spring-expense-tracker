# Diagrama Entidade Relacionamento (Inicial)
![image](https://github.com/user-attachments/assets/cc084dc7-5f5c-4ca1-8897-5c589065b402)


# Casos de uso:

## 1. Cadastro de Usuário
**Descrição**: Permite que novos usuários criem uma conta.

**Atores**: Usuário

**Fluxo Principal:**
- Usuário fornece nome, email e senha.
- Backend valida e salva no banco (users).
- Resultado esperado: Conta criada com sucesso.

## 2.Login / Autenticação
**Descrição:** Usuário faz login com email e senha para acessar o sistema.

**Atores**: Usuário

**Fluxo Principal:**
- Verifica email/senha.
- Gera JWT após autenticação.
- Resultado esperado: Token JWT válido retornado. 

## 3. Criar categoria
**Descrição**: Usuário pode organizar seus gastos por categoria (ex: "Alimentação", "Transporte").

**Atores:** Usuário autenticado

**Fluxo Principal:**
Envia nome da categoria.

Categoria é salva com o user_id.

**Validações:**
- Nome único por usuário

**Resultado esperado:** Categoria adicionada.

## 4. Registrar Despesa
**Descrição**: Usuário adiciona uma nova despesa.

**Atores**: Usuário autenticado

**Fluxo Principal:**

Informa valor, categoria (opcional), descrição, data.

É salva com user_id, category_id, amount, etc.

**Validações:**

Valor maior do que zero

**Resultado esperado:** Despesa registrada.


## 5. Listar Despesas
**Descrição**: Mostra histórico de gastos do usuário.

**Atores**: Usuário autenticado

**Fluxo Principal:**

Busca todas as despesas de um user_id

Pode ser filtrado por data, categoria, valor mínimo/máximo

**Resultado esperado**: Lista de despesas.


## 6. Editar Despesa
**Descrição:** Usuário atualiza uma despesa já registrada.

**Atores:** Usuário autenticado

**Fluxo Principal:**

Verifica se a despesa pertence ao usuário

Permite editar valor, descrição, data ou categoria

**Resultado esperado**: Despesa atualizada.

## 7. Deletar Despesa
**Descrição:** Remove uma despesa do histórico.

**Atores:** Usuário autenticado

**Fluxo Principal:**

Verifica se pertence ao user_id
(se sim) : 
Remove do banco

**Resultado esperado:** Despesa excluída.

## 8. Relatórios e Resumo Financeiro
**Descrição:** Exibe estatísticas mensais ou semanais de gastos.

**Atores:** Usuário autenticado

**Fluxo Principal:**

Agrega despesas por mês/categoria

Exibe totais, médias, gráficos

**Resultado esperado**: Dashboard informativo.


## 9. Filtrar Despesas por Categoria / Data
**Descrição**: Ajuda o usuário a encontrar gastos específicos.

**Atores**: Usuário autenticado

**Fluxo Principal:**

Informa filtros (ex: category_id, expense_date)

Backend retorna despesas filtradas

**Resultado esperado:** Lista refinada.


## 10. Deletar Conta
**Descrição**: Usuário remove permanentemente sua conta e dados.

**Atores:** Usuário autenticado

**Fluxo Principal:**

Confirma exclusão

`ON DELETE CASCADE` remove categorias e despesas associadas

**Resultado esperado:** Conta e dados excluídos.
