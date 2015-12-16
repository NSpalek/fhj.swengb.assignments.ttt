package fhj.swengb.assignments.ttt.nspalek

import javafx.application.Application
import javafx.stage.Stage

import scala.util.control.NonFatal

object TicTacToeApp {
  def main(args: Array[String]) {
    Application.launch(classOf[TicTacToeApp], args:_*)

    print(TicTacToe().asString())

  }
}

class TicTacToeApp extends javafx.application.Application {

  override def start(stage: Stage): Unit = {

  }

}