package org.zayn.teamhub.feature.home.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import org.koin.compose.koinInject
import org.zayn.teamhub.feature.home.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinInject()) {

   Text("Welcome ${viewModel.userId}")
}