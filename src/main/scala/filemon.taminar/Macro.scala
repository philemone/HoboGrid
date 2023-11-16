package filemon.taminar

import scala.quoted.*
import scala.deriving.Mirror
import scala.compiletime.constValueTuple

case class FieldRepresentation(
    name: String,
    typeAsString: String,
    valueAsString: String
)

inline def unwrapsOptionValueToString(v: Option[_]): String = {
  v match
    case None        => ""
    case Some(value) => value.toString()
}

inline def formatListValues(v: List[_]): String = {
  v.mkString(", ")
}

def fieldNamesWithTypes[T: Type](
    expr: Expr[T]
)(using Quotes): Expr[List[FieldRepresentation]] = {
  import quotes.reflect.*

  val typeRep = TypeRepr.of[T]
  val fields = TypeTree.of[T].symbol.caseFields
  val fieldNamesExpr = Expr(fields.map(_.name))
  val fieldsType = Expr(
    fields
      .map(typeRep.memberType)
      .map(_.typeSymbol)
      .map(symbol => {
        val tpSymbol = TypeIdent(symbol)
      
          val listTypesAsString = TypeTree.of[T].symbol.caseFields.flatMap { o =>
            typeRep.memberType(o).typeArgs.map(_.typeSymbol.name)
          }
          if(listTypesAsString.nonEmpty) s"${tpSymbol.tpe.typeSymbol.name}[${listTypesAsString.mkString(", ")}]" //Just for tests and fun that it works somehow
          else tpSymbol.tpe.typeSymbol.name
        
      })
  )

  val accessors = Expr.ofList(fields.map(Select(expr.asTerm, _).asExpr))
  '{
    $accessors
      .map(acc =>
        acc match
          case opt: Option[_] => unwrapsOptionValueToString(opt)
          case lst: List[_]   => formatListValues(lst)
          case _              => acc.toString()
      )
      .zip($fieldNamesExpr)
      .zip($fieldsType)
      .map(t3 => FieldRepresentation(t3._1._2, t3._2, t3._1._1))
  }
}

inline def fieldNamesAndTypes[A](cc: A): List[FieldRepresentation] = ${
  fieldNamesWithTypes[A]('cc)
}
