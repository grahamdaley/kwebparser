package org.kwebparser

import org.junit.jupiter.api.Test
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class AnnotatedPropertyTest {

    @Target(AnnotationTarget.PROPERTY)
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Foo

    data class TestData(@Foo val id: Int)

    @Test
    fun test() {
        val obj = TestData(123)
        val idProp = obj::class.declaredMemberProperties.first { it.name == "id" }

        println("# property: " + idProp) // "val ...TestData.id: kotlin.Int"
        println("# annotations: " + idProp.annotations) // [] - but why?
        println("# findAnnotations<Foo>: " + idProp.findAnnotation<Foo>()) // null - but why?

        assertEquals("id", idProp.name)
        assertEquals(1, idProp.annotations.size, "Bug! No annotations found!")
        assertNotNull(idProp.findAnnotation<Foo>())
    }

}