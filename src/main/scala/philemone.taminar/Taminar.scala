package philemone.taminar

import philemone.taminar.reflection.FieldRepresentation
import philemone.taminar.reflection.fieldNamesAndTypes

case class Dog(name: String, age: Int, isBig: Boolean)

class Taminar[T] {

  // TODO integrate it with scala.js
  // Method just to prove a point ? 
  inline def tableRepr(data: List[T]): List[List[FieldRepresentation]] = data.map(fieldNamesAndTypes[T](_))

}
