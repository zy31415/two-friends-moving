package io.github.zy31415.components

import scala.collection.immutable.HashSet

class City(val name: String, val pos: (Double, Double), n: => HashSet[City]){

  lazy val neighbors = n

  override def hashCode() = name.hashCode

  override def equals(other : Any) : Boolean = other match {
    case that : City => this.name == that.name
    case _ => false
  }

  def distanceTo(city: City): Double = {
    val d1 = pos._1 - city.pos._1
    val d2 = pos._2 - city.pos._2
    math.sqrt(d1*d1 + d2 * d2)
  }
}
