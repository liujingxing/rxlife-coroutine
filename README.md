[ ![Download](https://api.bintray.com/packages/32774707/maven/rxlife-coroutine/images/download.svg) ](https://bintray.com/32774707/maven/rxlife-coroutine/_latestVersion)

**gradle依赖**
```java
dependencies {
   //管理协程生命周期，页面销毁，自动关闭协程
   implementation 'com.ljx.rxlife:rxlife-coroutine:2.0.1'
}
```
**RxHttp&RxLife 交流群：378530627**

**友情提示: RxLife与[RxHttp](https://github.com/liujingxing/RxHttp)搭配使用，协程发请求，简直不要太爽**

# 简介

***RxLife-Coroutine 优势***

- ***开启协程，并自动管理协程生命周期，在页面销毁时，自动关闭协程***

- ***自动捕获协程异常，通过回调可拿到异常信息***

- ***可监听协程开启及结束***



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
