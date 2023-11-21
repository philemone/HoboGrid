package philemone.hobogrid

import com.raquo.laminar.api.L.*
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom
import org.scalajs.dom.HTMLTableElement
import philemone.hoboshape.HoboShape

class HoboGrid[T] {
  private val hoboShape = new HoboShape[T]
  inline def generateSimpleTable(caseClassList: List[T]): ReactiveHtmlElement[HTMLTableElement] = {
    val shape = hoboShape.tableShape(caseClassList)
    val tableHeader   = shape.headOption
      .map(elements => tr(elements.map(a => th(s"${a.fieldName} [${a.displayType}]"))))
      .fold(Nil)(_ :: Nil)
    val tableElements = shape.map { elements =>
      tr(elements.map(elem => td(s"${elem.displayValue}")))
    }
    table(tableHeader ++ tableElements)
  }
}
