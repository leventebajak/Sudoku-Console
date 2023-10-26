package sudoku

import Matrix
import dlx.DLX
import java.util.BitSet

/**
 * Sudoku solver using [DLX].
 */
object SudokuSolver {
    /**
     * Gets all solutions to the [Board].
     *
     * @param board The [Board] to solve.
     * @param limit The maximum number of solutions to find before stopping. Defaults to 1000.
     * @return A list of all solutions to the [board].
     */
    fun findAllSolutionsFor(board: Board, limit: Int = 1_000): List<Board> {
        val solutions = findNSolutionsFor(board, limit)
        if (solutions.size == limit)
            println("$limit solutions found, stopping")
        return solutions
    }

    /**
     * Gets maximum [n] solutions to the [Board].
     *
     * @param n The maximum number of solutions to find.
     * @return A list of maximum [n] solutions to the [board].
     */
    fun findNSolutionsFor(board: Board, n: Int): List<Board> {
        val dlx = DLX(getMatrix(), getClueRows(board))
        val solutions = dlx.findNSolutions(n)
        return solutions.map { it.toBoard() }
    }

    /**
     * Gets the [Exact Cover Matrix](https://www.stolaf.edu/people/hansonr/sudoku/exactcovermatrix.htm)
     * representation of the constraints of a Sudoku board.
     *
     * @return A [Matrix] representing the constraints of a Sudoku [Board].
     */
    private fun getMatrix(): Matrix {
        val matrix = Matrix(324, MutableList(729) { BitSet(324) })

        // Fill in the matrix with every possible combination
        for (row in 0..8) {
            for (col in 0..8) {
                for (n in 1..9) {
                    val i = row * 81 + col * 9 + n - 1

                    // Cell constraint
                    matrix.rows[i][row * 9 + col] = true

                    // Row constraint
                    matrix.rows[i][81 + row * 9 + n - 1] = true

                    // Column constraint
                    matrix.rows[i][162 + col * 9 + n - 1] = true

                    // Box constraint
                    matrix.rows[i][243 + (row / 3 * 3 + col / 3) * 9 + n - 1] = true

                    // println("Row $i: ${matrix.rows[i]}")
                }
            }
        }
        return matrix
    }

    /**
     * Gets the rows of the [Exact Cover Matrix](https://www.stolaf.edu/people/hansonr/sudoku/exactcovermatrix.htm)
     * that are filled in. These are the clues of the Sudoku board.
     *
     * @param board The [Board] with the clues.
     * @return A list of the filled in rows.
     * @throws IllegalArgumentException If the [board] is invalid.
     * @see getMatrix
     */
    private fun getClueRows(board: Board): MutableList<Int> {
        val clueRows = mutableListOf<Int>()
        for (row in 0..8) for (col in 0..8) {
            val n = board[row, col]
            if (n == 0)
                continue
            clueRows.add(row * 81 + col * 9 + n - 1)
        }
        return clueRows

    }

}