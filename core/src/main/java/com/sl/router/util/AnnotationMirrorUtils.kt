package com.sl.router.util

import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.AnnotationValue
import javax.lang.model.element.Element
import javax.lang.model.element.VariableElement
import javax.lang.model.type.TypeMirror

object AnnotationMirrorUtils {

    private fun getEventTypeAnnotationMirror(typeElement: Element, clazz: Class<*>): AnnotationMirror? {
        val clazzName = clazz.name
        for (m in typeElement.annotationMirrors) {
            if (m.annotationType.toString() == clazzName) {
                return m
            }
        }
        return null
    }

    private fun getAnnotationValue(annotationMirror: AnnotationMirror, key: String): AnnotationValue? {
        for ((key1, value) in annotationMirror.elementValues) {
            if (key1!!.simpleName.toString() == key) {
                return value
            }
        }
        return null
    }

    public fun<T> getValue(foo: Element, clazz: Class<*>, key: String): T? {
        val am = getEventTypeAnnotationMirror(foo, clazz) ?: return null
        val av = getAnnotationValue(am, key)
        return if (av == null) {
            null
        } else {
            av.value as T
        }
    }

}