package demo.sahha.android.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import demo.sahha.android.R
import demo.sahha.android.view.Screen
import demo.sahha.android.view.components.SahhaThemeButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import sdk.sahha.android.source.Sahha

@Composable
fun Home(navController: NavController) {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Image(painterResource(id = R.drawable.ic_sahha), contentDescription = "Sahha Logo")
                Spacer(Modifier.size(100.dp))

                SahhaThemeButton(buttonTitle = "Authenticate") {
                    navController.navigate(Screen.Authenticate.route)
                }

                SahhaThemeButton(buttonTitle = "Permissions") {
                    navController.navigate(Screen.Permission.route)
                }

                SahhaThemeButton(buttonTitle = "Profile") {
                    navController.navigate(Screen.Profile.route)
                }

                SahhaThemeButton(buttonTitle = "Analyze") {
                    navController.navigate(Screen.Analyze.route)
                }

                SahhaThemeButton(buttonTitle = "WebView") {
                    navController.navigate(Screen.WebView.route)
                }
            }
        }
    }
}