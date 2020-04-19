[ ![Download](https://api.bintray.com/packages/32774707/maven/rxlife-coroutine/images/download.svg) ](https://bintray.com/32774707/maven/rxlife-coroutine/_latestVersion)

**gradle依赖**
```java
dependencies {
   //管理协程生命周期，页面销毁，自动关闭协程
   implementation 'com.ljx.rxlife:rxlife-coroutine:2.0.0'
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

- launch方法有共有4个参数，仅有第一个参数是必须的，其它3个参数都有默认值，可以不穿

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
