import scala.collection.immutable.HashSet


class Node(val name: String, val neighbors: HashSet[Node])

class Element [T] (val value: T, p : => Element[T], n : => Element [T]) {
  lazy val prev = p
  lazy val next = n
}


val e1:Element[Int] = new Element [Int] (1,null, e2)
val e2:Element[Int] = new Element [Int] (2,e1, e3)
val e3:Element[Int] = new Element [Int] (3,e2, e4)
val e4:Element[Int] = new Element [Int] (4, e3,null)
