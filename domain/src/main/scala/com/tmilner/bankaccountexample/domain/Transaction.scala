package com.tmilner.bankaccountexample.domain

import com.tmilner.bankaccountexample.domain.BankAccount.AccountId

final case class Transaction(
  amount: BigDecimal,
  from: AccountId,
  to: AccountId
)
