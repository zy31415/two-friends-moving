package io.github.zy31415.twofriendsmoving.search

import io.github.zy31415.twofriendsmoving.components.City

class Node private (val city1: City, val city2: City, val cost: Double){
  def this(city1: City, city2: City) = this(city1, city2, 0.0)

  override def hashCode() = city1.hashCode() + city2.hashCode()

  override def equals(other: scala.Any): Boolean = other match {
    case other: Node =>
      if (other.city1 == city1)
        other.city2 == city2
      else if (other.city1 == city2)
        other.city2 == city1
      else
        false
    case _ => false
  }

  def isGoal = city1 == city2

  def next():List[Node] = {
    var nodes = List[Node]()
    for (c1 <- city1.neighbors) {
      val dis1 = city1.distanceTo(c1)
      for (c2 <- city2.neighbors) {
        val dis2 = city2.distanceTo(c2)
        nodes = new Node(c1, c2, Math.max(dis1, dis2)) :: nodes
      }
    }
    nodes
  }
}
