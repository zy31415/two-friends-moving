package io.github.zy31415.twofriendsmoving.components

import org.scalatest.FunSuite

class WorldGeneratorTest extends FunSuite {

  test("Test world generator") {
    val generator = new WorldGenerator(30, 10.0)
    generator.generate()
    generator.plotCities()
    generator.writeToFile("test-world-1.txt")
  }

}
