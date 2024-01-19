package org.kwebparser

import org.kwebparser.support.By

/**
 * Abstract class to work with fields in Page Objects.
 * Provides methods to process [org.kwebparser.annotation.FindBy],
 * [org.kwebparser.annotation.FindBys] and
 * [org.kwebparser.annotation.FindAll] annotations.
 */
abstract class AbstractAnnotations {
    /**
     * Defines whether or not given element
     * should be returned from cache on further calls.
     *
     * @return boolean if lookup cached
     */
    abstract val isLookupCached: Boolean

    /**
     * Defines how to transform given object (property, class, etc)
     * into [org.kwebparser.support.By] class to locate elements.
     *
     * @return By object
     */
    abstract fun buildBy(): By
}
