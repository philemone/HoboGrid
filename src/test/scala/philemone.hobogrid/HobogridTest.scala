package philemone.hobogrid

import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest._
import philemone.hobogrid.reflection.FieldRepresentation
import matchers._

class HobogridTest extends AnyFlatSpec with Matchers {

  "List[CC] - with primitives" should "display list of representations" in {
    case class TestCC(name: String, age: Int)
    val list = List(
      TestCC("Marry", 30),
      TestCC("Jerry", 35),
      TestCC("Tom", 42)
    )

    val hobogrid = new Hobogrid[TestCC]
    hobogrid.tableRepr(list) should contain theSameElementsAs List(
      FieldRepresentation("name", "String", "Marry") :: FieldRepresentation("age", "Int", "30") :: Nil,
      FieldRepresentation("name", "String", "Jerry") :: FieldRepresentation("age", "Int", "35") :: Nil,
      FieldRepresentation("name", "String", "Tom") :: FieldRepresentation("age", "Int", "42") :: Nil
    )
  }

}
