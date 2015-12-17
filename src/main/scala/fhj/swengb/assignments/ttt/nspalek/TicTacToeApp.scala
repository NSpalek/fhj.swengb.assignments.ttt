package fhj.swengb.assignments.ttt.nspalek

import java.net.URL
import javafx.event.EventHandler
import java.util.ResourceBundle
import javafx.application.Application
import javafx.fxml.{FXML, Initializable, FXMLLoader}
import javafx.scene.control.{Label, Button}
import javafx.scene.input.MouseEvent
import javafx.scene.layout.GridPane
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

import scala.util.control.NonFatal

object TicTacToeApp {
  def main(args: Array[String]) {
    Application.launch(classOf[TicTacToeApp], args:_*)
  }
}

class TicTacToeApp extends javafx.application.Application {

  val fxmlMain = "/fhj/swengb/assignments/ttt/TicTacToeApp.fxml"
  val cssMain = "/fhj/swengb/assignments/ttt/TicTacToe.css"

  val loader = new FXMLLoader(getClass.getResource(fxmlMain))

  def setSkin(stage: Stage, fxml: String, css: String): Boolean = {
    val scene = new Scene(loader.load[Parent]())
    stage.setScene(scene)
    stage.getScene.getStylesheets.clear()
    stage.getScene.getStylesheets.add(css)
  }

  override def start(stage: Stage): Unit =
    try {
      stage.setTitle("TicTacToe Game")
      loader.load[Parent]() // side effect
      val scene = new Scene(loader.getRoot[Parent])
      stage.setScene(scene)
      stage.getScene.getStylesheets.add(cssMain)
      stage.show()
    } catch {
      case NonFatal(e) => e.printStackTrace()
    }

}

class TicTacToeController extends Initializable {
  @FXML var top_left: Button = _
  @FXML var top_center: Button = _
  @FXML var top_right: Button = _
  @FXML var middle_left: Button = _
  @FXML var middle_center: Button = _
  @FXML var middle_right: Button = _
  @FXML var bottom_left: Button = _
  @FXML var bottom_center: Button = _
  @FXML var bottom_right: Button = _
  @FXML var close: Button = _
  @FXML var newGame: Button = _
  @FXML var gridPane: GridPane = _
  @FXML var label: Label =_

  var game = TicTacToe.apply()
  val moveMap: Map[Int, TMove] =  Map(0 -> TopLeft, 1 -> TopCenter, 2 -> TopRight, 3 -> MiddleLeft, 4 -> MiddleCenter, 5 -> MiddleRight,
                                     6 -> BottomLeft, 7 -> BottomCenter, 8 -> BottomRight)

  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    close.setOnMouseClicked(mouseClickCloseEvent)
    newGame.setOnMouseClicked(mouseClickResetEvent)

    top_left.setOnMouseClicked(mouseClickEvent)
    top_center.setOnMouseClicked(mouseClickEvent)
    top_right.setOnMouseClicked(mouseClickEvent)
    middle_left.setOnMouseClicked(mouseClickEvent)
    middle_center.setOnMouseClicked(mouseClickEvent)
    middle_right.setOnMouseClicked(mouseClickEvent)
    bottom_left.setOnMouseClicked(mouseClickEvent)
    bottom_center.setOnMouseClicked(mouseClickEvent)
    bottom_right.setOnMouseClicked(mouseClickEvent)

    label.setText("It's " + game.nextPlayer + "'s turn")
  }

  val mouseClickCloseEvent: EventHandler[_ >: MouseEvent] = new EventHandler[MouseEvent] {
    override def handle(event: MouseEvent): Unit = {
      event.getSource match {
        case onClick: Button => sys.exit()
        case _ => assert(false)
      }
    }
  }

  val mouseClickEvent: EventHandler[_ >: MouseEvent] = new EventHandler[MouseEvent] {
    override def handle(event: MouseEvent): Unit = {
      event.getSource match {
        case onClick: Button => {

          if(onClick.getText == "") {
            if(game.nextPlayer.equals(PlayerA))
              onClick.setText("X")
            else
              onClick.setText("O")

            game = game.turn(moveMap.get(onClick.getId.toInt).get , game.nextPlayer)
            label.setText("It's " + game.nextPlayer + "'s turn")

            if(game.gameOver){
              gridPane.setDisable(true)

              if(game.nextPlayer.equals(PlayerB))
                label.setText("PlayerA won!")
              else
                label.setText("PlayerB won!")
            }
            else if(game.moveHistory.size == 9)
              gridPane.setDisable(true)
          }
        }
        case _ => assert(false)
      }
    }
  }

  val mouseClickResetEvent: EventHandler[_ >: MouseEvent] = new EventHandler[MouseEvent] {
    override def handle(event: MouseEvent): Unit = {
      event.getSource match {
        case onClick: Button => reset
        case _ => assert(false)
      }
    }
  }

  def reset: Unit = {
    top_left.setText("")
    top_center.setText("")
    top_right.setText("")
    middle_left.setText("")
    middle_center.setText("")
    middle_right.setText("")
    bottom_left.setText("")
    bottom_center.setText("")
    bottom_right.setText("")
    gridPane.setDisable(false)
    game = TicTacToe()
    label.setText("It's " + game.nextPlayer + "'s turn")
  }

}