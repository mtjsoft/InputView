package cn.mtjsoft.inputview.entity

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import cn.mtjsoft.inputview.R
import java.io.Serializable

data class FunctionEntity(
    @DrawableRes
    val imgResId: Int,
    val name: String,
    @ColorRes
    val textColorResId: Int = R.color.black,
    val textSizeSp: Float = 14F
) : Serializable
