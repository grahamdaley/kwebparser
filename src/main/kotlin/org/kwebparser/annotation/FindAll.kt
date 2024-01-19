package org.kwebparser.annotation

import org.kwebparser.support.By
import org.kwebparser.support.ByAll

/**
 * Used to mark a property on a Page Object to indicate that lookup should use a series of @FindBy tags
 * It will then search for all elements that match any of the FindBy criteria. Note that elements
 * are not guaranteed to be in document order.
 *
 * It can be used on a types as well, but will not be processed by default.
 *
 * Eg:
 *
 * <pre class="code">
 * &#64;FindBy(className = "bar")})</pre> *
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.CLASS, AnnotationTarget.FILE)
@PageFactoryFinder(FindAll.FindByBuilder::class)
annotation class FindAll(vararg val value: FindBy) {
    class FindByBuilder : org.kwebparser.AbstractFindByBuilder() {
        override fun buildIt(
            annotation: Annotation,
            property: Any,
        ): By {
            val findBys = annotation as FindAll
            assertValidFindAll(findBys)
            return ByAll(findBys.value.mapNotNull { buildByFromFindBy(it) })
        }
    }
}
