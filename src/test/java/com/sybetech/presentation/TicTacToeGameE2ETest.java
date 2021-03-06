package com.sybetech.presentation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.sybetech.business.TicTacToeGame;
import com.sybetech.business.TicTacToeGameMove;
import com.sybetech.business.TicTacToeGameState;

/**
 * System Test.
 * Works only after deployment
 */
@Ignore
public class TicTacToeGameE2ETest {

	private WebDriver driver;

	@Before
	public void setUp() {
		driver = new HtmlUnitDriver();
	}

	@Test
	public void whenPlayAndWholeHorizontalLineFilled_ThenWinner() throws Exception {
		driver.get(Constants.APP_URL);
		assertThat(driver.getTitle(), equalTo(Constants.APP_TITLE));
		
		assertThat(play(1,1), equalTo("X"));//x
		TicTacToeGameState db = new TicTacToeGameState();
		TicTacToeGameMove persistedMove1 = db.findById(1);
		TicTacToeGameMove expectedMove1 = new TicTacToeGameMove(1, 'X', 1, 1);
		assertThat(persistedMove1, equalTo(expectedMove1));
		
		assertThat(play(1,2), equalTo("O"));//o
		TicTacToeGameMove persistedMove2 = db.findById(2);
		TicTacToeGameMove expectedMove2 = new TicTacToeGameMove(2, 'O', 1, 2);
		assertThat(persistedMove2, equalTo(expectedMove2));
		
		assertThat(play(2,1), equalTo("X"));//x
		assertThat(play(2,3), equalTo("O"));//o
		assertThat(play(3,1), equalTo("X"));//x
		
		WebElement result = driver.findElement(By.name("f:result"));
		assertThat(result.getAttribute("value"), equalTo(String.format(TicTacToeGame.RESULT_WINNER, "X")));

	}
	
	//TODO test vertical, 2 diagonals and draw

	private String play(int x, int y) {
		WebElement btn = driver.findElement(By.name(String.format("f:btn%s_%s", x, y)));
		btn.submit();
		btn = driver.findElement(By.name(String.format("f:btn%s_%s", x, y)));
		return btn.getAttribute("value");
	}

}
