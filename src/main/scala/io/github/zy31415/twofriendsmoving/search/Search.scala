package io.github.zy31415.twofriendsmoving.search

import io.github.zy31415.twofriendsmoving.components.World

import scala.collection.mutable


class Search {
  private var _goal:Node = _

  def goal = _goal

  def search(world: World): Boolean = {
    val root = new Node(world.city1, world.city2)

    val discovered = mutable.HashSet[Node](root)
    val frontier = mutable.Stack[Node](root)
    frontier.push(root)

    while (frontier.nonEmpty) {
      val node = frontier.pop()

      if (node.isGoal) {
        _goal = node
        return true
      }
      else {
        for (next <- node.next() if !discovered.contains(next)) {
          frontier.push(next)
          discovered.add(next)
        }
      }
    }
    false
  }
}
