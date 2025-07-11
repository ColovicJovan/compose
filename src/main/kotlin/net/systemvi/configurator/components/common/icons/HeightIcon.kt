package net.systemvi.configurator.components.common.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val HeightIcon: ImageVector
    get() {
        if (_Height != null) return _Height!!

        _Height = ImageVector.Builder(
            name = "Height",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000))
            ) {
                moveTo(480f, 840f)
                lineTo(320f, 680f)
                lineToRelative(56f, -56f)
                lineToRelative(64f, 63f)
                verticalLineToRelative(-414f)
                lineToRelative(-64f, 63f)
                lineToRelative(-56f, -56f)
                lineToRelative(160f, -160f)
                lineToRelative(160f, 160f)
                lineToRelative(-56f, 57f)
                lineToRelative(-64f, -64f)
                verticalLineToRelative(414f)
                lineToRelative(64f, -63f)
                lineToRelative(56f, 56f)
                close()
            }
        }.build()

        return _Height!!
    }

private var _Height: ImageVector? = null

