package com.sybetech.business;

import org.jongo.FindOne;
import org.jongo.MongoCollection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.mongodb.MongoException;

import java.net.UnknownHostException;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
/**
 * Demo for mocking framework Mockito
 * - spy (partial mocking): real object using real methods, unless specified otherwise
 */
@RunWith(MockitoJUnitRunner.class) // creates required mocks and inject them in the test class
//@Ignore
public class TicTacToeGameStateTest {
    /*****************************************************************************************
     * R5: use MongoDB as persistent storage for the game state.
     ****************************************************************************************/

    /**
     * R5.1: implement bean for storage representation TicTacToeGameState
     */

    private TicTacToeGameState state;
    private MongoCollection mongoCollection;
    private TicTacToeGameMove move;

    @Before
    public void setUp() throws UnknownHostException {
        // init move
    	move = new TicTacToeGameMove(1, 'X', 1, 2);

        // use method spy() to partial mock class to test
    	state = spy(new TicTacToeGameState());

        // use method mock(Class) to mock db dependency mongoCollection (all methods are shallow).
    	mongoCollection = mock(MongoCollection.class);

    }

    /**
     * R5.2: specify DB-Name
     */
    @Test
    public void whenInstantiated_ThenMongoHasDbName() throws Exception {
    	assertThat(state.getMongoCollection().getDBCollection().getDB().getName(), equalTo(TicTacToeGameState.DB_NAME));
    }

    /**
     * R5.2: specify the mongo collection-name
     */
    @Test
    public void whenInstantiated_ThenMongoCollectionHasName() throws Exception {
    	assertThat(state.getMongoCollection().getName(), equalTo(TicTacToeGameState.COLLECTION_NAME));
    }

    /**
     * R5.3: save data to mongo-db and return true
     * use doReturn(mockObj).when(classToMock).methodToMock(), verify()
     */
    @Test
    public void whenSave_ThenInvokeMongoCollectionSaveAndReturnTrue() throws Exception {
    	doReturn(mongoCollection).when(state).getMongoCollection();
    	boolean result = state.save(move);
    	assertThat(result, equalTo(true));
    }

    /**
     * R5.4: return false if MongoException occurs while saving
     * use doReturn, doThrow
     */
    @Test
    public void givenMongoException_WhenSave_ThenReturnFalse() {
    	doReturn(mongoCollection).when(state).getMongoCollection();
    	doThrow(new MongoException("saving failed")).when(mongoCollection).save(move);
    	boolean result = state.save(move);
    	assertThat(result, equalTo(false));
    }

    /**
     * R5.5: specify droping data
     */
    @Test
    public void whenClear_ThenInvokeMongoCollectionDrop() {
    	doReturn(mongoCollection).when(state).getMongoCollection();
    	boolean result = state.clear();
    	assertThat(result, equalTo(true));
    	verify(mongoCollection, times(1)).drop();
    }

    /**
     * R5.6: return false if MongoException occurs while dropping
     * use doReturn, doThrow
     * check when exception occurs during clear, then clear returns false
     */
    @Test
    public void givenMongoException_WhenClear_ThenReturnFalse() {
    	doReturn(mongoCollection).when(state).getMongoCollection();
    	doThrow(new MongoException("saving failed")).when(mongoCollection).drop();
    	boolean result = state.clear();
    	assertThat(result, equalTo(false));

    }
    
    @Test
    public void whenFindById_ThenInvokeMongoCollectionFindOneAndReturnMove() throws Exception {
    	// mock (check behavior)
    	doReturn(mongoCollection).when(state).getMongoCollection();
    	
    	// stubbing (change state /result values
    	FindOne findOne = mock(FindOne.class);
    	doReturn(findOne).when(mongoCollection).findOne("{_id:#}", move.getId());
    	doReturn(move).when(findOne).as(TicTacToeGameMove.class);
    	
    	TicTacToeGameMove result = state.findById(move.getId());
    	assertThat(result, equalTo(move));
    }
}