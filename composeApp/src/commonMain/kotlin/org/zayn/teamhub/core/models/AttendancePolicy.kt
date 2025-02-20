package org.zayn.teamhub.core.models

enum class AttendancePolicy {
    MANDATORY_ON_SITE,  // Employees must work from a designated location
    LOCATION_WARNING,    // Employees receive a warning if not in the expected location
    FLEXIBLE             // Employees can work from anywhere
}