package com.ninos.ajenda.page.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ninos.kotlin_androidutils.R
import com.ninos.kotlin_androidutils.recycler_menu.BaseAdapter
import com.ninos.kotlin_androidutils.recycler_menu.TestBean

/**
 * @author Ninos
 */
class FoundClassificationAdapter(context: Context) :
    BaseAdapter<FoundClassificationAdapter.VHolder, TestBean.ClassificationInfo>(
        context
    ) {
    override fun provideItemLayoutId() = R.layout.item_found_classification

    override fun createVH(parent: ViewGroup, viewType: Int, view: View): VHolder = VHolder(view)

    override fun bindData(holder: VHolder, position: Int) {
        var item = data[position]
        holder.classificationName.text = "Classification"
        if (item.checked) {
            holder.classificationTop.visibility = View.VISIBLE
            holder.classificationBottom.visibility = View.VISIBLE
            holder.classificationName.setBackgroundResource(R.color.white)
        } else {
            holder.classificationTop.visibility = View.GONE
            holder.classificationBottom.visibility = View.GONE
            holder.classificationName.setBackgroundResource(R.color.colorAccent)
        }
        holder.classificationName.text = item.name
    }

    inner class VHolder(view: View) : RecyclerView.ViewHolder(view) {
        var classificationTop: View = view.findViewById(R.id.classification_top)
        var classificationName: TextView = view.findViewById(R.id.classification_name)
        var classificationBottom: View = view.findViewById(R.id.classification_bottom)
    }
}