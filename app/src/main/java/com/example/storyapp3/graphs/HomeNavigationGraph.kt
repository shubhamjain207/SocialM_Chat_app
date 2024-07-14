package android.kotlin.foodclub.navigation.graphs


import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.storyapp3.views.ChatList
import com.example.storyapp3.views.ChatRoom
import com.example.storyapp3.views.Home
import com.example.storyapp3.views.MainDashboard

fun NavGraphBuilder.homeNavigationGraph(navController: NavHostController) {
    navigation(
        route = Graph.HOME,
        startDestination = HomeOtherRoutes.ChatList.route
    ) {
        composable(route = HomeOtherRoutes.Home.route) {

            Home(navController)
        }
        composable(route = HomeOtherRoutes.ChatList.route) {
            ChatList(navController)
        }

        composable(route = HomeOtherRoutes.MainDashboard.route) {
            MainDashboard(navController)
        }


        composable(route = HomeOtherRoutes.ChatRoom.route + "/{receiverUsername}",

            arguments = listOf(
                navArgument("receiverUsername") { type = NavType.StringType },
            )

        ) {backStackEntry ->
            ChatRoom(navController,backStackEntry.arguments?.getString("receiverUsername"))

        }

    }
}

sealed class HomeOtherRoutes(val route: String) {
    object Home : HomeOtherRoutes(route = "HOME")
    object ChatList : HomeOtherRoutes(route = "CHATLIST")
    object ChatRoom : HomeOtherRoutes(route = "CHATROOM")
    object MainDashboard: HomeOtherRoutes(route = "MAINDASHBOARD")
}
