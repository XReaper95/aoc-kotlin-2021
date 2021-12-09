private const val BOARD_SIZE = 5
private const val MARKER_VALUE = -1

private fun parseBoards(rawBoardsData: List<String>): List<Board> {
    return rawBoardsData.filter { it.isNotEmpty() }.chunked(BOARD_SIZE).map { Board(it) }
}

class Board(boardRows: List<String>) {
    private var cells: IntArray
    private var hasWon = false

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

    fun checkIfWon(cellRow: Int, cellColumn: Int): Boolean {
        var rowHits = 0
        var columnHits = 0

        for (row in 0 until BOARD_SIZE){
            for (column in 0 until BOARD_SIZE) {
                if (row == cellRow       && this.getCellValue(cellRow, column) == MARKER_VALUE) rowHits += 1
                if (column == cellColumn && this.getCellValue(row, cellColumn) == MARKER_VALUE) columnHits += 1
                if (rowHits >= BOARD_SIZE || columnHits >= BOARD_SIZE) {
                    this.hasWon = true
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

    fun hasNotWon(): Boolean {
        return !this.hasWon
    }
}

fun getWinnerBoardScore(numbersDrawn: IntArray, boards: List<Board>, earlyReturn: Boolean): Int {
    var winnerScore = -1

    numbersDrawn.forEachIndexed { numberIndex, numberDrawn ->
        boards.forEach { board ->
            if (board.hasNotWon()) {
                board.markNumberAndGetPosition(numberDrawn)?.let { (cellRow, cellColumn) ->
                    if (numberIndex >= 5 && board.checkIfWon(cellRow, cellColumn)) {
                        winnerScore = board.getWinScore(numberDrawn)

                        if (earlyReturn) return winnerScore
                    }
                }
            }
        }
    }

    return winnerScore
}

fun getFirstBoardToWinScore(numbersDrawn: IntArray, boards: List<Board>): Int {
    return getWinnerBoardScore(numbersDrawn, boards, earlyReturn = true)
}

fun getLastBoardToWinScore(numbersDrawn: IntArray, boards: List<Board>): Int {
    return getWinnerBoardScore(numbersDrawn, boards, earlyReturn = false)
}

fun main() {
    fun part1(input: List<String>): Int {
        val numbersDrawn = input.first().split(",").map { it.toInt() }.toIntArray()
        val boards = parseBoards(input.drop(1))

        return getFirstBoardToWinScore(numbersDrawn, boards)
    }

    fun part2(input: List<String>): Int {
        val numbersDrawn = input.first().split(",").map { it.toInt() }.toIntArray()
        val boards = parseBoards(input.drop(1))

        return getLastBoardToWinScore(numbersDrawn, boards)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))

}
