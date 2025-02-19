package com.phactum.lpm.annotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class MonitorErrors(val value: Array<MonitorError>)

