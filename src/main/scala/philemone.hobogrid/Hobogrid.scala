package philemone.hobogrid

import philemone.hobogrid.reflection.FieldRepresentation
import philemone.hobogrid.reflection.fieldNamesAndTypes

case class Dog(name: String, age: Int, isBig: Boolean)

class Hobogrid[T] {

  // TODO integrate it with scala.js
  // Method just to prove a point ? 
  inline def tableRepr(data: List[T]): List[List[FieldRepresentation]] = data.map(fieldNamesAndTypes[T](_))

}
