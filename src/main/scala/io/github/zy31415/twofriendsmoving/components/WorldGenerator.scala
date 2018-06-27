package io.github.zy31415.twofriendsmoving.components

import java.io.{File, FileOutputStream, PrintWriter}

import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer
import org.jfree.chart.{ChartFactory, ChartUtilities}
import org.jfree.data.xy.XYSeries
import scalax.chart.api._

import scala.collection.mutable
import scala.util.Random


class WorldGenerator (val numCities: Int, val r: Double){
  var cities: List[City] = _
  var first: City = _
  var last: City = _

  def generate(): Unit = {
    val width = 40
    val height = 20

    cities = List[City]()

    1 to numCities foreach(
      nth => cities = new City(s"city$nth", (Random.nextDouble * width, Random.nextDouble * height)) :: cities
    )

    val pq = mutable.PriorityQueue[City]()(Ordering.by((city:City) => city.pos._1))

    for (city <- cities) {
      pq.enqueue(city)
    }

    first = pq.head

    while(pq.nonEmpty) {
      val city = pq.dequeue()
      last = city

      val checked = mutable.Stack[City]()

      while (pq.nonEmpty && pq.head.pos._1 - city.pos._1 < r) {
        val neighbor = pq.dequeue()
        if (city.distanceTo(neighbor) < r)
          city.neighbors.add(neighbor)
        checked.push(neighbor)
      }

      for(c <- checked)
        pq.enqueue(c)
    }
  }

  def plotCities(): Unit = {

    val dataset = new XYSeriesCollection()
    val series = new XYSeries("X")

    for (c <- cities) {
      series.add(c.pos._1, c.pos._2)
    }
    dataset.addSeries(series)

    // Neighbors:

    for (c <- cities) {
      for (n <- c.neighbors) {
        val s = new XYSeries("%s-%s".format(c.name, n.name))
        s.add(c.pos._1, c.pos._2)
        s.add(n.pos._1, n.pos._2)
        dataset.addSeries(s)
      }
    }

    val chart = ChartFactory.createXYLineChart("Cities", "X", "Y", dataset)
    chart.removeLegend()

    val plot = chart.getPlot.asInstanceOf[XYPlot]
    val renderer = new XYLineAndShapeRenderer()

    renderer.setSeriesLinesVisible(0, false)
    renderer.setSeriesShapesVisible(0, true)

    for (nth <- 2 until dataset.getSeriesCount) {
      renderer.setSeriesLinesVisible(nth, true)
      renderer.setSeriesShapesVisible(nth, false)
    }

    plot.setRenderer(renderer)

    val out = new FileOutputStream("test-world-1.png")
    ChartUtilities.writeChartAsPNG(out, chart, 1000, 500)
  }

  def writeToFile(filename: String): Unit = {

    val pw = new PrintWriter(new File(filename))
    pw.write(s"$numCities, ${first.name}, ${last.name}\n")
    for (city <- cities) {
      pw.write(s"${city.name}, ${city.pos._1}, ${city.pos._2}")
      for (n <- city.neighbors) {
        pw.write(s", ${n.name}")
      }
      pw.write("\n")
    }
    pw.close()
  }

}
