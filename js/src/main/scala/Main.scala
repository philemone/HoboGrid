import com.raquo.laminar.api.L.*
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom
import org.scalajs.dom.HTMLTableElement
import philemone.hobogrid.Hobogrid
import philemone.hobogrid.reflection.FieldRepresentation

object Main extends App {
  case class Dog(name: String, age: Int, isBig: Boolean)
  case class User(name: String, height: Double, age: Int, kids: List[String], car: Option[String])

  val listOfDogs = List(
    Dog("Czarek", 9, true),
    Dog("Sisi", 15, false),
    Dog("Moli", 2, false)
  )

  val listOfSthElse = List(
    User("Filip", 1.70, 28, Nil, Some("Kia")),
    User("Monica", 1.80, 18, List("Ola", "Ala", "Ela", "Yoko"), None)
  )

  def generateSimpleTable(data: List[List[FieldRepresentation]]): ReactiveHtmlElement[HTMLTableElement] = {

    val tableHeader   = data.headOption
      .map(elements => tr(elements.map(a => th(s"${a.fieldName} [${a.displayType}]"))))
      .fold(Nil)(_ :: Nil)
    val tableElements = data.map { elements =>
      tr(elements.map(elem => td(s"${elem.displayValue}")))
    }

    table(tableHeader ++ tableElements)

  }

  val appContainer: dom.Element = dom.document.querySelector("#root")
  val appElement: Div           = div(
    h1("Table"),
    generateSimpleTable(new Hobogrid[User].tableRepr(listOfSthElse))
  )

  val root: RootNode = render(appContainer, appElement)

}
