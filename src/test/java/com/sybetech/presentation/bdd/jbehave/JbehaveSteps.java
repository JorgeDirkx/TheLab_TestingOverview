package com.sybetech.presentation.bdd.jbehave;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;

import com.codeborne.selenide.SelenideElement;
import com.sybetech.business.TicTacToeGame;
import static com.codeborne.selenide.Selenide.*;
/**
 * Acceptance test
 * steps matching those in scenarios in story file
 * works only after deployment
 */
public class JbehaveSteps {

	@BeforeStory
	public void setUp() {
		System.setProperty("selenide.browser", "htmlunit");
	}
	
	@Given("user browses to $url")
	public void givenUserBrowsesToHttplocalhost8080tictactoe(String url) {
		open(url);
	}

	@When("first move X is ($x,$y)")
	public void whenFirstMoveXIs(int x, int y) {
		assertThat(play(x,y), equalTo("X"));
	}

	@When("second move O is ($x,$y)")
	public void whenSecondMoveOIs(int x, int y) {
		assertThat(play(x,y), equalTo("O"));
	}

	@When("thrird move X is ($x,$y)")
	public void whenThrirdMoveXIs(int x, int y) {
		assertThat(play(x,y), equalTo("X"));
	}

	@When("fourth move O is ($x,$y)")
	public void whenFourthMoveOIs(int x, int y) {
		assertThat(play(x,y), equalTo("O"));
	}

	@When("fifth move X is ($x,$y)")
	public void whenFifthMoveXIs(int x, int y) {
		assertThat(play(x,y), equalTo("X"));
	}

	@Then("winner is X")
	public void thenWinnerIsX() {
		SelenideElement result = $(By.name("f:result"));
		assertThat(result.getAttribute("value"), equalTo(String.format(TicTacToeGame.RESULT_WINNER, "X")));
	}
	
	private String play(int x, int y) {
		SelenideElement btn = $(By.name(String.format("f:btn%s_%s", x, y)));
		btn.submit();
		btn = $(By.name(String.format("f:btn%s_%s", x, y)));
		return btn.getAttribute("value");
	}
}
