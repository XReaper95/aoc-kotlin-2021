package main

import utils.loadInputForDay
import utils.loadTestInputForDay

enum class Orientation {
    HORIZONTAL,
    VERTICAL,
    DIAGONAL
}

data class Point(val x: Int, val y: Int)

class VentLine(x1: Int, y1: Int, x2: Int, y2: Int) {
    private val orientation: Orientation
    private val points: Set<Point>

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
                this.orientation = Orientation.DIAGONAL
                val horizontalPointsRange = this.buildPointsRange(x1, x2)
                val verticalPointsRange = this.buildPointsRange(y1, y2)
                this.points = horizontalPointsRange.zip(verticalPointsRange)
                    .map { (x, y) -> Point(x, y) }.toSet()
            }
        }
    }

    private fun buildPointsRange(coord1: Int, coord2: Int): IntProgression {
        return if (coord1 < coord2) coord1..coord2 else coord1 downTo coord2
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

    init {
        this.lines = raw_lines.map {
            val matchResult = pointsRegex.find(it)
            val (x1, y1, x2, y2) = matchResult!!.destructured
            VentLine(x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt())
        }.toList()
    }

    fun countCommonUniquePoints(axialOnly: Boolean): Int {
        val lines = if (axialOnly) this.getAxialLines() else this.lines
        val overlappedPoints: MutableSet<Point> = mutableSetOf()

        for (i in lines.indices) {
            for (j in i + 1 until lines.size) {
                val pointsWithOverlap = lines[i].getOverlappingPoints(lines[j])
                overlappedPoints.addAll(pointsWithOverlap)
            }
        }

        return overlappedPoints.size
    }

    private fun getAxialLines(): List<VentLine> {
        return this.lines.filter { it.isAxial() }
    }
}


fun main() {
    fun part1(input: List<String>): Int {
        val diagram = VentDiagram(input)
        return diagram.countCommonUniquePoints(axialOnly = true)
    }

    fun part2(input: List<String>): Int {
        val diagram = VentDiagram(input)
        return diagram.countCommonUniquePoints(axialOnly = false)
    }

    val testInput = loadTestInputForDay(5)
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = loadInputForDay(5)
    println(part1(input))
    println(part2(input))

}
