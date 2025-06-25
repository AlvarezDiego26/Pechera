package com.example.pecherainteligenteapp.ui.theme.components

import android.graphics.Color
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*

@Composable
fun ECGChart(ecgValue: Int) {
    val entries = remember { mutableStateListOf<Entry>() }
    var xValue by remember { mutableStateOf(0f) }

    var chart: LineChart? by remember { mutableStateOf(null) }
    var dataSet: LineDataSet? by remember { mutableStateOf(null) }

    LaunchedEffect(ecgValue) {
        entries.add(Entry(xValue, ecgValue.toFloat()))
        xValue += 1f

        if (entries.size > 100) {
            entries.removeAt(0)
            // Reajustar valores X
            entries.forEachIndexed { index, entry -> entry.x = index.toFloat() }
            xValue = entries.size.toFloat()
        }

        chart?.let { chart ->
            dataSet?.let {
                it.values = entries
                chart.data.notifyDataChanged()
                chart.notifyDataSetChanged()
                chart.invalidate()
            }
        }
    }

    AndroidView(
        modifier = Modifier,
        factory = { context ->
            LineChart(context).apply {
                layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, 400)
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                axisRight.isEnabled = false
                axisLeft.axisMinimum = 0f
                axisLeft.axisMaximum = 1024f
                description.isEnabled = false
                legend.isEnabled = false
                setTouchEnabled(false)
                setScaleEnabled(false)

                val ds = LineDataSet(entries, "ECG").apply {
                    color = Color.GREEN
                    setDrawCircles(false)
                    lineWidth = 2f
                    setDrawValues(false)
                }

                data = LineData(ds)
                chart = this
                dataSet = ds
            }
        }
    )
}
