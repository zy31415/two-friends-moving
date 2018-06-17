package io.github.zy31415.components

import java.io.{BufferedReader, Reader}

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

class City(val name: String){
  var pos: (Double, Double) = _
  val neighbors = mutable.HashMap[City, Double]()

  override def hashCode() = name.hashCode

  override def equals(other : Any) : Boolean = other match {
    case that : City => this.name == that.name
    case _ => false
  }

  def addNeighbor(neighbor: City): Unit =
    if (!neighbors.contains(neighbor)) {
      neighbors += (neighbor -> distance(neighbor))
    }

  def distance(city: City): Double = {
    val d1 = pos._1 - city.pos._1
    val d2 = pos._2 - city.pos._2
    math.sqrt(d1*d1 + d2 * d2)
  }
}

object City {
  var cities = new ArrayBuffer[City]()

  // TODO: Refactor this code
  def readInput(input: Reader): Unit = {
    val (counter, neighborNamesList) = createCites(input)
    makeNeighborConnections(counter, neighborNamesList)
  }

  private def createCites(input: Reader): (mutable.HashMap[String, City], ArrayBuffer[ArrayBuffer[String]]) = {
    var counter = new mutable.HashMap[String, City]()

    val reader = new BufferedReader(input)
    var line = reader.readLine()

    val neighborNamesList: ArrayBuffer[ArrayBuffer[String]] = ArrayBuffer()

    while (line != null) {
      val words = line.trim.split(",")
      if (words.size >= 3) {
        val name = words(0).trim
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
      line = reader.readLine()
    }
    (counter, neighborNamesList)
  }

  private def makeNeighborConnections(counter: mutable.HashMap[String, City],
                                      neighborNamesList: ArrayBuffer[ArrayBuffer[String]]): Unit = {
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
