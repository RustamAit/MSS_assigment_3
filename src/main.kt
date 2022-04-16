import kotlin.collections.Map

const val VALID_INPUTS = "wasd"

fun main(){
    play()
}

fun play(){
    var game: Game? = null
    while (true){
        if(game?.stopped == true){
            println("Game stopped")
            break
        }
        val text = readLine()
        if(text == "new"){
            game = Game(kotlin.collections.Map(12, 12))
            game.run()
        }
        if(VALID_INPUTS.contains(text ?: "")){
            if(game == null){
                println("Please type 'new' to start game")
            } else {
                game.updateCurrentPlayerMove(text ?: "w")
            }
        }
    }
}