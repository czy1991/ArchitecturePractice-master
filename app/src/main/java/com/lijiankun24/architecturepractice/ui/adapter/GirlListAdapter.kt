package com.lijiankun24.architecturepractice.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.lijiankun24.architecturepractice.R
import com.lijiankun24.architecturepractice.data.local.db.entity.Girl
import com.lijiankun24.architecturepractice.ui.listener.OnItemClickListener

import java.util.ArrayList

/**
 * 类 `${CLASS_NAME}`
 *
 *
 * 描述：
 *
 * 创建日期：2017年11月15日
 *
 * @author zhaoyong.chen@ehking.com
 * @version 1.0
 */
class GirlListAdapter(listener: OnItemClickListener<Girl>) : RecyclerView.Adapter<GirlListAdapter.ViewHolder>() {

    private var mGirlClickListener: OnItemClickListener<Girl>? = null

    private var mGirlList: MutableList<Girl>? = null

    init {
        mGirlClickListener = listener
        mGirlList = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_girl_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val girl = mGirlList!![position]
        holder.tvGirlName.text = girl.who
        holder.tvGirlAge.text = girl.publishedAt
        Glide.with(holder.ivGirlAvatar.context)
                .load(girl.url)
                .error(R.drawable.ic_launcher)
                .centerCrop()
                .into(holder.ivGirlAvatar)
        holder.root.setOnClickListener {
            if (mGirlClickListener != null) {
                mGirlClickListener!!.onClick(girl)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (mGirlList == null) 0 else mGirlList!!.size
    }

    fun setGirlList(girlList: List<Girl>) {
        mGirlList!!.addAll(girlList)
        notifyDataSetChanged()
    }

    fun clearGirlList() {
        mGirlList!!.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var root: View= view.findViewById(R.id.rl_girl_item_root)

        var tvGirlName: TextView= view.findViewById(R.id.tv_girl_name)

        var tvGirlAge: TextView= view.findViewById(R.id.tv_girl_age)

        var ivGirlAvatar: ImageView= view.findViewById(R.id.iv_girl_avatar)


    }
}
