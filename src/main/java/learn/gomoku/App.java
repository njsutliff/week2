package learn.gomoku;

import learn.gomoku.game.Gomoku;
import learn.gomoku.players.Player;

/** Nik Sutliff
 * Creates a new GameController, uses its public welcome message to launch game.
 */
public class App {
    public static void main(String[] args) {
        GameController g = new GameController();
        g.welcome();
    }
}
