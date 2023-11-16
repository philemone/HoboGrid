package filemon.taminar

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest._
import matchers._
import filemon.taminar.fieldNamesAndTypes

class Test extends AnyFlatSpec with should.Matchers {

  "Case class with one field" should "display data, type and name" in {
    case class Person(name: String)
    val r = fieldNamesAndTypes[Person](Person("Monica"))
    r.size shouldBe 1
    r.head shouldBe FieldRepresentation("name", "String", "Monica")
  }

  "Case class with optional field" should "display only value" in {
    case class Person(name: Option[String])
    val r = fieldNamesAndTypes[Person](Person(Some("Monica")))
    r.size shouldBe 1
    r.head shouldBe FieldRepresentation("name", "Option[String]", "Monica")
  }

  "Case class with optional field" should "display nothing on empty" in {
    case class Person(name: Option[String])
    val r = fieldNamesAndTypes[Person](Person(None))
    r.size shouldBe 1
    r.head shouldBe FieldRepresentation("name", "Option[String]", "")
  }

  "Case class with list" should "display list inner type" in {
    case class Shelf(books: List[String])
    val r = fieldNamesAndTypes[Shelf](Shelf(List("FP 2022", "FP 2023")))
    r.size shouldBe 1
    r.head shouldBe FieldRepresentation("books", "List[String]", "FP 2022, FP 2023")
  }

}
