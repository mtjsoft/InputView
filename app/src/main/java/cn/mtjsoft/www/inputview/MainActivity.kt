package cn.mtjsoft.www.inputview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import cn.mtjsoft.inputview.InputView
import cn.mtjsoft.inputview.iml.AdapterItemClickListener
import cn.mtjsoft.inputview.iml.SendClickListener
import cn.mtjsoft.inputview.iml.VoiceOverListener
import cn.mtjsoft.inputview.manager.PCMAudioPlayer

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputView = findViewById<InputView>(R.id.bottom_input)
        inputView.setFuncationClickListener(object : AdapterItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                Toast.makeText(baseContext, "功能点击：$position", Toast.LENGTH_SHORT).show()
            }
        }).setSendClickListener(object : SendClickListener {
            override fun onSendClick(view: View, content: String) {
                Toast.makeText(baseContext, "发送$content", Toast.LENGTH_SHORT).show()
            }
        }).setVoiceOverListener(object : VoiceOverListener {
            override fun onOver(fileName: String, filePath: String, duration: Int) {
                Toast.makeText(baseContext, "录音时长：$duration 秒，$filePath", Toast.LENGTH_SHORT).show()
                Log.e("mtj","地址：$filePath \n时长：$duration 秒")
                PCMAudioPlayer.instance.startPlay(filePath)
            }
        })
    }

    override fun onPause() {
        super.onPause()
        PCMAudioPlayer.instance.release()
    }
}