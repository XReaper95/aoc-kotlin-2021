private const val BOARD_SIZE = 5
private const val MARKER_VALUE = -1

private fun parseBoards(rawBoardsData: List<String>): List<Board> {
    return rawBoardsData.filter { it.isNotEmpty() }.chunked(BOARD_SIZE).map { Board(it) }
}

class Board(boardRows: List<String>) {
    private var cells: IntArray

    init {
        this.cells = boardRows.joinToString(" ")
            .split(" ")
            .filter { it.isNotEmpty() }
            .map { it.toInt() }
            .toIntArray()

        assert(this.cells.size == 25)
    }

    fun markNumberAndGetPosition(number: Int): Pair<Int, Int>? {
        for (row in 0 until BOARD_SIZE){
            for (column in 0 until BOARD_SIZE) {
                if (this.cells[(row * BOARD_SIZE) + column] == number){
                    this.cells[(row * BOARD_SIZE) + column] = MARKER_VALUE
                    return Pair(row, column)
                }
            }
        }

        return null
    }

    fun won(cellRow: Int, cellColumn: Int): Boolean {
        var rowHits = 0
        var columnHits = 0

        for (row in 0 until BOARD_SIZE){
            for (column in 0 until BOARD_SIZE) {
                when {
                    row    == cellRow    && this.getCellValue(cellRow, column) == MARKER_VALUE -> rowHits += 1
                    column == cellColumn && this.getCellValue(row, cellColumn) == MARKER_VALUE -> columnHits += 1
                }

                if (rowHits >= BOARD_SIZE || columnHits >= BOARD_SIZE){
                    return true
                }
            }
        }

        return false
    }

    private fun getCellValue(cellRow: Int, cellColumn: Int): Int {
        return this.cells[(cellRow * BOARD_SIZE) + cellColumn]
    }

    fun getWinScore(winnerNumber: Int): Int {
        val unmarkedSum = this.cells.filter { it != MARKER_VALUE }.sum()

        return unmarkedSum * winnerNumber
    }
}


fun main() {
    fun part1(input: List<String>): Int {
        val numbersDrawn = input.first().split(",").map { it.toInt() }.toIntArray()
        val boards = parseBoards(input.drop(1))

        numbersDrawn.forEachIndexed { numberIndex, numberDrawn ->
            boards.forEach { board ->
                board.markNumberAndGetPosition(numberDrawn)?.let { (cellRow, cellColumn) ->
                    if (numberIndex + 1 >= 5) {
                        if (board.won(cellRow, cellColumn)) {
                            return board.getWinScore(numberDrawn)
                        }
                    }
                }
            }
        }

        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
   // check(part2(testInput) == 230)

    val input = readInput("Day04")
    println(part1(input))
    //println(part2(input))

}
