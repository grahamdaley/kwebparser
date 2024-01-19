package org.kwebparser

import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.kwebparser.support.By
import kotlin.reflect.KProperty

/**
 * The default searchFrom locator, which will lazily locate an element or an element list on a page. This class
 * understands the annotations [org.kwebparser.annotation.FindBy]
 * and [org.kwebparser.annotation.CacheLookup].
 */
open class DefaultElementLocator(
    private val searchFrom: Element,
    annotations: org.kwebparser.AbstractAnnotations,
) : ElementLocator {
    private val shouldCache: Boolean = annotations.isLookupCached
    private val by: By = annotations.buildBy()
    private var cachedElement: Element? = null
    private var cachedElements: Elements? = null

    /**
     * Creates a new searchFrom locator.
     *
     * @param searchFromElement The context to use when finding the searchFrom
     * @param property The property on the Page Object that will hold the located value
     */
    constructor(searchFromElement: Element, property: KProperty<*>) : this(
        searchFromElement,
        org.kwebparser.Annotations(property),
    )

    /**
     * Find the searchFrom.
     */
    override fun findElement(): Element? {
        if (cachedElement != null && shouldCache()) {
            return cachedElement
        }

        val thisElement = by.findElement(searchFrom)

        if (shouldCache()) {
            cachedElement = thisElement
        }

        return thisElement
    }

    /**
     * Find the searchFrom list.
     */
    override fun findElements(): Elements {
        if (cachedElements != null && shouldCache()) {
            return cachedElements!!
        }

        val elements = by.findElements(searchFrom)
        if (shouldCache()) {
            cachedElements = elements
        }

        return elements
    }

    /**
     * Returns whether the searchFrom should be cached.
     *
     * @return `true` if the searchFrom should be cached
     */
    protected fun shouldCache(): Boolean {
        return shouldCache
    }

    override fun toString(): String {
        return this.javaClass.simpleName + " '" + by + "'"
    }
}
