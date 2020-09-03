package com.ninos.kotlin_androidutils.recycler_menu

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Ninos
 */
abstract class BaseAdapter<VH : RecyclerView.ViewHolder, B> :
    RecyclerView.Adapter<VH> {

    val VIEW_TYPE_HEADER = 0
    val VIEW_TYPE_FOOTER = -1
    val VIEW_TYPE_NORMAL = 1

    var mOnItemClickListener: OnItemClickListener<B>? = null

    var data: ArrayList<B> = ArrayList()

    var context: Context

    private var header: View? = null

    private var footer: View? = null

    constructor(context: Context) {
        this.context = context
    }

    override fun getItemViewType(position: Int): Int {
        if (header != null && position == VIEW_TYPE_HEADER) {
            return VIEW_TYPE_HEADER
        }
        if (footer != null && position == itemCount - 1) {
            return VIEW_TYPE_FOOTER
        }
        return VIEW_TYPE_NORMAL
    }

    abstract fun provideItemLayoutId(): Int

    abstract fun createVH(parent: ViewGroup, viewType: Int, view: View): VH

    abstract fun bindData(holder: VH, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return when (viewType) {
            VIEW_TYPE_HEADER ->
                createVH(parent, viewType, header!!)
            VIEW_TYPE_FOOTER ->
                createVH(parent, viewType, footer!!)
            else -> {
                val view = LayoutInflater.from(context).inflate(provideItemLayoutId(), null)
                createVH(parent, viewType, view)
            }
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        if (holder.itemViewType != VIEW_TYPE_HEADER && holder.itemViewType != VIEW_TYPE_FOOTER) {
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener { view ->
                    mOnItemClickListener!!.onItemClick(
                        view,
                        if (header == null) position else position - 1,
                        data[if (header == null) position else position - 1]
                    )
                }
            } else {
                holder.itemView.setOnClickListener(null)
            }
            bindData(holder, if (header == null) position else position - 1)
        }
    }

    override fun getItemCount(): Int =
        if (header != null)
            if (footer != null) data.size + 2 else data.size + 1
        else
            if (footer != null) data.size + 1 else data.size

    fun setOnItemClickListener(listener: OnItemClickListener<B>) {
        this.mOnItemClickListener = listener
    }

    fun addDatas(datas: List<B>) {
        data.clear()
        data.addAll(datas)
        notifyDataSetChanged()
    }

    fun addMore(datas: ArrayList<B>) {
        this.data.addAll(datas)
        this.notifyItemRangeChanged(this.data.size, datas.size)
    }

    fun addLastData(item: B) {
        data.add(item)
        notifyItemRangeInserted(data.size - 1, 1)
    }

    fun addFirstData(item: B) {
        data.add(0, item)
        notifyItemRangeInserted(0, 1)
    }

    fun addData(postion: Int, item: B) {
        data.add(postion, item)
        notifyItemRangeInserted(postion, 1)
    }

    fun removeFirstData() {
        data.removeAt(0)
        notifyItemRangeRemoved(0, 1)
    }

    fun removeLastData() {
        data.removeAt(data.size - 1)
        notifyItemRangeRemoved(data.size - 1, 1)
    }

    fun removeData(postion: Int) {
        data.removeAt(postion)
        notifyItemRangeRemoved(postion, 1)
    }

    fun changedData(postion: Int, item: B) {
        data[postion] = item
        this.notifyItemRangeChanged(postion, 1)
    }

    fun setFooter(footer: View) {
        this.footer = footer
    }

    interface OnItemClickListener<B> {
        fun onItemClick(view: View, position: Int, item: B)
    }

    class TwoColumnsDecoration(spaces: Int, span: Int) : RecyclerView.ItemDecoration() {
        val SPAN_COUNT_ONE = 1
        val SPAN_COUNT_TWO = 2
        private var spaces = spaces
        private var span = span
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            var temp = parent.getChildAdapterPosition(view)
            if (span == SPAN_COUNT_ONE)
                return
            if (span == SPAN_COUNT_TWO)
                if (temp % 2 == 0) {
                    outRect.right = spaces / 2
                    outRect.bottom = spaces
                } else {
                    outRect.left = spaces / 2
                    outRect.bottom = spaces
                }
        }
    }

    class HorizontalDecoration(spaces: Int) : RecyclerView.ItemDecoration() {
        private var spaces = spaces
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.right = spaces
        }
    }

    class VerticalDecoration(spaces: Int) : RecyclerView.ItemDecoration() {
        private var spaces = spaces
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.top = spaces
        }
    }
}