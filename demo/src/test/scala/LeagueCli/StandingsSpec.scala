import LeagueCli.Standings

import zio._
import zio.test._
import zio.test.Assertion._
import zio.nio.file.Path

object StandingsSpec extends ZIOSpecDefault {

  def spec =
    suite("TeamStandingsSpec") {
      test("calculateStandings should return correct standings") {
        // Provide the path to the statically available file
        def testFile = Path("./in/scores.txt")

        // Expected standings for the provided content in the file
        def expectedStandings = Seq(
          (1, "Tarantulas", 6),
          (2, "Lions", 5),
          (3, "FC Awesome", 1),
          (3, "Snakes", 1),
          (5, "Grouches", 0)
        )
        for {
          res <- Standings.calculateStandings(testFile.toString)
        } yield assertTrue(
          res == expectedStandings
        )
      }
    }
}
