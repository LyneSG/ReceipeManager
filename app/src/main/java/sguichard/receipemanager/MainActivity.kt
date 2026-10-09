package sguichard.receipemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import sguichard.receipemanager.data.Home
import sguichard.receipemanager.data.HomeDetail
import sguichard.receipemanager.data.MainTab
import sguichard.receipemanager.data.Profile
import sguichard.receipemanager.data.Search
import sguichard.receipemanager.presentation.HomePage
import sguichard.receipemanager.presentation.ProfilePage
import sguichard.receipemanager.presentation.SearchPage
import sguichard.receipemanager.ui.theme.ReceipeManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReceipeManagerTheme {
                ReceipeManagerAppMain()
            }
        }
    }
}

@Composable
fun ReceipeManagerAppMain() {
    var selectedTab by rememberSaveable() {
        mutableStateOf( value = MainTab.HOME)
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == MainTab.HOME,
                    onClick = {selectedTab = MainTab.HOME},
                    icon = { Icon(painterResource(R.drawable.ic_home), null)},
                    label = {Text(text = "Accueil")}
                )
                NavigationBarItem(
                    selected = selectedTab == MainTab.SEARCH,
                    onClick = {selectedTab = MainTab.SEARCH},
                    icon = { Icon(painterResource(R.drawable.baseline_location_searching_24), null)},
                    label = {Text(text = "Recherche")}
                )
                NavigationBarItem(
                    selected = selectedTab == MainTab.PROFILE,
                    onClick = {selectedTab = MainTab.PROFILE},
                    icon = { Icon(painterResource(R.drawable.baseline_person_24), null)},
                    label = {Text(text = "Profil")}
                )
            }
        },
    )
    { padding ->
        val homeBackStack = rememberNavBackStack(Home)
        val searchBackStack = rememberNavBackStack(Search)
        val profileBackStack = rememberNavBackStack(Profile)
        Box(modifier = Modifier.padding(padding)) {
            when(selectedTab){
                MainTab.HOME -> NavDisplay(
                    backStack = homeBackStack,
                    entryProvider = entryProvider {
                        entry<Home> { HomePage() }
                    }
                )
                MainTab.SEARCH -> NavDisplay(
                    backStack = searchBackStack,
                    entryProvider = entryProvider {
                        entry<Search> { SearchPage() }
                    }
                )
                MainTab.PROFILE -> NavDisplay(
                    backStack = profileBackStack,
                    entryProvider = entryProvider {
                        entry<Profile> { ProfilePage() }
                    }
                )
            }
        }
    }
}