fun main() {
    fun part1(input: List<String>, days: Int): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }
    
    val testInput = readInput("Day06_test")
    check(part1(testInput, 18) == 26)
    check(part1(testInput, 80) == 5934)
//    check(part2(testInput) == 12)

    val input = readInput("Day06")
    println(part1(input, 0))
//    println(part2(input))

}
