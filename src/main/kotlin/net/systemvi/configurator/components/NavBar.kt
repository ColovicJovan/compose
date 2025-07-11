package net.systemvi.configurator.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBar(pageViewModel: ApplicationViewModel= viewModel { ApplicationViewModel() }) {
    data class Link(val title:String,val page:Page)
    val links=listOf(
        Link("Configure", ConfigurePage),
        Link("Key Tester", TesterPage),
        Link("Design", DesignPage),
        Link("Settings", SettingsPage),
    )
    TopAppBar(
        title={
            Row(modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically)
            {
                links.forEach { link->
                    TextButton(
                        onClick = { pageViewModel.currentPage = link.page }
                    ) {
                        Text(link.title)
                    }
                }
            }
        }
    )
}