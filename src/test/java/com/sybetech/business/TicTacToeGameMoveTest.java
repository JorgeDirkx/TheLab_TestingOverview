package com.sybetech.business;

import org.junit.*;
import org.junit.rules.TestName;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * JUnit Demo
 * use assertEquals, assertTrue (with and without message
 */
public class TicTacToeGameMoveTest {

    private TicTacToeGameMove move;
    private final int id = 3;
    private final int x = 1;
    private final int y = 1;
    private final char player = 'X';

    // https://github.com/junit-team/junit4/wiki/rules (JUnit interceptors)
    @Rule
    public final TestName name = new TestName();

    @BeforeClass
    public static void beforeClass() {
        // called once on class initialization time
    	System.out.println("beforeClass");
    }

    @AfterClass
    public static void afterClass() {
        // called once after all methods are executed
    	System.out.println("afterClass");
    }

    @Before
    public void setupMethod() {
        // called before each method execution
    	move = new TicTacToeGameMove(id, player, x, y);
    	System.out.println("setupMethod");
    }

    @After
    public void cleanupMethod() {
        // called after each method execution
    	move = null;
    	System.out.println("cleanupMethod");
    }

    // check id is set after instantiation
    @Test
    public void whenInstantiated_ThenIdIsSet() {
    	assertEquals("Asserting that id is set in constructor", id, move.getId());
    	System.out.println(name.getMethodName());
    }

    // check x is set after instantiation
    @Test
    public void whenInstantiated_ThenXIsSet() {
    	assertEquals("Asserting that id is set in constructor", x, move.getX());
    	System.out.println(name.getMethodName());
    }

    // check y is set after instantiation
    @Test
    public void whenInstantiated_ThenYIsSet() {
    	
    	assertEquals(y, move.getY());
    	System.out.println(name.getMethodName());
    }

    // check player is set after instantiation
    @Test
    public void whenInstantiated_ThenPlayerIsSet() {
    	assertEquals(player, move.getPlayer());
    	System.out.println(name.getMethodName());
    }

    // check allowedChars are O and X after instantiation
    @Test
    public void whenInstantiated_ThenAllowedCharsAreOandX() {
    	List<Character> expectedChars = Arrays.asList('X', 'O');
    	assertTrue(move.getAllowedChars().containsAll(expectedChars));
    	assertEquals(2, move.getAllowedChars().size());
    	System.out.println(name.getMethodName());
    }

    // check Rule TestName.getMethodName
    @Test
    public void whenGetMethodNameOfTestNameRuleInvoked_ThenReturnNameOfThisTest() {
    	assertEquals("whenGetMethodNameOfTestNameRuleInvoked_ThenReturnNameOfThisTest", name.getMethodName());
    	System.out.println(name.getMethodName());
    }

}