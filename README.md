# Gomoku
Nikhil Sutliff - Week 2 of Dev10 Program
## High-Level

 User interface for pre-provided Gomoku game classes by adding new Java classes and methods._Existing_ Gomoku classes cover all game rules. _New_ code collects user input and displays feedback. It is user interface only.

## Requirements

- Can set up two players.
- For a human player, collect their name. (A random player's name is randomly generated.)
- For each stone placement, use the player's name to ask questions.
- Since the random player doesn't require input, the UI should display stone placement and the results of placement. (Random player placement may fail since they don't know what they're doing.)
- Re-prompt on failed placement. The game must not proceed until a player has made a valid move. (It's not necessary to add a secondary, inner loop. The overall game loop may be sufficient.)
- Display the final result of the game.
- Give the option to play again.

## Steps

1. Display a welcome message.

2. Set up the Gomoku game.

    - Repeat for player 1 and player 2:
        1. Ask the user if they want to play as a HumanPlayer or RandomPlayer.
        2. If human, collect a name from the user and use it to instantiate a HumanPlayer.
        3. If random, instantiate a RandomPlayer. A random player's name is auto-generated.

    - Instantiate a Gomoku object using the two `Player` objects as arguments.

    - Display a "(Randomizing)" message.

    - Display the player who goes first. (The game tracks the current player.)

3. Play a full game.

    - Repeat until the game is over:
       
    - Display the final board.
    - If there's a winner, display them.
    - If it's a draw display it.

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