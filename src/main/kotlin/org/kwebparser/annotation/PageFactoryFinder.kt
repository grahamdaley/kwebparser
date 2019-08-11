package org.kwebparser.annotation

import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
annotation class PageFactoryFinder(val value: KClass<out org.kwebparser.AbstractFindByBuilder>)
