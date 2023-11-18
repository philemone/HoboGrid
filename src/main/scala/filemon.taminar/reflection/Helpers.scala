package filemon.taminar.reflection
import scala.quoted.*

//Takes as it was from: https://softwaremill.com/scala-3-macros-tips-and-tricks/#shape-of-a-macro
object PrintTree {
  inline def printTree[T](inline x: T): Unit = ${printTreeImpl('x)}
  def printTreeImpl[T: Type](x: Expr[T])(using qctx: Quotes): Expr[Unit] =
    import qctx.reflect.*
    println(x.asTerm.show(using Printer.TreeStructure))
    '{()}
}