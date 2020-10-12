
Feature: Play tictactoe win
  As a game member, I want to play TicTacToe, So that I can win

  Scenario: player who connects horizontal line first wins
    Given user browses to http://localhost:8080/tictactoe/
    When first move X is (1,1)
	When second move O is (1,2)
	When thrird move X is (2,1)
	When fourth move O is (2,3)
	When fifth move X is (3,1)
	Then winner is X

	