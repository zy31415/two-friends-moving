package io.github.zy31415.components

import java.io.{BufferedReader, Reader}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class City(val name: String, val pos: (Double, Double)){
  val neighbors = mutable.HashMap[Int, Double]()
}

object City {
  var cities = new ArrayBuffer[City]()

  def readInput(input: Reader): Unit = {
    val reader = new BufferedReader(input)

    var line = reader.readLine()

    // TODO: parse strings to generate cities.
    while (line != null) {
      val words = line.trim.split(",")
      if (words.size > 1) {
        val name = words(0);
        val pos = (words(1).toDouble, words(2).toDouble)
        val city = new City(name, pos)
        cities.append(city)
      }
      line = reader.readLine()
    }
  }
}
