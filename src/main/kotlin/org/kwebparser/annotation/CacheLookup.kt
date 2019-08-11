package org.kwebparser.annotation

/**
 * Marker annotation to be applied to WebElements to indicate that it never changes (that is, that
 * the same instance in the DOM will always be used)
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class CacheLookup
