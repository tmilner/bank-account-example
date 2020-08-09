package com.tmilner.bankaccountexample.domain
import com.tmilner.bankaccountexample.domain.BankAccount
import com.tmilner.bankaccountexample.domain.BankAccount._
import io.chrisdavenport.fuuid.FUUID

import cats.effect.Sync
import cats.implicits._

object BankAccountDSL {
  trait Withdraw[F[_]] {
    def withdraw(bankAccount: BankAccount, amount: BigDecimal): F[Either[String, BankAccount]]
  }
  object Withdraw {
    def apply[F[_]](implicit withdraw: Withdraw[F]): Withdraw[F] = withdraw

    implicit def interpreter[F[_]: Sync] = new Withdraw[F] {
      override def withdraw(bankAccount: BankAccount, amount: BigDecimal): F[Either[String, BankAccount]] =
        F.delay(
          Either.cond(
            amount > 0,
            bankAccount.copy(balance = bankAccount.balance - amount),
            "Cannot withdraw an amount that is less than 1"
          )
        )
    }
  }

  trait Deposit[F[_]] {
    def deposit(bankAccount: BankAccount, amount: BigDecimal): F[Either[String, BankAccount]]
  }
  object Deposit {
    def apply[F[_]](implicit deposit: Deposit[F]): Deposit[F] = deposit
    implicit def interpreter[F[_]: Sync] = new Deposit[F] {
      override def deposit(bankAccount: BankAccount, amount: BigDecimal): F[Either[String, BankAccount]] =
        F.delay(
          Either.cond(
            amount > 0,
            bankAccount.copy(balance = bankAccount.balance + amount),
            "Cannot deposit an amount that is less than 1"
          )
        )
    }
  }

  trait CreateAccount[F[_]] {
    def createAccount(initialBalance: BigDecimal): F[Either[String, BankAccount]]
  }
  object CreateAccount {
    def apply[F[_]](implicit createAccount: CreateAccount[F]): CreateAccount[F] = createAccount
    implicit def interpreter[F[_]: Sync] = new CreateAccount[F] {
      override def createAccount(initialBalance: BigDecimal): F[Either[String, BankAccount]] =
        FUUID
          .randomFUUID[F]
          .map(
            uuid =>
              Either.cond(
                initialBalance > 0,
                BankAccount(AccountId(uuid), initialBalance, AccountStatus.Open),
                "Cannot open an account with an amount that is less than 1"
              )
          )
    }
  }

  trait CloseAccount[F[_]] {
    def closeAccount(bankAccount: BankAccount): F[Either[String, BankAccount]]
  }
  object CloseAccount {
    def apply[F[_]](implicit closeAccount: CloseAccount[F]): CloseAccount[F] = closeAccount
    implicit def interpreter[F[_]: Sync] = new CloseAccount[F] {
      override def closeAccount(bankAccount: BankAccount): F[Either[String, BankAccount]] =
        F.delay(
          Either.cond(
            bankAccount.balance > 0,
            bankAccount.copy(status = AccountStatus.Closed),
            "Cannot close an account when the balance is less than 0"
          )
        )
    }
  }

  trait RetrieveAccount[F[_]] {
    def retrieve(id: AccountId): F[Either[String, BankAccount]]
  }
  object RetrieveAccount {
    def apply[F[_]](implicit retrieveAccount: RetrieveAccount[F]): RetrieveAccount[F] = retrieveAccount
  }

  trait SaveAccount[F[_]] {
    def save(acc: BankAccount): F[Either[String, BankAccount]]
  }
  object SaveAccount {
    def apply[F[_]](implicit saveAccount: SaveAccount[F]): SaveAccount[F] = saveAccount
  }
}
