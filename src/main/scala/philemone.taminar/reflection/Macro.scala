package philemone.taminar.reflection

import scala.quoted.*

case class FieldRepresentation(fieldName: String, displayType: String, displayValue: String)

private def fieldNamesWithTypes[T: Type](expr: Expr[T])(using Quotes): Expr[List[FieldRepresentation]] = {
  import quotes.reflect.*

  val typeRep    = TypeRepr.of[T]
  val caseFields = TypeTree.of[T].symbol.caseFields

  def fieldTypeStringRepr(tr: TypeRepr): String = {
    if (tr.typeArgs.isEmpty) tr.typeSymbol.name
    else {
      s"${tr.typeSymbol.name}[" +
        s"${tr.typeArgs.map(fieldTypeStringRepr).mkString(", ")}" +
        "]"
    }
  }

  val fieldNamesExpr = Expr(caseFields.map(_.name))
  val fieldsType     = Expr(
    caseFields
      .map(typeRep.memberType)
      .map(fieldTypeStringRepr)
  )

  val fieldValueAsString: Expr[List[String]] = Expr.ofList(
    caseFields.map(a => {
      typeRep.memberType(a).asType match {
        case '[t] => valuesToString(Select(expr.asTerm, a).asExprOf[t])
      }
    })
  )

  '{
    $fieldValueAsString
      .zip($fieldNamesExpr)
      .zip($fieldsType)
      .map { case (((displayValue, fieldName), displayType)) =>
        FieldRepresentation(fieldName, displayType, displayValue)
      }
  }

}

inline def fieldNamesAndTypes[A](inline cc: A): List[FieldRepresentation] = ${
  fieldNamesWithTypes[A]('cc)
}
