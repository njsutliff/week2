package learn.gomoku.game;

import learn.gomoku.players.HumanPlayer;
import learn.gomoku.players.Player;
import learn.gomoku.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class GomokuTest {
    private final HumanPlayer one = new HumanPlayer("Dori");
    private final HumanPlayer two = new HumanPlayer("Nemo");
    private Gomoku game;

    @BeforeEach
    void setupBeforeEachTest() {
        // Create a new game before each test is ran.
        game = new Gomoku(one, two);
    }

    @Test
    void blackShouldStart() {
        // The Gomoku class randomly selects a player as the current player
        // when an instance is created and tracks whose turn it is after that.
        // Given that, at the beginning of a game, the `isBlacksTurn()` method
        // should always return "true" (black always starts first).
        assertTrue(game.isBlacksTurn());
    }

    @Test
    void gameShouldNotBeOverAtStart() {
        // The Gomoku class tracks the state of the game including who if the game has completed.
        // At the beginning of a game, the `isOver()` method should return false.
        assertFalse(game.isOver());
    }

    @Test
    void winnerShouldBeNullAtStart() {
        // The Gomoku class tracks the state of the game
        // including who the winner is once the game has completed.
        // At the beginning of a game, the `getWinner()` method should return null.
        assertNull(game.getWinner());
    }

    @Test
    void humanPlayerShouldReturnNullStone() {
        // Because human players are expected to explicitly specify
        // where they'd like to place a stone on the game board
        // attempting to automatically generate a move by calling
        // the `generateMove()` method should return null.
        Stone stone = one.generateMove(game.getStones());
        assertNull(stone);
    }

    @Test
    void shouldSwapAfterSuccessfulStonePlacement() {
        // The Gomoku class tracks the state of the game including whose turn it is.
        // Check that the Gomoku class swaps the current player
        // after a stone has been successfully placed.

        // Get the current player.
        Player previous = game.getCurrent();

        // Rely upon the Gomoku class' `isBlacksTurn()` method to determine the current player's stone color.
        boolean isBlack = game.isBlacksTurn();

        // Create a new stone for the uppermost left hand spot on the board.
        Stone stone = new Stone(0, 0, isBlack);

        // Place the stone and capture the result into a variable.
        Result result = game.place(stone);

        // Check that the stone was successfully placed.
        assertTrue(result.isSuccess());

        // Check that the current player has changed.
        Player next = game.getCurrent();
        assertNotEquals(previous, next);

        // Check that it's no longer black's turn.
        assertFalse(game.isBlacksTurn());
    }

    @Test
    void shouldSwapIfTheSwapMethodIsCalledDirectly() {
        // Check that the current player is swapped after the `swap()` method is called.

        // NOTE: Because the `swap()` method is called internally by the Gomoku class
        // after a player has successfully placed a stone, it's only necessary to call
        // the `swap()` method directly if you're implementing one of the alternative
        // opening rules (see http://gomokuworld.com/gomoku/2 for more information).

        // Get the current player.
        Player previous = game.getCurrent();

        // Manually swap the current player.
        game.swap();

        // Get the current player again.
        Player next = game.getCurrent();

        // Check that the previous and next players are not the same player object.
        assertNotEquals(previous, next);
    }

    @Test
    void shouldNotPlayOffTheBoard() {
        // The Gomoku class' `place()` method validates the stone placement
        // before it adds a stone to the game board. You can check the Result object
        // returned from the `place()` method to determine if a stone was successfully placed or not.

        // Rely upon the Gomoku class' `isBlacksTurn()` method to determine the current player's stone color.
        boolean isBlack = game.isBlacksTurn();

        // Create a stone whose row is far off of the game board.
        Stone stone = new Stone(55, 4, isBlack);

        // Place the stone and capture the result into a variable.
        Result result = game.place(stone);

        // Check that the result was unsuccessful.
        assertFalse(result.isSuccess());
        assertEquals("Stone is off the board.", result.getMessage());
    }

    @Test
    void shouldNotPlayOutOfTurn() {
        // The Gomoku class' `place()` method validates the stone placement
        // before it adds a stone to the game board. You can check the Result object
        // returned from the `place()` method to determine if a stone was successfully placed or not.

        // NOTE: You should rely upon the Gomoku class' `isBlacksTurn()` method to
        // determine the current player's stone color. We're explicitly passing true or false
        // for the Stone class constructor's `isBlack` parameter value to check that the
        // `place()` method will correctly prevent playing the wrong color stone.

        Result result = game.place(new Stone(5, 5, false)); // invalid move, it's black's turn
        assertFalse(result.isSuccess());
        assertEquals("Wrong player.", result.getMessage());

        result = game.place(new Stone(5, 5, true)); // valid move
        assertTrue(result.isSuccess());
        assertNull(result.getMessage());

        result = game.place(new Stone(6, 6, true)); // invalid move, it's white's turn
        assertFalse(result.isSuccess());
        assertEquals("Wrong player.", result.getMessage());
    }

    @Test
    void shouldNotAllowDuplicateMove() {
        // The Gomoku class' `place()` method validates the stone placement
        // before it adds a stone to the game board. You can check the Result object
        // returned from the `place()` method to determine if a stone was successfully placed or not.

        // Rely upon the Gomoku class' `isBlacksTurn()` method to determine the current player's stone color.
        boolean isBlack = game.isBlacksTurn();

        // Create a new stone for the uppermost left hand spot on the board.
        Stone stone = new Stone(0, 0, isBlack);

        // Place the stone and capture the result into a variable.
        Result result = game.place(stone);

        // Check that the result was successful.
        assertTrue(result.isSuccess());
        assertNull(result.getMessage());

        // Rely upon the Gomoku class' `isBlacksTurn()` method to determine the current player's stone color.
        isBlack = game.isBlacksTurn();

        // Create a new stone (again) for the uppermost left hand spot on the board.
        stone = new Stone(0, 0, isBlack);

        // Place the stone and capture the result into a variable.
        result = game.place(stone);

        // Check that the result was unsuccessful.
        assertFalse(result.isSuccess());
        assertEquals("Duplicate move.", result.getMessage());
    }

    @Test
    void blackShouldWinInFiveMoves() {
        // Get a reference to the first player (i.e. the black player).
        Player black = game.getCurrent();

        // Black player's first move.
        Result result = game.place(new Stone(0, 0, game.isBlacksTurn()));
        assertTrue(result.isSuccess());
        assertNull(result.getMessage());

        // White player's first move.
        result = game.place(new Stone(1, 0, game.isBlacksTurn()));
        assertTrue(result.isSuccess());
        assertNull(result.getMessage());

        // Black player's second move.
        result = game.place(new Stone(0, 1, game.isBlacksTurn()));
        assertTrue(result.isSuccess());
        assertNull(result.getMessage());

        // White player's second move.
        result = game.place(new Stone(1, 1, game.isBlacksTurn()));
        assertTrue(result.isSuccess());
        assertNull(result.getMessage());

        // Black player's third move.
        result = game.place(new Stone(0, 2, game.isBlacksTurn()));
        assertTrue(result.isSuccess());
        assertNull(result.getMessage());

        // White player's third move.
        result = game.place(new Stone(1, 2, game.isBlacksTurn()));
        assertTrue(result.isSuccess());
        assertNull(result.getMessage());

        // Black player's fourth move.
        result = game.place(new Stone(0, 3, game.isBlacksTurn()));
        assertTrue(result.isSuccess());
        assertNull(result.getMessage());

        // White player's fourth move.
        result = game.place(new Stone(1, 3, game.isBlacksTurn()));
        assertTrue(result.isSuccess());
        assertNull(result.getMessage());

        // Black player's fifth move... the winning move of the game.
        // Not only should the result be successful, but it should contain the expected "winning" message.
        result = game.place(new Stone(0, 4, game.isBlacksTurn()));
        assertTrue(result.isSuccess());
        assertEquals(String.format("%s wins.", black.getName()), result.getMessage());

        // Check that the game is in fact over and that the winner was the black player.
        assertTrue(game.isOver());
        assertEquals(black, game.getWinner());
    }

    @Test
    void stoneCountShouldMatch() {
        // The Gomoku class tracks the state of the game including all of the successfully placed stones.
        // You can retrieve a list of the successfully placed stones
        // by calling the Gomoku class' `getStones()` method.

        // Check that the collection of stones doesn't contain any items at the start of the game.
        assertEquals(0, game.getStones().size());

        // Successfully place a stone.
        Result result = game.place(new Stone(0, 0, game.isBlacksTurn()));
        assertTrue(result.isSuccess());
        assertNull(result.getMessage());

        // Check that the collection of stones contains one stone.
        assertEquals(1, game.getStones().size());

        // Unsuccessfully place a stone by attempting to play where a stone is already placed.
        result = game.place(new Stone(0, 0, game.isBlacksTurn()));
        assertFalse(result.isSuccess());
        assertEquals("Duplicate move.", result.getMessage());

        // Check that the collection of stones still contains just one stone.
        assertEquals(1, game.getStones().size());

        // Successfully place a stone adjacent to the first stone that was placed.
        result = game.place(new Stone(1, 0, game.isBlacksTurn()));
        assertTrue(result.isSuccess());
        assertNull(result.getMessage());

        // Check that the collection of stones now contains two stones.
        assertEquals(2, game.getStones().size());
    }

    @Test
    void shouldEndInDraw() {
        int[] rows = {0, 2, 1, 3, 4, 6, 5, 7, 8, 10, 9, 11, 12, 14, 13};
        for (int row : rows) {
            for (int col = 0; col < Gomoku.WIDTH; col++) {
                Result result = game.place(new Stone(row, col, game.isBlacksTurn()));

                // Every placed stone should be successful.
                assertTrue(result.isSuccess());

                // If the game isn't over...
                if (!game.isOver()) {
                    // Check that the result has a null message.
                    assertNull(result.getMessage());
                } else {
                    // Check that the result of the last placed stone contains a "draw" message.
                    assertEquals("Game ends in a draw.", result.getMessage());
                }
            }
        }

        // Check that the game is in fact over and that there wasn't a winner.
        assertTrue(game.isOver());
        assertNull(game.getWinner());
    }

    @Test
    void shouldNotAllowPlayAfterGameHasEnded() {
        // Get a reference to the first player (i.e. the black player).
        Player black = game.getCurrent();

        // Black player's first move.
        Result result = game.place(new Stone(0, 0, game.isBlacksTurn()));
        assertTrue(result.isSuccess());
        assertNull(result.getMessage());

        // White player's first move.
        result = game.place(new Stone(1, 0, game.isBlacksTurn()));
        assertTrue(result.isSuccess());
        assertNull(result.getMessage());

        // Black player's second move.
        result = game.place(new Stone(0, 1, game.isBlacksTurn()));
        assertTrue(result.isSuccess());
        assertNull(result.getMessage());

        // White player's second move.
        result = game.place(new Stone(1, 1, game.isBlacksTurn()));
        assertTrue(result.isSuccess());
        assertNull(result.getMessage());

        // Black player's third move.
        result = game.place(new Stone(0, 2, game.isBlacksTurn()));
        assertTrue(result.isSuccess());
        assertNull(result.getMessage());

        // White player's third move.
        result = game.place(new Stone(1, 2, game.isBlacksTurn()));
        assertTrue(result.isSuccess());
        assertNull(result.getMessage());

        // Black player's fourth move.
        result = game.place(new Stone(0, 3, game.isBlacksTurn()));
        assertTrue(result.isSuccess());
        assertNull(result.getMessage());

        // White player's fourth move.
        result = game.place(new Stone(1, 3, game.isBlacksTurn()));
        assertTrue(result.isSuccess());
        assertNull(result.getMessage());

        // Black player's fifth move... the winning move of the game.
        // Not only should the result be successful, but it should contain the expected "winning" message.
        result = game.place(new Stone(0, 4, game.isBlacksTurn()));
        assertTrue(result.isSuccess());
        assertEquals(String.format("%s wins.", black.getName()), result.getMessage());

        // Check that the game is in fact over and that the winner was the black player.
        assertTrue(game.isOver());
        assertEquals(black, game.getWinner());

        // Attempting to play another stone should fail.
        result = game.place(new Stone(1, 4, game.isBlacksTurn()));
        assertFalse(result.isSuccess());
        assertEquals("Game is over.", result.getMessage());
    }
}
