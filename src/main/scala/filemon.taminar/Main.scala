package filemon.taminar
// import com.raquo.laminar.api.L.{*, given}
// import org.scalajs.dom
// import org.scalajs.dom.HTMLTableElement
// import com.raquo.laminar.nodes.ReactiveHtmlElement

case class Dog(name: String, age: Int, isBig: Boolean)

class Taminar[T] {
 // inline def tableOf(data: List[T]): ReactiveHtmlElement[HTMLTableElement] = {

    // data.headOption
    //   .map(elem => {
    //     val headers = fieldNamesAndTypes[T](elem) // i know i know
    //     table(
    //       tr(headers.map(a => th(s"${a._2} [${a._3}]"))),
    //       data.map(dd => tr(fieldNamesAndTypes[T](dd).map(a => td(s"${a._1}"))))
    //     )
    //   })
    //   .getOrElse(table(""))

//  }
}

object Main extends App {

  val listOfDogs = List(
    Dog("Czarek", 9, true),
    Dog("Sisi", 15, false),
    Dog("Moli", 2, false)
  )

  case class User(
      name: String,
      height: Double,
      age: Int,
      kids: List[String],
      car: Option[String]
  )

  val listOfSthElse = List(
    User("Filip", 1.70, 28, Nil, Some("Kia")),
    User("Monica", 1.80, 18, List("Ola", "Ala", "Ela", "Yoko"), None)
  )

  // val appContainer: dom.Element = dom.document.querySelector("#root")
  // val appElement: Div = div(
  //   h1("Table"),
  //   new Taminar[User].tableOf(listOfSthElse)
  // )

  // val root: RootNode = render(appContainer, appElement)

}
