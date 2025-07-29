package net.systemvi.configurator.components.design.saved_keymaps

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.data.defaultKeymaps
import net.systemvi.configurator.utils.services.KeymapService

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ExpandedContent(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val keymapApi = viewModel { KeymapService() }.keymapApi

    with(sharedTransitionScope) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ElevatedCard(
                modifier = modifier
                    .then(
                        Modifier
                            .sharedBounds(
                                rememberSharedContentState(key = "ElevatedCard"),
                                animatedVisibilityScope = animatedVisibilityScope,
                                enter = fadeIn(),
                                exit = fadeOut(),
                                resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds()
                            )
                            .background(
                                color = MaterialTheme.colorScheme.error
                            )
                    )
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        defaultKeymaps().forEach { keymap ->
                            SelectKeymap(keymap)
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        keymapApi.savedKeymaps.forEach { keymap ->
                            SelectKeymap(keymap)
                        }
                    }
                }
            }
        }
    }
}