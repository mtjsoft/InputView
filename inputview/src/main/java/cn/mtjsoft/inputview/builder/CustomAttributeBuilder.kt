package cn.mtjsoft.inputview.builder

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import cn.mtjsoft.inputview.R
import cn.mtjsoft.inputview.iml.CustomAttributeIml

/**
 * @author mtj
 * @date 2022/3/11
 * @desc
 * @email mtjsoft3@gmail.com
 */
class CustomAttributeBuilder(context: Context, attributeSet: AttributeSet?) : CustomAttributeIml {
    /**
     * 输入框部分
     */
    // 控件背景色
    @ColorRes
    private var inputViewBackgroundColor = R.color.input_bg

    // 语音图标
    @DrawableRes
    private var audioIconResId = R.mipmap.ic_read_voice

    // 表情图标
    @DrawableRes
    private var emojiIconResId = R.mipmap.ic_emjio

    // 功能图标
    @DrawableRes
    private var funCationIconResId = R.mipmap.ic_add_image

    // 键盘图标
    @DrawableRes
    private var keyboardIconResId = R.mipmap.icon_keyboard

    // 功能面板列数
    private var funCationColumnNumber = 4

    // 功能面板及表情面板背景色
    @ColorRes
    private var panelBackgroundColor = R.color.panel_function_bg

    // 发送按钮背景
    @DrawableRes
    private var sendBtnBackgroundResId = R.drawable.btn_normal

    // Emoji 表情列数
    private var emojiColumnNumber = 8

    // Emoji 类型列表背景色
    @ColorRes
    private var emojiTypeListBackgroundColor = R.color.emoji_type_list_bg

    // Emoji 类型 选中的背景色
    @ColorRes
    private var emojiTypeSelectBgColor = R.color.emoji_type_SelectBgColor

    // Emoji 返回图标
    @DrawableRes
    private var emojiBackIconResId = R.mipmap.revert

    // Emoji 删除图标
    @DrawableRes
    private var emojiDeleteIconResId = R.mipmap.delete_icon

    // 录音最大时长 （秒）
    private var voiceMaxTime = 60

    // 录音最小时长 （秒）
    private var voiceMinTime = 1

    init {
        if (attributeSet != null) {
            context.obtainStyledAttributes(attributeSet, R.styleable.InputView)
                .apply {
                    // 控件背景色
                    getResourceId(R.styleable.InputView_inputViewBgColor, 0).also {
                        if (it != 0) {
                            inputViewBackgroundColor = it
                        }
                    }
                    // 语音图标
                    getResourceId(R.styleable.InputView_audioIconResId, 0).also {
                        if (it != 0) {
                            audioIconResId = it
                        }
                    }
                    // 表情图标
                    getResourceId(R.styleable.InputView_emojiIconResId, 0).also {
                        if (it != 0) {
                            emojiIconResId = it
                        }
                    }
                    // 功能图标
                    getResourceId(R.styleable.InputView_funCationIconResId, 0).also {
                        if (it != 0) {
                            funCationIconResId = it
                        }
                    }
                    // 键盘图标
                    getResourceId(R.styleable.InputView_keyboardIconResId, 0).also {
                        if (it != 0) {
                            keyboardIconResId = it
                        }
                    }
                    // 功能面板列数
                    funCationColumnNumber = getInt(R.styleable.InputView_funCationColumnNumber, funCationColumnNumber)
                    // 功能面板及表情面板背景色
                    getResourceId(R.styleable.InputView_panelBackgroundColor, 0).also {
                        if (it != 0) {
                            panelBackgroundColor = it
                        }
                    }
                    // 发送按钮背景
                    getResourceId(R.styleable.InputView_sendBtnBackgroundResId, 0).also {
                        if (it != 0) {
                            sendBtnBackgroundResId = it
                        }
                    }
                    // Emoji 表情列数
                    emojiColumnNumber = getInt(R.styleable.InputView_emojiColumnNumber, emojiColumnNumber)
                    // Emoji 类型列表背景色
                    getResourceId(R.styleable.InputView_emojiTypeListBgColor, 0).also {
                        if (it != 0) {
                            emojiTypeListBackgroundColor = it
                        }
                    }
                    // Emoji 类型 选中的背景色
                    getResourceId(R.styleable.InputView_emojiTypeSelectBgColor, 0).also {
                        if (it != 0) {
                            emojiTypeSelectBgColor = it
                        }
                    }
                    // Emoji 返回图标
                    getResourceId(R.styleable.InputView_emojiBackIconResId, 0).also {
                        if (it != 0) {
                            emojiBackIconResId = it
                        }
                    }
                    // Emoji 删除图标
                    getResourceId(R.styleable.InputView_emojiDeleteIconResId, 0).also {
                        if (it != 0) {
                            emojiDeleteIconResId = it
                        }
                    }
                    // 录音最小时长 （秒）
                    getInt(R.styleable.InputView_voiceMinTime, voiceMinTime).also {
                        if (it != 0) {
                            voiceMinTime = it
                        }
                    }
                    // 录音最大时长 （秒）
                    getInt(R.styleable.InputView_voiceMaxTime, voiceMaxTime).also {
                        if (it != 0) {
                            voiceMaxTime = it
                        }
                    }
                    recycle()
                }
        }
    }

    override fun inputViewBackgroundColor(): Int = inputViewBackgroundColor

    override fun audioIconResId(): Int = audioIconResId

    override fun emojiIconResId(): Int = emojiIconResId

    override fun funCationIconResId(): Int = funCationIconResId

    override fun keyboardIconResId(): Int = keyboardIconResId

    override fun funCationColumnNumber(): Int = funCationColumnNumber

    override fun panelBackgroundColor(): Int = panelBackgroundColor

    override fun sendBtnBackgroundResId(): Int = sendBtnBackgroundResId

    override fun emojiColumnNumber(): Int = emojiColumnNumber

    override fun emojiTypeListBackgroundColor(): Int = emojiTypeListBackgroundColor

    override fun emojiTypeSelectBgColor(): Int = emojiTypeSelectBgColor

    override fun emojiBackIconResId(): Int = emojiBackIconResId

    override fun emojiDeleteIconResId(): Int = emojiDeleteIconResId

    override fun voiceMaxTime(): Int = voiceMaxTime

    override fun voiceMinTime(): Int = voiceMinTime
}