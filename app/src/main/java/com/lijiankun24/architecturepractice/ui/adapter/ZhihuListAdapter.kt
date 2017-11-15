package com.lijiankun24.architecturepractice.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.lijiankun24.architecturepractice.R
import com.lijiankun24.architecturepractice.data.local.db.entity.ZhihuStory
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

class ZhihuListAdapter(context: Context, listener: OnItemClickListener<ZhihuStory>) : RecyclerView.Adapter<ZhihuListAdapter.ZhihuViewHolder>() {

    private var mZhihuOnItemClickListener: OnItemClickListener<ZhihuStory>? = null

    private var mContext: Context? = null

    private var mStoryList: MutableList<ZhihuStory>? = null

    init {
        mStoryList = ArrayList()
        mContext = context
        mZhihuOnItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZhihuViewHolder {
        return ZhihuViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_zhihu_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ZhihuViewHolder, position: Int) {
        val zhihuStory = mStoryList!![position]
        holder.tvZhihuTitle.text = zhihuStory.title
        holder.tvZhihuTime.text = zhihuStory.ga_prefix
        holder.llZhihu.setOnClickListener {
            if (mZhihuOnItemClickListener != null) {
                mZhihuOnItemClickListener!!.onClick(zhihuStory)
            }
        }
        Glide.with(mContext)
                .load(zhihuStory.images[0])
                .centerCrop()
                .into(holder.ivZhihu)
    }

    override fun getItemCount(): Int {
        return mStoryList!!.size
    }

    fun setStoryList(storyList: List<ZhihuStory>?) {
        if (storyList == null || storyList.isEmpty()) {
            return
        }
        mStoryList!!.addAll(storyList)
        notifyDataSetChanged()
    }

    fun clearStoryList() {
        mStoryList!!.clear()
        notifyDataSetChanged()
    }

    inner class ZhihuViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

        var llZhihu: View= itemView.findViewById(R.id.ll_zhihu)
        var tvZhihuTitle: TextView= itemView.findViewById(R.id.tv_zhihu_title)
        var tvZhihuTime: TextView= itemView.findViewById(R.id.tv_zhihu_time)
        var ivZhihu: ImageView= itemView.findViewById(R.id.iv_zhihu)

    }
}
