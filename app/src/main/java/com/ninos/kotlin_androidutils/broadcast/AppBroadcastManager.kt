package com.ninos.kotlin_androidutils.broadcast

import android.content.*
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.ninos.kotlin_androidutils.DemoApplication

/**
 * @author Ninos
 * 2020年7月18日
 * 主要解决应用内传递广播数据重复代码问题
 * 简化接收及发送广播步骤
 */
object AppBroadcastManager {
    private val localBroadcastManager = LocalBroadcastManager.getInstance(DemoApplication.context)

    /**
     * 常见接收，传入action，收到广播后会有回调，在回调内处理想要的操作
     *@param action 广播标识，用来过滤匹配发送的广播，为自定义字符串，发送广播和接收者须统一
     *@param handler 回调，接收到广播后的操作，回传intent，获取发送者传递的参数
     */
    fun registerReceiver(action: String, handler: (intent: Intent) -> Unit) {
        //注册广播接收者
        localBroadcastManager.registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) = handler(intent)
        }, IntentFilter(action))
    }

    /**
     * 注册Receiver
     * 对于即用即销的Receiver，只能通过用户创建BroadcastReceiver子类来进行广播
     * 注册传入BroadcastReceiver对象
     *
     *@param broadcastReceiver BroadcastReceiver子类对象
     *@param action 广播标识，用来过滤匹配发送的广播，为自定义字符串，发送广播和接收者须统一
     */
    fun registerReceiver(broadcastReceiver: BroadcastReceiver, action: String) {
        localBroadcastManager.registerReceiver(broadcastReceiver, IntentFilter(action))
    }

    /**
     * 销毁Receiver
     *
     *@param broadcastReceiver BroadcastReceiver子类对象
     */
    fun unregisterReceiver(broadcastReceiver: BroadcastReceiver) {
        localBroadcastManager.unregisterReceiver(broadcastReceiver)
    }

    /**
     * 常见发送，传入intent
     *@param intent Intent(action).putExtra("","")
     */
    fun sendBroadcast(intent: Intent) {
        val intent = intent
        localBroadcastManager.sendBroadcast(intent)
    }

    /**
     * 静态注册发送，传入action和intent
     *@param intent Intent(action).putExtra("","")
     *@param receiverPath 高版本须传入Component的参数
     */
    fun sendBroadcast(intent: Intent, receiverPath: String) {
        val intent = intent
        intent.component = ComponentName(DemoApplication.context.packageName, receiverPath)
        localBroadcastManager.sendBroadcast(intent)
    }
}