package com.example.coroutine

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.rxLifeScope
import com.example.coroutine.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import rxhttp.toDownload
import rxhttp.wrapper.param.RxHttp

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        RxHttp.setDebug(true)
    }

    //文件下载，带进度
    fun downloadAndProgress(view: View) {
        val launch = rxLifeScope.launch({
            //文件存储路径
            val destPath = externalCacheDir.toString() + "/" + System.currentTimeMillis() + ".apk"
            val result = RxHttp.get("/miaolive/Miaolive.apk")
                .setDomainToUpdateIfAbsent() //使用指定的域名
                .toDownload(destPath, Dispatchers.Main) {
                    //下载进度回调,0-100，仅在进度有更新时才会回调，最多回调101次，最后一次回调文件存储路径
                    val currentProgress = it.progress //当前进度 0-100
                    val currentSize = it.currentSize //当前已下载的字节大小
                    val totalSize = it.totalSize //要下载的总字节大小
                    mBinding.tvResult.append(it.toString())
                }
                .await()
            mBinding.tvResult.append(result)
        }, {
            mBinding.tvResult.append("\n${it.message}")
        })
    }
}
