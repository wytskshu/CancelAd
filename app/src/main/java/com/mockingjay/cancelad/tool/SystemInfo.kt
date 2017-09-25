package com.mockingjay.cancelad.tool

import android.content.Context
import com.mockingjay.cancelad.adapter.AppInfo


/**
 * Created by Mockingjay on 2017/9/25.
 */
class SystemInfo{
    companion object {
        fun GetOherAppInfo(mContext: Context): ArrayList<AppInfo>{
            val pm = mContext.getPackageManager()
            val mPacks = pm.getInstalledPackages(0)
            var mlist= arrayListOf<AppInfo>()
            for (info in mPacks) {
                val mInfo = AppInfo()
                mInfo.AppName=info.applicationInfo.loadLabel(pm).toString()
                mInfo.PackName=info.packageName
                mInfo.Ico=info.applicationInfo.loadIcon(pm)
                mlist.add(mInfo)
            }
            return mlist
        }
    }

}
