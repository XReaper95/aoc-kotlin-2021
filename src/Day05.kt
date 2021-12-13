enum class Orientation {
    HORIZONTAL,
    VERTICAL,
    DIAGONAL
}

data class Point(val x: Int, val y: Int) {
    override fun toString(): String {
        return "(${this.x}, ${this.y})"
    }
}

class VentLine(x1: Int, y1: Int, x2: Int, y2: Int) {
    private val orientation: Orientation
    val points: Set<Point>

    init {
        when {
            y1 == y2 -> {
                this.orientation = Orientation.HORIZONTAL
                val pointsRange = this.buildPointsRange(x1, x2)
                this.points = pointsRange.map { Point(it, y1) }.toSet()
            }
            x1 == x2 -> {
                this.orientation = Orientation.VERTICAL
                val pointsRange = this.buildPointsRange(y1, y2)
                this.points = pointsRange.map { Point(x1, it) }.toSet()
            }
            else -> {
                this.orientation = Orientation.VERTICAL
                this.points = emptySet()
            }
        }
    }

    private fun buildPointsRange(coord1: Int, coord2: Int): IntProgression {
        return if (coord1 < coord2) coord1..coord2 else coord1 downTo coord2
    }

    override fun toString(): String {
        return "${this.orientation}: ${this.points.first()} -> ${this.points.last()}"
    }

    fun isAxial(): Boolean {
        return this.orientation == Orientation.HORIZONTAL || this.orientation == Orientation.VERTICAL
    }

    fun getOverlappingPoints(other: VentLine): Set<Point> {
        return this.points intersect other.points
    }
}

class VentDiagram(raw_lines: List<String>) {
    private val pointsRegex = """(\d+),(\d+) -> (\d+),(\d+)""".toRegex()
    private val lines: List<VentLine>
    private val axialLines: List<VentLine>

    init {
        this.lines = raw_lines.map {
            val matchResult = pointsRegex.find(it)
            val (x1, y1, x2, y2) = matchResult!!.destructured
            VentLine(x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt())
        }.toList()

        this.axialLines = this.lines.filter { it.isAxial() }
    }

    fun countCommonUniquePoints(axialOnly: Boolean): Int {
        val lines = if (axialOnly) this.axialLines else this.lines
        val overlappedPoints: MutableSet<Point> = mutableSetOf()

        for (i in lines.indices) {
            for (j in i + 1 until lines.size) {
                val pointsWithOverlap = lines[i].getOverlappingPoints(lines[j])
                overlappedPoints.addAll(pointsWithOverlap)
            }
        }

        return overlappedPoints.size
    }

    fun printLines(axialOnly: Boolean) {
        val lines = if (axialOnly) this.axialLines else this.lines

        lines.forEach { println(it) }
    }
}


fun main() {
    fun part1(input: List<String>): Int {
        val diagram = VentDiagram(input)
        return diagram.countCommonUniquePoints(axialOnly = true)
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    //check(part2(testInput) == 1924)

    val input = readInput("Day05")
    println(part1(input))
    //println(part2(input))

}
