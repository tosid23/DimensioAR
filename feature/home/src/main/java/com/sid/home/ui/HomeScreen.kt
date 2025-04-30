package com.sid.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sid.home.domain.HomeViewModel
import com.sid.home.domain.UiState

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onClickCreate: () -> Unit = {}
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    Scaffold(
        topBar = { ToolBar() },
        floatingActionButton = {
            Fab(onClickCreate)
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            when (state) {
                UiState.EmptyState -> EmptyState()
                UiState.Loading -> LoadingState()
                is UiState.HomeState -> {
                    HomeLayout(measurements = state.measurements)
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ToolBar() {
    TopAppBar(
        title = {
            Text(
                text = "Dimensio",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

@Composable
private fun Fab(onClickCreate: () -> Unit = {}) {
    FloatingActionButton(onClick = onClickCreate) {
        Icon(Icons.Filled.Add, "Create measurement")
    }
}

@Composable
private fun EmptyState() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Empty")
    }
}

@Composable
private fun LoadingState() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Loading")
    }
}