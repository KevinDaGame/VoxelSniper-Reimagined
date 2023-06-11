package com.github.kevindagame.util.brushOperation.executor.strategy

import com.github.kevindagame.snipe.Undo
import com.github.kevindagame.util.brushOperation.operation.BrushOperation

interface ExecutorStrategy<T> where T : BrushOperation {
    fun execute(operations: List<T>, undo: Undo) {
        operations.forEach { it.perform(undo) }
    }
}