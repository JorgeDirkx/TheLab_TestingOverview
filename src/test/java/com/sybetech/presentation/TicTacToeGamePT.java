package com.sybetech.presentation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.sybetech.business.TicTacToeGame;

/**
 * Stress Test.
 * Works only after deployment
 */
//@Ignore
public class TicTacToeGamePT {

	private WebDriver driver;

	@Before
	public void setUp() {
		driver = new HtmlUnitDriver();
	}

	@Test
	public void stressTest() throws Exception {
		System.out.println("Testing 1000 rounds. Begin at:" + new Date());
		driver.get(Constants.APP_URL);
		assertThat(driver.getTitle(), equalTo(Constants.APP_TITLE));
		
		for(int i= 0; i< 1000; i++) {
			playAndCheckARound();
		}
		System.out.println("Testing 1000 rounds. End at:" + new Date());
	}
	
	
	public void playAndCheckARound() throws Exception {
		
		
		assertThat(play(1,1), equalTo("X"));//x
		assertThat(play(1,2), equalTo("O"));//o
		assertThat(play(2,1), equalTo("X"));//x
		assertThat(play(2,3), equalTo("O"));//o
		assertThat(play(3,1), equalTo("X"));//x
		
		WebElement result = driver.findElement(By.name("f:result"));
		assertThat(result.getAttribute("value"), equalTo(String.format(TicTacToeGame.RESULT_WINNER, "X")));
		
		WebElement resetBtn = driver.findElement(By.name("f:reset"));
		resetBtn.submit();

	}
	
	//TODO test vertical, 2 diagonals and draw

	private String play(int x, int y) {
		WebElement btn = driver.findElement(By.name(String.format("f:btn%s_%s", x, y)));
		btn.submit();
		btn = driver.findElement(By.name(String.format("f:btn%s_%s", x, y)));
		return btn.getAttribute("value");
	}

}
