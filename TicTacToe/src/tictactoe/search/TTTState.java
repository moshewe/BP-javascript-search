package tictactoe.search;

import bp.BProgram;
import bp.BThread;
import bp.search.BPState;
import tictactoe.bThreads.DeclareWinner;
import tictactoe.events.X;
import tictactoe.externalApp.TicTacToe;

import java.util.Collection;

public class TTTState extends BPState {

    public TTTState(BProgram bp, TicTacToe ttt,
                    Collection<BThread> taken,
                    DeclareWinner declareWinner) {
        super(bp);
    }

    public TTTState(TTTState other) {
        super(other);
    }

    @Override
    public TTTState copy() {
        return new TTTState(this);
    }

    @Override
    public void restore() {
        super.restore();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String[][] board = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(_program.eventLog.contains(new X(i,j))){
                    board[i][j] = "X";
                }
                else{
                    board[i][j] = "O";
                }
            }
        }
        sb.append('\n');
        sb.append('|');
        sb.append(board[0][0]);
        sb.append('|');
        sb.append(board[0][1]);
        sb.append('|');
        sb.append(board[0][2]);
        sb.append('|');
        sb.append('\n');
        sb.append("-------");
        sb.append('\n');
        sb.append('|');
        sb.append(board[1][0]);
        sb.append('|');
        sb.append(board[1][1]);
        sb.append('|');
        sb.append(board[1][2]);
        sb.append('|');
        sb.append('\n');
        sb.append("-------");
        sb.append('\n');
        sb.append('|');
        sb.append(board[2][0]);
        sb.append('|');
        sb.append(board[2][1]);
        sb.append('|');
        sb.append(board[2][2]);
        sb.append('|');
        sb.append('\n');
        return sb.toString();
    }
}
