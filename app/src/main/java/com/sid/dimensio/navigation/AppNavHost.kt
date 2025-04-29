package com.sid.dimensio.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sid.home.domain.HomeViewModel
import com.sid.home.ui.HomeScreen
import com.sid.measure.domain.MeasureViewModel
import com.sid.measure.ui.MeasureScreen

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Home.route,
    ) {
        composable(route = Home.route) {
            HomeScreen(
                viewModel = hiltViewModel<HomeViewModel>(),
                onClickCreate = {
                    navController.navigateSingleTopTo(Measure.route)
                }
            )
        }
        composable(route = Measure.route) {
            MeasureScreen(viewModel = hiltViewModel<MeasureViewModel>(), onBackPressed = {
                navController.popBackStack()
            })
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }