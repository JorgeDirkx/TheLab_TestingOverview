package com.sybetech.business;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.mockito.Mockito.*;
/**
 * Demo TDD. implement TicTacToeGame by first defining the spec here
 * Apply Red-Green-Refactore
 ----------
|(1,1)|(1,2)|(1,3)|
|(2,1)|(2,2)|(2,3)|
|(3,1)|(3,2)|(3,3)|
----------
 */
public class TicTacToeGameTest {

    // to simulate exception use Junit Rule ExpectedException
    // https://github.com/junit-team/junit4/wiki/rules
    @Rule
    public ExpectedException exception = ExpectedException.none();

    private TicTacToeGame game;
    private TicTacToeGameState state;

    @Before
    public final void before() {
        // mock state
    	state = mock(TicTacToeGameState.class);

        // mock clear
    	doReturn(true).when(state).clear();

        // mock save
    	doReturn(true).when(state).save(org.mockito.Mockito.any(TicTacToeGameMove.class));

        // init game
    	game = new TicTacToeGame(state);
    }


    /****************************************************************************************
     * R1: play one piece within a board of 3x3
     ****************************************************************************************/

    /**
     * R1.1: throw RuntimeException if x not valid
     */
    @Test(expected=RuntimeException.class)
    public void whenXoutsideBoard_thenThrowRuntimeException() {
    	game.play(0, 2);
    }

    /**
     * R1.2: throw RuntimeException if y not valid
     */
    @Test
    public void whenYoutsideBoard_thenThrowRuntimeException() {
    	exception.expect(RuntimeException.class);
    	exception.expectMessage(TicTacToeGame.COORDINATE_ERR_MSG);
    	game.play(1, 4);
    }

    /**
     * R1.3: throw RuntimeException if field on x,y occupied
     */
    @Test
    public void whenOccupied_ThenThrowRuntimeException() {
    	exception.expect(RuntimeException.class);
    	exception.expectMessage(TicTacToeGame.FIELD_OCCUPIED_ERR_MSG);
    	game.play(1, 1);
    	game.play(1,1);
    }

    /*****************************************************************************************
     * R2: track last and next player
     ****************************************************************************************/

    /**
     * R2.1: first player is X
     */
    @Test
    public void givenFirstMove_whenNextPlayer_thenX() {
    	assertThat(game.getNextPlayer(), equalTo('X'));
    }

    /**
     * R2.2: if lastplayer was X, nextplayer is O
     */
    @Test
    public void givenLastMoveWasX_whenNextPlayerThenO() {
    	game.play(1, 1);
    	assertThat(game.getNextPlayer(), equalTo('O'));

    }

    /**
     * R2.3: if lastplayer was O, nextplayer is X
     * N.B: usefull?
     */
//    @Test
//    public void givenLastMoveWasO_WhenNextPlayer_ThenX() {
//    	game.play(1, 1);
//    	game.play(1, 2);
//    	assertThat(game.getNextPlayer(), equalTo('X'));
//    }



    /*****************************************************************************************
     * R3: player who connects a line (horizontal, vertical, or diagonal) first wins. Else draw
     ****************************************************************************************/

    /**
     * R3.1: before line connected play is in progress
     */
    @Test
    public void whenPlay_ThenInProgress() {
    	String result = game.play(1, 1);
    	assertThat(result, equalTo(TicTacToeGame.RESULT_IN_PROGRESS));
    }

    /**
     * R3.2: player who connects horizontal line first wins
        1     2     3
    |-----|-----|-----|-->x
   1|(1,1)|(2,1)|(3,1)|
   2|(1,2)|(2,2)|(3,2)|
   3|(1,3)|(2,3)|(3,3)|
    |-----|-----|-----|
    |
    y
     */
    @Test
    public void whenPlayAndHorizontalLineFilled_ThenWinner() {
    	game.play(1,1);//x
    	game.play(1,2);//o
    	game.play(2,1);//x
    	game.play(2,3);//o
    	String result = game.play(3,1);//x
    	assertThat(result, equalTo(String.format(TicTacToeGame.RESULT_WINNER, 'X')));
    }

    /**
     * R3.3: player who connects vertical line first wins
     */
    @Test
    public void whenPlayAndVerticalLineFilled_ThenWinner() {
    	game.play(3,1);//x
    	game.play(2,1);//o
    	game.play(3,2);//x
    	game.play(1,3);//o
    	String result = game.play(3,3);//x
    	assertThat(result, equalTo(String.format(TicTacToeGame.RESULT_WINNER, 'X')));
    }

