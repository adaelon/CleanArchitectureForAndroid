package com.mitteloupe.whoami.home.presentation.mapper

import com.mitteloupe.whoami.home.presentation.model.ErrorPresentationModel
import java.io.IOException
import javax.inject.Inject

class ExceptionPresentationMapper @Inject constructor() {

    /**
     * 将任何异常转换为 UI 显示的错误模型
     */
    fun toPresentation(throwable: Throwable): ErrorPresentationModel {
        // 这里根据异常的类型，返回不同的文案
        return when (throwable) {
            // 1. 如果是网络或 IO 问题 (虽然你的 App 是离线的，但如果有云同步可能会用到)
            is IOException -> ErrorPresentationModel(
                title = "连接断开了",
                description = "好像读取不到数据，请检查一下状态。"
            )

            // 2. 如果是业务逻辑异常 (比如你自定义的 "任务已满" 异常)
            is IllegalStateException -> ErrorPresentationModel(
                title = "哎呀",
                description = throwable.message ?: "操作好像有点问题。"
            )

            // 3. 兜底：未知的错误
            else -> ErrorPresentationModel(
                title = "出错了",
                description = "发生了一个未知错误: ${throwable.localizedMessage}"
            )
        }
    }
}
