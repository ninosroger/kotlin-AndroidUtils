package com.ninos.kotlin_androidutils.alipay

import android.app.Activity
import com.alipay.sdk.app.EnvUtils
import com.alipay.sdk.app.PayTask

/**
 * @author Ninos
 *
 * AlipayRepository为支付宝支付类
 * 此类包含测试类和数据
 * 为方便，支付宝相关配置参数都放在此类
 *
 * 集成步骤具体看官方文档（https://opendocs.alipay.com/open/204/105296），以下简略介绍
 * step 0
 *      创建应用，绑定信息，公钥私钥（好好保存！好好保存！好好保存！），上线应用（app支付必须上线才可以使用，前期可以使用沙箱环境测试
 * step 1
 *      下载最新版alipay sdk包，放到（app/libs）下。（同时集成阿里百川sdk的需要注意冲突
 * step 2
 *      在项目的build.gradle中，将libs加入依赖（详情见build.gradle文件
 * step 3
 *      在app的build.gradle中，加入alipay sdk依赖（implementation(name: 'alipaySdk-版本', ext: 'aar')
 */
object AlipayRepository {
    private const val ALIPAY_SUCCESS = "9000"
    /**
     * 支付方法
     *
     * @param orderInfo String类型，由服务器生成，支付宝支付必须参数
     * @param activity 唤起支付的activity对象
     * @param payResult 支付后的回调，paySuccess代表是否支付成功，具体订单是否支付成功需要服务器数据验证
     */
    fun alipay2Pay(
        orderInfo: String,
        activity: Activity,
        payResult: (paySuccess: Boolean) -> Boolean
    ) {
        val payRunnable = Runnable {
            val alipay = PayTask(activity)
            val result: Map<String, String> = alipay.payV2(orderInfo, true)

            val payResultMap =
                PayResult(result)
            val resultCode = payResultMap.resultStatus
            payResult(resultCode == ALIPAY_SUCCESS)
        }
        //支付宝支付逻辑必须异步调用
        val payThread = Thread(payRunnable)
        payThread.start()
    }

    /**
     * 测试数据开始位置
     *
     * 正式上线前测试数据   必须   清除，前端不允许保存用户私钥信息
     */

    /**
     * appid，支付宝应用的appid，开放平台查看
     */
    private const val APPID = "此处替换沙箱appid"
    /**
     * rsa2私钥，开发人员生成，与公钥成对，公钥保存在支付宝开放平台并用于生成支付宝公钥
     */
    private const val RSA2_PRIVATE = "私钥字符串"

    /**
     * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
     * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
     * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
     *
     * Base64  OrderInfoUtils2_0  SignUtils 均为测试用的辅助工具类，正式线上环境可以删除
     *
     * orderInfo 的获取必须来自服务端；
     *
     * ！！！！！沙箱环境必须在调用支付前加上   EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX)    ！！！！！
     *
     *
     * 沙箱环境有支付宝分配的商户账号和用户账号
     * 有对应的登录密码和支付密码，并有测试金额
     * 沙箱环境不要登录自己的支付宝账号
     *
     * @param activity 调用测试代码的activity实例
     */
    fun alipay2Test(activity: Activity) {

        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX)

        /**
         * 以下三行具体由服务器端实现，此处为测试代码
         */
        val params =
            OrderInfoUtil2_0.buildOrderParamMap(
                APPID,
                true
            )
        val orderParam =
            OrderInfoUtil2_0.buildOrderParam(
                params
            )
        val sign = OrderInfoUtil2_0.getSign(
            params,
            RSA2_PRIVATE,
            true
        )
        val orderInfo = "$orderParam&$sign"

        alipay2Pay(
            orderInfo,
            activity
        ) {
            /**
             * 回调代码写在java里，kotlin无返回值与java void不一样，java调用无返回值方法需要添加处理代码，例子见：FunUtils
             * 此处boolean类型返回值无用
             */
            /**
             * 回调代码写在java里，kotlin无返回值与java void不一样，java调用无返回值方法需要添加处理代码，例子见：FunUtils
             * 此处boolean类型返回值无用
             */
            return@alipay2Pay it
        }
    }
}