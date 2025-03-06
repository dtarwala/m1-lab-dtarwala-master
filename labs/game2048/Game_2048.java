package game2048;

import bridges.games.NonBlockingGame;
import bridges.base.NamedColor;
import bridges.base.NamedSymbol;
import java.util.ArrayList;
import java.util.Random;
import credentials.*;
//import Vector2;

public class Game_2048 extends NonBlockingGame {

  // / the game map.
  static int rows = 24;
  static int cols = 24;

  Random random = new Random(System.currentTimeMillis());

  // Keep track of all cells in this grid which are 6x6 colored tiles
  ArrayList<Cell> cells;

  // A list of numbered text symbols from 0-9
  NamedSymbol symbolList[] = {NamedSymbol.zero, NamedSymbol.one, NamedSymbol.two, NamedSymbol.three, NamedSymbol.four, NamedSymbol.five, NamedSymbol.six, NamedSymbol.seven, NamedSymbol.eight, NamedSymbol.nine};

  public Game_2048(int assid, String login, String apiKey, int col, int row) {
    super(assid, login, apiKey, col, row);
    setTitle("Game 2048");
    setDescription("Try to add up the highest number possible before the grid fills up!");
    start();
  }

  public static void main(String args[]) {
    Game_2048 game = new Game_2048(11, User.USERNAME, User.APIKEY, rows, cols);
  }
  public void gameLoop() {
    process();
  }

  /**
   * @return A random cell in the grid that does not have a tile
   */
  public Vector2 randomOpenCell() {

    // just a temp
    Vector2 aVector  = new Vector2(0, 0);

    return aVector;
  }

  @Override
  public void initialize() {

  }

  private void draw() {

  }

  /**
   * @param c The cell to check for collision from
   * @param pos The position to check for collision with c
   * @return True if there is another cell that is at pos or if the position
   * is outside the grid
   */
  private boolean checkCollision(Cell c, Vector2 pos) {
    return false;
  }

  /**
   * @return True if there is no move that the player can make
   */
  private boolean checkGameOver() {
    return false;
  }

  public void process() {

  }
}

// Helper class, represents a 6x6 game tile
class Cell {

  public Vector2 position = new Vector2(0, 0);
  public Vector2 size = new Vector2(6, 6);
  public int value = 2;

  public Cell(Vector2 pos) {
    position = pos;
  }

  public Cell(Vector2 pos, int value) {
    position = pos;
    this.value = value;
  }
}
