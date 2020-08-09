package com.tmilner.bankaccountexample.domain
import io.chrisdavenport.fuuid.FUUID
import com.tmilner.bankaccountexample.domain.BankAccount.{AccountId, AccountStatus}

final case class BankAccount(
  id: AccountId,
  balance: BigDecimal,
  status: AccountStatus
)

object BankAccount {
  final case class AccountId(value: FUUID) extends AnyVal

  trait AccountStatus
  object AccountStatus {
    case object Open extends AccountStatus
    case object Closed extends AccountStatus
  }
}
