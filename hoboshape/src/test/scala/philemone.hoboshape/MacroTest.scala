package philemone.hoboshape

import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest._
import philemone.hoboshape.reflection.fieldNamesAndTypes
import philemone.hoboshape.reflection.FieldRepresentation
import java.time.LocalDateTime
import matchers._

class MacroTest extends AnyFlatSpec with Matchers {

  "CC - one field" should "display data, type and name" in {
    case class TestCC(name: String)
    val r = fieldNamesAndTypes[TestCC](TestCC("Monica"))
    r.size shouldBe 1
    r.head shouldBe FieldRepresentation("name", "String", "Monica")
  }

  "CC - optional field" should "display value" in {
    case class TestCC(name: Option[String])
    val r = fieldNamesAndTypes[TestCC](TestCC(Some("Monica")))
    r.size shouldBe 1
    r.head shouldBe FieldRepresentation("name", "Option[String]", "Monica")
  }

  "CC - optional field" should "display empty string on empty" in {
    case class TestCC(name: Option[String])
    val r = fieldNamesAndTypes[TestCC](TestCC(None))
    r.size shouldBe 1
    r.head shouldBe FieldRepresentation("name", "Option[String]", "")
  }

  "CC - list" should "display list higher kinded type" in {
    case class TestCC(books: List[String])
    val r = fieldNamesAndTypes[TestCC](TestCC(List("FP 2022", "FP 2023")))
    r.size shouldBe 1
    r.head shouldBe FieldRepresentation("books", "List[String]", "FP 2022, FP 2023")
  }

  "CC - list inside list" should "display higher kinded types of level 2" in {
    case class TestCC(listInList: List[List[String]])
    val r = fieldNamesAndTypes[TestCC](TestCC(Nil))
    r.size shouldBe 1
    r.head shouldBe FieldRepresentation("listInList", "List[List[String]]", "")
  }

  "CC - two lists" should "display higher kinded types" in {
    case class TestCC(books: List[String], ids: List[Int])
    val r = fieldNamesAndTypes[TestCC](TestCC(List("FP 2022", "FP 2023"), List(100,101,102)))
    r.size shouldBe 2
    r should contain theSameElementsAs List(
      FieldRepresentation("books", "List[String]", "FP 2022, FP 2023"),
      FieldRepresentation("ids", "List[Int]", "100, 101, 102")
    )
  }

  "CC - mutiple basic types" should "display all together" in {
    case class User(name: String, age: Int, hot: Boolean, born: LocalDateTime, moneyInPocket: BigDecimal)
    val r = fieldNamesAndTypes[User](User("Eric", 29, true, LocalDateTime.of(2023, 2, 2, 12, 0), 20.3))
    r.size shouldBe 5
    r should contain theSameElementsAs List(
      FieldRepresentation("name", "String", "Eric"),
      FieldRepresentation("age", "Int", "29"),
      FieldRepresentation("hot", "Boolean", "true"),
      FieldRepresentation("born", "LocalDateTime", "2023-02-02T12:00"),
      FieldRepresentation("moneyInPocket", "BigDecimal", "20.3"),
    )
  }

  "CC - tuple" should "display tuple types" in {
    case class TestCC(tuple3: (String, Int, Boolean))
    val r = fieldNamesAndTypes[TestCC](TestCC(("Uno", 1, true)))
    r.size shouldBe 1
    r.head shouldBe FieldRepresentation("tuple3", "Tuple3[String, Int, Boolean]", "(Uno, 1, true)")
  }

  "CC - tuple with list" should "display nested tuple type" in {
    case class TestCC(tuple3: (String, List[Int], Boolean))
    val r = fieldNamesAndTypes[TestCC](TestCC(("Uno", List(1), true)))
    r.size shouldBe 1
    r.head shouldBe FieldRepresentation("tuple3", "Tuple3[String, List[Int], Boolean]", "(Uno, 1, true)")
  }

  "CC - tuple with nested tuple" should "display nested types" in {
    case class TestCC(tuple3: (String, (String, Integer), Integer))
    val r = fieldNamesAndTypes[TestCC](TestCC(("Uno", ("Due", 2), 1)))
    r.size shouldBe 1
    r.head shouldBe FieldRepresentation("tuple3", "Tuple3[String, Tuple2[String, Integer], Integer]", "(Uno, (Due, 2), 1)")
  }

}
