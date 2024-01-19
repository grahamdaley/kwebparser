package org.kwebparser

import org.jsoup.nodes.Element
import org.jsoup.select.Elements

interface ElementLocator {
    fun findElement(): Element?

    fun findElements(): Elements
}
