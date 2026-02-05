package com.mitteloupe.whoami.home.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.mitteloupe.whoami.home.domain.model.ActivityCategory
import com.mitteloupe.whoami.home.domain.model.EnergyLevel
import com.mitteloupe.whoami.home.ui.R

@Composable
fun SelectionContent(
    modifier: Modifier = Modifier,
    onCategorySelected: (ActivityCategory, EnergyLevel) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 1. 顶部：智能小猫 (Smart Cat)
        // 提示：确保 res/raw 下有 cat_animation.json
        SmartCat(
            lottieResId = R.raw.loadercat,
            message = "今天想做点什么？",
            modifier = Modifier.padding(top = 32.dp, bottom = 24.dp)
        )

        Text(
            text = "选择一个线头开始",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // 2. 中间：三个大按钮
        // 这里暂时默认选 "HIGH" 能量，你可以后续加个能量选择条
        val defaultEnergy = EnergyLevel.HIGH

        CategoryButton(
            title = "学点东西",
            iconRes = R.drawable.icon_city, // 替换成学习图标，如 icon_book
            color = Color(0xFF64B5F6), // 蓝色
            onClick = { onCategorySelected(ActivityCategory.LEARNING, defaultEnergy) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CategoryButton(
            title = "清理环境",
            iconRes = R.drawable.icon_post_code, // 替换成扫帚图标
            color = Color(0xFF81C784), // 绿色
            onClick = { onCategorySelected(ActivityCategory.CLEANING, defaultEnergy) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CategoryButton(
            title = "动动身体",
            iconRes = R.drawable.icon_region, // 替换成运动图标
            color = Color(0xFFFFB74D), // 橙色
            onClick = { onCategorySelected(ActivityCategory.MOVEMENT, defaultEnergy) }
        )
    }
}

@Composable
fun CategoryButton(
    title: String,
    iconRes: Int,
    color: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSelection() {
    SelectionContent(
        onCategorySelected = { _, _ -> }
    )
}