    /**
     * R3.4: player who connects top-bottom diagonal line first wins
     * /**
     * R3.2: player who connects horizontal line first wins
        1     2     3
    |-----|-----|-----|-->x
   1|(1,1)|(2,1)|(3,1)|
   2|(1,2)|(2,2)|(3,2)|
   3|(1,3)|(2,3)|(3,3)|
    |-----|-----|-----|
    |
    y
     */
    @Test
    public void whenPlayAndTopBottomDiagonalLineFilled_ThenWinner() {
    	game.play(1,2); //x
    	game.play(1,1); //o
    	game.play(2,1); //x
    	game.play(2,2); //o
    	game.play(1,3); //x
    	String result = game.play(3,3); //o
    	assertThat(result, equalTo(String.format(TicTacToeGame.RESULT_WINNER, 'O')));
    }

    /**
     * R3.5: player who connects bottom-top diagonal line first wins
        1     2     3
    |-----|-----|-----|-->x
   1|(1,1)|(2,1)|(3,1)|
   2|(1,2)|(2,2)|(3,2)|
   3|(1,3)|(2,3)|(3,3)|
    |-----|-----|-----|
    |
    y
     */
    @Test
    public void whenPlayAndBottomTopDiagonalLineFilled_ThenWinner() {
    	game.play(3,1);
    	game.play(1,1);
    	game.play(2,2);
    	game.play(2,1);
    	String result = game.play(1,3); //
    	assertThat(result, equalTo(String.format(TicTacToeGame.RESULT_WINNER, 'X')));
    }

    /**
     * R3.6: play is draw if all fields are set and no line connected
         1     2     3
    |-----|-----|-----|-->x
   1|(1,1)x|(2,1)o|(3,1)x|
   2|(1,2)o|(2,2)o|(3,2)x|
   3|(1,3)x|(2,3)x|(3,3)o|
    |-----|-----|-----|
    |
    y
     */
    @Test
    public void whenAllFieldsFilled_ThenDraw() {
    	game.play(1,1); //x
    	game.play(2,2); //o
    	game.play(1,3); //x
    	game.play(1,2); //o
    	game.play(3,1); //x
    	game.play(2,1); //o
    	game.play(2,3); //x
    	game.play(3,3); //o
    	String result = game.play(3,2); //o
    	assertThat(result, equalTo(TicTacToeGame.RESULT_DRAW));
    }


    /*****************************************************************************************
     * R4: save each move to DB and clean DB for each new game session
     ****************************************************************************************/

    /**
     * R4.1: game state should be initialized at session begin (intantiation)
     */
    @Test
    public void whenInstantiated_ThenSetState() {
    	assertThat(game.getState(), notNullValue());
    }

    /**
     * R4.2: each game move should be saved to DB. Focus on current class not external dependencies
     */
    @Test
    public void whenPlay_ThenSaveMoveIsInvoked() {
    	TicTacToeGameMove move = new TicTacToeGameMove(1, 'X', 1,1);
    	game.play(move.getX(), move.getY());
    	verify(state, times(1)).save(move);
    }

    /**
     * R4.3: throw RuntimeException if save failed. Save should defaultly return true
     */
    @Test
    public void whenPlayAndSaveReturnsFalse_ThenThrowRuntimeException() {
    	doReturn(false).when(state).save(org.mockito.Mockito.any(TicTacToeGameMove.class));
    	exception.expect(RuntimeException.class);
    	game.play(1,1);
    }

    /**
     * R4.4: the move should increase after each play
     */
    @Test
    public void whenPlayMultipleTimes_ThenTurnIncreased() {
    	TicTacToeGameMove move1 = new TicTacToeGameMove(1, 'X', 1, 1);
    	game.play(move1.getX(), move1.getY());
    	verify(state, times(1)).save(move1);
    	
    	TicTacToeGameMove move2 = new TicTacToeGameMove(2, 'O', 1, 2);
    	game.play(move2.getX(), move2.getY());
    	verify(state, times(1)).save(move2);
    }

    /**
     * R4.5: game state data should be cleared at session begin (intantiation)
     */
    @Test
    public void whenInstantiated_ThenStateClearInvoked() {
    	verify(state, times(1)).clear();
    }

    /**
     * R4.6: throw RuntimeException if clearing state data fails
     */
    @Test
    public void whenInstantiatedAndClearReturnsFalse_ThenThrowRuntimeException() {
    	doReturn(false).when(state).clear();
    	exception.expect(RuntimeException.class);
    	new TicTacToeGame(state);
    }

    /*****************************************************************************************
     * R5: use MongoDB as persistent storage for the game state. (TicTacToeGameStateTest)
     ****************************************************************************************/

    /****************************************************************************************
     * BP1: check the code coverage
     ****************************************************************************************/

    /****************************************************************************************
     * BP2: integrate real MongoDb. implement TicTacToeIntTest
     ****************************************************************************************/

    /****************************************************************************************
     * R7: create a web UI to play
     * implement TicTacToeGameUiController
     ****************************************************************************************/

    /****************************************************************************************
     * R8: create automated systemtest using ui. implement TicTacToeGameUiTest (UI-Testing)
     ****************************************************************************************/

    /****************************************************************************************
     * R9: create automated acceptancetest using ui and cucumber.
     * implement TicTacToeCucumberSteps
     ****************************************************************************************/
}
