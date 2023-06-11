package com.github.kevindagame.util.brushOperation.executor

import com.github.kevindagame.snipe.Undo
import com.github.kevindagame.util.brushOperation.executor.strategy.ExecutorStrategy
import com.github.kevindagame.util.brushOperation.operation.BrushOperation

class OperationExecutor<T>(
    private val strategy: ExecutorStrategy<T>,
    val operations: List<T>,
    val undo: Undo
) where T : BrushOperation {
    fun execute() {
        strategy.execute(operations, undo)
    }
}