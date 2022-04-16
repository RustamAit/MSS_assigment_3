import java.sql.Time
import java.util.*
import java.util.concurrent.SubmissionPublisher
import kotlin.concurrent.thread

class ModeFSA {
    private var modeStateEvent = SubmissionPublisher<String>()
    private var normalModeTimer: Timer? = null
    var currentMode = GameMode.NORMAL

    init {
        thread {
            listenEvents()
        }
    }

    fun sendEvent(event: String){
        modeStateEvent.submit(event)
    }

    private fun listenEvents(){
        modeStateEvent.subscribe(BasicSubscriber{
            if(it == "p"){
                normalModeTimer?.cancel()
                normalModeTimer = null
                currentMode = GameMode.POWERED
                normalModeTimer = Timer()
                normalModeTimer?.schedule(object: TimerTask(){
                    override fun run() {
                        currentMode = GameMode.NORMAL
                    }
                }, 5000)
            }
        })
    }
}

enum class GameMode{
    NORMAL,
    POWERED
}