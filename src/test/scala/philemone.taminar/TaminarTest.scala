package philemone.taminar

import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest._
import philemone.taminar.reflection.FieldRepresentation
import matchers._

class TaminarTest extends AnyFlatSpec with Matchers {

  "List[CC] - with primitives" should "display list of representations" in {
    case class TestCC(name: String, age: Int)
    val list = List(
      TestCC("Marry", 30),
      TestCC("Jerry", 35),
      TestCC("Tom", 42)
    )

    val taminar = new Taminar[TestCC]
    taminar.tableRepr(list) should contain theSameElementsAs List(
      FieldRepresentation("name", "String", "Marry") :: FieldRepresentation("age", "Int", "30") :: Nil,
      FieldRepresentation("name", "String", "Jerry") :: FieldRepresentation("age", "Int", "35") :: Nil,
      FieldRepresentation("name", "String", "Tom") :: FieldRepresentation("age", "Int", "42") :: Nil
    )
  }

}
