package learn.gomoku;

import learn.gomoku.game.Gomoku;
import learn.gomoku.game.Result;
import learn.gomoku.game.Stone;
import learn.gomoku.players.HumanPlayer;
import learn.gomoku.players.Player;
import learn.gomoku.players.RandomPlayer;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class GameController {
    Scanner console = new Scanner(System.in);
    char[][] board = new char[15][15];
    Gomoku game;

    /**
     * Prints a welcome to user, calls run()
     * TODO printing output nicely
     * TODO two random players, print column numbers
     * TODO getInput requirements
     */
    public void welcome() {
        System.out.println("Welcome to Gomoku!");
        run();
    }

    /**
     * set up the game
     * play the game
     * ask to play again
     * uses: setup, play, playAgain
     */

    public void run() {
        setup();
        play();
    }

    /**
     * create player 1
     * create player 2
     * use 1 and 2 to instantiate the game
     * calls displayMessages()
     * uses: getPlayer
     */

    private void setup() {
        Player playerOne = getPlayer(1);
        Player playerTwo = getPlayer(2);

        game = new Gomoku(playerOne, playerTwo);
        displayMessages();
    }

    /**
     * Displays relevant messages to user. Initializes board.
     * Prints 'randomizing'
     */

    private void displayMessages() {
        System.out.println("~~~ GAME START ~~~");
        System.out.println(game.getCurrent().getName() + " goes first");
        System.out.println("Randomizing: ");

        for (int row = 0; row < board.length; row++) {
            Arrays.fill(board[row], '-');
        }
    }

    /**
     * prompt user for human or random
     * If human, prompt user for a name. Use the name to instantiate a HumanPlayer.
     * If random, instantiate a RandomPlayer.
     *
     * @param playerNumber 1 or 2 for playerOne or playerTwo
     * @return the Player
     * uses: readInt, readRequiredString
     */

    private Player getPlayer(int playerNumber) {
        Player player = null;
        System.out.println("Do you want player " + playerNumber + " to be [Human] or [Random]: ");
        String message = readRequiredString(console.next());

        switch (message.toLowerCase(Locale.ROOT)) {
            case "human":
                System.out.println("Enter a player name: ");
                String name = readRequiredString(console.next());
                player = new HumanPlayer(name);
                break;
            case "random":
                player = new RandomPlayer();
                break;
        }
        return player;
    }

    /**
     * display board
     * display current player
     * generate a stone from the current player
     * place the stone and collect the result
     * display result
     * repeat until game over
     * uses: printBoard, readInt (for row and column)
     */

    private void play() {
        while (!hasWon()) {
            System.out.println(game.getCurrent().getName() + "'s turn. ");
            System.out.println("Blacks turn? " + game.isBlacksTurn());
            printBoard();
            Stone stone = game.getCurrent().generateMove(game.getStones());
            Stone temp = null;

            if (stone == null) { // human player
                System.out.println("Enter a row to place: ");
                int row = readInt(console.next(), 1, board.length);
                row--;
                System.out.println("Enter a column to place: ");
                int col = readInt(console.next(), 1, board.length);
                col--;
                temp = new Stone(row, col, game.isBlacksTurn());
                Result result = game.place(temp);
                System.out.println(result.toString());
                System.out.println("Blacks turn? " + game.isBlacksTurn());
            }

            if (stone != null) { // random player
                Result result = game.place(stone);
                System.out.println("random placed at " + stone.getRow() + " , " + stone.getColumn());
                System.out.println(result.toString());
            }

        }

        if (playAgain()) {
            run();
        }
    }

    /**
     * Simplifies the play() method by incorporating checking game over functionality here.
     *
     * @return boolean true if game over false if not
     */
    private boolean hasWon() {
        if (game.getWinner() != null) {
            System.out.println(game.getWinner().getName() + " wins the game!");
            game.swap();
            printBoard();
            return true;
        }
        return false;
    }

    /**
     * use the game to fill in the char[][] board (game.getStones()) -- synchronize the game's state with the char[][]
     * print the board
     */

    private void printBoard() {
        List<Stone> stones = game.getStones();

        char x = 'X';
        char o = 'O';
        int lastI = 0;
        int lastJ = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                for (int k = 0; k < stones.size(); k++) {
                    if(game.isOver() && game.isBlacksTurn()){
                        if (i == stones.get(k).getRow() && j == stones.get(k).getColumn()) {
                            if (board[i][j] != x&& board[i][j]!=o) {
                                board[i][j] = x;
                            }
                        }
                    }
                    if(game.isOver() && !game.isBlacksTurn()){
                        if (i == stones.get(k).getRow() && j == stones.get(k).getColumn()) {
                            if (board[i][j] != o && board[i][j]!=x) {
                                board[i][j] = o;
                            }
                        }
                    }
                    if (!game.isBlacksTurn()) {
                        if (i == stones.get(k).getRow() && j == stones.get(k).getColumn()) {
                            if (board[i][j] != o) {
                                board[i][j] = x;
                            }
                        }
                    }
                        if (game.isBlacksTurn()) {
                            if (i == stones.get(k).getRow() && j == stones.get(k).getColumn()) {
                                if (board[i][j] != x) {
                                    board[i][j] = o;
                                }
                            }
                        }
                }
            }
            System.out.printf("%3s %s %n", i + 1, new String(board[i]));
        }
    }

    /**
     * prompt the user
     * collect their input
     * if whitespace or empty, repeat
     *
     * @param message the message input
     * @return String the input of user
     */

    private String readRequiredString(String message) {
        boolean done = false;
        do {
            if (!message.isBlank()) {
                done = true;
                return message;
            } else {
                System.out.println("Re-enter a valid string. ");
                String str = console.nextLine();
                message = str;
            }
        } while (!done);
        return message;
    }

    /**
     * prompt the user
     * collect their input
     * if the input is not a number, it's invalid, display message and repeat
     * convert the string input to an int
     * if the input is not within the min/max, it's invalid, display message and repeat
     * if valid, return the value
     * may use: readRequiredString
     *
     * @param message input from user
     * @param min     range minimum
     * @param max     range maximum
     * @return if valid return the int value of message
     */

    private int readInt(String message, int min, int max) {
        int temp = Integer.parseInt(readRequiredString(message));
        if ((temp <= max) && temp >= min) {
            return temp;
        } else {
            System.out.println("Invalid input:" + message);
            return Integer.parseInt(message);
        }
    }

    /**
     * asks the user if they want to play again
     * returns true if yes, false for any other input
     * may use: readRequiredString
     *
     * @return boolean to play again if true else finish.
     */

    private boolean playAgain() {
        System.out.println("Do you want to play again? Y: Yes N: No");
        if (console.next().equals(readRequiredString("Y"))) {
            return true;
        } else {
            return false;
        }
    }
}
