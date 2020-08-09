package com.tmilner.bankaccountexample.domain
import com.tmilner.bankaccountexample.domain.BankAccount
import com.tmilner.bankaccountexample.domain.BankAccount.AccountId

object BankAccountDSL {
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
      val retrieveAccount: F[Either[String, BankAccount]] = RetrieveAccount[F].retrieveAccount(id)
    }

    implicit class SaveAccountOps[F[_]: SaveAccount](acc: BankAccount) {
      val save: F[Either[String, BankAccount]] = SaveAccount[F].saveAccount(acc)
    }
  }

  trait Withdraw[F[_]] {
    def withdraw(bankAccount: BankAccount, amount: BigDecimal): F[Either[String, BankAccount]]
  }
  object Withdraw {
    def apply[F[_]](implicit withdraw: Withdraw[F]): Withdraw[F] = withdraw
  }

  trait Deposit[F[_]] {
    def deposit(bankAccount: BankAccount, amount: BigDecimal): F[Either[String, BankAccount]]
  }
  object Deposit {
    def apply[F[_]](implicit deposit: Deposit[F]): Deposit[F] = deposit
  }

  trait CreateAccount[F[_]] {
    def createAccount(initialBalance: BigDecimal): F[Either[String, BankAccount]]
  }
  object CreateAccount {
    def apply[F[_]](implicit createAccount: CreateAccount[F]): CreateAccount[F] = createAccount
  }

  trait CloseAccount[F[_]] {
    def closeAccount(bankAccount: BankAccount): F[Either[String, BankAccount]]
  }
  object CloseAccount {
    def apply[F[_]](implicit closeAccount: CloseAccount[F]): CloseAccount[F] = closeAccount
  }

  trait RetrieveAccount[F[_]] {
    def retrieveAccount(id: AccountId): F[Either[String, BankAccount]]
  }
  object RetrieveAccount {
    def apply[F[_]](implicit retrieveAccount: RetrieveAccount[F]): RetrieveAccount[F] = retrieveAccount
  }

  trait SaveAccount[F[_]] {
    def saveAccount(acc: BankAccount): F[Either[String, BankAccount]]
  }
  object SaveAccount {
    def apply[F[_]](implicit saveAccount: SaveAccount[F]): SaveAccount[F] = saveAccount
  }
}
