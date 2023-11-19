package philemone.hoboshape

import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest._
import philemone.hoboshape.reflection.FieldRepresentation
import matchers._

class HoboShapeTest extends AnyFlatSpec with Matchers {

  "List[CC] - with primitives" should "display list of representations" in {
    case class TestCC(name: String, age: Int)
    val list = List(
      TestCC("Marry", 30),
      TestCC("Jerry", 35),
      TestCC("Tom", 42)
    )

    val hoboShape = new HoboShape[TestCC]
    hoboShape.tableShape(list) should contain theSameElementsAs List(
      FieldRepresentation("name", "String", "Marry") :: FieldRepresentation("age", "Int", "30") :: Nil,
      FieldRepresentation("name", "String", "Jerry") :: FieldRepresentation("age", "Int", "35") :: Nil,
      FieldRepresentation("name", "String", "Tom") :: FieldRepresentation("age", "Int", "42") :: Nil
    )
  }

}
