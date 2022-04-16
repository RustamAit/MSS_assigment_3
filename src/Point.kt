class Point(var x: Int, var y: Int) {
    fun shift(xIncr: Int, yIncr: Int, xMax: Int, yMax: Int){
        x += xIncr
        y += yIncr
    }

    override fun equals(other: Any?): Boolean {
        if(other is Point){
            return other.x == this.x && other.y == this.y
        }
        return false
    }

    override fun toString(): String {
        return "Point ($x $y)"
    }
}