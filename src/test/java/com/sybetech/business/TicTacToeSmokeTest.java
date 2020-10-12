package com.sybetech.business;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Ignore;
import org.junit.Test;

// System does not smoke after invocation
@Ignore
public class TicTacToeSmokeTest {

	@Test
	public void whenDeployed_ThenWebsiteIsReachable() throws IOException {
		URL url = new URL("http://localhost:8080/tictactoe");
		URLConnection connection = url.openConnection();
		assertThat(connection.getContent(), notNullValue());
	}
	
	@Test
	public void whenDeployed_ThenDatabaseIsReachable() throws IOException {
		TicTacToeGameState state = new TicTacToeGameState();
		state.findById(4711);
	}

}
