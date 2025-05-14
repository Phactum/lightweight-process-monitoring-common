package com.phactum.lpm.annotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Tasks(val value: Array<Task>)
