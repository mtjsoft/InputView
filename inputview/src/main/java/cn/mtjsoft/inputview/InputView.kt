package cn.mtjsoft.inputview

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.text.Editable
import android.text.TextUtils
import android.util.ArrayMap
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.mtjsoft.inputview.adapter.EmojiAdapter
import cn.mtjsoft.inputview.adapter.EmojiTypeAdapter
import cn.mtjsoft.inputview.entity.EmojiEntry
import cn.mtjsoft.inputview.iml.AdapterItemClickListener
import java.lang.Exception
import java.lang.StringBuilder
import java.util.*

/**
 * 自定义IM输入控件
 * 包含表情库、操作面板
 */
class InputView : LinearLayout {

    private val EMOJI_ASSERT_SRC = "emoji"
    private val EMPTY_EMOJI_CODE = "200D"
    private val EMOJI_SUFFIX = ".png"
    private val FREQUENT_TYPE = "frequent"
    private val FACE_TYPE = "face"
    private val PERSON_TYPE = "person"
    private val NATURE_TYPE = "nature"
    private val FOOD_TYPE = "food"
    private val TRAVEL_TYPE = "travel"
    private val ACTIVITY_TYPE = "activity"
    private val OBJECT_TYPE = "object"
    private val SYMBOL_TYPE = "symbol"
    private val FLAG_TYPE = "flag"

    private lateinit var inputView: LinearLayout
    private lateinit var mEtInput: EditText
    private lateinit var openEmojiView: ImageView

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var gridLayoutManager: GridLayoutManager

    private val emojiGroup = ArrayMap<String, LinkedList<EmojiEntry>>()
    private val emojiData = LinkedList<EmojiEntry>()
    private var emojiAdapter: EmojiAdapter? = null

