package com.leventebajak.sudoku.dlx

/**
 * A [Node] that represents a column header in the circular doubly-linked list in [DLX].
 *
 * @property id The id of the column.
 * @property size The number of [nodes][Node] in this column.
 */
class Column(val id: Int) : Node() {
    var size: Int = 0

    /**
     * Removes the column and all [nodes][Node] in it from the circular doubly-linked list in [DLX].
     */
    fun cover() {
        right.left = left
        left.right = right
        var i = down
        while (i != this) {
            var j = i.right
            while (j != i) {
                j.down.up = j.up
                j.up.down = j.down
                j.column!!.size--
                j = j.right
            }
            i = i.down
        }
    }

    /**
     * Reinserts the column and all [nodes][Node] in it into the circular doubly-linked list in [DLX].
     */
    fun uncover() {
        var i = up
        while (i != this) {
            var j = i.left
            while (j != i) {
                j.column!!.size++
                j.down.up = j
                j.up.down = j
                j = j.left
            }
            i = i.up
        }
        right.left = this
        left.right = this
    }

    override fun toString(): String {
        return "Column(id=$id, size=$size)"
    }
}