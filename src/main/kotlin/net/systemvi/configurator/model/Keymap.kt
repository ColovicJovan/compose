package net.systemvi.configurator.model

import arrow.core.right
import arrow.optics.dsl.index
import arrow.optics.optics
import arrow.optics.typeclasses.Index
import eu.mihosoft.jcsg.Cube
import kotlinx.serialization.Serializable
import java.io.FileWriter

@Serializable
@optics data class KeyMap(val name:String,val keycaps:List<List<Keycap>>){companion object}

fun KeyMap.setKeycapWidth(i:Int, j:Int, width:KeycapWidth): KeyMap=
    KeyMap.keycaps
        .index(i)
        .index(j)
        .set(this,this.keycaps[i][j].overrideWidth(width))

fun KeyMap.setKeycapHeight(i:Int, j:Int, height: KeycapHeight): KeyMap =
    KeyMap.keycaps
        .index(i)
        .index(j)
        .set(this, this.keycaps[i][j].overrideHeight(height))

fun KeyMap.setKeycapLeftPadding(i:Int, j:Int, padding: KeycapPadding): KeyMap =
    KeyMap.keycaps
        .index(i)
        .index(j)
        .set(this, this.keycaps[i][j].overrideLeftPadding(padding))

fun KeyMap.setKeycapBottomPadding(i:Int, j:Int, padding: KeycapPadding): KeyMap =
    KeyMap.keycaps
        .index(i)
        .index(j)
        .set(this, this.keycaps[i][j].overrideBottomPadding(padding))


fun KeyMap.updateKeycap(x:Int,y:Int,layer:Int,key:Key): KeyMap=
    KeyMap.keycaps
        .index(x)
        .index(y)
        .layers.index(Index.list(),layer)
        .set(this,key.right())

fun KeyMap.exportStl(name:String) {
    val topPlate = Cube(30.0)
    val fileWriter = FileWriter(name)
    fileWriter.write(topPlate.toCSG().toStlString())
    fileWriter.close()
}
