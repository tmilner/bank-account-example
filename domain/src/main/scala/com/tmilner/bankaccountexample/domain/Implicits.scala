package com.tmilner.bankaccountexample.domain

import com.tmilner.bankaccountexample.domain.BankAccountDSL._
import com.tmilner.bankaccountexample.domain.BankAccount.AccountId

object Implicits {
  implicit class WithdrawOps[F[_]: Withdraw](acc: BankAccount) {
    def withdraw(amount: BigDecimal): F[Either[String, BankAccount]] = Withdraw[F].withdraw(acc, amount)
  }

  implicit class DepositOps[F[_]: Deposit](acc: BankAccount) {
    def deposit(amount: BigDecimal): F[Either[String, BankAccount]] = Deposit[F].deposit(acc, amount)
  }

  implicit class CreateAccountOps[F[_]: CreateAccount](initialBalance: BigDecimal) {
    val createAccount: F[Either[String, BankAccount]] =
      CreateAccount[F].createAccount(initialBalance)
  }

  implicit class CloseAccountOps[F[_]: CloseAccount](acc: BankAccount) {
    def closeAccount: F[Either[String, BankAccount]] = CloseAccount[F].closeAccount(acc)
  }

  implicit class RetrieveAccountOps[F[_]: RetrieveAccount](id: AccountId) {
    val retrieveAccount: F[Either[String, BankAccount]] = RetrieveAccount[F].retrieve(id)
  }

  implicit class SaveAccountOps[F[_]: SaveAccount](acc: BankAccount) {
    val save: F[Either[String, BankAccount]] = SaveAccount[F].save(acc)
  }
}
