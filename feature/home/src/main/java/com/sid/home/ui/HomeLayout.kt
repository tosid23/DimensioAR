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
import com.sid.data.data.entity.dummyMeasurements

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
    MaterialTheme {
        HomeLayout(measurements = dummyMeasurements)
    }
}

