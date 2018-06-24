package io.github.zy31415.search

import java.io.{ByteArrayInputStream, StringReader}

import io.github.zy31415.components.GraphFactory
import org.scalatest.FunSuite

class SearchTest extends FunSuite{

  test("Read Input (in a line)") {
    val cities =
      """3, 0, 2
        |0, 0, 0, 1
        |1, 1, 0, 0, 2
        |2, 2, 0, 1
      """.stripMargin

    val inputStream = new ByteArrayInputStream(cities.getBytes)

    val reader = new StringReader(cities)

    val graph = new GraphFactory().readInput(reader)

    assert(graph.cities.length == 3)

    val city0 = graph.cities(0)
    val city1 = graph.cities(1)
    val city2 = graph.cities(2)

    assert(city0.neighbors.size == 1)
    assert(city0.neighbors.contains(city1))

    assert(city1.neighbors.size == 2)
    assert(city1.neighbors.contains(city0))
    assert(city1.neighbors.contains(city2))

    assert(city2.neighbors.size == 1)
    assert(city2.neighbors.contains(city1))
  }

  test("Read Input (in a line diff dis)") {
    val cities =
      """3, 0, 2
        |0, 0, 0, 1
        |1, 1, 0, 0, 2
        |2, 3, 0, 1
      """.stripMargin

    val inputStream = new ByteArrayInputStream(cities.getBytes)

    val reader = new StringReader(cities)

    val graph = new GraphFactory().readInput(reader)

    assert(graph.cities.length == 3)

    val city0 = graph.cities(0)
    val city1 = graph.cities(1)
    val city2 = graph.cities(2)

    assert(city0.neighbors.size == 1)
    assert(city0.neighbors.contains(city1))

    assert(city1.neighbors.size == 2)
    assert(city1.neighbors.contains(city0))
    assert(city1.neighbors.contains(city2))

    assert(city2.neighbors.size == 1)
    assert(city2.neighbors.contains(city1))

  }
}
