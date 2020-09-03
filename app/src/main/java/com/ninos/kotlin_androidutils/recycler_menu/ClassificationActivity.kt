package com.ninos.kotlin_androidutils.recycler_menu

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ninos.ajenda.page.adapter.FoundClassificationAdapter
import com.ninos.ajenda.page.adapter.FoundGroupAdapter
import com.ninos.kotlin_androidutils.R

class ClassificationActivity : AppCompatActivity() {
    private lateinit var foundClassification: RecyclerView
    private lateinit var foundGroup: RecyclerView
    private var scrollStatus = 0
    private var posChecked = 0

    private val foundClassificationAdapter by lazy {
        FoundClassificationAdapter(this)
    }

    private val foundGroupAdapter by lazy {
        FoundGroupAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classification)

        foundClassification = findViewById(R.id.found_classification)
        foundGroup = findViewById(R.id.found_group)

        foundClassification.layoutManager = LinearLayoutManager(this)
        foundClassification.adapter = foundClassificationAdapter
        foundClassificationAdapter.addDatas(TestBean.getListDataForClassification())

        foundGroup.layoutManager = LinearLayoutManager(this)
        foundGroup.adapter = foundGroupAdapter
        foundGroupAdapter.addDatas(TestBean.getListDataForClassification())

        foundGroup.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                scrollStatus += newState
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    scrollStatus = if (scrollStatus >= 3) {
                        var position =
                            (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                        changeDataAndItem(position)
                        0
                    } else 0
                }
            }
        })
        foundClassificationAdapter.setOnItemClickListener(object :
            BaseAdapter.OnItemClickListener<TestBean.ClassificationInfo> {
            override fun onItemClick(view: View, position: Int, item: TestBean.ClassificationInfo) {
                changeDataAndItemAndToPosition(position)
            }
        })
    }

    fun changeDataAndItem(position: Int) {
        var old = foundClassificationAdapter.data[posChecked]
        old.checked = false
        foundClassificationAdapter.changedData(posChecked, old)
        var new = foundClassificationAdapter.data[position]
        new.checked = true
        posChecked = position
        foundClassificationAdapter.changedData(position, new)
    }

    fun changeDataAndItemAndToPosition(position: Int) {
        var old = foundClassificationAdapter.data[posChecked]
        old.checked = false
        foundClassificationAdapter.changedData(posChecked, old)
        var new = foundClassificationAdapter.data[position]
        new.checked = true
        posChecked = position
        foundClassificationAdapter.changedData(position, new)
        scrollToGroup(position)
    }

    fun scrollToGroup(pos: Int) {
        foundGroup.smoothScrollToPosition(pos)
    }
}