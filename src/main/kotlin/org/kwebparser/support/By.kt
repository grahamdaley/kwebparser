package org.kwebparser.support

import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.Serializable

/**
 * Mechanism used to locate elements within a document
 */
abstract class By {

    /**
     * Find a single searchFrom. Override this method if necessary.
     *
     * @param searchFrom An searchFrom in the document from which to find the required searchFrom.
     * @return The Element that matches the selector.
     */
    open fun findElement(searchFrom: Element): Element {
        val allElements = findElements(searchFrom)
        if (allElements.isEmpty()) {
            throw NoSuchElementException("Cannot locate an searchFrom using " + toString())
        }
        return allElements[0]
    }

    /**
     * Find many elements.
     *
     * @param searchFrom A searchFrom to use to find the elements.
     * @return A list of Elements matching the selector.
     */
    abstract fun findElements(searchFrom: Element): Elements

    override fun equals(other: Any?): Boolean {
        if (other !is By) {
            return false
        }

        val that = other as By?

        return this.toString() == that!!.toString()
    }

    override fun hashCode(): Int {
        return toString().hashCode()
    }

    override fun toString(): String {
        // A stub to prevent endless recursion in hashCode()
        return "[unknown locator]"
    }

    class ById(private val id: String?) : By(), Serializable {

        init {
            if (id == null) {
                throw IllegalArgumentException("Cannot find elements when the id is null.")
            }
        }

        override fun findElements(searchFrom: Element): Elements {
            return Elements(searchFrom.getElementById(id))
        }

        override fun findElement(searchFrom: Element): Element {
            return searchFrom.getElementById(id)
        }

        override fun toString(): String {
            return "By.id: $id"
        }

        companion object {
            private const val serialVersionUID = 5341968046120372169L
        }
    }

    class ByLinkText(private val linkText: String?) : By(), Serializable {

        init {
            if (linkText == null) {
                throw IllegalArgumentException("Cannot find elements when the link text is null.")
            }
        }

        override fun findElements(searchFrom: Element): Elements {
            return Elements(searchFrom.getElementsByTag("a").filter { it.text() == linkText })
        }

        override fun toString(): String {
            return "By.linkText: $linkText"
        }

        companion object {
            private const val serialVersionUID = 1967414585359739708L
        }
    }

    class ByPartialLinkText(private val partialLinkText: String?) : By(), Serializable {

        init {
            if (partialLinkText == null) {
                throw IllegalArgumentException("Cannot find elements when the link text is null.")
            }
        }

        override fun findElements(searchFrom: Element): Elements {
            return Elements(searchFrom.getElementsByTag("a").filter { it.text().contains(partialLinkText!!) })
        }

        override fun toString(): String {
            return "By.partialLinkText: $partialLinkText"
        }

        companion object {
            private const val serialVersionUID = 1163955344140679054L
        }
    }

    class ByName(private val name: String?) : By(), Serializable {

        init {
            if (name == null) {
                throw IllegalArgumentException("Cannot find elements when name text is null.")
            }
        }

        override fun findElements(searchFrom: Element): Elements {
            return searchFrom.getElementsByAttributeValue("name", name)
        }

        override fun toString(): String {
            return "By.name: $name"
        }

        companion object {
            private const val serialVersionUID = 376317282960469555L
        }
    }

    class ByTagName(private val tagName: String?) : By(), Serializable {

        init {
            if (tagName == null) {
                throw IllegalArgumentException("Cannot find elements when the tag name is null.")
            }
        }

        override fun findElements(searchFrom: Element): Elements {
            return searchFrom.getElementsByTag(tagName)
        }

        override fun toString(): String {
            return "By.tagName: $tagName"
        }

        companion object {
            private const val serialVersionUID = 4699295846984948351L
        }
    }

    class ByClassName(private val className: String?) : By(), Serializable {

        init {
            if (className == null) {
                throw IllegalArgumentException(
                    "Cannot find elements when the class name expression is null."
                )
            }
        }

        override fun findElements(searchFrom: Element): Elements {
            return searchFrom.getElementsByClass(className)
        }

        override fun toString(): String {
            return "By.className: $className"
        }

        companion object {
            private const val serialVersionUID = -8737882849130394673L
        }
    }

    class ByCssSelector(private val cssSelector: String?) : By(), Serializable {

        init {
            if (cssSelector == null) {
                throw IllegalArgumentException("Cannot find elements when the selector is null")
            }
        }

        override fun findElements(searchFrom: Element): Elements {
            return searchFrom.select(cssSelector)
        }

        override fun toString(): String {
            return "By.cssSelector: $cssSelector"
        }

        companion object {
            private const val serialVersionUID = -3910258723099459239L
        }
    }

    companion object {
        /**
         * @param id The value of the "id" attribute to search for.
         * @return A By which locates elements by the value of the "id" attribute.
         */
        fun id(id: String): By {
            return ById(id)
        }

        /**
         * @param linkText The exact text to match against.
         * @return A By which locates A elements by the exact text it displays.
         */
        fun linkText(linkText: String): By {
            return ByLinkText(linkText)
        }

        /**
         * @param partialLinkText The partial text to match against
         * @return a By which locates elements that contain the given link text.
         */
        fun partialLinkText(partialLinkText: String): By {
            return ByPartialLinkText(partialLinkText)
        }

        /**
         * @param name The value of the "name" attribute to search for.
         * @return A By which locates elements by the value of the "name" attribute.
         */
        fun name(name: String): By {
            return ByName(name)
        }

        /**
         * @param tagName The element's tag name.
         * @return A By which locates elements by their tag name.
         */
        fun tagName(tagName: String): By {
            return ByTagName(tagName)
        }

        /**
         * Find elements based on the value of the "class" attribute. If an element has multiple classes, then
         * this will match against each of them. For example, if the value is "one two onone", then the
         * class names "one" and "two" will match.
         *
         * @param className The value of the "class" attribute to search for.
         * @return A By which locates elements by the value of the "class" attribute.
         */
        fun className(className: String): By {
            return ByClassName(className)
        }

        /**
         * Find elements via the driver's underlying W3C Selector engine. If the browser does not
         * implement the Selector API, a best effort is made to emulate the API. In this case, we strive
         * for at least CSS2 support, but offer no guarantees.
         *
         * @param cssSelector CSS expression.
         * @return A By which locates elements by CSS.
         */
        fun cssSelector(cssSelector: String): By {
            return ByCssSelector(cssSelector)
        }
    }
}
