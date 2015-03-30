package bp;

import bp.eventSets.EventSetInterface;
import bp.eventSets.RequestableInterface;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BProgram implements Cloneable, Serializable {

    /**
     * A collection containing all the be-threads in the system. A be-thread
     * adds itself to the list either - in its constructor and removes itself
     * when its run() function finishes - or a Java thread adds itself and
     * removes itself explicitly
     */
    public transient Collection<BThread> _bthreads;
    /**
     * A variable counting the number of events fired since the beginning of the
     * execution
     */
    // the index of the last event (= number of events -1)
    transient public int _eventCounter = -1;
    static private Object error = null;
    /**
     * Stores the strings of the events that occurred in this run
     */
    transient ArrayList<String> eventLog = new ArrayList<String>();
    /**
     * The number of event Strings to be saved in the list
     */
    transient int eventLogSize = 100;
    /**
     * A variable containing the last fired event.
     */
    transient private BEvent lastEvent;
    /**
     * Program name is set to be the simple class name by default.
     */
    transient private String name = this.getClass().getSimpleName();
    private Arbiter _arbiter;
    private volatile BlockingQueue<BEvent> _externalEventsQueue;

    public void setArbiter(Arbiter arbiter) {
        this._arbiter = arbiter;
        arbiter.setProgram(this);
    }

    public BProgram() {
        setBThreads(new ArrayList<BThread>());
        _arbiter = new Arbiter();
        _arbiter.setProgram(this);
        _externalEventsQueue = new ArrayBlockingQueue<>(100);
        System.out.println("BProgram instantiated");
    }

    public void log(String s) {
        System.out.println("[" + name + "]: " + s);
    }

    public Collection<BThread> getBThreads() {
        return _bthreads;
    }

    /**
     * @return an ArrayList of all enabled events that are requestable
     */
    public Set<BEvent> legalEvents() {
        Set<BEvent> enabled = new TreeSet<>();
        for (BThread bt : _bthreads) {
            if (bt.getRequestedEvents() == null)
                continue;

            ArrayList<BEvent> reqList = bt.getRequestedEvents().getEventList();
            for (BEvent e : reqList) {
                if (!isBlocked(e)) {
                    enabled.add(e);
                }
            }
        }
        return enabled;
    }

    /**
     * @return the given bprogram name
     */
    public String getName() {
        return name;
    }

    /**
     * A function that checks if an event is blocked by some be-thread.
     *
     * @param e An event.
     * @return true if the event is blocked by some be-thread.
     */
    public boolean isBlocked(BEvent e) {
        for (BThread bt : getBThreads()) {
            // log("_bt=" + _bt + " blockedEvents=" + _bt.blockedEvents);
            if (bt.getBlockedEvents().contains(e)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Utility function (for debugging purposes) that prints the ordered list of
     * active be-threads.
     */
    public void printAllBThreads() {
        int c = 0;
        for (BThread bt : getBThreads()) {
            log("\t" + (c++) + ":" + bt);
        }
    }

    public void printEventLog() {

        System.out.println("\n ***** Printing last " + eventLog.size()
                + " choice points out of " + (_eventCounter + 1) + ":");

        if (_eventCounter < eventLogSize)
            for (String eventString : eventLog)
                System.out.println(eventString);
        else
            for (int i = 1; i <= eventLogSize; i++)
                System.out.println(eventLog.get((_eventCounter + i)
                        % eventLogSize));

        System.out.println("***** end event log ******");
    }

    public void setBThreads(Collection<BThread> _bthreads) {
        this._bthreads = _bthreads;
    }

    public void setDebugMode(boolean mode) {
    }

    /**
     * Sets the error that occurred during the run, to make BProgram terminate
     * at the next bSync and print the error.
     *
     * @param error An Object of the error occurred during the run - better have
     *              an informative toString().
     */
    public static void setError(Object error) {
        BProgram.error = error;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Start all added scenarios.
     */
    public void start() {
        // public void start() {
        log("********* Starting " + getBThreads().size()
                + " scenarios  **************");
        for (BThread bt : getBThreads()) {
            bt.start();
        }
        log("********* " + getBThreads().size()
                + " scenarios started **************");
        bpLoop();
    }

    /**
     * Get all waited-for events when program is idle
     *
     * @return
     */
    public Collection<EventSetInterface> getWatchedEventSets() {
        Collection<EventSetInterface> ret = new ArrayList<EventSetInterface>();
        for (BThread bt : _bthreads) {
            ret.add(bt.getWaitedEvents());
        }
        return ret;
    }

    // GW: Get all events that are requested but blocked when program is idle
    public Collection<BEvent> getRequestedBlockedEvents() {
        Collection<BEvent> blocked = new ArrayList<BEvent>();
        for (BThread bt : _bthreads) {
            Iterator<RequestableInterface> it = bt.getRequestedEvents().iterator();
            while (it.hasNext()) {
                RequestableInterface req = it.next();
                if (req.isEvent()) {
                    BEvent e = (BEvent) req;
                    for (BThread other : _bthreads) {
                        if (other.getBlockedEvents().contains(e)) {
                            blocked.add(e);
                        }
                    }
                }
            }
        }
        return blocked;
    }

    public void bpLoop() {
        if (_bthreads.isEmpty()) {
            log("=== ALL DONE!!! ===");
            return;
        }

        BEvent next = _arbiter.nextEvent();
        if (next == null) {
            log("no event chosen, waiting for an external event to be fired...");
            next = dequeueExternalEvent();
        }

        triggerEvent(next);
        bthreadCleanup();
        bpLoop();
    }

    private void bthreadCleanup() {
        for (Iterator<BThread> it = _bthreads.iterator();
             it.hasNext(); ) {
            BThread bt = it.next();
            if (!bt.isAlive()) {
                it.remove();
            }
        }
    }

    /**
     * Used by arbiters to notify programs of events triggered.
     *
     * @param lastEvent
     */
    private void triggerEvent(BEvent lastEvent) {
        String st;
        if (lastEvent != null) {
            _eventCounter++;
            setLastEvent(lastEvent.getEvent());
            st = new String("Event #" + _eventCounter + ": " + getLastEvent());
            log(st);
            log(">> starting bthread wakeup");
            // Interrupt and notify the be-threads that need to be
            // awaken
            for (BThread bt : _bthreads) {
                boolean waited = bt.isWaited(lastEvent);
                boolean requested = bt.isRequested(lastEvent);
                if (waited || requested) {
                    bt.resume(lastEvent);
                }
            }
            log("<< finished bthread wakeup");
        } else { // lastEvent == null -> deadlock?
            st = new String(
                    "No events chosen. Waiting for external event or stuck in bsync...?");
            log(st);
        }

        if (_eventCounter < eventLogSize)
            eventLog.add(st); // at position _eventCounter
        else
            eventLog.set(_eventCounter % eventLogSize, st);
    }

    public BEvent getLastEvent() {
        return lastEvent;
    }

    public void setLastEvent(BEvent lastEvent) {
        this.lastEvent = lastEvent;
    }

    public Arbiter getArbiter() {
        return _arbiter;
    }

    public void add(Collection<BThread> bts) {
        for (BThread bt : bts) {
            add(bt);
        }
    }

    public void add(BThread bt) {
        _bthreads.add(bt);
    }

    public void fireExternalEvent(BEvent e) {
        _externalEventsQueue.add(e);
    }

    public BEvent dequeueExternalEvent() {
        BEvent e = null;
        try {
            e = _externalEventsQueue.take();
        } catch (InterruptedException ie) {
            // TODO Auto-generated catch block
            ie.printStackTrace();
        }

        return e;
    }

}
