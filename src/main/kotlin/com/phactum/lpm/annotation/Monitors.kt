package com.phactum.lpm.annotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Monitors(val value: Array<Task>)
