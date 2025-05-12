package com.phactum.lpm.annotation

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Task(
    val id: String,
    val beforeIds: Array<String> = [],
    val afterIds: Array<String> = [],
    val errorEndEventIds: Array<String> = [],
)
