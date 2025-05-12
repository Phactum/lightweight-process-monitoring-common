package com.phactum.lpm.annotation

import com.phactum.lpm.model.ProcessState

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class TaskState(val elementIds: Array<String>, val state: ProcessState)

