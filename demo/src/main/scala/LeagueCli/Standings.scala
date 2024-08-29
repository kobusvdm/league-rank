package LeagueCli

import java.io.File

import zio._
import zio.nio.file._
import zio.stream._
import zio.Console.printLine

object Standings {

  private val resultPattern = """(.*) (\d+), (.*) (\d+)""".r

  def calculateStandings(filename: String): ZIO[Any, Throwable, Seq[(Int, String, Int)]] = {
    for {
      lines <- readFileLines(filename)
      standingsMap <- ZIO.succeed {
        lines.foldLeft(Map[String, Int]().withDefaultValue(0)) { (standings, line) =>
          line match {
            case resultPattern(teamA, scoreA, teamB, scoreB) =>
              val teamAScore = scoreA.toInt
              val teamBScore = scoreB.toInt

              val updatedStandings = if (teamAScore > teamBScore) {
                standings.updated(teamA, standings(teamA) + 3)
                  .updated(teamB, standings(teamB)) // Looser gets 0 points, no change
              } else if (teamBScore > teamAScore) {
                standings.updated(teamB, standings(teamB) + 3)
                  .updated(teamA, standings(teamA)) // Looser gets 0 points, no change
              } else {
                standings
                  .updated(teamA, standings(teamA) + 0)
                  .updated(teamB, standings(teamB) + 0)
                  .updated(teamA, standings(teamA) + 1)
                  .updated(teamB, standings(teamB) + 1)
              }
              updatedStandings

            case _ => standings // Skip lines that do not match the pattern
          }
        }
      }

      // Prepare list of standings with positions
      standingsList = standingsMap.toSeq.sortBy(-_._2)
      rankedStandings = standingsList.zipWithIndex.foldLeft(List.empty[(Int, String, Int)]) {
        case (acc, ((team, points), index)) =>
          val position = if (index == 0 || standingsList(index)._2 != standingsList(index - 1)._2) {
            index + 1
          } else {
            acc.head._1 // Same rank as previous team
          }
          (position, team, points) :: acc
      }.reverse

    } yield rankedStandings
  }

  def printStandings(filename: String): ZIO[Any, Throwable, Unit] = {
    for {
      standings <- calculateStandings(filename)
      _ <- ZIO.foreachDiscard(standings) { case (position, team, points) =>
        printLine(s"$position. $team, $points pts")
      }
    } yield ()
  }

  private def readFileLines(filename: String): ZIO[Any, Throwable, List[String]] = {
    ZStream
      .fromFile(File(filename))
      .via(ZPipeline.utf8Decode)
      .via(ZPipeline.splitLines)
      .runCollect
      .map(_.toList)
  }


  def writeStandingsToFile(standings: Seq[(Int, String, Int)], outputFilename: String): ZIO[Any, Throwable, Unit] = {
    val content = standings.map { case (position, team, points) =>
      s"$position. $team, $points pts"
    }

    val path = Path(outputFilename)
    Files.writeLines(path, content)
      .map(_ => ())
  }
}
