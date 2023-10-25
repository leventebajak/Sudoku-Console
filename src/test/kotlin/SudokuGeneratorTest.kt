import sudoku.SudokuGenerator
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.assertTrue

class SudokuGeneratorTest {
    @Test
    fun generate() {
        val sudoku = SudokuGenerator.generate(SudokuGenerator.Difficulty.EASY)

        assertEquals(1, sudoku.solutions.size)

        for (i in 0..8) {
            val remainingInRow = (1..9).toMutableSet()
            val remainingInCol = (1..9).toMutableSet()
            for (j in 0..8) {
                assertTrue(remainingInRow.remove(sudoku.solutions[0][i, j]))
                assertTrue(remainingInCol.remove(sudoku.solutions[0][j, i]))
            }
        }

        for (boxRow in 0..2) for (boxCol in 0..2) {
            val remainingInBox = (1..9).toMutableSet()
            for (r in 0..2) for (c in 0..2)
                assertTrue(remainingInBox.remove(sudoku.solutions[0][boxRow * 3 + r, boxCol * 3 + c]))
        }
    }
}