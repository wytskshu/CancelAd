package com.mockingjay.cancelad

import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import com.mockingjay.cancelad.tool.AlertDialog
import com.mockingjay.cancelad.tool.Filed
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var sdpath = Environment.getExternalStorageDirectory().absolutePath + "/cancleAd/"
        var packname = ""
        var jupword = ""
        var setuse = ""
        var setmixpackname = ""
        if (Filed.isExist(sdpath)) {
            if (Filed.isExist(sdpath + "setpackname.txt")) {
                packname = Filed.readFile(sdpath + "setpackname.txt")
            }
            if (Filed.isExist(sdpath + "setjupword.txt")) {
                jupword = Filed.readFile(sdpath + "setjupword.txt")
            }
            if (Filed.isExist(sdpath + "setuse.txt")) {
                setuse = Filed.readFile(sdpath + "setuse.txt")
            }
            if (Filed.isExist(sdpath + "setmixpackname.txt")) {
                setmixpackname = Filed.readFile(sdpath + "setmixpackname.txt")
            }
        }
        if (setuse == "true\n") {

            setting_use.isChecked = true
        }

        setting_packname.setOnClickListener {

            var edit = EditText(this)
            edit.append(packname)
            AlertDialog.alertshuerview(this, "输入包名,'/'符号分割输入多个", edit, {
                var sdpath = Environment.getExternalStorageDirectory().absolutePath + "/cancleAd/"
                Filed.isExist(sdpath)
                Filed.saveToFile(sdpath + "setpackname.txt", it)
            }, {})
        }
        setting_word.setOnClickListener {

            var edit = EditText(this)
            edit.append(jupword)
            AlertDialog.alertshuerview(this, "输入关键词,'/'符号分割输入多个", edit, {
                var sdpath = Environment.getExternalStorageDirectory().absolutePath + "/cancleAd/"
                Filed.isExist(sdpath)
                Filed.saveToFile(sdpath + "setjupword.txt", it)

            }, {})

        }
        setting_use.setOnCheckedChangeListener { compoundButton, b ->
            var sdpath = Environment.getExternalStorageDirectory().absolutePath + "/cancleAd/"
            if (b) {
                //打开
                Filed.saveToFile(sdpath + "setuse.txt", "true")

            } else {

                Filed.saveToFile(sdpath + "setuse.txt", "false")

            }

        }
        setting_mixpackname.setOnClickListener {

            var edit = EditText(this)
            edit.append(setmixpackname)
            AlertDialog.alertshuerview(this, "输入包名,'/'符号分割输入多个", edit, {
                var sdpath = Environment.getExternalStorageDirectory().absolutePath + "/cancleAd/"
                Filed.isExist(sdpath)
                Filed.saveToFile(sdpath + "setmixpackname.txt", it)
            }, {})
        }
    }

    override fun onResume() {
        super.onResume()
        AndPermission.with(this)
                .requestCode(100)
                .permission(Permission.STORAGE)
                .start()
    }
}
