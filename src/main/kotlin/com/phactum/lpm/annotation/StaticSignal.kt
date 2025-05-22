package com.phactum.lpm.annotation

import com.phactum.lpm.model.ProcessState

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class StaticSignal
