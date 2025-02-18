package org.zayn.teamhub.core.utils

expect suspend fun getCurrentLocation(): Pair<Double, Double>?

expect fun openMap(latitude: Double, longitude: Double)

expect fun isGpsAvailable(): Boolean

expect fun isLocationAllowed(): Boolean


