package com.phactum.lpm.annotation

@Target(
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.FIELD,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
)
@Retention(AnnotationRetention.RUNTIME)
@Repeatable
annotation class CorrelateWith(
    val from: String,
    val label: String = "",
)
