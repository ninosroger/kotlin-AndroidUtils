package com.ninos.ajenda.page.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ninos.kotlin_androidutils.R
import com.ninos.kotlin_androidutils.recycler_menu.BaseAdapter

/**
 * @author Ninos
 */
class FoundGroupItemAdapter(context: Context) :
    BaseAdapter<FoundGroupItemAdapter.VHolder, Any>(context) {
    override fun provideItemLayoutId() = R.layout.item_found_group_item

    override fun createVH(parent: ViewGroup, viewType: Int, view: View): VHolder = VHolder(view)

    override fun bindData(holder: VHolder, position: Int) {
        var item = data[position]
    }

    inner class VHolder(view: View) : RecyclerView.ViewHolder(view) {
    }
}