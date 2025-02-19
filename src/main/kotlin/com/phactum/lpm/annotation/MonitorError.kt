package com.phactum.lpm.annotation

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class MonitorError(val id: String, val processFileName: String)
