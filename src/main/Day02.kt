@file:Suppress("KotlinConstantConditions")

package main

import utils.loadInputForDay
import utils.loadTestInputForDay

fun main() {
    fun part1(input: List<String>): Int {
        var horizontalPosition = 0
        var depth = 0

        input.map { it.split(" ") }.forEach { (command, quantityText) ->
            val quantity = quantityText.toInt()

            when (command) {
                "forward" -> horizontalPosition += quantity
                "up" -> depth -= quantity
                "down" -> depth += quantity
                else -> {}
            }
        }

        return horizontalPosition * depth
    }

    fun part2(input: List<String>): Int {
        var horizontalPosition = 0
        var depth = 0
        var aim = 0

        input.map { it.split(" ") }.forEach { (command, quantityText) ->
            val quantity = quantityText.toInt()

            when (command) {
                "forward" -> {
                    horizontalPosition += quantity; depth += aim * quantity
                }
                "up" -> aim -= quantity
                "down" -> aim += quantity
                else -> {}
            }
        }

        return horizontalPosition * depth
    }

    val testInput = loadTestInputForDay(2)
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = loadInputForDay(2)
    println(part1(input))
    println(part2(input))
}
