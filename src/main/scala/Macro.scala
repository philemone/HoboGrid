import scala.quoted.*
import scala.deriving.Mirror
import scala.compiletime.constValueTuple

def fieldNamesWithTypes[T: Type](
    expr: Expr[T]
)(using Quotes): Expr[List[(String, String, String)]] = {
  import quotes.reflect.*

  val typeRep = TypeRepr.of[T]
  val fields = TypeTree.of[T].symbol.caseFields
  val fieldNamesExpr = Expr(fields.map(_.name))
  val fieldsType = Expr(
    fields
      .map(typeRep.memberType)
      .map(_.typeSymbol)
      .map(symbol => TypeIdent(symbol).tpe.typeSymbol.name)
  )

  val accessors = Expr.ofList(fields.map(Select(expr.asTerm, _).asExpr))
  '{
    $accessors
      .map(_.toString)
      .zip($fieldNamesExpr)
      .zip($fieldsType)
      .map(t3 => (t3._1._1, t3._1._2, t3._2))
  }
}

inline def fieldNamesAndTypes[A](cc: A): List[(String, String, String)] = ${
  fieldNamesWithTypes[A]('cc)
}
