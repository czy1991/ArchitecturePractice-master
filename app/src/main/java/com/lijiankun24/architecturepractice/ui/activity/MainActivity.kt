package com.lijiankun24.architecturepractice.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar

import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.lijiankun24.architecturepractice.R
import com.lijiankun24.architecturepractice.ui.fragment.GirlListFragment
import com.lijiankun24.architecturepractice.ui.fragment.ZhihuListFragment

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
class MainActivity : BaseActivity() {

    private val mFragmentList = ArrayList<Fragment>()

    private var mViewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        mFragmentList.add(GirlListFragment())
        mFragmentList.add(ZhihuListFragment())

        mViewPager = findViewById(R.id.vp_home)
        mViewPager!!.adapter = MainFragmentPagerAdapter(supportFragmentManager)

        val navigationBar = findViewById<BottomNavigationBar>(R.id.bottom_navigation_bar)
        navigationBar.setTabSelectedListener(MainOnTabSelectedListener())
        navigationBar.addItem(BottomNavigationItem(R.drawable.ic_favorite, "Girl"))
                .addItem(BottomNavigationItem(R.drawable.ic_grade, "Zhihu"))
                .initialise()

        val toolbar = findViewById<Toolbar>(R.id.tool_bar)
        initToolbar(toolbar, false, R.string.app_name)
    }

    private inner class MainOnTabSelectedListener : BottomNavigationBar.OnTabSelectedListener {

        override fun onTabSelected(position: Int) {
            mViewPager!!.currentItem = position
        }

        override fun onTabUnselected(position: Int) {}

        override fun onTabReselected(position: Int) {}
    }

    private inner class MainFragmentPagerAdapter (fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }
    }
}
