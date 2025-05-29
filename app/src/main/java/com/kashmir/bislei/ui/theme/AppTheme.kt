package com.kashmir.bislei.ui.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val darkColorScheme = AppColorScheme(
    background = bgDark,
    onBackground = Purple80,
    primary = btnDark,
    onPrimary = PurpleGrey80,
    secondary = Pink40,
    onSecondary = Pink80,
    btn_Dark = btnDark,
    btn_Light = btnLight,
    buttonBg = btnDark,
)



private val lightColorScheme= AppColorScheme(
    background = Color.White,
    onBackground = Purple40,
    btn_Light = btnLight,
    primary = bgLight,
    onPrimary = PurpleGrey40,
    secondary = Pink80,
    onSecondary = Pink40,
    buttonBg = btnDark,
    btn_Dark = btnLight,
)

private val typography=Apptypography(
    titleLarge = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    titleNormal = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    body = TextStyle(
        fontFamily = Poppins,
        fontSize = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    labelNormal = TextStyle(
        fontFamily = Poppins,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Poppins,
        fontSize = 12.sp
    ),
)

private val shape =AppShape(
    container = RoundedCornerShape(12.dp),
    button = RoundedCornerShape(50.dp)
)

private val size=AppSize(
    large=24.dp,
    medium=16.dp,
    normal=12.dp,
    small=8.dp
)

@Composable
fun AppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
   content: @Composable () -> Unit
) {
    val colorScheme = if(isDarkTheme) darkColorScheme else lightColorScheme
    val rippleIndication = ripple()
    CompositionLocalProvider(
        LocalAppColorScheme provides colorScheme,
        LocalAppTypography provides typography,
        LocalAppShape provides shape,
        LocalAppSize provides size,
        LocalIndication provides  rippleIndication,
       content= content
    )
}

object AppTheme{
    val colorScheme: AppColorScheme
        @Composable get() = LocalAppColorScheme.current

    val typography: AppColorScheme
        @Composable get() = LocalAppColorScheme.current

    val shape: AppColorScheme
        @Composable get() = LocalAppColorScheme.current

    val size: AppColorScheme
        @Composable get() = LocalAppColorScheme.current
}