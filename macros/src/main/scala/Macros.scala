import scala.reflect.macros.Context
import scala.language.experimental.macros
import scala.annotation.StaticAnnotation

object helloMacro {
  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    import Flag._
    val result = {
      annottees.map(_.tree).toList match {
        case q"object $name extends ..$parents { ..$body }" :: Nil =>
          
          //println(c.typecheck(q"class A")) // FIXME causes "no progress in completing object ..."
          
          // workaround:
          if (c.enclosingClass.isEmpty) println(c.typecheck(q"class A"))
          else // The following does NOT work on top-level defs:
           println(c.typecheck(q"() => { class A }") match { case q"() => $cls" => cls })
          
          q"""
            object $name extends ..$parents {
              def hello: ${typeOf[String]} = "hello"
              ..$body
            }
          """
      }
    }
    c.Expr[Any](result)
  }
}

class hello extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro helloMacro.impl
}
