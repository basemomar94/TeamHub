package org.zayn.teamhub.core.utils

import org.zayn.teamhub.core.models.Location

expect suspend fun getCurrentLocation(): Pair<Double, Double>?

expect fun openMap(latitude: Double, longitude: Double)
