package filemon.taminar.reflection

import scala.quoted.*
import scala.deriving.Mirror
import scala.compiletime.constValueTuple
import filemon.taminar.reflection.PrintTree.printTreeImpl
import filemon.taminar.reflection.PrintTree.printTree

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

  val accessors = Expr.ofList(fields.map(Select(expr.asTerm, _).asExpr))

  '{
    $accessors
      .map(valuesToString)
      .zip($fieldNamesExpr)
      .zip($fieldsType)
      .map(t3 => FieldRepresentation(t3._1._2, t3._2, t3._1._1))
  }

}

inline def fieldNamesAndTypes[A](cc: A): List[FieldRepresentation] = ${
  fieldNamesWithTypes[A]('cc)
}
