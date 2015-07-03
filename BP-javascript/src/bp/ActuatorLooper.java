package bp;

/**
 * Created by moshewe on 03/07/2015.
 */
public class ActuatorLooper implements Runnable {
    private final BPJavascriptApplication _bpjs;
    private BEventVisitor _vis;

    public ActuatorLooper(BPJavascriptApplication bpjs,
                          BEventVisitor vis) {
        this._vis = vis;
        this._bpjs = bpjs;
    }

    @Override
    public void run() {
        _bpjs.actuatorLoop(_vis);
    }
}
