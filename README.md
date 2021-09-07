[![](https://img.shields.io/badge/QQ群-378530627-red.svg)](https://jq.qq.com/?_wv=1027&k=E53Hakvv)

# 该项目已停止维护，正式被抛弃，原因如下：

1、同一个`FragmentActivity/Fragment`下，`rxLifeScope`与`lifecycleScope`不能共用；同一个ViewModel下，`rxLifeScope`与`viewModelScope`不能共用                      
                                                                                                                                         
2、配合[RxHttp](https://github.com/liujingxing/RxHttp)发请求时，每次都要开启一个协程来捕获异常，这对于再次封装的人，非常不友好；                                                                                        
目前`RxHttp`的`awaitResult`操作符一样可以捕获异常，所以`rxLifeScope`就没了用武之地，是时候退出历史舞台了                                                                    
                                                                                                                                         
3、不能同`lifecycleScope`或`viewModelScope`一样，开启协程时，传入`CoroutineContext`或`CoroutineStart`参数                                                   
亦没有一系列`launchXxx`方法                                                                                                                      
                                                                                                                                         
4、`rxLifeScope`配合`RxHttp v2.6.6`及以上版本发请求时，调用`async`方法将导致请求结束回调不被调用           


# 替代品

```java
//lifecycleScope                                                  
implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1"   
//viewModelScope                                                  
implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1" 
```
**使用**
```java
//使用lifecycleScope或viewModelScope开协程，更多用法，请自行查阅相关资料
lifecycleScope.launch {      
     //请求开始                  
    RxHttp.get("...")        
        .toStr()             
        .awaitResult {       
             //请求成功          
        }.onFailure {        
             //请求异常          
        }                    
    //请求结束                   
}                            
```
其中`awaitResult`只是`RxHttp`的一种异常处理操作符，更多异常操作符，请查看[RxHttp ，比Retrofit 更优雅的协程体验](https://juejin.cn/post/6844904100090347528)

以上代码，同样可以做到页面销毁，协程自动关闭，协程关闭，请求跟着关闭








# 简介

***RxLife-Coroutine 优势***

- ***开启协程，并自动管理协程生命周期，在页面销毁时，自动关闭协程***

- ***自动捕获协程异常，通过回调可拿到异常信息***

- ***可监听协程开启及结束***

**gradle依赖**
```java
dependencies {
   //管理协程生命周期，页面销毁，自动关闭协程
   implementation 'com.github.liujingxing.rxlife:rxlife-coroutine:2.2.0'
}
```


# 使用
## FragmentActivity/Fragment/ViewModel环境下
```java
rxLifeScope.launch({
    //协程体
}, {
    //异常回调
}, {
    //协程开始回调
}, {
    //协程结束回调，不管成功/失败都会回调
})
```

以上代码，有两点需要注意

- launch方法有共有4个参数，仅有第一个参数是必须的，其它3个参数都有默认值，可以不传

- 我们无需手动关闭协程，在页面销毁时，会自动关闭协程，以防止内存泄露

## 非FragmentActivity/Fragment/ViewModel环境下
```java
val job = RxLifeScope().launch({
    //协程体
}, {
    //异常回调
}, {
    //协程开始回调
}, {
    //协程结束回调，不管成功/失败都会回调
})

//在合适的时机，手动关闭协程
job.cancel()
```
以上代码使用`RxLifeScope()`手动创建了RxLifeScope对象，这种方式，我们需要在合适的时机，拿到`Job`对象，关闭协程

# 更新日志

### v2.0.1 (2020-09-14)

- 修改：协程被关闭后，若有异常，不再走失败回调

- 修复：捕获失败回调里的异常，并打印日志


# Licenses
```
Copyright 2020 liujingxing

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
