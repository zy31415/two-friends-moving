package io.github.zy31415.twofriendsmoving.components

class World(val cities: Array[City], val loc1: Int, val loc2: Int) {
  val numCities = cities.length
  def city1 = cities(loc1)
  def city2 = cities(loc2)
}
