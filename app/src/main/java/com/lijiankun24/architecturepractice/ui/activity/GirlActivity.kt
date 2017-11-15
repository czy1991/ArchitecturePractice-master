package com.lijiankun24.architecturepractice.ui.activity

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.webkit.WebView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.lijiankun24.architecturepractice.R
import com.lijiankun24.architecturepractice.utils.Consts
import com.lijiankun24.architecturepractice.utils.Util

import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.util.Date

import uk.co.senab.photoview.PhotoView
import uk.co.senab.photoview.PhotoViewAttacher

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
class GirlActivity : BaseActivity() {

    private var mPhotoViewAttacher: PhotoViewAttacher? = null

    private var mIsToolbarHidden: Boolean = false

    private var mGirlImgUrl: String? = null

    private var mToolbar: Toolbar? = null

    private var mRLGirlRoot: View? = null

    private var mStatusBar: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_girl)
        readIntent()
        initView()
    }

    private fun readIntent() {
        val intent = this@GirlActivity.intent
        if (intent.hasExtra(GIRL_IMG_URL)) {
            mGirlImgUrl = intent.getStringExtra(GIRL_IMG_URL)
        }
    }

    private fun initView() {

        val photoView = findViewById<PhotoView>(R.id.photoView)
        mPhotoViewAttacher = PhotoViewAttacher(photoView)
        mPhotoViewAttacher!!.setOnViewTapListener { _, _, _ -> toggleToolbar() }
        mPhotoViewAttacher!!.setOnLongClickListener {
            showSaveGirlDialog()
            true
        }
        Glide.with(this)
                .load(mGirlImgUrl)
                .fitCenter()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(photoView)

        mToolbar = findViewById(R.id.toolbar)
        initToolbar(mToolbar!!, true, R.string.girl_title)

        mRLGirlRoot = findViewById(R.id.rl_girl_root)
        mStatusBar = findViewById(R.id.fake_status_bar)
    }

    private fun showSaveGirlDialog() {
        AlertDialog.Builder(this@GirlActivity)
                .setMessage(getString(R.string.girl_save))
                .setNegativeButton(android.R.string.cancel) { anInterface, _ -> anInterface.dismiss() }
                .setPositiveButton(android.R.string.ok) { anInterface, _ ->
                    anInterface.dismiss()
                    saveGirl()
                }.show()
    }

    private fun saveGirl() {
        val directory = File(Environment.getExternalStorageDirectory(), Consts.FILEROOTPATH)
        if (!directory.exists())
            directory.mkdirs()
        val drawingCache = mPhotoViewAttacher!!.imageView.drawingCache
        try {
            val file = File(directory, Date().time.toString() + Consts.IMAGE_FORMAT)
            val fos = FileOutputStream(file)
            drawingCache.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            val uri = Uri.fromFile(file)
            intent.data = uri
            this@GirlActivity.applicationContext.sendBroadcast(intent)
            Util.showSnackbar(mRLGirlRoot, getString(R.string.girl_save_succeed))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            Util.showSnackbar(mRLGirlRoot, getString(R.string.girl_save_failed))
        }

    }

    private fun toggleToolbar() {
        mToolbar!!.animate()
                .translationY((if (this.mIsToolbarHidden) 0 else -(mToolbar!!.height + mStatusBar!!.height)).toFloat())
                .setInterpolator(DecelerateInterpolator(2f))
                .start()
        mIsToolbarHidden = !mIsToolbarHidden

    }

    companion object {

        private val GIRL_IMG_URL = "girl_img_url"

        fun startGirlActivity(activity: Activity?, girlUrl: String) {
            if (activity == null || TextUtils.isEmpty(girlUrl)) {
                return
            }
            val intent = Intent(activity, GirlActivity::class.java)
            intent.putExtra(GIRL_IMG_URL, girlUrl)
            activity.startActivity(intent)
        }
    }
}