    private lateinit var emojiTypeView: LinearLayout
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var emojiTypeAdapter: EmojiTypeAdapter? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        orientation = VERTICAL
        initView()
        initEmojiTypeData()
        if (context is androidx.appcompat.app.AppCompatActivity) {
        }
    }

    private val MAIN_HANDLER = Handler(Looper.getMainLooper())

    private fun initView() {
        // 输入控制面板
        inputView =
            LayoutInflater.from(context).inflate(R.layout.input_view, this, true)
                .findViewById(R.id.inputLayout)
        // 表情库、常用面板
        mRecyclerView =
            LayoutInflater.from(context)
                .inflate(R.layout.symbols_emoji_recycleview, this, true)
                .findViewById(R.id.gv_symbols_emoji)
        // 表情类型
        emojiTypeView =
            LayoutInflater.from(context)
                .inflate(R.layout.emoji_type_view, this, true)
                .findViewById(R.id.ll_symbols_emoji_type_item)

        inputViewChildView()
    }

    /**
     * 输入控制面板 子view
     */
    private fun inputViewChildView() {
        mEtInput = findViewById(R.id.et_chat_input)
        mEtInput.setOnClickListener {
            hideEmojiView(true)
        }
        val sendBtn = findViewById<TextView>(R.id.bt_chat_send)
        val addImageView = findViewById<ImageView>(R.id.iv_add_image)
        mEtInput.addTextChangedListener {
            it?.let {
                if (TextUtils.isEmpty(it.toString())) {
                    sendBtn.visibility = GONE
                    addImageView.visibility = VISIBLE
                } else {
                    sendBtn.visibility = VISIBLE
                    addImageView.visibility = GONE
                }
            }
        }
        sendBtn.setOnClickListener {
            // 点击发送消息
            mEtInput.setText("")
        }
        addImageView.setOnClickListener {
            // 点击添加，切换功能面板
        }
        // 表情显示/隐藏
        openEmojiView = findViewById(R.id.iv_emoji)
        openEmojiView.setOnClickListener {
            // 展开或隐藏表情面板
            if ("0" == it.tag) {
                // 展开
                openEmojiView.setImageResource(R.mipmap.icon_keyboard)
                openEmojiView.tag = "1"
                hideKeyboard(it.windowToken)
                MAIN_HANDLER.postDelayed({
                    initEmojiData()
                }, 100)
            } else {
                hideEmojiView(true)
            }
        }
    }

    /**
     * 初始化emoji数据
     */
    private fun initEmojiData() {
        val typeEns = context.resources.getStringArray(R.array.emoji_types_en)
        if (emojiGroup.size == typeEns.size) {
            // 数据已初始化过了
            setDefaultFaceEmoji()
        } else {
            Thread {
                typeEns.mapIndexed { _, dir ->
                    val tempFiles: Array<String>? = context.assets.list("$EMOJI_ASSERT_SRC/$dir")
                    val tempEmojis = LinkedList<EmojiEntry>()
                    tempFiles?.mapIndexed { _, s ->
                        tempEmojis.add(
                            EmojiEntry(
                                s.substringBefore(EMOJI_SUFFIX),
                                s,
                                "$EMOJI_ASSERT_SRC/$dir/$s"
                            )
                        )
                    }
                    emojiGroup.put(dir, tempEmojis)
                }
                MAIN_HANDLER.post {
                    setDefaultFaceEmoji()
                }
            }.start()
        }
    }

    /**
     * 设置默认黄脸表情
     */
    private fun setDefaultFaceEmoji() {
        emojiData.clear()
        emojiGroup[FACE_TYPE]?.let {
            emojiData.addAll(it)
        }
        emojiAdapter = EmojiAdapter(context, emojiData, object : AdapterItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                // emoji点击，添加入输入框
                val result = getEmoji(emojiData[position].code)
                mEtInput.text.insert(mEtInput.selectionStart, result)
            }
        })
        gridLayoutManager = GridLayoutManager(context, 6)
        mRecyclerView.layoutManager = gridLayoutManager
        mRecyclerView.adapter = emojiAdapter
        // 显示
        mRecyclerView.visibility = VISIBLE
        emojiTypeView.visibility = VISIBLE
        //
        emojiTypeAdapter?.setClickPosition(0)
        linearLayoutManager.scrollToPosition(0)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setEmojiByType(type: String) {
        emojiData.clear()
        emojiGroup[type]?.let {
            emojiData.addAll(it)
        }
        emojiAdapter?.notifyDataSetChanged()
        gridLayoutManager.scrollToPosition(0)
    }

    /**
     * 初始化emoji类型数据
     */
    private fun initEmojiTypeData() {
        val recyclerView: RecyclerView = findViewById(R.id.rv_symbols_emoji_type)
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = linearLayoutManager
        val typeEns = context.resources.getStringArray(R.array.emoji_types_en)
        emojiTypeAdapter = EmojiTypeAdapter(
            context,
            context.resources.getStringArray(R.array.emoji_types).toList(),
            object : AdapterItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    setEmojiByType(typeEns[position])
                }
            })
        recyclerView.adapter = emojiTypeAdapter
        findViewById<ImageView>(R.id.ib_symbols_emoji_type_back).setOnClickListener {
            // 返回按键
            hideEmojiView(true)
        }

        findViewById<ImageView>(R.id.iv_symbols_emoji_type_delete).setOnClickListener {
            // 删除按键
            deleteEmoji(mEtInput)
        }
    }

    /**
     * 隐藏表情面板
     * 并设置是否弹出软键盘
     */
    private fun hideEmojiView(showSoftInput: Boolean) {
        openEmojiView.tag = "0"
        openEmojiView.setImageResource(R.mipmap.ic_emjio)
        mRecyclerView.visibility = GONE
        emojiTypeView.visibility = GONE
        if (showSoftInput) {
            // 获取焦点
            mEtInput.requestFocus()
            val manager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            manager?.showSoftInput(mEtInput, 0)
        }
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     */
    private fun hideKeyboard(token: IBinder) {
        val im = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        im?.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    /**
     * 根据code 获取 Emoji
     */
    fun getEmoji(code: String): String {
        val codes = code.split("_")
        val result = StringBuilder()
        for (s in codes) {
            result.append(String(Character.toChars(s.toInt(16))))
        }
        return result.toString()
    }

    /**
     * 删除输入框中的Emoji
     *
     * @param mEtInput 输入框
     */
    fun deleteEmoji(mEtInput: EditText) {
        val keyCode = KeyEvent.KEYCODE_DEL
        val keyEventDown = KeyEvent(KeyEvent.ACTION_DOWN, keyCode)
        val keyEventUp = KeyEvent(KeyEvent.ACTION_UP, keyCode)
        mEtInput.onKeyDown(keyCode, keyEventDown)
        mEtInput.onKeyUp(keyCode, keyEventUp)
    }
}