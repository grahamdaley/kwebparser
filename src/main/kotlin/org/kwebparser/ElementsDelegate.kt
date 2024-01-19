package org.kwebparser

import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import kotlin.reflect.KProperty

class ElementsDelegate(private val document: Document) {
    operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): Elements {
        return org.kwebparser.DefaultElementLocator(document, property).findElements()
    }
}
