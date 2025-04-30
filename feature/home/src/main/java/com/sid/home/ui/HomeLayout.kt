package com.sid.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sid.data.data.entity.MeasurementEntity

@Composable
internal fun HomeLayout(measurements: List<MeasurementEntity>) {
    ExistingMeasurement(measurements)
}

@Composable
internal fun ExistingMeasurement(measurements: List<MeasurementEntity>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = 16.dp) // Add padding at the bottom of the list
    ) {
        items(measurements) { measurement ->
            Card(
                onClick = {

                },
                modifier = Modifier.fillParentMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp) // spacing between text
                ) {
                    Text(
                        text = measurement.name,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${measurement.resultValue} ${measurement.resultUnit}",
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeLayoutPreview() {
    // Dummy data for preview
    val dummyMeasurements = listOf(
        MeasurementEntity(
            name = "Measurement 1",
            timestamp = 1678886400,
            measurementType = "Distance",
            resultValue = 10.5,
            resultUnit = "meters",
            arPoints = null
        ),
        MeasurementEntity(
            name = "Measurement 2",
            timestamp = 1678972800,
            measurementType = "Height",
            resultValue = 1.8,
            resultUnit = "meters",
            arPoints = null
        ),
        MeasurementEntity(
            name = "Measurement 3",
            timestamp = 1679059200,
            measurementType = "Distance",
            resultValue = 5.2,
            resultUnit = "meters",
            arPoints = null
        ),
        MeasurementEntity(
            name = "Measurement 4",
            timestamp = 1679145600,
            measurementType = "Width",
            resultValue = 2.5,
            resultUnit = "meters",
            arPoints = null
        ),
        MeasurementEntity(
            name = "Measurement 5",
            timestamp = 1679232000,
            measurementType = "Distance",
            resultValue = 8.0,
            resultUnit = "meters",
            arPoints = null
        ),
    )
    MaterialTheme {
        HomeLayout(measurements = dummyMeasurements)
    }
}

