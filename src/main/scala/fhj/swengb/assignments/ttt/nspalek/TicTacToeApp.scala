package fhj.swengb.assignments.ttt.nspalek

import javafx.application.Application
import javafx.stage.Stage

import scala.util.control.NonFatal

object TicTacToeApp {
  def main(args: Array[String]) {
    Application.launch(classOf[TicTacToeApp], args:_*)
  }
}

class TicTacToeApp extends javafx.application.Application {

  override def start(stage: Stage): Unit = {
    try {
      stage.show()
    }
    catch {
      case NonFatal(e) => e.printStackTrace()
    }

  }

}