package com.phactum.lpm.application

import com.phactum.lpm.model.BusinessKey

interface BusinessKeyMapper {
    fun map(args: List<Any?>) : BusinessKey
}

class BusinessKeyToStringMapper : BusinessKeyMapper {
    override fun map(args: List<Any?>) : BusinessKey {
        return BusinessKey(args.joinToString("_"))
    }
}