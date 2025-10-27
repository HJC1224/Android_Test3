package com.example.test31

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat

class MainActivity : AppCompatActivity() {
    // 列表控件
    private var lvAnimals: ListView? = null

    // 数据源：存储每个列表项的“名称”和“图片资源”
    private var dataList: MutableList<MutableMap<String?, Any?>>? = null

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 初始化控件
        lvAnimals = findViewById(R.id.lv_animals)
        // 初始化数据
        initData()
        // 设置适配器
        setAdapter()
        // 设置列表点击事件
        setListViewClick()
    }

    // 初始化列表数据（动物名称+图片）
    private fun initData() {
        dataList = ArrayList<MutableMap<String?, Any?>>()
        // 添加数据（参数：名称，图片资源ID）
        addItem("狮子", R.drawable.lion)
        addItem("老虎", R.drawable.tiger)
        addItem("猴子", R.drawable.monkey)
        addItem("小狗", R.drawable.dog)
        addItem("小猫", R.drawable.cat)
        addItem("大象", R.drawable.elephant)
    }

    // 向数据源添加一个列表项
    private fun addItem(name: String?, imageId: Int) {
        val item: MutableMap<String?, Any?> = HashMap<String?, Any?>()
        item.put("name", name) // 键“name”对应名称
        item.put("image", imageId) // 键“image”对应图片资源
        dataList!!.add(item)
    }

    // 设置SimpleAdapter
    private fun setAdapter() {
        val adapter = SimpleAdapter(
            this,  // 上下文
            dataList,  // 数据源
            R.layout.item_layout,  // 列表项布局
            arrayOf<String>("name", "image"),  // 数据源的键（与item中的键对应）
            intArrayOf(R.id.tv_name, R.id.iv_img) // 布局中控件的ID（与键一一对应）
        )
        lvAnimals!!.setAdapter(adapter)
    }

    // 设置列表项点击事件（Toast提示+发送通知）
    private fun setListViewClick() {
        lvAnimals!!.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // 获取当前点击项的数据
                val item = dataList!!.get(position)
                val animalName = item.get("name") as String?

                // 1. 显示Toast提示
                Toast.makeText(this@MainActivity, "你选择了：" + animalName, Toast.LENGTH_SHORT)
                    .show()

                // 2. 发送通知
                sendNotification(animalName, position)
            }
        })
    }

    // 发送通知的方法
    private fun sendNotification(title: String?, notificationId: Int) {
        // 获取通知管理器
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 1. 创建通知渠道（Android 8.0+ 必须）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 渠道ID（唯一）、渠道名称（用户可见）、重要性
            val channel = NotificationChannel(
                "animal_channel",
                "动物列表通知",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // 2. 构建通知内容
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, "animal_channel")
            .setSmallIcon(R.mipmap.ic_launcher) // 通知小图标（必须设置）
            .setContentTitle("选中提示") // 通知标题
            .setContentText("你选择了：" + title) // 通知内容
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) // 优先级
            .setAutoCancel(true) // 点击后自动消失

        // 3. 发送通知（notificationId用于区分不同通知）
        val notification = builder.build()
        notificationManager.notify(notificationId, notification)
    }
}