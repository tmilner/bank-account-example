package com.tmilner.bankaccountexample.domain

import com.tmilner.bankaccountexample.domain.BankAccount.{AccountId, AccountStatus}

final case class BankAccount(
  id: AccountId,
  balance: BigDecimal,
  status: AccountStatus
)

object BankAccount {
  final case class AccountId(value: Int) extends AnyVal

  trait AccountStatus
  object AccountStatus {
    case object Open extends AccountStatus
    case object Closed extends AccountStatus
  }
}
