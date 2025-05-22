package com.phactum.lpm.annotation

import com.phactum.lpm.application.BusinessKeyMapper
import com.phactum.lpm.application.BusinessKeyToStringMapper
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Process(
    val startEventId: String,
    val endEventId: String,
    val processFileName: String,
    val useCase: String = "",
    val businessKeyMapper: KClass<out BusinessKeyMapper> = BusinessKeyToStringMapper::class
)

