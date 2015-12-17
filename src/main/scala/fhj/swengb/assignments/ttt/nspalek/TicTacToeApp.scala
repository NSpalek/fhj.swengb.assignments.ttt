package fhj.swengb.assignments.ttt.nspalek

import java.net.URL
import javafx.event.EventHandler
import java.util.ResourceBundle
import javafx.application.Application
import javafx.fxml.{FXML, Initializable, FXMLLoader}
import javafx.scene.control.Button
import javafx.scene.input.MouseEvent
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
  val loader = new FXMLLoader(getClass.getResource(fxmlMain))

  override def start(stage: Stage): Unit =
    try {
      stage.setTitle("TicTacToe Game")
      loader.load[Parent]() // side effect
      val scene = new Scene(loader.getRoot[Parent])
      stage.setScene(scene)
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
    game = TicTacToe.apply()
  }

}