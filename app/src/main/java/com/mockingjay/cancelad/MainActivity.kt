package com.mockingjay.cancelad

import android.content.ContentValues
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.mockingjay.cancelad.adapter.AppInfo
import com.mockingjay.cancelad.adapter.MainAdapter
import com.mockingjay.cancelad.tool.AlertDialog
import com.mockingjay.cancelad.tool.Filed
import com.mockingjay.cancelad.tool.SystemInfo
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var mainAdapter: MainAdapter
    lateinit var mlists: ArrayList<AppInfo>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var sdpath = Environment.getExternalStorageDirectory().absolutePath + "/cancleAd/"
        var packname = ""
        var jupword = ""
        var setuse = ""
        var setmixpackname = ""
        if (Filed.isExist(sdpath)) {
            if (Filed.isExist(sdpath + "setuse.txt")) {
                setuse = Filed.readFile(sdpath + "setuse.txt")
            }


        }
        if (setuse == "true\n") {

            setting_use.isChecked = true
        }

        setting_packname.setOnClickListener {
            var edit = EditText(this)
            if (Filed.isExist(sdpath + "setpackname.txt")) {
                packname = Filed.readFile(sdpath + "setpackname.txt")
            }
            edit.append(packname)
            AlertDialog.alertshuerview(this, "输入包名,'/'符号分割输入多个", edit, {
                var sdpath = Environment.getExternalStorageDirectory().absolutePath + "/cancleAd/"
                Filed.isExist(sdpath)
                Filed.saveToFile(sdpath + "setpackname.txt", it, false)
            }, {})
        }
        setting_word.setOnClickListener {
            if (Filed.isExist(sdpath + "setjupword.txt")) {
                jupword = Filed.readFile(sdpath + "setjupword.txt")
            }
            var edit = EditText(this)
            edit.append(jupword)
            AlertDialog.alertshuerview(this, "输入关键词,'/'符号分割输入多个", edit, {
                var sdpath = Environment.getExternalStorageDirectory().absolutePath + "/cancleAd/"
                Filed.isExist(sdpath)
                Filed.saveToFile(sdpath + "setjupword.txt", it, false)

            }, {})

        }
        setting_use.setOnCheckedChangeListener { compoundButton, b ->

            var sdpath = Environment.getExternalStorageDirectory().absolutePath + "/cancleAd/"
            if (b) {
                //打开
                Filed.saveToFile(sdpath + "setuse.txt", "true", false)

            } else {

                Filed.saveToFile(sdpath + "setuse.txt", "false", false)

            }

        }
        setting_mixpackname.setOnClickListener {
            if (Filed.isExist(sdpath + "setmixpackname.txt")) {
                setmixpackname = Filed.readFile(sdpath + "setmixpackname.txt")
            }
            var edit = EditText(this)
            edit.append(setmixpackname)
            AlertDialog.alertshuerview(this, "输入包名,'/'符号分割输入多个", edit, {
                var sdpath = Environment.getExternalStorageDirectory().absolutePath + "/cancleAd/"
                Filed.isExist(sdpath)
                Filed.saveToFile(sdpath + "setmixpackname.txt", it, false)
            }, {})
        }
        initView(initData())

    }

    fun initData(): ArrayList<AppInfo> {
        var appInfoList = SystemInfo.GetOherAppInfo(this)
        mlists = appInfoList
        return appInfoList
    }

    fun initView(data: Any) {
        main_recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mainAdapter = MainAdapter(this, data as ArrayList<AppInfo>)
        main_recyclerView.addItemDecoration(DefaultItemDecoration(Color.parseColor("#C0C5CD"), 2, 2))
        var mswipmenu = SwipeMenuCreator { swipeLeftMenu, swipeRightMenu, viewType ->
            var addItem = SwipeMenuItem(this)
            addItem.text = "普通"
            addItem.width = 120
            addItem.setTextColor(Color.WHITE)
            addItem.height = ViewGroup.LayoutParams.MATCH_PARENT
            addItem.setBackgroundColor(Color.parseColor("#2EB872"))
            swipeRightMenu.addMenuItem(addItem)

            var addfzitem = SwipeMenuItem(this)
            addfzitem.width = 120
            addfzitem.text = "复杂"
            addfzitem.setTextColor(Color.WHITE)
            addfzitem.height = ViewGroup.LayoutParams.MATCH_PARENT

            addfzitem.setBackgroundColor(Color.parseColor("#0962EA"))
            swipeRightMenu.addMenuItem(addfzitem)
        }
        main_recyclerView.setSwipeMenuCreator(mswipmenu)
        main_recyclerView.setSwipeMenuItemClickListener {
            it.closeMenu()
            val direction = it.getDirection() // 左侧还是右侧菜单。
            val adapterPosition = it.getAdapterPosition() // RecyclerView的Item的position。
            val menuPosition = it.getPosition() // 菜单在RecyclerView的Item中的Position。
            var packname = mlists[adapterPosition].PackName
            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                //Toast.makeText(this@MainActivity, "list第$adapterPosition; 右侧菜单第$menuPosition", Toast.LENGTH_SHORT).show()
                when (menuPosition) {
                    0 -> {
                        var sdpath = Environment.getExternalStorageDirectory().absolutePath + "/cancleAd/"
                        Filed.isExist(sdpath)
                        Filed.saveToFile(sdpath + "setpackname.txt", "/" + packname, true)
                    Toast.makeText(this@MainActivity,"已加入普通模式",Toast.LENGTH_SHORT).show()
                    }
                    1 -> {
                        var sdpath = Environment.getExternalStorageDirectory().absolutePath + "/cancleAd/"
                        Filed.isExist(sdpath)
                        Filed.saveToFile(sdpath + "setmixpackname.txt", "/" + packname, true)
                        Toast.makeText(this@MainActivity,"已加入复杂模式",Toast.LENGTH_SHORT).show()

                    }
                }
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
            }
        }
        main_recyclerView.adapter = mainAdapter
    }

    override fun onResume() {

        AndPermission.with(this)
                .requestCode(100)
                .permission(Permission.STORAGE)
                .start()
        super.onResume()
    }
}
