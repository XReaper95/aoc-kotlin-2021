import kotlin.math.floor

private const val BOARD_SIZE = 5
private const val MARKER_VALUE = -1

private fun parseBoards(rawBoardsData: List<String>): List<Board> {
    return rawBoardsData.filter { it.isNotEmpty() }.chunked(BOARD_SIZE).map { Board(it) }
}

data class CellPosition(val row: Int, val column: Int)

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

    fun markNumberAndGetPosition(number: Int): CellPosition? {
        for (index in 0 until this.cells.size){
            if (this.cells[index] == number) {
                val position = getPositionFromIndex(index)
                this.markCell(position.row, position.column)

                return position
            }
        }

        return null
    }

    private fun getPositionFromIndex(index: Int): CellPosition {
        val nearestSizeMultipleDown = floor(BOARD_SIZE.toDouble() * (index / BOARD_SIZE)).toInt()
        val row = if (nearestSizeMultipleDown > 0) nearestSizeMultipleDown / BOARD_SIZE else 0
        val column = index - nearestSizeMultipleDown

        return CellPosition(row, column)
    }

    fun checkIfWon(cellRow: Int, cellColumn: Int): Boolean {
        var rowHits = 0
        var columnHits = 0

        for (index in 0 until BOARD_SIZE) {
            if (this.getCellValue(cellRow, index) == MARKER_VALUE) rowHits += 1
            if (this.getCellValue(index, cellColumn) == MARKER_VALUE) columnHits += 1
        }

        if (rowHits >= BOARD_SIZE || columnHits >= BOARD_SIZE) {
            this.hasWon = true
        }

        return this.hasWon
    }

    private fun getCellValue(cellRow: Int, cellColumn: Int): Int {
        return this.cells[(cellRow * BOARD_SIZE) + cellColumn]
    }

    private fun markCell(cellRow: Int, cellColumn: Int) {
        this.cells[(cellRow * BOARD_SIZE) + cellColumn] = MARKER_VALUE
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

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))

}
