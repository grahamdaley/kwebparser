package org.kwebparser.annotation

import org.kwebparser.support.By
import org.kwebparser.support.ByChained

/**
 * Used to mark a property on a Page Object to indicate that lookup should use a series of @FindBy tags
 * in a chain as described in [org.kwebparser.support.ByChained]
 *
 * It can be used on a types as well, but will not be processed by default.
 *
 * Eg:
 *
 * <pre class="code">
 * &#64;FindBys({&#64;FindBy(id = "foo"),
 * &#64;FindBy(className = "bar")})</pre> *
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.CLASS, AnnotationTarget.FILE)
@PageFactoryFinder(FindBys.FindByBuilder::class)
annotation class FindBys(vararg val value: FindBy) {
    class FindByBuilder : org.kwebparser.AbstractFindByBuilder() {
        override fun buildIt(
            annotation: Annotation,
            property: Any,
        ): By {
            val findBys = annotation as FindBys
            assertValidFindBys(findBys)
            return ByChained(findBys.value.mapNotNull { buildByFromFindBy(it) })
        }
    }
}
