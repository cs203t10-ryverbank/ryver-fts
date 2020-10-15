# Ryver Fund Transfer System API

The Ryver Fund Transfer System API handles account and transactions management for RyverBank.

## Endpoints

/accounts
ROLE_USER: view account information for all accounts
ROLE_MANAGER: create new account for customer

/accounts/{accountId}
ROLE_USER: view account information for specific account

/accounts/transactions
ROLE_USER: create new transaction between own account and other accounts

/accounts/{accountId}/addBalance?amount={amount}
ROLE_TRADE_SERVICE: add to balance of specific account

/accounts/{accountId}/addAvailableBalance?amount={amount}
ROLE_TRADE_SERVICE: add to available balance of specific account

/accounts/{accountId}/deductBalance?amount={amount}
ROLE_TRADE_SERVICE: deduct from balance of specific account

/accounts/{accountId}/deductAvailableBalance?amount={amount}
ROLE_TRADE_SERVICE: deduct from available balance of specific account

## Response codes

200 OK: add/deduct successful
400 Bad Request: Account has insufficient balance
404 Not Found: Account does not exist