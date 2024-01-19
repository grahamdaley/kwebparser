package org.kwebparser

import org.kwebparser.annotation.CacheLookup
import org.kwebparser.annotation.FindAll
import org.kwebparser.annotation.FindBy
import org.kwebparser.annotation.FindBys
import org.kwebparser.annotation.PageFactoryFinder
import org.kwebparser.support.By
import org.kwebparser.support.ByIdOrName
import kotlin.reflect.KProperty
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.findAnnotation

/**
 * @param property expected to be an element in a Page Object
 */
class Annotations(
    private val property: KProperty<*>,
) : AbstractAnnotations() {
    /**
     * {@inheritDoc}
     *
     * @return true if @CacheLookup annotation exists on a property
     */
    override val isLookupCached: Boolean
        get() = property.findAnnotation<CacheLookup>() != null

    /**
     * {@inheritDoc}
     *
     * Looks for one of [org.openqa.selenium.support.FindBy],
     * [org.openqa.selenium.support.FindBys] or
     * [org.openqa.selenium.support.FindAll] property annotations. In case
     * no annotations provided for property, uses property name as 'id' or 'name'.
     * @throws IllegalArgumentException when more than one annotation on a property provided
     */
    override fun buildBy(): By {
        assertValidAnnotations()

        var ans: By? = null

        for (annotation in property.annotations) {
            val pageFactoryFinder = annotation.annotationClass.findAnnotation<PageFactoryFinder>()
            var builder: org.kwebparser.AbstractFindByBuilder? = null
            if (pageFactoryFinder != null) {
                try {
                    builder = pageFactoryFinder.value.createInstance()
                } catch (_: ReflectiveOperationException) {
                }
            }
            if (builder != null) {
                ans = builder.buildIt(annotation, property)
                break
            }
        }

        if (ans == null) {
            ans = buildByFromDefault()
        }

        return ans
    }

    private fun buildByFromDefault(): By {
        return ByIdOrName(property.name)
    }

    private fun assertValidAnnotations() {
        val findBys = property.findAnnotation<FindBys>()
        val findAll = property.findAnnotation<FindAll>()
        val findBy = property.findAnnotation<FindBy>()

        assertFindBysWithoutFindBy(findBys, findBy)
        assertFindAllWithoutFindBy(findAll, findBy)
        assertFindAllWithoutFindBys(findAll, findBys)
    }

    private fun assertFindBysWithoutFindBy(
        findBys: FindBys?,
        findBy: FindBy?,
    ) {
        if (findBys != null && findBy != null) {
            throw IllegalArgumentException(
                "If you use a '@FindBys' annotation, " +
                    "you must not also use a '@FindBy' annotation",
            )
        }
    }

    private fun assertFindAllWithoutFindBy(
        findAll: FindAll?,
        findBy: FindBy?,
    ) {
        if (findAll != null && findBy != null) {
            throw IllegalArgumentException(
                "If you use a '@FindAll' annotation, " +
                    "you must not also use a '@FindBy' annotation",
            )
        }
    }

    private fun assertFindAllWithoutFindBys(
        findAll: FindAll?,
        findBys: FindBys?,
    ) {
        if (findAll != null && findBys != null) {
            throw IllegalArgumentException(
                "If you use a '@FindAll' annotation, " +
                    "you must not also use a '@FindBys' annotation",
            )
        }
    }
}
