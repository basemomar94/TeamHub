package org.zayn.teamhub.core.desgin_repo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource


@Composable
fun BaseImage(
    url: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null
) {
    KamelImage(
        { asyncPainterResource(url ?: "") }, contentDescription = contentDescription,
        modifier = modifier
    )
}

