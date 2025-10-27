package com.example.test33

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import android.graphics.Color // 导入颜色类

class MainActivity : AppCompatActivity() {

    // 使用lateinit声明非空变量（避免空安全错误）
    private lateinit var tvTest: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 初始化TextView（确保不为null，否则lateinit会抛异常）
        tvTest = findViewById(R.id.tv_test)
    }

    // 加载菜单
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // 处理菜单点击
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // 字体大小：小（10sp）
            R.id.font_small -> {
                tvTest.textSize = 10f // Kotlin中需显式加f表示Float
                true
            }
            // 字体大小：中（16sp）
            R.id.font_medium -> {
                tvTest.textSize = 16f
                true
            }
            // 字体大小：大（20sp）
            R.id.font_large -> {
                tvTest.textSize = 20f
                true
            }
            // 普通菜单项：弹出Toast
            R.id.item_normal -> {
                Toast.makeText(this, "您点击了普通菜单项", Toast.LENGTH_SHORT).show()
                true
            }
            // 字体颜色：红色（使用Color类的常量，通用且无版本问题）
            R.id.color_red -> {
                tvTest.setTextColor(Color.RED)
                true
            }
            // 字体颜色：黑色
            R.id.color_black -> {
                tvTest.setTextColor(Color.BLACK)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}