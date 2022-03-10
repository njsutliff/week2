package learn.gomoku.game;

import learn.gomoku.players.Player;
import learn.gomoku.players.RandomPlayer;
import org.junit.jupiter.api.Test;

class GomokuRandomTest {

    @Test
    void shouldFinish() {
        // Random behavior can't really be tested.
        // This "test" verifies that a game will eventually end with two RandomPlayers.

        // Create two random players.
        RandomPlayer one = new RandomPlayer();
        RandomPlayer two = new RandomPlayer();

        // Create a new game.
        Gomoku game = new Gomoku(one, two);

        // Keep looping until the game is over.
        // The Gomoku class internally tracks the state of the game.
        // We can use the `isOver()` method to determine if the game is over or not.
        while (!game.isOver()) {
            // The Gomoku class internally tracks which player is the current player.
            // We can use the `getCurrent()` method to get a reference to the current player.
            // Notice that we're using the `Player` interface for the `currentPlayer` variable's data type.
            // Every player type implements the `Player` interface so we can refer to players
            // using the interface type without being concerned if the player is a `HumanPlayer` or `RandomPlayer`.
            Player currentPlayer = game.getCurrent();

            // Generate a random stone from the existing game moves.
            // A random player is not guaranteed to generate a successful move
            // so we need to keep generating a move until the result of placing the stone is successful.
            Result result;
            do {
                Stone stone = currentPlayer.generateMove(game.getStones());
                result = game.place(stone);
                System.out.println(result);
            } while (!result.isSuccess());
        }
    }

    @Test
    void makeNames() {
        // A simple test to exercise the logic within the `RandomPlayer` class
        // for generating random names.

        for (int i = 0; i < 100; i++) {
            RandomPlayer player = new RandomPlayer();
            System.out.println(player.getName());
        }
    }
}
