package net.systemvi.configurator.model

import androidx.compose.foundation.layout.PaddingValues
import arrow.core.Either
import arrow.core.right
import arrow.optics.*
import arrow.optics.dsl.index
import arrow.optics.typeclasses.Index
import eu.mihosoft.jcsg.CSG
import eu.mihosoft.jcsg.Cube
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.io.FileWriter
@Serializable
enum class KeycapWidth(val size:Float){
    SIZE_1U(1f),
    SIZE_125U(1.25f),
    SIZE_15U(1.5f),
    SIZE_175U(1.75f),
    SIZE_2U(2f),
    SIZE_225U(2.25f),
    SIZE_275U(2.75f),
    SIZE_625U(6.25f);
}

@Serializable
enum class KeycapHeight(val size:Float){
    SIZE1U(1f),
    SIZE2U(2f);
}

@Serializable
@optics data class KeycapOffset(val x:Float=0f, val y:Float=0f){companion object}

@Serializable
@optics data class Key(val value:Byte, val name:String, val nativeCode:Long=0){companion object}

@Serializable
@optics data class KeycapMatrixPosition(val x:Int,val y:Int){companion object}

@Serializable
@optics data class KeycapPadding(val bottom:Float = 0f, val left:Float = 0f){companion object}

@Serializable
@optics data class Keycap(
    val layers:List<@Contextual Either<Macro,Key>>,
    val width:KeycapWidth= KeycapWidth.SIZE_1U,
    val height:KeycapHeight= KeycapHeight.SIZE1U,
    val offset: KeycapOffset= KeycapOffset(),
    val padding: KeycapPadding = KeycapPadding(),
    val rotation:Float=0f,
    val matrixPosition:KeycapMatrixPosition = KeycapMatrixPosition(0,0),
){companion object}

fun Keycap.overrideWidth(width: KeycapWidth): Keycap = Keycap.width.modify(this){ width }

fun Keycap.overrideHeight(height: KeycapHeight): Keycap = Keycap.height.modify(this){ height }

@Serializable
enum class MacroActionType(val id:Int){
    KEY_UP(1),KEY_DOWN(0);
}
@Serializable
@optics data class MacroAction(val key:Key,val action:MacroActionType){companion object}
@Serializable
@optics data class Macro(val name:String,val actions:List<MacroAction>){companion object}

@Serializable
@optics data class KeyMap(val name:String,val keycaps:List<List<Keycap>>){companion object}

fun KeyMap.setKeyWidth(i:Int,j:Int,width:KeycapWidth): KeyMap=
    KeyMap.keycaps
        .index(Index.list(),i)
        .index(Index.list(),j)
        .set(this,this.keycaps[i][j].overrideWidth(width))

fun KeyMap.updateKeycap(x:Int,y:Int,layer:Int,key:Key): KeyMap=
    KeyMap.keycaps
        .index(Index.list(),x)
        .index(Index.list(),y)
        .layers.index(Index.list(),layer)
        .set(this,key.right())

fun KeyMap.exportStl(name:String){
    val topPlate= Cube(30.0)
    val fileWriter= FileWriter(name)
    fileWriter.write(topPlate.toCSG().toStlString())
    fileWriter.close()
}
