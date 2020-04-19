package rxhttp.wrapper.param

import kotlin.Any
import kotlin.Deprecated
import kotlin.Unit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import rxhttp.await
import rxhttp.wrapper.callback.ProgressCallback
import rxhttp.wrapper.entity.Progress
import rxhttp.wrapper.parse.Parser
import rxhttp.wrapper.parse.SimpleParser

/**
 * 调用此方法监听上传进度                                                    
 * @param coroutine  CoroutineScope对象，用于开启协程回调进度，进度回调所在线程取决于协程所在线程
 * @param progress 进度回调  
 * 注意：此方法仅在协程环境下才生效                                         
 */
fun RxHttpFormParam.upload(coroutine: CoroutineScope? = null, progress: (Progress) -> Unit):
    RxHttpFormParam {
  param.setProgressCallback(ProgressCallback { currentProgress, currentSize, totalSize ->
      val p = Progress(currentProgress, currentSize, totalSize)
      coroutine?.launch { progress(p) } ?: progress(p)
  })
  return this
}

/**
 * please use [upload] + awaitXxx method instead
 */
@Deprecated("Will be removed in a future release")
suspend inline fun <reified T : Any> RxHttpFormParam.awaitUpload(coroutine: CoroutineScope? = null,
    noinline progress: (Progress) -> Unit): T = awaitUpload(object: SimpleParser<T>() {}, coroutine,
    progress)

/**
 * please use [upload] + awaitXxx method instead
 */
@Deprecated("Will be removed in a future release")
suspend fun <T : Any> RxHttpFormParam.awaitUpload(
  parser: Parser<T>,
  coroutine: CoroutineScope? = null,
  progress: (Progress) -> Unit
): T {
  upload(coroutine, progress)
  return await(parser)
}
