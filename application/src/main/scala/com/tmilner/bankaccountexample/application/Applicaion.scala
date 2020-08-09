package com.tmilner.bankaccountexample.application

import com.tmilner.bankaccountexample.domain.BankAccount
import com.tmilner.bankaccountexample.domain.BankAccount.AccountId
import com.tmilner.bankaccountexample.domain.BankAccountDSL._
import com.tmilner.bankaccountexample.domain.BankAccountDSL.Implicits._
import cats.implicits._
import cats.Monad

class Application[F[_]: RetrieveAccount: SaveAccount: Withdraw: Monad] {
  def withdraw(id: AccountId, amount: BigDecimal): F[Either[String, BankAccount]] =
    for {
      account <- id.retrieveAccount
      withdrawnAccount <- account.flatTraverse[F, BankAccount](_.withdraw(amount))
      savedAccount <- withdrawnAccount.flatTraverse(t => t.save)
    } yield savedAccount
}
