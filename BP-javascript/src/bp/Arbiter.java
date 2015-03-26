package bp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by orelmosheweinstock on 3/26/15.
 */
public class Arbiter {

    BProgram _program;

    public Arbiter(){
    }

    public BEvent nextEvent() {
        List requested = new ArrayList();
        for (BThread bt : _program.getBThreads()) {
            requested.add(bt._request);
        }
        System.out.println("requested: " + requested);
        for (BThread bt : _program.getBThreads()) {
            requested.remove(bt._block);
        }

        Object lastEvent = requested.get(0);
        return (BEvent) lastEvent;
    }
}
