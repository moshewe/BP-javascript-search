package bp.bthreads;

import bp.BEvent;
import bp.BThread;
import bp.eventSets.EventSet;
import bp.eventSets.EventSetInterface;
import bp.eventSets.RequestableEventSet;
import bp.eventSets.RequestableInterface;
import org.mozilla.javascript.Function;

/**
 * Created by orelmosheweinstock on 8/1/15.
 */
public class DataServiceBThread<DataRequestEvent extends BEvent,
        DataResponseEvent extends BEvent> extends BThread {

    protected DataRequestEvent _dataReq;
    protected DataResponseEvent _dataResp;
    protected boolean _isDataRequested = false;

    public DataServiceBThread(String name, Function func,
                              DataRequestEvent reqEvent) {
        super(name, func);
        _dataReq = reqEvent;
    }

    @Override
    public BEvent bsync(RequestableInterface requestedEvents,
                        EventSetInterface waitedEvents,
                        EventSetInterface blockedEvents) {
        BEvent result;
        if (_isDataRequested) {
            RequestableEventSet newReq =
                    new RequestableEventSet(_dataResp, requestedEvents);
            result = super.bsync(newReq, waitedEvents, blockedEvents);
            if (result.equals(_dataResp)) {
                _isDataRequested = false;
                return bsync(requestedEvents,
                        waitedEvents, blockedEvents);
            }
        } else {
            EventSet newWaited = new EventSet(_dataReq, waitedEvents);
            result = super.bsync(requestedEvents, newWaited,
                    blockedEvents);
            if (result.equals(_dataReq)) {
                _isDataRequested = true;
                return bsync(requestedEvents,
                        waitedEvents, blockedEvents);
            }
        }

        return result;
    }
}
