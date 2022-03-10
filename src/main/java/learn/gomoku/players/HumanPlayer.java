package learn.gomoku.players;

import learn.gomoku.game.Stone;

import java.util.List;

public class HumanPlayer implements Player {

    private final String name;

    public HumanPlayer(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Stone generateMove(List<Stone> previousMoves) {
        return null;
    }
}
