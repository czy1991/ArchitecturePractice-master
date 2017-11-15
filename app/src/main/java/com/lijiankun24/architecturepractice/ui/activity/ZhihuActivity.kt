package com.lijiankun24.architecturepractice.ui.activity

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.lijiankun24.architecturepractice.MyApplication
import com.lijiankun24.architecturepractice.R
import com.lijiankun24.architecturepractice.data.Injection
import com.lijiankun24.architecturepractice.data.remote.model.ZhihuStoryDetail
import com.lijiankun24.architecturepractice.ui.listener.AppBarStateChangeListener
import com.lijiankun24.architecturepractice.ui.widget.MarqueeText
import com.lijiankun24.architecturepractice.utils.WebUtil
import com.lijiankun24.architecturepractice.viewmodel.ZhihuViewModel

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
class ZhihuActivity : BaseActivity() {

    private var mIVZhihuHeader: ImageView? = null

    private var mZhihuTitle: String? = null

    private var mWebView: WebView? = null

    private var mZhihuId: String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zhihu)
        readIntent()
        initView()
        subscribeUI()
    }

    private fun subscribeUI() {
        val factory = ZhihuViewModel.Factory(MyApplication.instance,
                Injection.getDataRepository(MyApplication.instance), mZhihuId)
        val zhihuViewModel = ViewModelProviders.of(this, factory).get(ZhihuViewModel::class.java)
        zhihuViewModel.zhihuDetail.observe(this, Observer { detail ->
            if (detail == null) {
                return@Observer
            }
            Glide.with(this@ZhihuActivity)
                    .load(detail.image)
                    .centerCrop()
                    .into(mIVZhihuHeader!!)
            if (TextUtils.isEmpty(detail.body)) {
                mWebView!!.loadUrl(detail.share_url)
            } else {
                val body = detail.body
                val cssList = detail.css
                val htmlTemp = WebUtil.buildHtmlWithCss(body, cssList, false)
                mWebView!!.loadDataWithBaseURL(WebUtil.BASE_URL, htmlTemp, WebUtil.MIME_TYPE, WebUtil.ENCODING, WebUtil.FAIL_URL)
            }
        })
    }

    private fun initView() {
        mIVZhihuHeader = findViewById(R.id.iv_zhihu_head)

        mWebView = findViewById(R.id.wv_zhihu)
        val settings = mWebView!!.settings
        settings.javaScriptEnabled = true
        settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        settings.loadWithOverviewMode = true
        settings.builtInZoomControls = true
        //settings.setUseWideViewPort(true);造成文字太小
        settings.domStorageEnabled = true
        settings.databaseEnabled = true
        settings.setAppCachePath(cacheDir.absolutePath + "/webViewCache")
        settings.setAppCacheEnabled(true)
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        mWebView!!.webChromeClient = WebChromeClient()


        val toolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_layout)
        val marqueeText = findViewById<MarqueeText>(R.id.toolbar_title)
        val appBarLayout = findViewById<AppBarLayout>(R.id.app_bar_layout)
        val toolbar = findViewById<Toolbar>(R.id.tb_zhihu)
        initToolbar(toolbar, true, mZhihuTitle!!)
        marqueeText.text = mZhihuTitle
        toolbarLayout.title = mZhihuTitle
        appBarLayout.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: AppBarStateChangeListener.State) {
                if (state == AppBarStateChangeListener.State.EXPANDED) {
                    //展开状态
                    marqueeText.visibility = View.GONE
                } else if (state == AppBarStateChangeListener.State.COLLAPSED) {
                    //折叠状态
                    marqueeText.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun readIntent() {
        mZhihuId = this.intent.getStringExtra(ZHIHU_ID)
        mZhihuTitle = this.intent.getStringExtra(ZHIHU_TITLE)
    }

    companion object {

        val ZHIHU_TITLE = "zhihu_title"

        val ZHIHU_ID = "zhihu_id"


        fun startZhihuActivity(activity: Activity?, zhihuId: String, zhihuTitle: String) {
            if (activity == null || TextUtils.isEmpty(zhihuId)
                    || TextUtils.isEmpty(zhihuTitle)) {
                return
            }
            val intent = Intent(activity, ZhihuActivity::class.java)
            intent.putExtra(ZhihuActivity.ZHIHU_ID, zhihuId)
            intent.putExtra(ZhihuActivity.ZHIHU_TITLE, zhihuTitle)
            activity.startActivity(intent)
        }
    }
}
