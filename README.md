# Gomoku

## High-Level

Create a user interface for existing Gomoku game classes by adding new Java classes and methods. _Existing_ Gomoku classes cover all game rules. _New_ code collects user input and displays feedback. It is user interface only.

## Requirements

- Can set up two players.
- For a human player, collect their name. (A random player's name is randomly generated.)
- For each stone placement, use the player's name to ask questions.
- Since the random player doesn't require input, the UI should display stone placement and the results of placement. (Random player placement may fail since they don't know what they're doing.)
- Re-prompt on failed placement. The game must not proceed until a player has made a valid move. (It's not necessary to add a secondary, inner loop. The overall game loop may be sufficient.)
- Display the final result of the game.
- Give the option to play again.

## Technical Requirements

- All rules are modeled inside the `Gomoku` class. You may not modify `Gomoku`, `Player`, `HumanPlayer`, `RandomPlayer`, `Stone`, or `Result`.
- Add your code to the project provided. Be sure to use sensible class names, method names, and packages.
- At least one class beyond the `App` class is required.
- Stones use 0-based rows and columns.

## Steps

1. Display a welcome message.

2. Set up the Gomoku game.

    - Repeat for player 1 and player 2:
        1. Ask the user if they want to play as a HumanPlayer or RandomPlayer.
        2. If human, collect a name from the user and use it to instantiate a HumanPlayer.
        3. If random, instantiate a RandomPlayer. A random player's name is auto-generated.

    - Instantiate a Gomoku object using the two `Player` objects as arguments.

    - Display a "(Randomizing)" message.

    - Display the player who goes first. (The game tracks the current player: `game.getCurrent().getName()`.)

3. Play a full game.

    - Repeat until the game is over:
        1. Display the board.
        2. Display who's turn it is: `game.getCurrent().getName()`.
        3. Generate a stone/move from the current player: `Stone stone = game.getCurrent().generateMove(game.getStones());`
        4. If `stone` is `null`, the player must be a human. Collect a row and column from the user. Use the row and column to instantiate a `Stone` object.
        5. If `stone` is not `null`, the player must be random. Use the `stone` in the step below.
        6. Place the stone and capture the result: `Result result = game.place(stone);`
        7. Display the result if it's not successful.
        8. Check if the game is over. If it is, exit the main game loop.

    - Display the final board.
    - If there's a winner, display them: `game.getWinner().getName()`.
    - If it's a draw (`game.getWinner() == null`), display it.

4. Ask the user if they want to play again. If yes, back to major step 2. If not, exit.

## Classes

### GameController

Provides a user interface through which the user can interact with the `Gomoku` class.

#### Fields

- `Scanner` console (initialize immediately)
- `char[][]` board (initialize immediately 15X15)
- `Gomoku` game

#### Public Methods

- `void` run
    - set up the game
    - play the game
    - ask to play again
    - uses: `setup`, `play`, `playAgain`

#### Private Methods

- `void` setup
    - create player 1
    - create player 2
    - use 1 and 2 to instantiate the game
    - reset the board -- clear all characters
    - display messages
    - uses: `getPlayer`

- `Player` getPlayer(`int` playerNumber)
    - prompt user for human or random
    - If human, prompt user for a name. Use the name to instantiate a `HumanPlayer`.
    - If random, instantiate a `RandomPlayer`.
    - return the `Player`
    - uses: `readInt`, `readRequiredString`

- `void` play
    - display board
    - display current player
    - generate a stone from the current player
    - place the stone and collect the result
    - display result
    - repeat until game over
    - display win or draw
    - uses: `printBoard`, `readInt` (for row and column)

- `void` printBoard
    - use the game to fill in the `char[][]` board (`game.getStones()`) -- synchronize the game's state with the `char[][]`
    - print the board

- `String` readRequiredString(`String` message)
    - prompt the user
    - collect their input
    - if whitespace or empty, repeat
    - else, return the input

- `int` readInt(`String` message, `int` min, `int` max)
    - prompt the user
    - collect their input
    - if the input is not a number, it's invalid, display message and repeat
    - convert the string input to an `int`
    - if the input is not within the min/max, it's invalid, display message and repeat
    - if valid, return the value
    - may use: `readRequiredString`

- `boolean` playAgain
    - asks the user if they want to play again
    - returns `true` if yes, `false` for any other input
    - may use: `readRequiredString`

### App

#### Public Methods

- `void` main(`String[]` args)
    - instantiate a `GameController`
    - execute its `run` method