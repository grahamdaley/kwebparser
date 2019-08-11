package org.kwebparser

import org.kwebparser.annotation.FindAll
import org.kwebparser.annotation.FindBy
import org.kwebparser.annotation.FindBys
import org.kwebparser.support.By
import java.util.*

abstract class AbstractFindByBuilder {
    abstract fun buildIt(annotation: Annotation, property: Any): By

    protected fun buildByFromFindBy(findBy: FindBy): By? {
        assertValidFindBy(findBy)

        if ("" != findBy.className) {
            return By.className(findBy.className)
        }

        if ("" != findBy.css) {
            return By.cssSelector(findBy.css)
        }

        if ("" != findBy.id) {
            return By.id(findBy.id)
        }

        if ("" != findBy.linkText) {
            return By.linkText(findBy.linkText)
        }

        if ("" != findBy.name) {
            return By.name(findBy.name)
        }

        if ("" != findBy.partialLinkText) {
            return By.partialLinkText(findBy.partialLinkText)
        }

        if ("" != findBy.tagName) {
            return By.tagName(findBy.tagName)
        }

        return null
    }

    protected fun assertValidFindBys(findBys: FindBys) {
        for (findBy in findBys.value) {
            assertValidFindBy(findBy)
        }
    }

    protected fun assertValidFindBy(findBy: FindBy) {
        val finders = HashSet<String>()
        if ("" != findBy.className) finders.add("class name:" + findBy.className)
        if ("" != findBy.css) finders.add("css:" + findBy.css)
        if ("" != findBy.id) finders.add("id: " + findBy.id)
        if ("" != findBy.linkText) finders.add("link text: " + findBy.linkText)
        if ("" != findBy.name) finders.add("name: " + findBy.name)
        if ("" != findBy.partialLinkText) finders.add("partial link text: " + findBy.partialLinkText)
        if ("" != findBy.tagName) finders.add("tag name: " + findBy.tagName)

        // A zero count is okay: it means to look by name or id.
        if (finders.size > 1) {
            throw IllegalArgumentException(
                String.format(
                    "You must specify at most one location strategy. Number found: %d (%s)",
                    finders.size, finders.toString()
                )
            )
        }
    }

    protected fun assertValidFindAll(findBys: FindAll) {
        for (findBy in findBys.value) {
            assertValidFindBy(findBy)
        }
    }

}