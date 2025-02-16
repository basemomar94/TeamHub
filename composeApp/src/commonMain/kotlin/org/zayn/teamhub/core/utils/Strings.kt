package org.zayn.teamhub.core.utils

import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.StringResource

expect fun getString(id: StringResource, args: List<Any>): String

@Composable
fun getStringResource(id: StringResource, vararg args: Any): String {
    return getString(id,args.toList())
}