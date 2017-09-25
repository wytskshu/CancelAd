package com.mockingjay.cancelad.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by Mockingjay on 2017/9/25.
 */
class MainAdapter(context: Context,list: ArrayList<AppInfo>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  var mcontext=context
    var mlist=list
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}