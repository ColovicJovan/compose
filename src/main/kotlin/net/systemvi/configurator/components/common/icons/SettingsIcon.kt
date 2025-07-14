package net.systemvi.configurator.components.common.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val SettingsIcon: ImageVector
    get() {
        if (_Settings2 != null) return _Settings2!!

        _Settings2 = ImageVector.Builder(
            name = "Settings2",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(20f, 7f)
                horizontalLineToRelative(-9f)
                moveToRelative(3f, 10f)
                horizontalLineTo(5f)
            }
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(20f, 17f)
                arcTo(3f, 3f, 0f, false, true, 17f, 20f)
                arcTo(3f, 3f, 0f, false, true, 14f, 17f)
                arcTo(3f, 3f, 0f, false, true, 20f, 17f)
                close()
            }
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(10f, 7f)
                arcTo(3f, 3f, 0f, false, true, 7f, 10f)
                arcTo(3f, 3f, 0f, false, true, 4f, 7f)
                arcTo(3f, 3f, 0f, false, true, 10f, 7f)
                close()
            }
        }.build()

        return _Settings2!!
    }

private var _Settings2: ImageVector? = null

