package fhj.swengb.assignments.ttt.nspalek

import scala.collection.Set

/**
  * models the different moves the game allows
  *
  * each move is made by either player a or player b.
  */
sealed trait TMove {
  def idx: Int
}

case object TopLeft extends TMove {
  override def idx: Int = 0
}

case object TopCenter extends TMove {
  override def idx: Int = 1
}

case object TopRight extends TMove {
  override def idx: Int = 2
}

case object MiddleLeft extends TMove {
  override def idx: Int = 3
}

case object MiddleCenter extends TMove {
  override def idx: Int = 4
}

case object MiddleRight extends TMove {
  override def idx: Int = 5
}

case object BottomLeft extends TMove {
  override def idx: Int = 6
}

case object BottomCenter extends TMove {
  override def idx: Int = 7
}

case object BottomRight extends TMove {
  override def idx: Int = 8
}


/**
  * for a tic tac toe game, there are two players, player A and player B
  */
sealed trait Player

case object PlayerA extends Player

case object PlayerB extends Player

object TicTacToe {

  /**
    * creates an empty tic tac toe game
    * @return
    */

  def apply(): TicTacToe = TicTacToe(Map())


  /**
    * For a given tic tac toe game, this function applies all moves to the game.
    *
    * @param t
    * @param moves
    * @return
    */
  def play(t: TicTacToe, moves: Seq[TMove]): TicTacToe = {

    var player:Player = PlayerA

    for(move <- moves){
      t.turn(move, player)

      if(player.equals(PlayerA))
        player = PlayerB
      else
        player = PlayerA
    }

    return t
  }

  /**
    * creates all possible games.
    * @return
    */
  def mkGames(): Map[Seq[TMove], TicTacToe] = {

    val allGames : Map[Seq[TMove], TicTacToe] = Map()


    ???


    return allGames
  }

}

/**
  * Models the well known tic tac toe game.
  *
  * The map holds the information which player controls which field.
  *
  * The nextplayer parameter defines which player makes the next move.
  */
case class TicTacToe(moveHistory: Map[TMove, Player], nextPlayer: Player = PlayerA) {

  /**
    * outputs a representation of the tic tac toe like this:
    *
    * |---|---|---|
    * | x | o | x |
    * |---|---|---|
    * | x | o | x |
    * |---|---|---|
    * | x | o | x |
    * |---|---|---|
    *
    *
    * @return
    */
  def asString(): String = {
    var field: String = "|---|---|---|\n" +
                        "|   |   |   |\n" +
                        "|---|---|---|\n" +
                        "|   |   |   |\n" +
                        "|---|---|---|\n" +
                        "|   |   |   |\n" +
                        "|---|---|---|\n"

    val pos = Map(0->16, 1->20, 2->24, 3->44, 4->48, 5->52, 6->72, 7->76, 8->80)

    for((m, p) <- moveHistory) {
      if (p == PlayerA)
        field = field.updated(pos(m.idx), "X").mkString
      else if (p == PlayerB)
        field = field.updated(pos(m.idx), "O").mkString
    }
    return field
  }

  /**
    * is true if the game is over.
    *
    * The game is over if either of a player wins or there is a draw.
    */
  val gameOver : Boolean = {
    if(winner == None)
      false
    else
      true
  }

  /**
    * the moves which are still to be played on this tic tac toe.
    */
  val remainingMoves: Set[TMove] = {
    val allMoves: Set[TMove] = Set(TopLeft, TopCenter, TopRight, MiddleLeft, MiddleCenter, MiddleRight, BottomLeft, BottomCenter, BottomRight)
    for(move <- allMoves if !moveHistory.contains(move)) yield move
  }

  /**
    * given a tic tac toe game, this function returns all
    * games which can be derived by making the next turn. that means one of the
    * possible turns is taken and added to the set.
    */
  lazy val nextGames: Set[TicTacToe] = ???

  /**
    * Either there is no winner, or PlayerA or PlayerB won the game.
    *
    * The set of moves contains all moves which contributed to the result.
    */
  def winner: Option[(Player, Set[TMove])] = {
    val winnerLines = List((0,1,2), (3,4,5), (6,7,8), (0,3,6), (1,4,7), (2,5,8), (0,4,8), (2,4,6))
    var movesA: List[Int] = List()
    var movesB: List[Int] = List()

    for(move <- moveHistory){
      if(move._2.equals(PlayerA))
        movesA :+ (move._1.idx)
      else
        movesB :+ (move._1.idx)
    }

    for(wl <- winnerLines){
      if(movesA.contains(wl._1) && movesA.contains(wl._2) && movesA.contains(wl._3))
        Some(PlayerA, moveHistory.keySet)
      else if(movesB.contains(wl._1) && movesB.contains(wl._2) && movesB.contains(wl._3))
        Some(PlayerB, moveHistory.keySet)
    }
    None
  }

  /**
    * returns a copy of the current game, but with the move applied to the tic tac toe game.
    *
    * @param move to be played
    * @param player the player
    * @return
    */
  def turn(move: TMove, player: Player): TicTacToe = {
    if(!moveHistory.contains(move)){
      if(player.equals(PlayerA))
        TicTacToe(moveHistory + (move -> player), PlayerB)
      else
        TicTacToe(moveHistory + (move -> player))
    }
    else
      TicTacToe(moveHistory)
  }
}


