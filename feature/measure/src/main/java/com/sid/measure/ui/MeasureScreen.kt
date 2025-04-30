package com.sid.measure.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sid.measure.domain.MeasureViewModel
import com.sid.measure.domain.UiState

@Composable
fun MeasureScreen(
    viewModel: MeasureViewModel = hiltViewModel(),
    onBackPressed: () -> Unit = {}
) {

    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            when (state) {
                UiState.EmptyState -> EmptyLayout()
                UiState.ARNotSupportedState -> ARNotSupportedLayout()
                UiState.MeasureState -> {
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Yay! 🥳 AR is supported on this device")
                    }
                }
            }
        }
    }
}

@Composable
internal fun EmptyLayout() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Empty")
    }
}

@Composable
internal fun ARNotSupportedLayout() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Oops! 😬AR is not supported on this device")
    }
}