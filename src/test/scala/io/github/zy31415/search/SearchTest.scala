package io.github.zy31415.search

import java.io.{ByteArrayInputStream, StringReader}

import io.github.zy31415.components.{City, Node}
import org.scalatest.FunSuite

class SearchTest extends FunSuite{

  test("Read Input") {
    val cities =
      """3, 0, 2
        |0, 0, 0, 1
        |1, 1, 0, 0, 1
        |2, 2, 0, 1
      """.stripMargin

    val inputStream = new ByteArrayInputStream(cities.getBytes)

    val reader = new StringReader(cities)

    City.readInput(reader)

  }
}
