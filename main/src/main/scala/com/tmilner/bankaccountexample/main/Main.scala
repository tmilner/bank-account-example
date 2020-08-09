package example

import com.tmilner.bankaccountexample.application.Application
import com.tmilner.bankaccountexample.domain.BankAccountDSL.SaveAccount
import com.tmilner.bankaccountexample.domain.BankAccountDSL.RetrieveAccount
import cats.effect.{IO, Sync}
import com.tmilner.bankaccountexample.domain.BankAccount

object Main extends App {
  val app = new Application

  implicit def repo[F[_]: Sync] = new SaveAccount[F] with RetrieveAccount[F] {
    def save(acc: BankAccount): F[Either[String, BankAccount]] = F.delay(Right(acc))
    def retrieve(id: BankAccount.AccountId): F[Either[String, BankAccount]] = ???
  }

  val t = app.createAccount[IO](20.00).unsafeRunSync()

  println(s"RES: $t")
}
