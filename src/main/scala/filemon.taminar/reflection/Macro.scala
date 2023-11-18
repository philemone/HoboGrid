package filemon.taminar.reflection

import scala.quoted.*
import scala.deriving.Mirror
import scala.compiletime.constValueTuple

case class FieldRepresentation(name: String, typeAsString: String, valueAsString: String)

private def fieldNamesWithTypes[T: Type](expr: Expr[T])(using Quotes): Expr[List[FieldRepresentation]] = {
  import quotes.reflect.*

  val typeRep = TypeRepr.of[T]
  val fields = TypeTree.of[T].symbol.caseFields

  def fieldTypeStringRepr(tr: TypeRepr): String = {
    if (tr.typeArgs.isEmpty) tr.typeSymbol.name
    else {
      s"${tr.typeSymbol.name}[" +
        s"${tr.typeArgs.map(fieldTypeStringRepr).mkString(", ")}" +
        "]"
    }
  }

  val fieldNamesExpr = Expr(fields.map(_.name))
  val fieldsType = Expr(
    fields
      .map(typeRep.memberType)
      .map(fieldTypeStringRepr)
  )

  def testStringRepresentation[T: Type](expr: Expr[T]) = {}

  testStringRepresentation(expr)
  val accessors: Expr[List[String]] = Expr.ofList(fields.map(a => {
    typeRep.memberType(a).asType match {
      case '[t] => {
        val s = Select(expr.asTerm, a).asExprOf[t]
        valuesToString(s)
      }
    }

  }))

  '{
    $accessors
      .zip($fieldNamesExpr)
      .zip($fieldsType)
      .map(t3 => FieldRepresentation(t3._1._2, t3._2, t3._1._1))
  }

}

inline def fieldNamesAndTypes[A](inline cc: A): List[FieldRepresentation] = ${
  fieldNamesWithTypes[A]('cc)
}
