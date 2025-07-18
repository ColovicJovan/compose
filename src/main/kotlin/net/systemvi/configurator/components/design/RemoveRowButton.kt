package net.systemvi.configurator.components.design

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.systemvi.configurator.model.KeyMap
import net.systemvi.configurator.model.keycaps

@Composable
fun RemoveRowButton(keymap: KeyMap, row: Int, onClick: (keymap: KeyMap) -> Unit) {
    ElevatedButton(
        onClick = {
            onClick(KeyMap.keycaps.modify(keymap, {keycaps ->
                keycaps.filterIndexed {index, _ -> index != row}
            }))
        },
        modifier = Modifier
            .padding(start = 10.dp)
    ) {
        Text(
            "Remove Row",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
