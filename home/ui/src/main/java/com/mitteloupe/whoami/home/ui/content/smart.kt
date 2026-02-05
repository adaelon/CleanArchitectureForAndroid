package com.mitteloupe.whoami.home.ui.content


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.compose.Balloon
import com.skydoves.balloon.compose.rememberBalloonBuilder
import com.mitteloupe.whoami.home.ui.R

/**
 * 智能小猫组件
 * @param lottieResId 你的 JSON 资源 ID (例如 R.raw.cat_animation)
 * @param message 小猫要说的话，如果不为 null 就会弹出气泡
 * @param modifier 修饰符
 */
@Composable
fun SmartCat(
    lottieResId: Int,
    message: String? = null,
    modifier: Modifier = Modifier,
    catSize: Dp = 150.dp
) {
    // 1. 配置气泡 (Balloon)
    val builder = rememberBalloonBuilder {
        setArrowSize(10)
        setArrowOrientation(ArrowOrientation.BOTTOM)
        setArrowPosition(0.5f)
        setCornerRadius(12f)
        setBackgroundColor(Color.White.hashCode()) // 气泡背景色
        setTextColor(Color.Black.hashCode())     // 文字颜色
        setTextSize(14f)
        setPadding(12)
        setMarginBottom(4) // 气泡和小猫头顶的距离
        setBalloonAnimation(BalloonAnimation.FADE)
        setIsVisibleArrow(true)
        setAutoDismissDuration(3000L) // 3秒自动消失
    }

    // 2. 配置 Lottie 动画
    // 假设你把 json 放到了 res/raw/cat_animation.json
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieResId))

    // 控制动画播放 (无限循环)
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        // 3. 气泡包裹着小猫
        Balloon(
            builder = builder,
            balloonContent = {
                if (message != null) {
                    Text(
                        text = message,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        ) { balloonWindow ->

            // 当 message 变化时，显示气泡
            LaunchedEffect(message) {
                if (message != null) {
                    balloonWindow.showAlignTop()
                }
            }

            // 4. 显示 Lottie 动画
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .size(catSize)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null // 点击无波纹，只是为了触发气泡交互（可选）
                    ) {
                        balloonWindow.showAlignTop()
                    }
            )
        }
    }
}
