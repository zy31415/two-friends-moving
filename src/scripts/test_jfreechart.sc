import scalax.chart.api._

val data = for (i <- 1 to 5) yield (i,i)
val chart = XYLineChart(data)

chart.show()