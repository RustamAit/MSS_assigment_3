import java.lang.IllegalStateException
import java.util.concurrent.Flow

class BasicSubscriber<T>(private val onNext: (input: T) -> Unit): Flow.Subscriber<T>{
    private var subscription: Flow.Subscription? = null

    override fun onNext(item: T?) {
        item?.let {
            onNext.invoke(item)
        }
        subscription?.request(1)
    }

    override fun onSubscribe(subscription: Flow.Subscription?) {
        this.subscription = subscription
        subscription?.request(1)
    }
    override fun onError(throwable: Throwable?) {
        println(throwable.toString())
        throw throwable ?: IllegalStateException()
    }
    override fun onComplete() {}
}