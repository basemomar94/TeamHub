package org.zayn.teamhub.core.desgin_repo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.VisualTransformation


@Composable
fun CustomTextField(
    query: String = "",
    placeHolder: String = "",
    endIcon: ImageVector? = null,
    endIconAction: (() -> Unit)? = null,
    startIcon: ImageVector? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onQueryChanged: (String) -> Unit,

    ) {
    TextField(
        value = query, onValueChange = onQueryChanged, shape = RoundedCornerShape(
            15
        ),
        placeholder = {
            Text(placeHolder)
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.background, shape = RoundedCornerShape(
                    15
                )
            ),
        leadingIcon = {
            if (startIcon != null) {
                Icon(imageVector = startIcon,
                    contentDescription = "")
            }
        },
        trailingIcon = {
            if (endIcon != null) {
                IconButton(onClick = { endIconAction?.invoke() }) {
                    Icon(
                        imageVector = endIcon,
                        contentDescription = ""
                    )
                }
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedLabelColor = MaterialTheme.colors.primary,
            unfocusedLabelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
        ),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions
    )
}