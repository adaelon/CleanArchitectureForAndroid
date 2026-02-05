package com.mitteloupe.whoami.home.ui.view

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.delay

@Composable
fun SpriteAnimation(
    drawableId: Int,          // 图片资源ID (R.drawable.cat_run)
    frameCount: Int,          // 这张图里一共有几个小猫 (你的图是 8 个)
    frameDuration: Long = 100,// 每一帧停留多久 (毫秒)，越小跑得越快
    modifier: Modifier = Modifier
) {
    // 1. 加载图片
    val imageBitmap = ImageBitmap.imageResource(id = drawableId)

    // 2. 计算每一帧的宽度
    // 假设图片是横着排的，总宽度 / 帧数 = 单个小猫的宽度
    val frameWidth = imageBitmap.width / frameCount
    val frameHeight = imageBitmap.height

    // 3. 动画计时器
    var currentFrameIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(frameDuration)
            // 循环播放：0 -> 1 -> ... -> 7 -> 0
            currentFrameIndex = (currentFrameIndex + 1) % frameCount
        }
    }

    // 4. 绘制
    Canvas(modifier = modifier) {
        // 计算当前要显示的那一小块区域 (src)
        val srcOffset = IntOffset(
            x = currentFrameIndex * frameWidth,
            y = 0
        )
        val srcSize = IntSize(frameWidth, frameHeight)

        // 关键点：设置 FilterQuality.None
        // 这样像素放大的时候边缘是锯齿状的（清晰），而不是模糊的
        drawImage(
            image = imageBitmap,
            srcOffset = srcOffset,
            srcSize = srcSize,
            dstSize = IntSize(size.width.toInt(), size.height.toInt()),
            filterQuality = FilterQuality.None
        )
    }
}
