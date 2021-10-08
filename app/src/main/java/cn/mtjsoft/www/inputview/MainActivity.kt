package cn.mtjsoft.www.inputview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import cn.mtjsoft.inputview.InputView
import cn.mtjsoft.inputview.entity.FunctionEntity
import cn.mtjsoft.inputview.iml.AdapterItemClickListener
import cn.mtjsoft.inputview.iml.SendClickListener
import cn.mtjsoft.inputview.iml.VoiceOverListener
import cn.mtjsoft.inputview.manager.PCMAudioPlayer
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputView = findViewById<InputView>(R.id.bottom_input)

        // 功能面板数据
        val functionData = LinkedList<FunctionEntity>()
        val ids = listOf(
            cn.mtjsoft.inputview.R.mipmap.btn_skb_record,
            cn.mtjsoft.inputview.R.mipmap.btn_skb_picture,
            cn.mtjsoft.inputview.R.mipmap.btn_skb_file
        )
        val names = listOf("拍照", "相册", "文件")
        ids.mapIndexed { index, i ->
            functionData.add(FunctionEntity(i, names[index]))
        }

        inputView
            // 设置功能面板数据
            .setFuncationData(functionData)
            // 设置功能面板点击回调
            .setFuncationClickListener(object : AdapterItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    Toast.makeText(baseContext, "功能点击：${functionData[position].name}", Toast.LENGTH_SHORT).show()
                }
            })
            // 设置发送按钮点击回调
            .setSendClickListener(object : SendClickListener {
                override fun onSendClick(view: View, content: String) {
                    Toast.makeText(baseContext, "发送$content", Toast.LENGTH_SHORT).show()
                }
            })
            // 设置录音完成回调
            .setVoiceOverListener(object : VoiceOverListener {
                override fun onOver(fileName: String, filePath: String, duration: Int) {
                    Toast.makeText(baseContext, "录音时长：$duration 秒，$filePath", Toast.LENGTH_SHORT)
                        .show()
                    Log.e("mtj", "地址：$filePath \n时长：$duration 秒")
                    // 播放PCM格式音频
                    PCMAudioPlayer.instance.startPlay(filePath)
                }
            })
    }

    override fun onPause() {
        super.onPause()
        // 释放音频播放
        PCMAudioPlayer.instance.release()
    }
}