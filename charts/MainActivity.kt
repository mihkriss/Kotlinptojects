package com.example.grafic
import android.os.Bundle
import co.yml.charts.common.model.Point
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import android.util.Log
import co.yml.charts.axis.AxisData
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.example.grafic.ui.theme.GraficTheme
import kotlin.random.Random

// 2. Настройка оси Х и У
const val steps = 10
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val pointsList = getPointsList()
            val max = getMax(pointsList)
            val min = getMin(pointsList)
            Log.d("MyLog", "Max: $max Min: $min")
            val xAxisData = AxisData.Builder()
                .axisStepSize(100.dp)
                .backgroundColor(Color.Transparent)
                .steps(pointsList.size - 1)
                .labelData { i -> i.toString() + "day" }
                .labelAndAxisLinePadding(15.dp)
                .build()

            val yAxisData = AxisData.Builder()
                .steps(steps)
                .backgroundColor(Color.Transparent)
                .labelAndAxisLinePadding(20.dp)
                .labelData { i ->
                    val yScale = (max - min) / steps.toFloat()
                    String.format("%.1f", ((i * yScale) + min))
                }.build()

            GraficTheme {
                // 3. Cписок из линий
                val lineChartData = LineChartData(
                    linePlotData = LinePlotData(
                        lines = listOf(
                            Line(
                                dataPoints = pointsList,
                                LineStyle(color = Color.DarkGray, width = 3.0f),
                                IntersectionPoint(Color.Gray, radius = 3.dp),
                                SelectionHighlightPoint(color = Color.Black ),

                                ShadowUnderLine(),
                                SelectionHighlightPopUp()
                            )
                        ),
                    ),
                    xAxisData = xAxisData,
                    yAxisData = yAxisData,
                    gridLines = GridLines(),
                    backgroundColor = Color.White
                )

                //  Создания линии
                LineChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    lineChartData = lineChartData
                )

            }
        }

    }

    //1. Содать список данных
    fun getPointsList(): List<Point> {
        val list = ArrayList<Point>()
        for (i in 0..31) {
            list.add(
                Point(
                    i.toFloat(),
                    Random.nextInt(50, 90).toFloat()

                )
            )
        }
        return list
    }

    // Вычисление самого максимального значения
    private fun getMax(list: List<Point>) : Float{
        var max = 0F
        list.forEach { point ->
            if(max < point.y) max = point.y
        }
        return max
    }

    // Вычисление самого минимального значения
    private fun getMin(list: List<Point>) : Float{
        var min = 100F
        list.forEach { point ->
            if(min > point.y) min = point.y
        }
        return min
    }
}


