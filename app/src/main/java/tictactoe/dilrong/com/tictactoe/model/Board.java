package tictactoe.dilrong.com.tictactoe.model;

import static tictactoe.dilrong.com.tictactoe.model.Player.O;
import static tictactoe.dilrong.com.tictactoe.model.Player.X;

public class Board {

    private Cell[][] cells = new Cell[3][3];

    private Player winner;
    private GameState state;
    private Player currentTurn;

    private enum GameState { IN_PROGRESS, FINISHED };

    public Board(){
        restart();
    }

    public void restart(){
        clearCells();
        winner = null;
        currentTurn = X;
        state = GameState.IN_PROGRESS;
    }

    /**
     *
     * @param row
     * @param col
     * @return the player that moved or null if we did not move anything
     */
    public Player mark(int row, int col){
        Player playerThatMoved = null;

        if(isValid(row, col)){
            cells[row][col].setValue(currentTurn);
            playerThatMoved = currentTurn;

            if(isWinningMoveByPlayer(currentTurn, row, col)){
                state = GameState.FINISHED;
                winner = currentTurn;
            }else{
                flipCurrentTurn();
            }
        }
        return playerThatMoved;
    }

    public Player getWinner(){
        return winner;
    }

    private void clearCells(){
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                cells[i][j] = new Cell();
            }
        }
    }

    private boolean isValid(int row, int col){
        if(state == GameState.FINISHED){
            return false;
        }else if(isOutOfBounds(row) || isOutOfBounds(col)){
            return false;
        }else if(isCellValueAlreadySet(row, col)){
            return false;
        }else{
            return true;
        }
    }

    private boolean isOutOfBounds(int idx){
        return idx < 0 || idx > 2;
    }

    private boolean isCellValueAlreadySet(int row, int col){
        return cells[row][col].getValue() != null;
    }

    /**
     * Algorithm http://www.ntu.edu.sg/home/ehchua/programming/java/JavaGame_TicTacToe.html
     * @param player
     * @param currentRow
     * @param currentCol
     * @return true if player who just played the move at current row, currentCol has a tic tac toe.
     */
    private boolean isWinningMoveByPlayer(Player player, int currentRow, int currentCol){
        return (cells[currentRow][0].getValue() == player
                && cells[currentRow][1].getValue() ==player
                && cells[currentRow][2].getValue() ==player
                || cells[0][currentCol].getValue() ==player
                && cells[1][currentCol].getValue() ==player
                && cells[2][currentCol].getValue() ==player
                || currentRow == currentCol
                && cells[0][0].getValue() ==player
                && cells[1][1].getValue() ==player
                && cells[2][2].getValue() ==player
                || currentRow + currentCol == 2
                && cells[0][2].getValue() == player
                && cells[1][1].getValue() == player
                && cells[2][0].getValue() == player);
    }

    private  void flipCurrentTurn(){
        currentTurn = currentTurn == X ? O : X;
    }
}
