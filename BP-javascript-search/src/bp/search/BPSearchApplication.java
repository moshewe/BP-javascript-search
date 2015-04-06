package bp.search;

import bp.BPJavascriptApplication;
import bp.BThread;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by orelmosheweinstock on 4/6/15.
 */
public abstract class BPSearchApplication extends BPJavascriptApplication {


    protected List<BThread> _simBThreads;

    @Override
    protected void addBThreads() {
        super.addBThreads();
        addSimBThreads();
    }

    protected void addSimBThreads(){
        _simBThreads = new ArrayList<>();
    }

    @Override
    protected void setupBThreadScopes() {
        super.setupBThreadScopes();
        setupBThreadScopes(_simBThreads);
    }
}
