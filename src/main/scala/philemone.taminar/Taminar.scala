package philemone.taminar

import philemone.taminar.reflection.FieldRepresentation
import philemone.taminar.reflection.fieldNamesAndTypes

case class Dog(name: String, age: Int, isBig: Boolean)

class Taminar[T] {

  // TODO integrate it with scala.js
  // Method just to prove a point ? 
  inline def tableRepr(data: List[T]): List[List[FieldRepresentation]] = data.map(fieldNamesAndTypes[T](_))

  // inline def tableOf(data: List[T]): ReactiveHtmlElement[HTMLTableElement] = {

  //   data.headOption
  //     .map(elem => {
  //       val headers = fieldNamesAndTypes[T](elem) // i know i know
  //       table(
  //         tr(headers.map(a => th(s"${a._2} [${a._3}]"))),
  //         data.map(dd => tr(fieldNamesAndTypes[T](dd).map(a => td(s"${a._1}"))))
  //       )
  //     })
  //     .getOrElse(table(""))

  // }
}
