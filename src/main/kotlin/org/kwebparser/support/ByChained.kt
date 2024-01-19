package org.kwebparser.support

import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.Serializable

/**
 * Mechanism used to locate elements within a document using a series of other lookups.  This class
 * will find all DOM elements that matches each of the locators in sequence, e.g.
 *
 * <pre>
 * driver.findElements(new ByChained(by1, by2))
</pre> *
 *
 * will find all elements that match <var>by2</var> and appear under an element that matches
 * <var>by1</var>.
 */
class ByChained(private val bys: List<By>) : By(), Serializable {
    override fun findElement(searchFrom: Element): Element {
        val elements = findElements(searchFrom)
        if (elements.isEmpty()) {
            throw NoSuchElementException("Cannot locate an searchFrom using " + toString())
        }
        return elements[0]
    }

    override fun findElements(searchFrom: Element): Elements {
        if (bys.isEmpty()) {
            return Elements()
        }

        var elems: Elements? = null
        for (by in bys) {
            val newElems = Elements()

            if (elems == null) {
                newElems.addAll(by.findElements(searchFrom))
            } else {
                for (elem in elems) {
                    newElems.addAll(by.findElements(elem))
                }
            }
            elems = newElems
        }

        return elems ?: Elements()
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder("By.chained(")
        stringBuilder.append("{")

        var first = true
        for (by in bys) {
            stringBuilder.append(if (first) "" else ",").append(by)
            first = false
        }
        stringBuilder.append("})")
        return stringBuilder.toString()
    }

    companion object {
        private const val serialVersionUID = 1563769051170172451L
    }
}
