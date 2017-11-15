package com.lijiankun24.architecturepractice.ui.fragment


import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import com.lijiankun24.architecturepractice.MyApplication
import com.lijiankun24.architecturepractice.R
import com.lijiankun24.architecturepractice.data.Injection
import com.lijiankun24.architecturepractice.data.local.db.entity.ZhihuStory
import com.lijiankun24.architecturepractice.ui.activity.ZhihuActivity
import com.lijiankun24.architecturepractice.ui.adapter.ZhihuListAdapter
import com.lijiankun24.architecturepractice.ui.listener.OnItemClickListener
import com.lijiankun24.architecturepractice.utils.L
import com.lijiankun24.architecturepractice.utils.Util
import com.lijiankun24.architecturepractice.viewmodel.ZhihuListViewModel

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

class ZhihuListFragment : Fragment() {

    // ZhihuListFragment 所对应的 ViewModel 类的对象
    private var mListViewModel: ZhihuListViewModel? = null

    private var mRefreshLayout: SwipeRefreshLayout? = null

    private var mAdapter: ZhihuListAdapter? = null

    private var mLoadMorebar: ProgressBar? = null

    private var mRLZhihuRoot: View? = null

    // 自定义接口，将 RecyclerView 的 Adapter 对其中每个 Item 的点击事件会传到 ZhihuListFragment 中。
    private val mZhihuOnItemClickListener = OnItemClickListener<ZhihuStory> { zhihuStory ->
        if (Util.isNetworkConnected(MyApplication.instance)) {
            ZhihuActivity.startZhihuActivity(activity, zhihuStory.id,
                    zhihuStory.title)
        } else {
            Util.showSnackbar(mRLZhihuRoot, getString(R.string.network_error))
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_zhihu_list, container, false)
        initView(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUI()
    }

    /**
     * 将 ZhihuListFragment 对应的 ZhihuListViewModel 类中的 LiveData 添加注册监听到
     * 此 ZhihuListFragment
     */
    private fun subscribeUI() {
        // 通过 ViewModelProviders 创建对应的 ZhihuListViewModel 对象
        val factory = ZhihuListViewModel.Factory(MyApplication.instance, Injection.getDataRepository(MyApplication.instance))
        mListViewModel = ViewModelProviders.of(this, factory).get(ZhihuListViewModel::class.java)
        mListViewModel!!.zhihuList.observe(this, Observer { stories ->
            if (stories == null || stories.size <= 0) {
                return@Observer
            }
            L.i("size is " + stories.size)
            mAdapter!!.setStoryList(stories)
        })
        mListViewModel!!.isLoadingZhihuList.observe(this, Observer { aBoolean ->
            if (aBoolean == null) {
                return@Observer
            }
            L.i("state " + aBoolean)
            mRefreshLayout!!.isRefreshing = false
            mLoadMorebar!!.visibility = if (aBoolean) View.VISIBLE else View.INVISIBLE
        })
        mListViewModel!!.refreshZhihusData()
    }

    /**
     * 初始化页面 UI
     *
     * @param view Fragment 的 View
     */
    private fun initView(view: View?) {
        if (view == null) {
            return
        }
        val layoutManager = LinearLayoutManager(context)
        mAdapter = ZhihuListAdapter(context, mZhihuOnItemClickListener)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_zhihu_list)
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = layoutManager
        recyclerView.addOnScrollListener(ZhihuOnScrollListener())

        mRefreshLayout = view.findViewById(R.id.srl_zhihu)
        mRefreshLayout!!.setOnRefreshListener(ZhihuSwipeListener())
        mRefreshLayout!!.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)

        mLoadMorebar = view.findViewById(R.id.bar_load_more_zhihu)
        mRLZhihuRoot = view.findViewById(R.id.rl_zhihu_root)
    }

    /**
     * ZhihuSwipeListener 用于 SwipeRefreshLayout 下拉刷新操作
     */
    private inner class ZhihuSwipeListener : SwipeRefreshLayout.OnRefreshListener {
        override fun onRefresh() {
            mAdapter!!.clearStoryList()
            mListViewModel!!.refreshZhihusData()
        }
    }

    /**
     * ZhihuOnScrollListener 用于 RecyclerView 下拉到最低端时的上拉加载更多操作
     */
    private inner class ZhihuOnScrollListener : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            val layoutManager = recyclerView!!.layoutManager as LinearLayoutManager
            val lastPosition = layoutManager
                    .findLastCompletelyVisibleItemPosition()
            if (lastPosition == mAdapter!!.itemCount - 1) {
                // 上拉加载更多数据
                mListViewModel!!.loadNextPageZhihu(lastPosition)
            }
        }
    }
}
