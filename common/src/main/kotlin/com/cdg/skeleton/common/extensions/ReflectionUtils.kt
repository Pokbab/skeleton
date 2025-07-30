package com.cdg.skeleton.common.extensions

import kotlin.reflect.KClass
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.memberProperties

object ReflectionUtils {
    fun <T : Any> getter(
        obj: T,
        propertyName: String,
    ): Any? {
        obj::class.memberProperties.forEach { kCallable ->
            if (propertyName == kCallable.name) {
                return kCallable.getter.call(obj) as Any
            }
        }
        return null
    }

    fun callFunctionInCompanionObject(
        klass: KClass<*>,
        functionName: String,
        vararg args: Any?,
    ): Any? {
        var currentClass: KClass<*>? = klass
        while (currentClass != null) {
            val companionObject = currentClass.companionObjectInstance
            if (companionObject != null) {
                val function = companionObject::class.declaredFunctions.find { it.name == functionName }
                if (function != null) {
                    return function.call(companionObject, *args)
                }
            }
            currentClass = currentClass.supertypes.firstOrNull()?.classifier as KClass<*>?
        }
        return null
    }
}
