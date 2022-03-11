package cn.mtjsoft.inputview.iml

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

/**
 * @author mtj
 * @date 2022/3/11
 * @desc
 * @email mtjsoft3@gmail.com
 */
interface CustomAttributeIml {

    // 控件背景色
    @ColorRes
    fun inputViewBackgroundColor(): Int

    // 语音图标
    @DrawableRes
    fun audioIconResId(): Int

    // 表情图标
    @DrawableRes
    fun emojiIconResId(): Int

    // 功能图标
    @DrawableRes
    fun funCationIconResId(): Int

    // 键盘图标
    @DrawableRes
    fun keyboardIconResId(): Int

    // 功能面板列数
    fun funCationColumnNumber(): Int

    // 功能面板及表情面板背景色
    @ColorRes
    fun panelBackgroundColor(): Int

    // 发送按钮背景
    @DrawableRes
    fun sendBtnBackgroundResId(): Int

    // Emoji 表情列数
    fun emojiColumnNumber(): Int

    // Emoji 类型列表背景色
    @ColorRes
    fun emojiTypeListBackgroundColor(): Int

    // Emoji 类型 选中的背景色
    @ColorRes
    fun emojiTypeSelectBgColor(): Int

    // Emoji 返回图标
    @DrawableRes
    fun emojiBackIconResId(): Int

    // Emoji 删除图标
    @DrawableRes
    fun emojiDeleteIconResId(): Int

    // 录音最大时长 （秒）
    fun voiceMaxTime(): Int

    // 录音最小时长 （秒）
    fun voiceMinTime(): Int
}