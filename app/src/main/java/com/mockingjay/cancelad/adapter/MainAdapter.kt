package com.mockingjay.cancelad.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.mockingjay.cancelad.R

/**
 * Created by Mockingjay on 2017/9/25.
 */
class MainAdapter(context: Activity,list: ArrayList<AppInfo>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  var mcontext=context
    var mlists=list
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        var mholder=holder as MyHolder
        var appname=mlists[position].AppName
        var packname=mlists[position].PackName
        var icodraw=mlists[position].Ico
        mholder.imgView.setImageDrawable(icodraw)
        mholder.textViewAppname.text=appname
        mholder.textViewPackname.text=packname
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var itemview = LayoutInflater.from(mcontext).inflate(R.layout.main_list_item, parent, false)
        return MyHolder(itemview)
    }

    override fun getItemCount(): Int {
        return mlists.size
    }
    inner class MyHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var imgView=itemView.findViewById<ImageView>(R.id.img_appico)
        var textViewAppname=itemView.findViewById<TextView>(R.id.text_appname)
        var textViewPackname=itemView.findViewById<TextView>(R.id.text_packname)

    }

}