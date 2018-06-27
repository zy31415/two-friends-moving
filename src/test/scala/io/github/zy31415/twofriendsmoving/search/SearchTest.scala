package io.github.zy31415.twofriendsmoving.search

import java.io.{File, StringReader}
import scala.io.Source
import io.github.zy31415.twofriendsmoving.components.WorldFactory
import org.scalatest.FunSuite

class SearchTest extends FunSuite {
  test("search 1") {
    val cities =
      """3, 0, 2
        |0, 0, 0, 1
        |1, 1, 0, 0, 2
        |2, 2, 0, 1
      """.stripMargin

    val world = new WorldFactory().readInput(
      new StringReader(cities)
    )

    val search = new Search()
    assert(search.search(world))
    assert(search.goal.city1.name == "1")
    assert(search.goal.city2.name == "1")
    assert(search.goal.cost == 1.0)
  }

  test("search 2") {
    val cities =
      """3, 0, 2
        |0, 0, 0, 1
        |1, 1, 0, 0, 2
        |2, 3, 0, 1
      """.stripMargin

    val world = new WorldFactory().readInput(
      new StringReader(cities)
    )

    val search = new Search()
    assert(search.search(world))
    assert(search.goal.city1.name == "1")
    assert(search.goal.city2.name == "1")
    assert(search.goal.cost == 2.0)
  }

  test("search - no solution") {
    val cities =
      """4, 0, 3
        |0, 0, 0, 1
        |1, 1, 0, 0, 2
        |2, 3, 0, 1, 3
        |3, 4, 0, 2
      """.stripMargin

    val world = new WorldFactory().readInput(
      new StringReader(cities)
    )

    val search = new Search()
    assert(!search.search(world))
    assert(search.goal == null)
  }

  test("search - generated") {
    val cities = Source.fromResource("test-world-1.txt").getLines().mkString("\n").stripMargin

    val world = new WorldFactory().readInput(
      new StringReader(cities)
    )

    val search = new Search()
    assert(!search.search(world))
    assert(search.goal == null)
  }
}
