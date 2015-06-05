package bp.search.informed;

import aima.core.search.framework.HeuristicFunction;
import aima.core.search.informed.AStarSearch;

/**
 * Created by orelmosheweinstock on 6/5/15.
 */
public class BPAStarSearch extends AStarSearch {

    /**
     * Constructs an A* search from the specified search problem and heuristic
     * function
     *
     * @param search a search problem
     * @param hf     a heuristic function <em>h(n)</em>, which estimates the cost
     *               of the cheapest path from the state at node <em>n</em> to a
     */
    public BPAStarSearch(BPQueueSearch search, HeuristicFunction hf) {
        super(search, hf);
    }
}
