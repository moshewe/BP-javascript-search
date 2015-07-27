package tictactoe.search;

import bp.BPApplication;
import bp.search.informed.InitStateGenerator;

/**
 * Created by orelmosheweinstock on 6/6/15.
 */
public class TTTInitStateGenerator extends InitStateGenerator {

    public TTTInitStateGenerator(BPApplication program) {
        super(program);
    }

    @Override
    protected TTTState genBPStateFromProgram() {
        return new TTTState(_program);
    }
}
