import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread


class Game(private val map: Map){
    private var currentPlayerMove: ActionType = ActionType.NONE
    private val foxFSA = FoxFSA()
    private val modeFSA = ModeFSA()

    var stopped: Boolean = false

    fun run(){
        logCurrentState()
        thread {
            while (!stopped){
                TimeUnit.SECONDS.sleep(2)
                movePlayer()
                moveFox()
                updateGameMode()
                updateFoxState()
                updateMap()
                updateGame()
                logCurrentState()
            }
        }
    }

    fun updateCurrentPlayerMove(move: String){
        currentPlayerMove = enumValueOf(move.trim().uppercase())
    }

    private fun movePlayer(){
        when(currentPlayerMove){
            ActionType.D -> map.shiftPlayer(1,0)
            ActionType.A -> map.shiftPlayer(-1, 0)
            ActionType.W -> map.shiftPlayer(0,-1)
            ActionType.S -> map.shiftPlayer(0,1)
            ActionType.NONE -> {}
        }
        currentPlayerMove = ActionType.NONE
    }

    private fun moveFox(){
        map.shiftFox(1,1)
    }

    private fun updateGameMode(){
        if(map.playerInPowerMushroomPosition()){
            modeFSA.sendEvent("p")
        } else {
            modeFSA.sendEvent("")
        }
    }

    private fun updateMap(){
        map.updateMushrooms()
        map.updatePowerMushrooms()
    }

    private fun updateFoxState(){
        when{
            map.playerPosition == map.foxPosition -> foxFSA.sendEvent("m")
            modeFSA.currentMode == GameMode.POWERED -> foxFSA.sendEvent("p")
            modeFSA.currentMode == GameMode.NORMAL -> foxFSA.sendEvent("n")
            else -> foxFSA.sendEvent("")
        }
    }

    private fun updateGame(){
        stopped = !map.hasMushrooms() || foxFSA.getState() == "Fox won"
        if(stopped){
            println("GAME END")
        }
    }

    private fun logCurrentState(){
        println()
        println("Fox State: ${foxFSA.getState()}")
        println("Fox position: ${map.foxPosition}")
        println("Player position: ${map.playerPosition}")
        println("-------")
        println("Next move ->")
    }
}

enum class ActionType(val value: String){
    W("w"),
    S("s"),
    A("a"),
    D("d"),
    NONE("")
}