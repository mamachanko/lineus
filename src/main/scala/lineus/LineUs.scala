package lineus

trait LineUsOp

case object Recv extends LineUsOp

case class Send(msg: String) extends LineUsOp

class LineUs(in: () => String, out: String => Unit) {

  def execute(commands: String*): Unit = {
    compile(commands: _*) foreach {
      case Recv => println(in())
      case Send(msg) => out(msg)
    }
  }

  private def compile(commands: String*): List[LineUsOp] = {
    Recv +: (commands flatMap { command => List(Send(command), Recv) }) toList
  }
}
