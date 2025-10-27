package com.example.test32

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 【示例】点击按钮时显示对话框（需在布局中添加按钮，或直接在onCreate中触发）
        val showDialogBtn: Button = findViewById(R.id.btn_show_dialog) // 假设布局中有此按钮
        showDialogBtn.setOnClickListener(View.OnClickListener { v: View? ->
            showCustomAlertDialog()
        })
    }

    private fun showCustomAlertDialog() {
        // 1. 加载自定义布局
        val inflater: LayoutInflater = getLayoutInflater()
        val dialogView: View = inflater.inflate(R.layout.dialog_login, null)

        // 2. 找到布局中的控件，用于设置交互逻辑
        val btnCancel = dialogView.findViewById<Button?>(R.id.btn_cancel)
        val btnSignIn = dialogView.findViewById<Button?>(R.id.btn_sign_in)
        val etUsername = dialogView.findViewById<EditText?>(R.id.et_username)
        val etPassword = dialogView.findViewById<EditText?>(R.id.et_password)

        // 3. 创建AlertDialog.Builder并设置自定义布局
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView) // 将自定义布局设置到对话框

        // 4. 创建AlertDialog实例
        val alertDialog = builder.create()

        // 5. 设置“Cancel”按钮的点击事件：关闭对话框
        btnCancel.setOnClickListener(View.OnClickListener { v: View? ->
            alertDialog.dismiss()
        })

        // 6. 设置“Sign in”按钮的点击事件：处理登录逻辑（示例为Toast提示）
        btnSignIn.setOnClickListener(View.OnClickListener { v: View? ->
            val username = etUsername.getText().toString().trim { it <= ' ' }
            val password = etPassword.getText().toString().trim { it <= ' ' }
            Toast.makeText(this@MainActivity, "Sign in: " + username, Toast.LENGTH_SHORT).show()
            alertDialog.dismiss() // 处理完后关闭对话框
        })

        // 7. 显示对话框
        alertDialog.show()
    }
}