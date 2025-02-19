package com.phactum.lpm.annotation

import com.phactum.lpm.model.ProcessState

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Color(val ids: Array<String>, val state: ProcessState)

