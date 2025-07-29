package net.systemvi.configurator.components.design.saved_keymaps

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.design.DesignPageViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun InitialContent(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope) {
    val viewModel = viewModel { DesignPageViewModel() }

    with(sharedTransitionScope) {
        ElevatedButton(
            onClick = { viewModel.showDetails = true},
            modifier = modifier
                .then(
                    Modifier
                        .sharedBounds(
                            rememberSharedContentState(key = "text"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            enter = fadeIn(),
                            exit = fadeOut(),
                            resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds()
                        )
                        .width(IntrinsicSize.Min)
                        .height(IntrinsicSize.Min)
                )
        ) {
            Text(text = "Keymaps")
        }
    }
}