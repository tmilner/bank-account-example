package com.tmilner.bankaccountexample.domain
import com.tmilner.bankaccountexample.domain.BankAccount
import com.tmilner.bankaccountexample.domain.BankAccount.AccountId

object BankAccountDSL {
  object Implicits
      extends Withdraw
      with Deposit
      with CreateAccount
      with CloseAccount
      with RetrieveAccount
      with SaveAccount

  trait Withdraw[F[_]] {
    def withdraw(bankAccount: BankAccount, amount: BigDecimal): F[Either[String, BankAccount]]
  }
  object Withdraw {
    def apply[F[_]](implicit withdraw: Withdraw[F]): Withdraw[F] = withdraw

    implicit class WithdrawOps[F[_]: Withdraw](acc: BankAccount) {
      def withdraw(amount: BigDecimal): F[Either[String, BankAccount]] = Withdraw[F].withdraw(acc, amount)
    }
  }

  trait Deposit[F[_]] {
    def deposit(bankAccount: BankAccount, amount: BigDecimal): F[Either[String, BankAccount]]
  }
  object Deposit {
    def apply[F[_]](implicit deposit: Deposit[F]): Deposit[F] = deposit

    implicit class DepositOps[F[_]: Deposit](acc: BankAccount) {
      def deposit(amount: BigDecimal): F[Either[String, BankAccount]] = Deposit[F].deposit(acc, amount)
    }
  }

  trait CreateAccount[F[_]] {
    def createAccount(initialBalance: BigDecimal): F[Either[String, BankAccount]]
  }
  object CreateAccount {
    def apply[F[_]](implicit createAccount: CreateAccount[F]): CreateAccount[F] = createAccount

    implicit class CreateAccountOps[F[_]: CreateAccount](initialBalance: BigDecimal) {
      def createAccount(initialBalance: BigDecimal): F[Either[String, BankAccount]] =
        CreateAccount[F].createAccount(initialBalance)
    }
  }

  trait CloseAccount[F[_]] {
    def closeAccount(bankAccount: BankAccount): F[Either[String, BankAccount]]
  }
  object CloseAccount {
    def apply[F[_]](implicit closeAccount: CloseAccount[F]): CloseAccount[F] = closeAccount

    implicit class CloseAccountOps[F[_]: CloseAccount](acc: BankAccount) {
      def closeAccount: F[Either[String, BankAccount]] = CloseAccount[F].closeAccount(acc)
    }
  }

  trait RetrieveAccount[F[_]] {
    def retrieveAccount(id: AccountId): F[Either[String, BankAccount]]
  }
  object RetrieveAccount {
    def apply[F[_]](implicit retrieveAccount: RetrieveAccount[F]): RetrieveAccount[F] = retrieveAccount

    implicit class RetrieveAccountOps[F[_]: RetrieveAccount](id: AccountId) {
      val retrieveAccount: F[Either[String, BankAccount]] = RetrieveAccount[F].retrieveAccount(id)
    }
  }

  trait SaveAccount[F[_]] {
    def saveAccount(acc: BankAccount): F[Either[String, BankAccount]]
  }
  object SaveAccount {
    def apply[F[_]](implicit saveAccount: SaveAccount[F]): SaveAccount[F] = saveAccount

    implicit class SaveAccountOps[F[_]: SaveAccount](acc: BankAccount) {
      val save: F[Either[String, BankAccount]] = SaveAccount[F].saveAccount(acc)
    }
  }
}
