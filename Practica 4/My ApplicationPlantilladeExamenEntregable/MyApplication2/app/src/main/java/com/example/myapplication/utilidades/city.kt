package com.example.myapplication.utilidades/*
Font Awesome Free License
-------------------------

Font Awesome Free is free, open source, and GPL friendly. You can use it for
commercial projects, open source projects, or really almost whatever you want.
Full Font Awesome Free license: https://fontawesome.com/license/free.

# Icons: CC BY 4.0 License (https://creativecommons.org/licenses/by/4.0/)
In the Font Awesome Free download, the CC BY 4.0 license applies to all icons
packaged as SVG and JS file types.

# Fonts: SIL OFL 1.1 License (https://scripts.sil.org/OFL)
In the Font Awesome Free download, the SIL OFL license applies to all icons
packaged as web and desktop font files.

# Code: MIT License (https://opensource.org/licenses/MIT)
In the Font Awesome Free download, the MIT license applies to all non-font and
non-icon files.

# Attribution
Attribution is required by MIT, SIL OFL, and CC BY licenses. Downloaded Font
Awesome Free files already contain embedded comments with sufficient
attribution, so you shouldn't need to do anything additional when using these
files normally.

We've kept attribution comments terse, so we ask that you do not actively work
to remove them from files, especially code. They're a great way for folks to
learn about Font Awesome.

# Brand Icons
All brand icons are trademarks of their respective owners. The use of these
trademarks does not indicate endorsement of the trademark holder by Font
Awesome, nor vice versa. **Please do not use brand logos for any purpose except
to represent the company, product, or service to which they refer.**

*/
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val FontAwesomeCity: ImageVector
    get() {
        if (_FontAwesomeCity != null) return _FontAwesomeCity!!
        
        _FontAwesomeCity = ImageVector.Builder(
            name = "city",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 640f,
            viewportHeight = 512f
        ).apply {
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(616f, 192f)
                horizontalLineTo(480f)
                verticalLineTo(24f)
                curveToRelative(0f, -13.26f, -10.74f, -24f, -24f, -24f)
                horizontalLineTo(312f)
                curveToRelative(-13.26f, 0f, -24f, 10.74f, -24f, 24f)
                verticalLineToRelative(72f)
                horizontalLineToRelative(-64f)
                verticalLineTo(16f)
                curveToRelative(0f, -8.84f, -7.16f, -16f, -16f, -16f)
                horizontalLineToRelative(-16f)
                curveToRelative(-8.84f, 0f, -16f, 7.16f, -16f, 16f)
                verticalLineToRelative(80f)
                horizontalLineToRelative(-64f)
                verticalLineTo(16f)
                curveToRelative(0f, -8.84f, -7.16f, -16f, -16f, -16f)
                horizontalLineTo(80f)
                curveToRelative(-8.84f, 0f, -16f, 7.16f, -16f, 16f)
                verticalLineToRelative(80f)
                horizontalLineTo(24f)
                curveToRelative(-13.26f, 0f, -24f, 10.74f, -24f, 24f)
                verticalLineToRelative(360f)
                curveToRelative(0f, 17.67f, 14.33f, 32f, 32f, 32f)
                horizontalLineToRelative(576f)
                curveToRelative(17.67f, 0f, 32f, -14.33f, 32f, -32f)
                verticalLineTo(216f)
                curveToRelative(0f, -13.26f, -10.75f, -24f, -24f, -24f)
                close()
                moveTo(128f, 404f)
                curveToRelative(0f, 6.63f, -5.37f, 12f, -12f, 12f)
                horizontalLineTo(76f)
                curveToRelative(-6.63f, 0f, -12f, -5.37f, -12f, -12f)
                verticalLineToRelative(-40f)
                curveToRelative(0f, -6.63f, 5.37f, -12f, 12f, -12f)
                horizontalLineToRelative(40f)
                curveToRelative(6.63f, 0f, 12f, 5.37f, 12f, 12f)
                verticalLineToRelative(40f)
                close()
                moveToRelative(0f, -96f)
                curveToRelative(0f, 6.63f, -5.37f, 12f, -12f, 12f)
                horizontalLineTo(76f)
                curveToRelative(-6.63f, 0f, -12f, -5.37f, -12f, -12f)
                verticalLineToRelative(-40f)
                curveToRelative(0f, -6.63f, 5.37f, -12f, 12f, -12f)
                horizontalLineToRelative(40f)
                curveToRelative(6.63f, 0f, 12f, 5.37f, 12f, 12f)
                verticalLineToRelative(40f)
                close()
                moveToRelative(0f, -96f)
                curveToRelative(0f, 6.63f, -5.37f, 12f, -12f, 12f)
                horizontalLineTo(76f)
                curveToRelative(-6.63f, 0f, -12f, -5.37f, -12f, -12f)
                verticalLineToRelative(-40f)
                curveToRelative(0f, -6.63f, 5.37f, -12f, 12f, -12f)
                horizontalLineToRelative(40f)
                curveToRelative(6.63f, 0f, 12f, 5.37f, 12f, 12f)
                verticalLineToRelative(40f)
                close()
                moveToRelative(128f, 192f)
                curveToRelative(0f, 6.63f, -5.37f, 12f, -12f, 12f)
                horizontalLineToRelative(-40f)
                curveToRelative(-6.63f, 0f, -12f, -5.37f, -12f, -12f)
                verticalLineToRelative(-40f)
                curveToRelative(0f, -6.63f, 5.37f, -12f, 12f, -12f)
                horizontalLineToRelative(40f)
                curveToRelative(6.63f, 0f, 12f, 5.37f, 12f, 12f)
                verticalLineToRelative(40f)
                close()
                moveToRelative(0f, -96f)
                curveToRelative(0f, 6.63f, -5.37f, 12f, -12f, 12f)
                horizontalLineToRelative(-40f)
                curveToRelative(-6.63f, 0f, -12f, -5.37f, -12f, -12f)
                verticalLineToRelative(-40f)
                curveToRelative(0f, -6.63f, 5.37f, -12f, 12f, -12f)
                horizontalLineToRelative(40f)
                curveToRelative(6.63f, 0f, 12f, 5.37f, 12f, 12f)
                verticalLineToRelative(40f)
                close()
                moveToRelative(0f, -96f)
                curveToRelative(0f, 6.63f, -5.37f, 12f, -12f, 12f)
                horizontalLineToRelative(-40f)
                curveToRelative(-6.63f, 0f, -12f, -5.37f, -12f, -12f)
                verticalLineToRelative(-40f)
                curveToRelative(0f, -6.63f, 5.37f, -12f, 12f, -12f)
                horizontalLineToRelative(40f)
                curveToRelative(6.63f, 0f, 12f, 5.37f, 12f, 12f)
                verticalLineToRelative(40f)
                close()
                moveToRelative(160f, 96f)
                curveToRelative(0f, 6.63f, -5.37f, 12f, -12f, 12f)
                horizontalLineToRelative(-40f)
                curveToRelative(-6.63f, 0f, -12f, -5.37f, -12f, -12f)
                verticalLineToRelative(-40f)
                curveToRelative(0f, -6.63f, 5.37f, -12f, 12f, -12f)
                horizontalLineToRelative(40f)
                curveToRelative(6.63f, 0f, 12f, 5.37f, 12f, 12f)
                verticalLineToRelative(40f)
                close()
                moveToRelative(0f, -96f)
                curveToRelative(0f, 6.63f, -5.37f, 12f, -12f, 12f)
                horizontalLineToRelative(-40f)
                curveToRelative(-6.63f, 0f, -12f, -5.37f, -12f, -12f)
                verticalLineToRelative(-40f)
                curveToRelative(0f, -6.63f, 5.37f, -12f, 12f, -12f)
                horizontalLineToRelative(40f)
                curveToRelative(6.63f, 0f, 12f, 5.37f, 12f, 12f)
                verticalLineToRelative(40f)
                close()
                moveToRelative(0f, -96f)
                curveToRelative(0f, 6.63f, -5.37f, 12f, -12f, 12f)
                horizontalLineToRelative(-40f)
                curveToRelative(-6.63f, 0f, -12f, -5.37f, -12f, -12f)
                verticalLineTo(76f)
                curveToRelative(0f, -6.63f, 5.37f, -12f, 12f, -12f)
                horizontalLineToRelative(40f)
                curveToRelative(6.63f, 0f, 12f, 5.37f, 12f, 12f)
                verticalLineToRelative(40f)
                close()
                moveToRelative(160f, 288f)
                curveToRelative(0f, 6.63f, -5.37f, 12f, -12f, 12f)
                horizontalLineToRelative(-40f)
                curveToRelative(-6.63f, 0f, -12f, -5.37f, -12f, -12f)
                verticalLineToRelative(-40f)
                curveToRelative(0f, -6.63f, 5.37f, -12f, 12f, -12f)
                horizontalLineToRelative(40f)
                curveToRelative(6.63f, 0f, 12f, 5.37f, 12f, 12f)
                verticalLineToRelative(40f)
                close()
                moveToRelative(0f, -96f)
                curveToRelative(0f, 6.63f, -5.37f, 12f, -12f, 12f)
                horizontalLineToRelative(-40f)
                curveToRelative(-6.63f, 0f, -12f, -5.37f, -12f, -12f)
                verticalLineToRelative(-40f)
                curveToRelative(0f, -6.63f, 5.37f, -12f, 12f, -12f)
                horizontalLineToRelative(40f)
                curveToRelative(6.63f, 0f, 12f, 5.37f, 12f, 12f)
                verticalLineToRelative(40f)
                close()
            }
        }.build()
        
        return _FontAwesomeCity!!
    }

private var _FontAwesomeCity: ImageVector? = null

