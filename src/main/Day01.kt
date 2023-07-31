package main

import utils.loadInputForDay
import utils.loadTestInputForDay

fun main() {
    fun calculation(input: List<Int>): Int {
        return input.zipWithNext { current, next -> if (current < next) 1 else 0 }.sum()
    }

    fun part1(input: List<String>): Int {
        return calculation(input.map { s -> s.toInt() })
    }

    fun part2(input: List<String>): Int {
        val chunkedInput = input.windowed(3) {
            it.sumOf { s -> s.toInt() }
        }

        return calculation(chunkedInput)
    }

    val testInput = loadTestInputForDay(1)
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = loadInputForDay(1)
    println(part1(input))
    println(part2(input))
}
