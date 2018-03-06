package com.wanwang.wanandroidtest.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import com.gyf.barlibrary.ImmersionBar

/**
 * Created by wanwang on 2018-03-06.
 */
abstract class BaseActivity : AppCompatActivity() {
    //沉浸式状态bar
    protected lateinit var immersionBar: ImmersionBar
    //懒加载软键盘输入输出
    private val imm: InputMethodManager by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置布局
        setContentView(setLayoutId())
        //初始化沉浸式
        initImmersionBar()

    }

    protected  abstract fun setLayoutId(): Int

    open protected fun initImmersionBar() {
        immersionBar=  ImmersionBar.with(this)
        immersionBar.init()
    }

    /**
     * cancel request
     */
    protected abstract fun cancelRequest()

    override fun onDestroy() {
        super.onDestroy()
        immersionBar.destroy() //必须调用销毁 不然状态栏会保存上一次销毁的状态
        cancelRequest()
    }

    override fun finish() {
        super.finish()
        hideSoftKeyBoard()
    }

    private fun hideSoftKeyBoard() {
        currentFocus.let {
            imm.hideSoftInputFromWindow(it.windowToken,2)
        }
    }
}