import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

const val W = "w"
const val S = "s"
const val A = "a"
const val D = "d"

class Game(val map: Map){
    var currentPlayerMove: String = ""
    var stopped: Boolean = false
    private val foxFSA = FoxFSA()
    private val modeFSA = ModeFSA()

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
        currentPlayerMove = move
    }

    private fun movePlayer(){
        when(currentPlayerMove){
            D -> map.shiftPlayer(1,0)
            A -> map.shiftPlayer(-1, 0)
            W -> map.shiftPlayer(0,-1)
            S -> map.shiftPlayer(0,1)
            else -> {}
        }
        currentPlayerMove = ""
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
        stopped = !map.hasMushrooms()
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