import java.io.Serializable;
import java.util.Collection;

/**
 * Created by orelmosheweinstock on 3/24/15.
 */
public class RWB implements Serializable {

    Object request;
    Object wait;
    Object block;

    public RWB(Object request, Object wait, Object block) {
        this.request = request;
        this.wait = wait;
        this.block = block;
    }
}

