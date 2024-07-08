package com.example.derssrosia.uicomponents

import android.app.Activity
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

/**
 * A simple top app bar with a title, navigation icon, and action icons.
 * This composable function is designed to be used in a Jetpack Compose-based UI.
 *
 * @param title The title text to display in the app bar.
 */
@ExperimentalMaterial3Api
@ExperimentalLayoutApi
@Composable
fun SimpleLightTopAppBar(title: String) {
    // Get the current activity context
    val act = LocalContext.current as Activity

    // Define the top app bar
    TopAppBar(
        title = {
            // Display the title text
            Text(title, style = MaterialTheme.typography.titleMedium)
        },
        navigationIcon = {
            // Navigation icon that finishes the activity when clicked
            IconButton(onClick = {
                act.finish()
            }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Back")
            }
        },
        actions = {
            // First action icon (search) with no defined action yet
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            }
            // Second action icon (more options) with no defined action yet
            IconButton(onClick = { /* doSomething */ }) {
                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "More options")
            }
        },
        // Set the colors for the top app bar
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
        )
    )
}