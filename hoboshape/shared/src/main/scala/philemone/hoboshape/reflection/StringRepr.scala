package philemone.hoboshape.reflection

import scala.quoted.*

private object TupleStrRepr {
  val (start, end)                 = ("(", ")")
  val separator                    = ", "
  def mkString(list: List[String]) = list.mkString(start, separator, end)
}

private object ListStrRepr {
  val (start, end)                 = ("[", "]")
  val separator                    = ", "
  def mkString(list: List[String]) = list.mkString(start, separator, end)
}

private[hoboshape] def valuesToString[T: Type](expr: Expr[T], depth: Int = 0)(using Quotes): Expr[String] = {
  import quotes.reflect.*

  val typeArgs = TypeRepr.of[T].typeArgs
  val typeRep  = TypeRepr.of[T]

  def goThroughTypeArgs: Expr[List[String]] = {
    Expr.ofList(typeArgs.map(a => {
      a.asType match {
        case '[t] => {
          expr match {
            case '{ ($ls: Option[t]) } =>
              valuesToString('{ $ls.getOrElse("") }, depth + 1) // TODO getOrElse seems wrong
            case '{ ($ls: List[t]) }   =>
              '{ ListStrRepr.mkString($ls.map(elem => ${ valuesToString('elem, depth + 1) })) }
            case t                     => '{ $t.toString() }
          }
        }
      }
    }))
  }

  def tupleTypeAsStr: Expr[List[String]] = {
    Expr.ofList(tupleValuesToString(expr.asExprOf[Tuple], depth))
  }

  typeRep match {
    case tr if tr.isTupleN       => '{ TupleStrRepr.mkString($tupleTypeAsStr) }
    case tr if typeArgs.nonEmpty => '{ $goThroughTypeArgs.mkString(", ") }
    case _                       => '{ $expr.toString() }
  }
}

def tupleValuesToString(expr: Expr[Tuple], depth: Int)(using Quotes): List[Expr[String]] = {
  expr match {
    case '{ $ls: head *: tail } => valuesToString('{ $ls.head }, depth) :: tupleValuesToString('{ $ls.tail }, depth)
    case '{ $ls: EmptyTuple }   => Nil
    case _                      => Nil // TODO error
  }
}
