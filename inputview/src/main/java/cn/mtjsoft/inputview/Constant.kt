package cn.mtjsoft.inputview

import android.media.AudioFormat

object Constant {
    /**
     * 录音播放参数
     */
    // 设置音频采样率，44100是目前的标准，单位hz
    const val FREQUENCY = 44100

    // 设置音频的录制的声道CHANNEL_IN_STEREO为双声道，CHANNEL_CONFIGURATION_MONO为单声道
    const val CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_STEREO

    // 音频数据格式:PCM 16位每个样本。保证设备支持。PCM 8位每个样本。不一定能得到设备支持。
    const val AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT
}