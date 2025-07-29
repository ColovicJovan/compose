package net.systemvi.configurator.components.design

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import arrow.core.right
import arrow.optics.dsl.index
import net.systemvi.configurator.components.configure.KeycapPosition
import net.systemvi.configurator.model.KeyMap
import net.systemvi.configurator.data.allKeys
import net.systemvi.configurator.model.Keycap
import net.systemvi.configurator.model.KeycapMatrixPosition
import net.systemvi.configurator.model.changeName
import net.systemvi.configurator.model.keycaps
import kotlin.collections.plus
import kotlin.random.Random

class DesignPageViewModel : ViewModel() {

    var selectedKeycap: KeycapPosition? by mutableStateOf(null)
    var showSaveButton by mutableStateOf(false)
    var random = Random
    var showDetails by mutableStateOf(false)

    var keymap by mutableStateOf(KeyMap("", listOf(listOf())))
        private set

    fun updateKeymap(keymap: KeyMap) {
        this.keymap = keymap
    }

    fun setName(name: String) {
        keymap = keymap.changeName(name)
    }

    fun addRow() {
        keymap = KeyMap.keycaps.modify(keymap, {
            it + listOf(listOf())
        })
    }

    fun removeRow(row: Int) {
        keymap = KeyMap.keycaps.modify(keymap, { keycaps ->
            keycaps.filterIndexed { index, _ -> index != row }
        })
    }

    fun addKeycap(row: Int) {
        keymap = KeyMap.keycaps.index(row).modify(keymap, {
            it + Keycap(
                listOf(allKeys[0].right()),
                matrixPosition = KeycapMatrixPosition(random.nextInt(), random.nextInt())
            )
        })
    }

    fun deleteKeycap(row: Int, key: Int) {
        keymap = KeyMap.keycaps.index(row).modify(keymap, {
            it.filterIndexed { index, _ -> index != key }
        })
    }

    fun reorderRows(rows: List<List<Keycap>>) {
        keymap = KeyMap.keycaps.set(keymap, rows)
    }

    fun reorderKeycaps(keycaps: List<Keycap>, row: Int) {
        keymap = KeyMap.keycaps.index(row).set(keymap, keycaps)
    }

}