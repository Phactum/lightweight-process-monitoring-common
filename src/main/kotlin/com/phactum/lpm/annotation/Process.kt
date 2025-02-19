package com.phactum.lpm.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Process(val startId: String, val endId: String, val processFileName: String = "")

