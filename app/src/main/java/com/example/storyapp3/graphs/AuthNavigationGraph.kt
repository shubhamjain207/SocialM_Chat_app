package android.kotlin.foodclub.navigation.graphs


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.storyapp3.views.Login
import com.example.storyapp3.views.Register


fun NavGraphBuilder.authNavigationGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Register.route
    ) {

        composable(route = AuthScreen.Register.route) {
            Register(navController)
        }

        composable(route = AuthScreen.Login.route) {
            Login(navController)
        }

    }
}

sealed class AuthScreen(val route: String) {
    object Register : AuthScreen(route = "REGISTER")
    object Login : AuthScreen(route = "LOGIN")

}
