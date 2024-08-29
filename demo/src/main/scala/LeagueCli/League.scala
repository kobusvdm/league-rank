package LeagueCli

import zio._
import zio.cli.*
import zio.cli.HelpDoc.Span.text
import zio.cli.Exists.{No, Yes}
import zio.{Scope, ZIOAppArgs}

import java.nio.file.Path

object League extends ZIOCliDefault {
  sealed trait Subs extends Product with Serializable
  object Subs {
    final case class Rank(games: Path, standings: Path) extends Subs
    final case class Print(games: Path) extends Subs
  }

  // Rank command
  private val gamesPath: Options[Path] = Options.file("games", exists = Yes)
  private val standingsRankPath: Options[Path] = Options.file("standings")
  private val help: HelpDoc = HelpDoc.p("Calculate league standings from game results")
  private val rank = Command("rank", gamesPath ++ standingsRankPath)
    .withHelp(help)

  // Print command
  private val gamesPrintPath: Options[Path] = Options.file("games")
  private val print = Command("print", gamesPrintPath)
    .withHelp("Print the calculated standings")

  // Main command
  private val league: Command[Any] = Command("league")
    .withHelp("Process league standings and game results")
    .subcommands(rank, print)

  // Main entrypoint
  val cliApp: CliApp[Any & ZIOAppArgs & Scope, Any, Any] = CliApp.make(
    name = "League Rank",
    version = "1.1.0",
    summary = text("Calculate game league standings"),
    command = league
  ) {
    case (games: Path, standings: Path) => for {
      res <- Standings.calculateStandings(games.toString)
      _ <- Standings.writeStandingsToFile(res, standings.toString)
      _ <- Standings.printStandings(standings.toString)
      _ <- Console.printLine(s"Standings written to $standings")
    } yield ()
    case (games: Path) => for {
      _ <- Standings.printStandings(games.toString)
    } yield ()
  }
}