import java.util.*
import java.util.concurrent.SubmissionPublisher
import kotlin.concurrent.thread

class ModeFSA {
    private var modeStateEvent = SubmissionPublisher<String>()
    private var powerModeTimer: Timer? = null
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
                startPowerMode(5000)
            }
        })
    }

    private fun startPowerMode(duration: Long){
        powerModeTimer?.cancel()
        powerModeTimer = null
        currentMode = GameMode.POWERED
        powerModeTimer = Timer()
        powerModeTimer?.schedule(object: TimerTask(){
            override fun run() {
                currentMode = GameMode.NORMAL
            }
        }, duration)
    }
}

enum class GameMode{
    NORMAL,
    POWERED
}