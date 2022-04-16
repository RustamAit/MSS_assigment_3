class Map(val xMax: Int, val yMax: Int) {
    var playerPosition = Point(xMax, yMax)
    var foxPosition = Point(2, 2)
    private var mushroomPositions = mutableListOf<Point>()
    private var powerMushroomPositions = mutableListOf<Point>()
    private var treesPositions = mutableListOf<Point>()

    init {
       populateMap()
    }

    private fun populateMap(){
        mushroomPositions.add(Point(11,11))
        //mushroomPositions.addAll(listOf(Point(2,3), Point(5,9), Point(10,1), Point(2,11)))
        powerMushroomPositions.add(Point(12,11))
        treesPositions.addAll(listOf(Point(5,8), Point(6,8), Point(6,9), Point(6,10), Point(6,11)))
    }

    fun shiftFox(xIncr: Int, yIncr: Int){
        if(couldShift(foxPosition, xIncr, yIncr)) foxPosition.shift(xIncr, yIncr, xMax, yMax)
    }

    fun shiftPlayer(xIncr: Int, yIncr: Int){
        if(couldShift(playerPosition, xIncr, yIncr)) playerPosition.shift(xIncr, yIncr, xMax, yMax)
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