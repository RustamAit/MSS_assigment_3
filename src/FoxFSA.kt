import java.lang.IllegalStateException
import java.util.concurrent.Flow
import java.util.concurrent.SubmissionPublisher
import kotlin.concurrent.thread

class FoxFSA {
    private var foxState: FoxState = FoxState.CHASING
    set(value) {
        field = value
        foxStateObserver.submit(value)
    }
    private val foxStateEvent = SubmissionPublisher<String>()
    private val foxStateObserver = SubmissionPublisher<FoxState>()
    var stopped: Boolean = false

    init {
        thread {
            foxStateObserver.subscribe(BasicSubscriber<FoxState>{
                when(it){
                    FoxState.CHASING -> {
                        listenChases()
                    }
                    FoxState.RUNNING -> {
                        listenRuns()
                    }
                    FoxState.EATS -> {
                        listenEats()
                    }
                    FoxState.SUFFERS -> {
                        listenSuffers()
                    }
                }
            })
            foxStateObserver.submit(foxState)
        }
    }

    fun getState() = when(foxState){
        FoxState.CHASING -> "Chasing"
        FoxState.RUNNING -> "Running"
        FoxState.EATS -> "Fox won"
        FoxState.SUFFERS -> "Fox Suffers"
    }

    fun sendEvent(event: String){
        foxStateEvent.submit(event)
    }

    private fun listenChases(){
        foxStateEvent.subscribe(BasicSubscriber{
            when(it){
                "p" -> foxState = FoxState.RUNNING
                "m" -> foxState = FoxState.EATS
                "" -> foxState = FoxState.CHASING
            }
        })
    }

    private fun listenRuns(){
        foxStateEvent.subscribe(BasicSubscriber{
            when(it){
                "p" -> foxState = FoxState.RUNNING
                "m" -> foxState = FoxState.SUFFERS
                "n" -> foxState = FoxState.CHASING
                "" -> foxState = FoxState.RUNNING
            }
        })
    }

    private fun listenEats(){
        foxStateEvent.subscribe(BasicSubscriber{
            println(it)
        })
    }

    private fun listenSuffers(){
        foxStateEvent.subscribe(BasicSubscriber{
            when(it){
                "n" -> foxState = FoxState.CHASING
            }
        })
    }
}

enum class FoxState{
    RUNNING,
    CHASING,
    EATS,
    SUFFERS
}