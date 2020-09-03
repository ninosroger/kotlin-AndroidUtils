package com.ninos.ajenda.page.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ninos.kotlin_androidutils.R
import com.ninos.kotlin_androidutils.recycler_menu.BaseAdapter
import com.ninos.kotlin_androidutils.recycler_menu.TestBean

/**
 * @author Ninos
 */
class FoundGroupAdapter(context: Context) :
    BaseAdapter<FoundGroupAdapter.VHolder, Any>(context) {
    override fun provideItemLayoutId() = R.layout.item_found_group

    override fun createVH(parent: ViewGroup, viewType: Int, view: View): VHolder = VHolder(view)

    override fun bindData(holder: VHolder, position: Int) {
        var item = data[position]
        val foundGroupItemAdapter = FoundGroupItemAdapter(context)
        holder.foundGroupItem.layoutManager = LinearLayoutManager(context)
        holder.foundGroupItem.adapter = foundGroupItemAdapter
        foundGroupItemAdapter.addDatas(TestBean.getListDataForHome())
    }

    inner class VHolder(view: View) : RecyclerView.ViewHolder(view) {
        var foundGroupItem: RecyclerView = view.findViewById(R.id.found_group_item)
    }
}