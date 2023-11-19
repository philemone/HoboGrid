package philemone.hoboshape

import philemone.hoboshape.reflection.FieldRepresentation
import philemone.hoboshape.reflection.fieldNamesAndTypes

class HoboShape[T] {

  // TODO integrate it with scala.js
  // Method just to prove a point ? 
  inline def tableShape(data: List[T]): List[List[FieldRepresentation]] = data.map(fieldNamesAndTypes[T](_))

}
