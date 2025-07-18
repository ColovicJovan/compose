package net.systemvi.configurator.components.configure

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import arrow.core.some
import net.systemvi.configurator.data.allKeys
import net.systemvi.configurator.data.alphabetKeys
import net.systemvi.configurator.data.fKeys
import net.systemvi.configurator.data.mediaKeys
import net.systemvi.configurator.data.miscKeys
import net.systemvi.configurator.data.modifierKeys
import net.systemvi.configurator.data.numberKeys
import net.systemvi.configurator.data.numpadKeys
import net.systemvi.configurator.data.symbolKeys
import net.systemvi.configurator.model.*
import net.systemvi.configurator.utils.KeyboardSerialApi
import net.systemvi.configurator.utils.KeymapApi

data class KeycapPosition(val x:Int,val y:Int)

enum class KeyboardLayoutPages(val title:String){
    Keymap("Keymap"),
    SaveAndLoad("Save and Load");
}

enum class KeyboardKeysPages(val title:String,val keys:List<Key>){
    All("All", allKeys),
    Alphabet("Alphabet", alphabetKeys),
    Numbers("Numbers", numberKeys),
    Symbols("Symbols", symbolKeys),
    FKeys("Function Keys", fKeys),
    NumpadKeys("Numpad Keys", numpadKeys),
    ModifierKeys("Modifier Keys", modifierKeys),
    MiscKeys("Misc", miscKeys),
    MediaKeys("Media Keys", mediaKeys),
    LayerKeys("Layer Keys",emptyList()),
    SnapTapKeys("Snap tap",emptyList()),
    MacroKeys("Macros",emptyList()),
}

data class CurrentlySelectedSnapTapKeys(var first: KeycapMatrixPosition? = null,var second: KeycapMatrixPosition? = null)

class ConfigureViewModel(): ViewModel() {
    val serialApi = KeyboardSerialApi()
    val keymapApi = KeymapApi()
    var currentlyPressedKeycaps:Set<KeycapMatrixPosition> by mutableStateOf(emptySet())
    var currentKeyboardLayoutPage: KeyboardLayoutPages by mutableStateOf(KeyboardLayoutPages.Keymap)
    var currentKeyboardKeysPage: KeyboardKeysPages by mutableStateOf(KeyboardKeysPages.All)
    var currentlySelectingSnapTapPair by mutableStateOf(false)
    var currentlySelectedSnapTapKeys by mutableStateOf(CurrentlySelectedSnapTapKeys())

    fun setKeyboardLayoutPage(page: KeyboardLayoutPages){
        currentKeyboardLayoutPage=page
    }

    fun setKeyboardKeysPage(page: KeyboardKeysPages){
        currentKeyboardKeysPage=page
    }

    private var selectedLayer by mutableStateOf(0)
    private var selectedKeycapPositon by mutableStateOf<KeycapPosition?>(null)

    fun isKeycapSelected(i:Int,j:Int):Boolean=
        selectedKeycapPositon!=null
                && selectedKeycapPositon!!.x == i
                && selectedKeycapPositon!!.y == j

    fun selectKeycap(i:Int,j:Int){
        selectedKeycapPositon = KeycapPosition(i,j)
    }

    fun unselectKeycap(){
        selectedKeycapPositon = null
    }

    fun selectLayer(layer:Int){
        selectedLayer = layer
    }

    fun selectedLayer()=selectedLayer

    fun setNormalKey(key: Key){
        keymapApi.keymap.onSome { keymap->
            if(selectedKeycapPositon!=null){
                val x=selectedKeycapPositon!!.x
                val y=selectedKeycapPositon!!.y
                val keycap=keymap.keycaps[x][y]
                val layer=selectedLayer
                keymapApi.keymap=keymap.updateKeycap(x,y,layer,key).some()
                keymapApi.save(keymapApi.keymap.getOrNull()!!)
                serialApi.setKeyOnLayer(key,layer,keycap.matrixPosition)
            }
        }
    }

    fun setMacroKey(macro:Macro){
        keymapApi.keymap.onSome { keymap->
            if(selectedKeycapPositon!=null){
                val x=selectedKeycapPositon!!.x
                val y=selectedKeycapPositon!!.y
                val keycap=keymap.keycaps[x][y]
                val layer=selectedLayer
                keymapApi.keymap=keymap.updateKeycap(x,y,layer,macro).some()
                keymapApi.save(keymapApi.keymap.getOrNull()!!)
                serialApi.setKeyOnLayer(macro,layer, keycap.matrixPosition)
            }
        }
    }

    fun setLayerKey(layer:Int){
        keymapApi.keymap.onSome { keymap->
            if(selectedKeycapPositon!=null){
                val x=selectedKeycapPositon!!.x
                val y=selectedKeycapPositon!!.y
                val keycap=keymap.keycaps[x][y]
                keymapApi.keymap=keymap.addLayerKey(LayerKeyPosition(keycap.matrixPosition,layer)).some()
                keymapApi.save(keymapApi.keymap.getOrNull()!!)
                serialApi.addLayerKeyPosition(keycap.matrixPosition,layer)
            }
        }
    }

    fun removeLayerKey(){
        keymapApi.keymap.onSome { keymap->
            if(selectedKeycapPositon!=null){
                val x=selectedKeycapPositon!!.x
                val y=selectedKeycapPositon!!.y
                val keycap=keymap.keycaps[x][y]
                keymapApi.keymap=keymap.removeLayerKey(keycap.matrixPosition).some()
                keymapApi.save(keymapApi.keymap.getOrNull()!!)
                serialApi.removeLayerKeyPosition(keycap.matrixPosition)
            }
        }
    }

    fun setSnapTapPair(pair: SnapTapPair){
        keymapApi.keymap.onSome { keymap->
            keymapApi.keymap=keymap.addSnapTapPair(pair).some()
            keymapApi.save(keymapApi.keymap.getOrNull()!!)
            serialApi.addSnapTapPair(pair)
        }
    }

    fun removeSnapTapPair(pair: SnapTapPair){
        keymapApi.keymap.onSome { keymap->
            keymapApi.keymap=keymap.removeSnapTapPair(pair).some()
            keymapApi.save(keymapApi.keymap.getOrNull()!!)
            serialApi.removeSnapTapPair(pair)
        }
    }

    fun readPortNames() = serialApi.getPortNames()

    fun selectPort(name:String?){
        serialApi.selectPort(name)
        serialApi.onKeymapRead { keymap->
            println("new keymap read")
            keymapApi.keymap=keymap.some()
        }
        serialApi.onKeycapPress { keycap->
            currentlyPressedKeycaps += keycap
        }
        serialApi.onKeycapRelease { keycap->
            currentlyPressedKeycaps -= keycap
        }
        serialApi.enableKeyPressEvents()
        serialApi.requestKeymapRead()
    }

    fun closePort(){
        serialApi.closePort()
    }

    fun keymapLoad(keymap: KeyMap){
        keymapApi.keymapLoad(keymap)
        unselectKeycap()
        setKeyboardLayoutPage(KeyboardLayoutPages.Keymap)
    }

    fun keymapSave(keymap: KeyMap) {
        keymapApi.save(keymap)
    }

    fun keymapSaveAs(keymap: KeyMap){
        keymapApi.saveAs(keymap)
    }

    fun keymapLoadFromDisk():List<KeyMap>{
        return keymapApi.loadFromDisk()
    }

    suspend fun keymapUpload(keymap: KeyMap){
        keymapApi.upload(serialApi,keymap)
    }
}