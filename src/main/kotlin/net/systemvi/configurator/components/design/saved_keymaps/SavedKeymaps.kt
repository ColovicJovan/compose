package net.systemvi.configurator.components.design.saved_keymaps

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.design.DesignPageViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SavedKeymaps() {
    val viewModel = viewModel { DesignPageViewModel() }

    SharedTransitionLayout {
        AnimatedContent(
            targetState = viewModel.showDetails,
            label = "basic_transition"
        ) { targetState ->
            if (!targetState) {
                InitialContent(
                    animatedVisibilityScope = this@AnimatedContent,
                    sharedTransitionScope = this@SharedTransitionLayout
                )
            } else {
                ExpandedContent(
                    modifier = Modifier.wrapContentSize(unbounded = true).zIndex(1f),
                    animatedVisibilityScope = this@AnimatedContent,
                    sharedTransitionScope = this@SharedTransitionLayout
                )
            }
        }
    }
}