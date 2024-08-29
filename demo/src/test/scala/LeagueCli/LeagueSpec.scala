package LeagueCli

import zio._
import zio.test.{ test, _ }

object LeagueSpec extends ZIOSpecDefault {
  val spec: Spec[Any, Nothing] = suite("LeagueRankSpec")(
    test("rank correctly parse arguments") {
      val exit: Unit = League.cliApp.run(List("rank", "--games", "./in/scores.txt", "--standings", "./out/standings.txt"))
      assertTrue(exit.is(_.anything))
    }
  )
}