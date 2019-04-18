package com.mashael.weatherkotlin.ui.utils

import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import com.mashael.weatherkotlin.R
import com.mashael.weatherkotlin.extensions.ctx
import com.mashael.weatherkotlin.ui.App
import com.mashael.weatherkotlin.ui.SettingsActivity
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

interface ToolbarManager {
    val toolbar: Toolbar
    var toolbarTitle: String
        get() = toolbar.title.toString()
        set(value) {
            toolbar.title = value
        }

    fun initToolbar() {
        toolbar.inflateMenu(R.menu.menu_main)
        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.action_settings -> toolbar.ctx.startActivity<SettingsActivity>()
                else -> App.instance.toast("Unknown option")
            }
            true
        }
    }

    fun enableHomeAsUp(up:()->Unit){
        toolbar.navigationIcon = createUpDrawable()
        toolbar.setNavigationOnClickListener { up() }
    }

    private fun createUpDrawable()= with(DrawerArrowDrawable(toolbar.ctx)) {
        progress=1f
        this
    }

    fun attachToScroll(recyclerView: RecyclerView){
        recyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy>0) toolbar.slideExit()else toolbar.slideEnter()
            }
        })
    }
}

private fun View.slideEnter() {
    if (translationY < 0f)animate().translationY(0f)

}

private fun View.slideExit() {
    if (translationY == 0f)animate().translationY(-height.toFloat())
}
