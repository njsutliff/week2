package learn.gomoku.players;

import learn.gomoku.game.Stone;

import java.util.List;

public interface Player {

    String getName();

    Stone generateMove(List<Stone> previousMoves);
}
