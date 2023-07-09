package main.kotlin.leetcode

import java.util.*
import kotlin.math.abs

/**
 * Path With Minimum Effort
 * https://leetcode.com/problems/path-with-minimum-effort/description/
 */

class leetcode_0709 {
    private var directions = arrayOf(
        intArrayOf(0, 1),
        intArrayOf(1, 0),
        intArrayOf(0, -1),
        intArrayOf(-1, 0)
    )

    fun minimumEffortPath(heights: Array<IntArray>): Int {
        val row = heights.size
        val col = heights[0].size
        val differenceMatrix = Array(row) { IntArray(col) }
        for (eachRow in differenceMatrix)
            Arrays.fill(eachRow, Int.MAX_VALUE)

        differenceMatrix[0][0] = 0

        val visited = Array(row) { BooleanArray(col) }

        val queue = PriorityQueue { a: Cell, b: Cell ->
            a.difference.compareTo(b.difference)
        }

        queue.add(Cell(0, 0, differenceMatrix[0][0]))
        while (!queue.isEmpty()) {
            val curr = queue.poll()
            visited[curr.x][curr.y] = true

            if (curr.x == row - 1 && curr.y == col - 1)
                return curr.difference

            for (direction in directions) {
                val adjacentX = curr.x + direction[0]
                val adjacentY = curr.y + direction[1]

                if (isValidCell(adjacentX, adjacentY, row, col) && !visited[adjacentX][adjacentY]) {
                    val currentDifference = abs(heights[adjacentX][adjacentY] - heights[curr.x][curr.y])
                    val maxDifference = currentDifference.coerceAtLeast(differenceMatrix[curr.x][curr.y])

                    if (differenceMatrix[adjacentX][adjacentY] > maxDifference) {
                        differenceMatrix[adjacentX][adjacentY] = maxDifference
                        queue.add(Cell(adjacentX, adjacentY, maxDifference))
                    }
                }
            }
        }

        return differenceMatrix[row - 1][col - 1]
    }

    private fun isValidCell(
        x: Int,
        y: Int,
        row: Int,
        col: Int
    ): Boolean {
        return x >= 0 &&
                x <= row - 1 &&
                y >= 0 &&
                y <= col - 1
    }
}

data class Cell(var x: Int, var y: Int, var difference: Int)

fun main() {
    println(
        leetcode_0709().minimumEffortPath(
            arrayOf(
                intArrayOf(1, 2, 1, 1, 1),
                intArrayOf(1, 2, 1, 2, 1),
                intArrayOf(1, 2, 1, 2, 1),
                intArrayOf(1, 2, 1, 2, 1),
                intArrayOf(1, 1, 1, 2, 1)
            )
        )
    )

    println(
        leetcode_0709().minimumEffortPath(
            arrayOf(
                intArrayOf(1, 2, 3),
                intArrayOf(3, 8, 4),
                intArrayOf(5, 3, 5),
            )
        )
    )
}