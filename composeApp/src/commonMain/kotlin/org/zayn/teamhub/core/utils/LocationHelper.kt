package org.zayn.teamhub.core.utils

expect suspend fun getCurrentLocation(): Pair<Double, Double>?

expect fun openMap(latitude: Double, longitude: Double)

expect fun isLocationAvailable(): Boolean

