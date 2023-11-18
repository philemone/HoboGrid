package filemon.taminar.reflection

import scala.quoted.*

def valuesToString(any: Any): String = {
  any match
    case opt: Option[_] => unwrapsOptionValueToString(opt)
    case lst: List[_]   => formatListValues(lst)
    case _              => any.toString()
}

private[reflection] def unwrapsOptionValueToString(v: Option[_]): String = {
  v match
    case None        => ""
    case Some(value) => value.toString()
}

private[reflection] def formatListValues(v: List[_]): String = {
  v.mkString(", ")
}
