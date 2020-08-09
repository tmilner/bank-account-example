package com.tmilner.bankaccountexample.application

import com.tmilner.bankaccountexample.domain.BankAccount
import com.tmilner.bankaccountexample.domain.BankAccount.AccountId
import com.tmilner.bankaccountexample.domain.BankAccountDSL._
import com.tmilner.bankaccountexample.domain.Implicits._
import cats.implicits._
import cats.Monad

class Application {
  def withdraw[F[_]: RetrieveAccount: SaveAccount: Withdraw: Monad](
    id: AccountId,
    amount: BigDecimal
  ): F[Either[String, BankAccount]] = {
    for {
      account <- id.retrieveAccount
      withdrawnAccount <- account.flatTraverse[F, BankAccount](_.withdraw(amount))
      savedAccount <- withdrawnAccount.flatTraverse(_.save)
    } yield savedAccount
  }

  def createAccount[F[_]: SaveAccount: CreateAccount: Monad](
    initialAmount: BigDecimal
  ): F[Either[String, BankAccount]] =
    for {
      account <- initialAmount.createAccount
      savedAccount <- account.flatTraverse(_.save)
    } yield savedAccount
}
