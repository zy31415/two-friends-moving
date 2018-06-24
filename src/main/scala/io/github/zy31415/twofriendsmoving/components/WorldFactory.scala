package io.github.zy31415.twofriendsmoving.components

import java.io.{BufferedReader, Reader}

import scala.collection.immutable
import scala.collection.mutable.ArrayBuffer

class WorldFactory {
  var numCities:Int = _
  var loc1: Int = _
  var loc2: Int = _

  val cities: ArrayBuffer[City] = new ArrayBuffer()
  var nameToCityMap = immutable.HashMap[String, City]()

  def readInput(input: Reader): World = {
    createCites(input)
    new World(cities.toArray, loc1, loc2)
  }

  private def createCites(input: Reader): Unit = {
    val reader = new BufferedReader(input)
    val line = reader.readLine()

    val (_n, city1Name, city2Name) = parseHeader(line)
    numCities = _n

    for (nth <- 0 until numCities) {
      val line = reader.readLine()
      val words = splitLine(line)

      require(words.length >= 3, "Input file format is wrong.")

      val name = words(0)

      if (city1Name == name) loc1 = nth
      if (city2Name == name) loc2 = nth

      val pos = (words(1).toDouble, words(2).toDouble)

      val city = new City(name, pos, getNeighbors(words.drop(3)))

      nameToCityMap += (city.name -> city)
      cities.append(city)
    }
  }

  private def parseHeader(header: String): (Int, String, String) = {
    val words = splitLine(header)
    val numCities = words(0).toInt
    val loc1Name = words(1)
    val loc2Name = words(2)
    (numCities, loc1Name, loc2Name)
  }

  private def getNeighbors(neighborNames: Array[String]): immutable.HashSet[City] = {
    var neighbors = new immutable.HashSet[City]()

    neighborNames.foreach(
      neighbors += nameToCityMap(_)
    )

    neighbors
  }

  private def splitLine(line:String) = line.trim.split(",").map(x=>x.trim)
}
