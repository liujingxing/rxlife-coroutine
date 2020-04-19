package com.example.coroutine

import rxhttp.wrapper.annotation.DefaultDomain
import rxhttp.wrapper.annotation.Domain

/**
 * User: ljx
 * Date: 2020/4/19
 * Time: 13:33
 */
object Url {

    @Domain(name = "Update")
    const val update = "http://update.9158.com"

    @DefaultDomain //设置为默认域名
    const val baseUrl = "https://www.wanandroid.com/"
}