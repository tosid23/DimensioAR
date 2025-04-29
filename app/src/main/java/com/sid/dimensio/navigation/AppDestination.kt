package com.sid.dimensio.navigation

interface AppDestination {
    val route: String
}

object Home : AppDestination {
    override val route = "home"
}

object Measure : AppDestination {
    override val route = "measure"
}