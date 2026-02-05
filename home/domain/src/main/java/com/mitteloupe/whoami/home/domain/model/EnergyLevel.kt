package com.mitteloupe.whoami.home.domain.model

enum class EnergyLevel(
    val checkIntervalMs: Long, // ⏱️ 查岗间隔 (毫秒)
    val description: String
) {
    /**
     * ⚡️ 满电：45分钟查一次岗。
     * 允许用户长时间沉浸，因为他现在自控力强。
     */
    HIGH(45 * 60 * 1000L, "⚡️ 状态极佳"),

    /**
     * 🔋 正常：25分钟查一次岗 (标准番茄钟)。
     */
    MEDIUM(25 * 60 * 1000L, "🔋 还行吧"),

    /**
     * 🪫 省电：10分钟查一次岗。
     * 就像重症监护一样，频繁确认状态，防止立刻走神。
     */
    LOW(10 * 60 * 1000L, "🪫 省电模式");
}
