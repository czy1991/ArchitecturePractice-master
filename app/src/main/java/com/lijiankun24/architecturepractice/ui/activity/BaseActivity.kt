package com.lijiankun24.architecturepractice.ui.activity

import android.arch.lifecycle.LifecycleRegistry
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.lijiankun24.architecturepractice.R
import com.lijiankun24.architecturepractice.about.AboutActivity

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

open class BaseActivity : AppCompatActivity() , Toolbar.OnMenuItemClickListener {

    private val mRegistry = LifecycleRegistry(this)
    override fun getLifecycle(): LifecycleRegistry {
        return mRegistry
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (this is AboutActivity) {
            return super.onCreateOptionsMenu(menu)
        }
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_main_about -> startActivity(Intent(this@BaseActivity, AboutActivity::class.java))
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this@BaseActivity.finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 初始化 Toolbar
     *
     * @param toolbar                   要初始化的 Toolbar 对象
     * @param setDisplayHomeAsUpEnabled 是否显示返回键
     * @param resId                     标题 Title 的 resId
     */
    protected fun initToolbar(toolbar: Toolbar, setDisplayHomeAsUpEnabled: Boolean, resId: Int) {
        initToolbar(toolbar, setDisplayHomeAsUpEnabled, this@BaseActivity.getString(resId))
    }

    /**
     * 初始化 Toolbar
     *
     * @param toolbar                   要初始化的 Toolbar 对象
     * @param setDisplayHomeAsUpEnabled 是否显示返回键
     * @param title                     标题 Title
     */
    protected fun initToolbar(toolbar: Toolbar, setDisplayHomeAsUpEnabled: Boolean, title: String) {
        setSupportActionBar(toolbar)
        toolbar.setOnMenuItemClickListener(this)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(setDisplayHomeAsUpEnabled)
            supportActionBar!!.title = title
        }
    }
}
