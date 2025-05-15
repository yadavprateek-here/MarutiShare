package com.rudra.marutishare.UIScreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.rudra.marutishare.UIScreen.backgroundIndicator
import com.rudra.marutishare.UIScreen.foregroundIndicator

//@Composable
//fun CustomComponent(
//    modifier: Modifier = Modifier,
//    canvasSize: Dp = 300.dp,
//    indicatorValue: Float = 0f,
//    maxIndicatorValue: Float = 50f,
//    backgroundIndicatorColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
//    backgroundIndicatorStrokeWidth: Float = 20f,
//    foregroundIndicatorColor: Color = Color(0xFF6F43FA),
//    foregroundIndicatorStrokeWidth: Float = 20f,
//    indicatorStrokeCap: StrokeCap = StrokeCap.Round,
//    bigTextFontSize: TextUnit = 20.sp,
//    bigTextColor: Color = MaterialTheme.colorScheme.onSurface,
//    bigTextSuffix: String = indicatorValue.toString(),
//    smallText: String = "MBps",
//    smallTextFontSize:TextUnit =  16.sp,
//    smallTextColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
//) {
//    var allowedIndicatorValue by remember { mutableStateOf(maxIndicatorValue) }
//    allowedIndicatorValue = if (indicatorValue <= maxIndicatorValue) {
//        indicatorValue
//    } else {
//        maxIndicatorValue
//    }
//
//    var animatedIndicatorValue by remember { mutableStateOf(0f) }
//    LaunchedEffect(key1 = allowedIndicatorValue) {
//        animatedIndicatorValue = allowedIndicatorValue
//    }
//
//    val percentage =
//        (animatedIndicatorValue / maxIndicatorValue) * 100
//
//    val sweepAngle by animateFloatAsState(
//        targetValue = (2.4 * percentage).toFloat(),
//        animationSpec = tween(200)
//    )
//
//    val receivedValue by animateFloatAsState(
//        targetValue = allowedIndicatorValue,
//        animationSpec = tween(1000)
//    )
//
//    val animatedBigTextColor by animateColorAsState(
//        targetValue = if (allowedIndicatorValue == 0f)
//            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
//        else
//            bigTextColor,
//        animationSpec = tween(1000)
//    )
//
//    Column(
//        modifier = Modifier
//            .size(canvasSize)
//            .drawBehind {
//                val componentSize = size / 1.25f
//                backgroundIndicator(
//                    componentSize = componentSize,
//                    indicatorColor = backgroundIndicatorColor,
//                    indicatorStrokeWidth = backgroundIndicatorStrokeWidth,
//
//                    )
//                foregroundIndicator(
//                    sweepAngle = sweepAngle,
//                    componentSize = componentSize,
//                    indicatorColor = foregroundIndicatorColor,
//                    indicatorStrokeWidth = foregroundIndicatorStrokeWidth
//                )
//            },
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        EmbeddedElements(
//            bigText = receivedValue,
//            bigTextFontSize = bigTextFontSize,
//            bigTextColor = animatedBigTextColor,
//            bigTextSuffix = bigTextSuffix,
//            smallText = smallText,
//            smallTextColor = smallTextColor,
//            smallTextFontSize = smallTextFontSize
//        )
//    }
//}


@Composable
fun CustomComponent(
    modifier: Modifier = Modifier,
    canvasSize: Dp = 300.dp,
    indicatorValue: Float = 0f,
    maxIndicatorValue: Float = 50f,
    backgroundIndicatorColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
    backgroundIndicatorStrokeWidth: Float = 20f,
    foregroundIndicatorColor: Color = Color(0xFF6F43FA),
    foregroundIndicatorStrokeWidth: Float = 20f,
    indicatorStrokeCap: StrokeCap = StrokeCap.Round,
    bigTextFontSize: TextUnit = 20.sp,
    bigTextColor: Color = MaterialTheme.colorScheme.onSurface,
    bigTextSuffix: String = indicatorValue.toString(),
    smallText: String = "MBps",
    smallTextFontSize: TextUnit = 16.sp,
    smallTextColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
) {
    var allowedIndicatorValue by remember { mutableStateOf(maxIndicatorValue) }
    allowedIndicatorValue = if (indicatorValue <= maxIndicatorValue) indicatorValue else maxIndicatorValue

    var animatedIndicatorValue by remember { mutableStateOf(0f) }
    LaunchedEffect(key1 = allowedIndicatorValue) {
        animatedIndicatorValue = allowedIndicatorValue
    }

    val percentage = (animatedIndicatorValue / maxIndicatorValue) * 100
    val sweepAngle by animateFloatAsState(targetValue = (2.4 * percentage).toFloat(), animationSpec = tween(200))
    val receivedValue by animateFloatAsState(targetValue = allowedIndicatorValue, animationSpec = tween(1000))
    val animatedBigTextColor by animateColorAsState(
        targetValue = if (allowedIndicatorValue == 0f)
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
        else
            bigTextColor,
        animationSpec = tween(1000)
    )

    Column(
        modifier = modifier
            .size(canvasSize)
            .drawBehind {
                val componentSize = size / 1.25f
                backgroundIndicator(componentSize, backgroundIndicatorColor, backgroundIndicatorStrokeWidth)
                foregroundIndicator(sweepAngle, componentSize, foregroundIndicatorColor, foregroundIndicatorStrokeWidth)
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmbeddedElements(
            bigText = receivedValue,
            bigTextFontSize = bigTextFontSize,
            bigTextColor = animatedBigTextColor,
            bigTextSuffix = bigTextSuffix,
            smallText = smallText,
            smallTextColor = smallTextColor,
            smallTextFontSize = smallTextFontSize
        )
    }
}


fun DrawScope.backgroundIndicator(
    componentSize: androidx.compose.ui.geometry.Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float,
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 150f,
        sweepAngle = 240f,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}

fun DrawScope.foregroundIndicator(
    sweepAngle: Float,
    componentSize: androidx.compose.ui.geometry.Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float,
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 150f,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}

@Composable
fun EmbeddedElements(
    bigText: Float,
    bigTextFontSize: TextUnit,
    bigTextColor: Color,
    bigTextSuffix: String,
    smallText: String,
    smallTextColor: Color,
    smallTextFontSize: TextUnit
) {
    Text(
        text = smallText,
        color = smallTextColor,
        fontSize = smallTextFontSize,
        textAlign = TextAlign.Center
    )
    Text(
        text = "$bigText",// ${bigTextSuffix.take(2)}",
        color = bigTextColor,
        fontSize = bigTextFontSize,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
}