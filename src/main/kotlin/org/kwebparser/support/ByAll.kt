package org.kwebparser.support

import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.Serializable

/**
 * Mechanism used to locate elements within a document using a series of  lookups. This class will
 * find all DOM elements that matches any of the locators in sequence, e.g.
 *
 * <pre>
 * driver.findElements(new ByAll(by1, by2))
</pre> *
 *
 * will find all elements that match <var>by1</var> and then all elements that match <var>by2</var>.
 * This means that the list of elements returned may not be in document order.
 */
class ByAll(private val bys: List<By>) : By(), Serializable {
    override fun findElement(searchFrom: Element): Element {
        for (by in bys) {
            val elements = by.findElements(searchFrom)
            if (!elements.isEmpty()) {
                return elements[0]
            }
        }
        throw NoSuchElementException("Cannot locate an searchFrom using " + toString())
    }

    override fun findElements(searchFrom: Element): Elements {
        val elems = ArrayList<Element>()
        for (by in bys) {
            elems.addAll(by.findElements(searchFrom))
        }

        return Elements(elems)
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder("By.all(")
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
        private const val serialVersionUID = 4573668832699497306L
    }
}
