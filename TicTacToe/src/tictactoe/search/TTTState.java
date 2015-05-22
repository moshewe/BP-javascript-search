package tictactoe.search;

import bp.BProgram;
import bp.BThread;
import bp.search.BPState;
import tictactoe.events.O;
import tictactoe.events.X;

import java.util.List;

public class TTTState extends BPState {

    protected List<BThread> _reqMove;

    public TTTState(BProgram bp, List<BThread> taken) {
        super(bp);
        _reqMove = taken;
    }

    public TTTState(TTTState other) {
        super(other);
        _reqMove = other._reqMove;
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
                if (_program.eventLog.contains(new X(i, j))) {
                    board[i][j] = "X";
                } else if (_program.eventLog.contains(new O(i, j))) {
                    board[i][j] = "O";
                } else {
                    board[i][j] = " ";
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
