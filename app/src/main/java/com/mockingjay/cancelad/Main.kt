package com.mockingjay.cancelad

import android.os.Environment
import android.view.View
import android.widget.TextView
import com.mockingjay.cancelad.tool.Filed
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers.findAndHookMethod
import de.robv.android.xposed.callbacks.XC_LoadPackage


/**
 * Created by Mockingjay on 2017/9/23.
 */
class Main : IXposedHookLoadPackage {

    override fun handleLoadPackage(p0: XC_LoadPackage.LoadPackageParam) {
        var stat = 0
        var sdpath_use = Environment.getExternalStorageDirectory().absolutePath + "/cancleAd/" + "setuse.txt"
        var openuse = Filed.readFile(sdpath_use)
        if (openuse == "false\n") {
            XposedBridge.log("TAG=====" + "未启用")
            return
        }
        // XposedBridge.log("TAG=====" + "启用")
        var sdpath_packname = Environment.getExternalStorageDirectory().absolutePath + "/cancleAd/" + "setpackname.txt"
        var needpack = Filed.readFile(sdpath_packname)
        var sdpath_mixpackname = Environment.getExternalStorageDirectory().absolutePath + "/cancleAd/" + "setmixpackname.txt"
        var mixpackname = Filed.readFile(sdpath_mixpackname)
        if (needpack.indexOf(p0.packageName) == -1) {
            if (mixpackname.indexOf(p0.packageName) == -1) {
                return
            } else {
                XposedBridge.log("TAG=====HOOK到"+p0.packageName + "复杂")
                stat = 2
            }
        } else {
            XposedBridge.log("TAG=====HOOK到"+p0.packageName + "普通")
            stat = 1
        }
        var sdpath_jubword = Environment.getExternalStorageDirectory().absolutePath + "/cancleAd/" + "setjupword.txt"
        var jubword = Filed.readFile(sdpath_jubword)
        var action = object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam?) {
                super.afterHookedMethod(param)

            }

            override fun beforeHookedMethod(param: MethodHookParam?) {

                var title = param!!.args[0] as String
                var cancleword = jubword
                var words = cancleword.split("/")
//                for (word in words) {
//                    XposedBridge.log("TAG分=====" + word)
//                }

                for (word in words) {
                    if (title.indexOf(word) != -1) {
                        if (stat == 1) {
                            var textview = param!!.thisObject as TextView
                            textview.performClick()
                            XposedBridge.log("TAG=====" + "主动点击"+"出现词--"+title+"匹配词--"+word)
                        } else if (stat == 2) {
                            var textview = param!!.thisObject as TextView
                            var parentView = textview.parent as View
//                                var intX = textview.top + (textview.bottom - textview.top) / 2
//                                var intY = textview.left + (textview.right - textview.left) / 2
//                                var order = arrayListOf<String>("input", "tap", "" + intX, "" + intY)
//                                ProcessBuilder(order).start()
                            parentView.performClick()
                            XposedBridge.log("TAG=====" + "复杂点击"+"出现词--"+title+"匹配词--"+word)
                        }
                        break

                    }
                }


            }
        }

        findAndHookMethod(TextView::class.java, "setText", CharSequence::class.java, action)


    }


}

