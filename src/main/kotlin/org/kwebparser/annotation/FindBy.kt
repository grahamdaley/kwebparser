package org.kwebparser.annotation

import org.kwebparser.support.By

/**
 *
 * Used to mark a property on a Page Object to indicate an alternative mechanism for locating the
 * element or a list of elements.
 *
 * You need to specify one of the location strategies (eg: "id") with an appropriate
 * value to use.
 *
 * For example:
 *
 * <pre class="code">
 * &#64;FindBy(id = "foobar") Element foobar;</pre> *
 *
 * and this points to a list of elements:
 *
 * <pre class="code">
 * &#64;FindBy(tagName = "a") Elements links;</pre> *
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.CLASS, AnnotationTarget.FILE)
@PageFactoryFinder(FindBy.FindByBuilder::class)
@Suppress("LongParameterList")
annotation class FindBy(
    val id: String = "",
    val name: String = "",
    val className: String = "",
    val css: String = "",
    val tagName: String = "",
    val linkText: String = "",
    val partialLinkText: String = "",
) {
    class FindByBuilder : org.kwebparser.AbstractFindByBuilder() {
        override fun buildIt(
            annotation: Annotation,
            property: Any,
        ): By {
            val findBy = annotation as FindBy
            assertValidFindBy(findBy)
            return buildByFromFindBy(findBy)!!
        }
    }
}
