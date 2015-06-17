package bp;

/**
 * Created by orelmosheweinstock on 6/14/15.
 */
public abstract class BEventVisitor {
    public void visit(BEvent event) {
        bplog("Unimplemented");
    }

    private void bplog(String s) {
        if (BProgramControls.debugMode)
            System.out.println(getClass().getSimpleName() + ": " + s);
    }
}
