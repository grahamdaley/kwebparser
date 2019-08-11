package org.kwebparser.support

import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.Serializable
import java.util.*

class ByIdOrName(private val idOrName: String) : By(), Serializable {
    private val idFinder: By = By.id(idOrName)
    private val nameFinder: By = By.name(idOrName)

    override fun findElement(searchFrom: Element): Element {
        return try {
            this.idFinder.findElement(searchFrom)
        } catch (nse: NoSuchElementException) {
            this.nameFinder.findElement(searchFrom)
        }
    }

    override fun findElements(searchFrom: Element): Elements {
        val elements = ArrayList<Element>()
        elements.addAll(this.idFinder.findElements(searchFrom))
        elements.addAll(this.nameFinder.findElements(searchFrom))
        return Elements(elements)
    }

    override fun toString(): String {
        return "by id or name \"" + this.idOrName + '"'.toString()
    }

    companion object {
        private const val serialVersionUID = 3986638402799576701L
    }
}
