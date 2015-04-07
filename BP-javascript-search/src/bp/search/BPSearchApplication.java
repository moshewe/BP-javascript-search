package bp.search;

import bp.BPJavascriptApplication;
import bp.BThread;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by orelmosheweinstock on 4/6/15.
 */
public abstract class BPSearchApplication extends BPJavascriptApplication {


    protected List<BThread> _simBThreads = new ArrayList<>();


    public BPSearchApplication() {
        super();
        addSimBThreads();
        setupSimBThreadScopes();
    }

    protected abstract void addSimBThreads();

    protected void setupSimBThreadScopes() {
        setupBThreadScopes(_simBThreads);
    }
}
