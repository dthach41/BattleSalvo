package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GameResultTest {

  @Test
  void values() {
    GameResult[] gameResultValues = {GameResult.WIN, GameResult.DRAW, GameResult.LOSE};
    for (int i = 0; i < gameResultValues.length; i++) {
      assertEquals(gameResultValues[i], GameResult.values()[i]);
    }
  }

  @Test
  void valueOf() {
    assertEquals(GameResult.valueOf("WIN"), GameResult.WIN);
    assertEquals(GameResult.valueOf("DRAW"), GameResult.DRAW);
    assertEquals(GameResult.valueOf("LOSE"), GameResult.LOSE);
  }
}