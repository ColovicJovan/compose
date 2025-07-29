package net.systemvi.configurator.components.design.saved_keymaps

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.systemvi.configurator.components.common.keyboard_grid.Grid
import net.systemvi.configurator.components.configure.keyboard_layout.KeyboardPreviewKeycapComponent
import net.systemvi.configurator.components.design.DesignPageViewModel
import net.systemvi.configurator.model.KeyMap

@Composable fun SelectKeymap(keymap: KeyMap) {
    val viewModel = viewModel { DesignPageViewModel() }

    Card(
        modifier = Modifier
            .clipToBounds()
            .padding(end = 10.dp)
            .height(200.dp)
            .width(IntrinsicSize.Min)
            .clickable(onClick = {
                viewModel.updateKeymap(keymap)
                viewModel.showDetails = false
            })
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Text(
                keymap.name,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f).fillMaxWidth()
            ) {
                Grid(keymap, KeyboardPreviewKeycapComponent, 15)
            }
        }
    }
}