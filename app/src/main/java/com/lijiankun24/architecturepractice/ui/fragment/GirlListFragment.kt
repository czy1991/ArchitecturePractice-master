package com.lijiankun24.architecturepractice.ui.fragment

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
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
import com.lijiankun24.architecturepractice.data.local.db.entity.Girl
import com.lijiankun24.architecturepractice.ui.activity.GirlActivity
import com.lijiankun24.architecturepractice.ui.adapter.GirlListAdapter
import com.lijiankun24.architecturepractice.ui.listener.OnItemClickListener
import com.lijiankun24.architecturepractice.utils.L
import com.lijiankun24.architecturepractice.utils.Util
import com.lijiankun24.architecturepractice.viewmodel.GirlListViewModel

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

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
class GirlListFragment : Fragment() {

    private var mGirlListViewModel: GirlListViewModel? = null

    private var mGirlListAdapter: GirlListAdapter? = null

    private var mRefreshLayout: SwipeRefreshLayout? = null

    private var mLoadMorebar: ProgressBar? = null

    private var RLGirlRoot: View? = null

    private val mGirlClickListener = OnItemClickListener<Girl> { girl ->
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            if (Util.isNetworkConnected(MyApplication.instance)) {
                GirlActivity.startGirlActivity(activity, girl.url)
            } else {
                Util.showSnackbar(RLGirlRoot, getString(R.string.network_error))
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_girl_list, container, false)
        initView(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUI()
    }

    private fun subscribeUI() {
        if (!isAdded) {
            return
        }
        val factory = GirlListViewModel.Factory(MyApplication.instance,
                Injection.getDataRepository(MyApplication.instance))
        mGirlListViewModel = ViewModelProviders.of(this, factory).get(GirlListViewModel::class.java)
        mGirlListViewModel!!.gilrsLiveData.observe(this, Observer { girls ->
            if (girls == null || girls.isEmpty()) {
                return@Observer
            }
            L.i("girls size " + girls.size)
            mGirlListAdapter!!.setGirlList(girls)
        })
        mGirlListViewModel!!.loadMoreState.observe(this, Observer { state ->
            if (state == null) {
                return@Observer
            }
            L.i("state " + state)
            if (mRefreshLayout!!.isRefreshing) {
                mRefreshLayout!!.isRefreshing = false
            } else {
                mLoadMorebar!!.visibility = if (state) View.VISIBLE else View.INVISIBLE
            }
        })
        mGirlListViewModel!!.refreshGrilsData()
        mRefreshLayout!!.isRefreshing = true
    }

    private fun initView(view: View) {
        val context = view.context

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_girl_list)
        recyclerView.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
        mGirlListAdapter = GirlListAdapter(mGirlClickListener)
        recyclerView.adapter = mGirlListAdapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                val layoutManager = recyclerView!!.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager
                        .findLastVisibleItemPosition()
                if (lastPosition == mGirlListAdapter!!.itemCount - 1) {
                    // 上拉加载更多数据
                    mGirlListViewModel!!.loadNextPageGirls()
                }
            }
        })

        mRefreshLayout = view.findViewById(R.id.srl)
        mRefreshLayout!!.setOnRefreshListener {
            mGirlListAdapter!!.clearGirlList()
            mRefreshLayout!!.isRefreshing = true
            mGirlListViewModel!!.refreshGrilsData()
        }
        mRefreshLayout!!.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)

        mLoadMorebar = view.findViewById(R.id.load_more_bar)
        RLGirlRoot = view.findViewById(R.id.rl_girl_root)
        Observable.create(ObservableOnSubscribe<Int> { }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(object : io.reactivex.Observer<Int> {
            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(value: Int?) {

            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {

            }
        })
    }
}
