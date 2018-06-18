package io.github.zy31415.components

import java.io.{BufferedReader, Reader}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class GraphFactory {
  var numCities:Int = _
  var loc1: Int = _
  var loc2: Int = _

  val cities: ArrayBuffer[City] = new ArrayBuffer()
  val counter = new mutable.HashMap[String, City]()
  val neighborNamesList = new ArrayBuffer[ArrayBuffer[String]]

  def readInput(input: Reader): Graph = {
    createCites(input)
    makeNeighborConnections()
    new Graph(cities.toArray, loc1, loc2)
  }

  private def createCites(input: Reader): Unit = {
    val reader = new BufferedReader(input)
    val line = reader.readLine()

    val words = line.trim.split(",")
    numCities = words(0).toInt
    val city1Name = words(1).trim
    val city2Name = words(2).trim

    for (nth <- 0 until numCities) {
      val line = reader.readLine()
      val words = line.trim.split(",")
      if (words.size >= 3) {
        val name = words(0).trim

        if (city1Name == name) loc1 = nth
        if (city2Name == name) loc2 = nth

        val pos = (words(1).toDouble, words(2).toDouble)
        val city = new City(name)
        counter += (city.name -> city)
        // set position and add to the city array
        city.pos = pos
        cities.append(city)

        neighborNamesList.append(ArrayBuffer())

        // add neighbours to make connections
        var i = 3
        while (i < words.size) {
          neighborNamesList.last.append(words(i).trim)
          i += 1
        }
      } else if (words.size > 1) {
        throw new IllegalArgumentException("Input file has wrong format.")
      }
    }
  }

  private def makeNeighborConnections(): Unit = {
    // setup neighbour
    var i = 0
    for (neighborNames <- neighborNamesList) {

      val city = cities(i)

      for (neighborName <- neighborNames) {
        val neighbor = counter(neighborName)
        city.neighbors += (neighbor -> city.distance(neighbor))
      }
      i += 1
    }
  }
}
