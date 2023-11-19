import com.raquo.laminar.api.L.*
import org.scalajs.dom
import com.raquo.laminar.nodes.ReactiveHtmlElement
import philemone.hobogrid.HoboGrid

object Main extends App {
  case class Dog(name: String, age: Int, isBig: Boolean)
  case class User(name: String, height: Double, age: Int, kids: List[String], car: Option[String])
  case class Nested(listOfList: List[List[Integer]], tuple3: (String, BigDecimal, List[Boolean]))

  val listOfDogs  = List(Dog("Czarek", 9, true), Dog("Sisi", 15, false), Dog("Moli", 2, false))
  val listOfUsers = List(
    User("Filip", 1.70, 28, Nil, Some("Kia")),
    User("Monica", 1.80, 18, List("Ola", "Ala", "Ela", "Yoko"), None)
  )
  val listOfNested = List(
    Nested(List(List(1,2,3,4), List(4,3,2,1), List(9,8,7)), ("birthday", 21.4, List(true,true,false))),
    Nested(List(List(1,2,3,4), List(4,3,2,1), List(9,8,7)), ("pick up cake", 38.002, List(false,true,false))),
    Nested(List(List(1,2,3,4), List(4,3,2,1), List(9,8,7)), ("go shopping", 0.12, List()))
  )

  val appContainer: dom.Element = dom.document.querySelector("#root")
  val appElement: Div           = div(
    h1("Users"),
    new HoboGrid[User].generateSimpleTable(listOfUsers),
    h1("Dogs"),
    new HoboGrid[Dog].generateSimpleTable(listOfDogs),
    h1("Nested"),
    new HoboGrid[Nested].generateSimpleTable(listOfNested)
  )

  val root: RootNode = render(appContainer, appElement)

}
