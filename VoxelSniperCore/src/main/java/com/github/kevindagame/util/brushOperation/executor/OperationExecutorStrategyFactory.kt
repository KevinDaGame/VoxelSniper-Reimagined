package com.github.kevindagame.util.brushOperation.executor

import com.github.kevindagame.util.brushOperation.executor.strategy.*
import com.github.kevindagame.util.brushOperation.operation.*

object OperationExecutorStrategyFactory {
    private val executorMap: MutableMap<Class<out BrushOperation>, ExecutorStrategy<out BrushOperation>> = HashMap()

    init {
        executorMap[BlockOperation::class.java] = BlockExecutorStrategy()
        executorMap[BiomeOperation::class.java] = BiomeExecutorStrategy()
        executorMap[BlockStateOperation::class.java] = BlockStateExecutorStrategy()
        executorMap[CustomOperation::class.java] = CustomOperationExecutor()
        executorMap[EntityRemoveOperation::class.java] = EntityRemoveExecutorStrategy()
        executorMap[EntitySpawnOperation::class.java] = EntitySpawnExecutorStrategy()
    }

    fun setExecutorStrategy(clazz: Class<out BrushOperation>, executor: ExecutorStrategy<out BrushOperation>) {
        executorMap[clazz] = executor
    }

    fun <T: BrushOperation> getStrategy(clazz: Class<T>): ExecutorStrategy<T> {
        return (executorMap[clazz]
            ?: throw IllegalArgumentException("ExecutorStrategy for class " + clazz.name + " not found")) as ExecutorStrategy<T>
    }
}