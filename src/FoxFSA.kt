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

    init {
        thread {
            foxStateObserver.subscribe(BasicSubscriber<FoxState>{
                when(it){
                    FoxState.CHASING -> {
                        listenChaseState()
                    }
                    FoxState.RUNNING -> {
                        listenRunState()
                    }
                    FoxState.EATS -> {
                        listenEatsState()
                    }
                    FoxState.SUFFERS -> {
                        listenSuffersState()
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

    private fun listenChaseState(){
        foxStateEvent.subscribe(BasicSubscriber{
            when(it){
                "p" -> foxState = FoxState.RUNNING
                "m" -> foxState = FoxState.EATS
                "" -> foxState = FoxState.CHASING
            }
        })
    }

    private fun listenRunState(){
        foxStateEvent.subscribe(BasicSubscriber{
            when(it){
                "p" -> foxState = FoxState.RUNNING
                "m" -> foxState = FoxState.SUFFERS
                "n" -> foxState = FoxState.CHASING
                "" -> foxState = FoxState.RUNNING
            }
        })
    }

    private fun listenEatsState(){
        foxStateEvent.subscribe(BasicSubscriber{
        })
    }

    private fun listenSuffersState(){
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