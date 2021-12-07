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
                "forward" -> { horizontalPosition += quantity; depth += aim * quantity }
                "up" ->  aim -= quantity
                "down" ->  aim += quantity
            }
        }

        return horizontalPosition * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
