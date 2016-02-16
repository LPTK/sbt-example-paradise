@hello
object Test extends App {
  println(this.hello)
}

object Test2 {
  
  @hello
  object Inner
  
}
