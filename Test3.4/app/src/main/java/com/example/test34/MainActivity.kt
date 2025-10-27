package com.example.test34

import android.app.Activity
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.ListView
import android.widget.Toast


class MainActivity : Activity() {
    private var listView: ListView? = null
    private var adapter: CustomAdapter? = null
    private var itemList: MutableList<Item?>? = null
    private var actionMode: ActionMode? = null

    // 用Set存储选中的位置（支持多选，避免重复）
    private val selectedPositions: MutableSet<Int?> = HashSet<Int?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById<ListView?>(R.id.list_view)

        // 初始化列表数据（1-5项）
        itemList = ArrayList<Item?>()
        itemList!!.add(Item("One", R.mipmap.ic_launcher))
        itemList!!.add(Item("Two", R.mipmap.ic_launcher))
        itemList!!.add(Item("Three", R.mipmap.ic_launcher))
        itemList!!.add(Item("Four", R.mipmap.ic_launcher))
        itemList!!.add(Item("Five", R.mipmap.ic_launcher))

        // 设置Adapter
        adapter = CustomAdapter(this, itemList, selectedPositions)
        listView!!.setAdapter(adapter)

        // 长按列表项：进入ActionMode并选中当前项
        listView!!.setOnItemLongClickListener(object : OnItemLongClickListener {
            override fun onItemLongClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ): Boolean {
                if (actionMode != null) {
                    return false // 已有ActionMode，直接返回
                }
                // 启动ActionMode
                actionMode = startActionMode(actionModeCallback)
                // 添加当前位置到选中集合
                selectedPositions.add(position)
                // 刷新列表（更新选中状态）
                adapter!!.notifyDataSetChanged()
                // 更新ActionMode标题（显示选中数量）
                actionMode!!.setTitle(selectedPositions.size.toString() + " selected")
                return true
            }
        })

        // 点击列表项：在ActionMode模式下切换选中状态
        listView!!.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // 只有在ActionMode模式下才处理点击（切换选中）
                if (actionMode != null) {
                    if (selectedPositions.contains(position)) {
                        // 已选中：移除
                        selectedPositions.remove(position)
                    } else {
                        // 未选中：添加
                        selectedPositions.add(position)
                    }
                    // 刷新列表（更新选中状态）
                    adapter!!.notifyDataSetChanged()
                    // 更新标题
                    actionMode!!.setTitle(selectedPositions.size.toString() + " selected")
                    // 如果没有选中项，关闭ActionMode
                    if (selectedPositions.isEmpty()) {
                        actionMode!!.finish()
                    }
                }
            }
        })
    }

    // ActionMode回调（处理菜单和选中逻辑）
    private val actionModeCallback: ActionMode.Callback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu?): Boolean {
            // 加载菜单资源（删除按钮）
            mode.getMenuInflater().inflate(R.menu.context_menu, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        // 点击菜单项（如删除）
        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            when (item.getItemId()) {
                R.id.menu_delete -> {
                    // 从后往前删除选中项（避免位置错乱）
                    val positionsToRemove: MutableList<Int?> = ArrayList<Int?>(selectedPositions)
                    var i = positionsToRemove.size - 1
                    while (i >= 0) {
                        val pos: Int = positionsToRemove.get(i)!!
                        itemList!!.removeAt(pos)
                        i--
                    }
                    // 刷新列表
                    adapter!!.notifyDataSetChanged()
                    Toast.makeText(
                        this@MainActivity,
                        "Deleted " + positionsToRemove.size + " items",
                        Toast.LENGTH_SHORT
                    ).show()
                    // 关闭ActionMode
                    mode.finish()
                    return true
                }

                else -> return false
            }
        }

        // 退出ActionMode时
        override fun onDestroyActionMode(mode: ActionMode?) {
            actionMode = null
            // 清空选中集合
            selectedPositions.clear()
            // 刷新列表（清除选中状态）
            adapter!!.notifyDataSetChanged()
        }
    }
}