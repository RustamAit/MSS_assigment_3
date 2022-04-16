class Map(private val xMax: Int, private val yMax: Int) {
    var playerPosition = Point(xMax, yMax)
    var foxPosition = Point(2, 2)
    private var mushroomPositions = mutableListOf<Point>()
    private var powerMushroomPositions = mutableListOf<Point>()

    init {
       populateMap()
    }

    private fun populateMap(){
        mushroomPositions.addAll(listOf(Point(11,11), Point(2,3), Point(5,9), Point(10,1), Point(2,11)))
        powerMushroomPositions.add(Point(12,11))
    }

    fun shiftFox(xIncr: Int, yIncr: Int){
        if(couldShift(foxPosition, xIncr, yIncr)) foxPosition.shift(xIncr, yIncr)
    }

    fun shiftPlayer(xIncr: Int, yIncr: Int){
        if(couldShift(playerPosition, xIncr, yIncr)) playerPosition.shift(xIncr, yIncr)
    }

    private fun couldShift(point: Point, xIncr: Int, yIncr: Int): Boolean {
        return (xIncr + point.x) <= xMax && (yIncr + point.y) <= yMax
                && (xIncr + point.x) >= 0 && (yIncr + point.y) >= 0
    }

    fun updateMushrooms(){
        mushroomPositions.remove(playerPosition)
    }

    fun updatePowerMushrooms(){
        powerMushroomPositions.remove(playerPosition)
    }

    fun hasMushrooms() = mushroomPositions.isNotEmpty()

    fun playerInPowerMushroomPosition() = powerMushroomPositions.contains(playerPosition)
}