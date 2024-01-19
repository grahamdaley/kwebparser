package org.kwebparser

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import kotlin.reflect.KProperty

class ElementDelegate(private val document: Document) {
    operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): Element? {
        return org.kwebparser.DefaultElementLocator(document, property).findElement()
    }
}
