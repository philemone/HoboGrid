package filemon.taminar.reflection

import scala.quoted.*

def valuesToString[T: Type](expr: Expr[T], depth: Int = 0)(using Quotes): Expr[String] = {
  import quotes.reflect.*
  
  val typeArgs = TypeRepr.of[T].typeArgs
  val typeRep = TypeRepr.of[T]
  if (typeRep.isTupleN) {
    val outo = Expr.ofList(tupleValuesToString(expr.asExprOf[Tuple], depth))
    '{ $outo.mkString("(", ", ", ")") }
  }
  else if (typeArgs.nonEmpty) {
    val out = Expr.ofList(typeArgs.map(a => {
      a.asType match {
        case '[t] => {
          expr match {
            case '{ ($ls: Option[t]) } =>
              valuesToString('{ $ls.getOrElse("") }, depth + 1) // TODO getOrElse seems wrong
            case '{ ($ls: List[t]) } =>
              '{ $ls.map(elem => ${ valuesToString('elem, depth + 1) }).mkString(", ") }
            case t => t
          }
        }
      }
    }))
    '{ $out.mkString(", ") }
  } else '{ $expr.toString() }

}

def tupleValuesToString(expr: Expr[Tuple], depth: Int)(using Quotes): List[Expr[String]] = {
  expr match {
    case '{ $ls: head *: tail }  => valuesToString('{$ls.head}, depth) :: tupleValuesToString('{$ls.tail}, depth)
    case '{ $ls: EmptyTuple } => Nil
    case _ => Nil //TODO error
  }
}
