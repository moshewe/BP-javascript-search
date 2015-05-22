package bp;

import bp.eventSets.EventSetInterface;
import bp.eventSets.RequestableInterface;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static bp.BProgramControls.debugMode;

public class BProgram implements Cloneable, Serializable {

    /**
     * A collection containing all the be-threads in the system. A be-thread
     * adds itself to the list either - in its constructor and removes itself
     * when its run() function finishes - or a Java thread adds itself and
     * removes itself explicitly
     */
    public transient Collection<BThread> _bthreads;
    static private Object error = null;
    /**
     * Stores the strings of the events that occurred in this run
     */
    public transient Deque<BEvent> eventLog = new LinkedList<>();
    /**
     * Program name is set to be the simple class name by default.
     */
    transient private String name = this.getClass().getSimpleName();
    private Arbiter _arbiter;
    private volatile BlockingQueue<BEvent> _inputEventQueue;
    private volatile BlockingQueue<BEvent> _outputEventQueue;

    public void setArbiter(Arbiter arbiter) {
        this._arbiter = arbiter;
        arbiter.setProgram(this);
    }

    public BProgram() {
        _bthreads = new ArrayList<BThread>();
        _arbiter = new Arbiter();
        _arbiter.setProgram(this);
        _inputEventQueue = new ArrayBlockingQueue<>(100);
        _outputEventQueue = new ArrayBlockingQueue<>(100);
        bplog("BProgram instantiated");
    }

    protected void bplog(String string) {
        if (debugMode)
            System.out.println("[" + this + "]: " + string);
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
            // bplog("_bt=" + _bt + " blockedEvents=" + _bt.blockedEvents);
            if (bt.getBlockedEvents().contains(e)) {
//                bplog(e + " is blocked by " + bt);
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
            bplog("\t" + (c++) + ":" + bt);
        }
    }

    public void printEventLog() {

        System.out.println("\n ***** Printing last " + eventLog.size()
                + " choice points out of " + eventLog.size() + ":");

        for (BEvent ev : eventLog)
            System.out.println(ev);

        System.out.println("***** end event bplog ******");
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
        bplog("********* Starting " + getBThreads().size()
                + " scenarios  **************");
        for (BThread bt : getBThreads()) {
            bt.start();
        }

        bplog("********* " + getBThreads().size()
                + " scenarios started **************");
        loop();
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

    public void loop() {
        if (_bthreads.isEmpty()) {
            bplog("=== ALL DONE!!! ===");
            return;
        }

        BEvent next = _arbiter.nextEvent();
        if (next == null) {
            bplog("no event chosen, waiting for an external event to be fired...");
            next = dequeueInputEvent();
        } else if (next.isOutputEvent()) {
            bplog(next + " is an output event.");
            publishEvent(next);
        }

        triggerEvent(next);
        bthreadCleanup();
        loop();
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
            eventLog.add(lastEvent);
            st = new String("Event #" + eventLog.size() + ": " + getLastEvent());
            bplog(st);
            bplog(">> starting bthread wakeup");
            // Interrupt and notify the be-threads that need to be
            // awaken
            for (BThread bt : _bthreads) {
//                if (bt.getName().startsWith("SquareTaken"))
//                    bplog(bt + " waitlist:" + bt.getWaitedEvents().toString());
                boolean waited = bt.isWaited(lastEvent);
                boolean requested = bt.isRequested(lastEvent);
                if (waited || requested) {
                    bt.resume(lastEvent);
                }
            }
            bplog("<< finished bthread wakeup");
        } else { // lastEvent == null -> deadlock?
            st = new String(
                    "No events chosen. Waiting for external event or stuck in bsync...?");
            bplog(st);
        }
    }

    public BEvent getLastEvent() {
        return eventLog.getLast();
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
        _inputEventQueue.add(e);
    }

    private BEvent dequeueInputEvent() {
        BEvent e = null;
        try {
            e = _inputEventQueue.take();
        } catch (InterruptedException ie) {
            // TODO Auto-generated catch block
            ie.printStackTrace();
        }

        return e;
    }

    protected void publishEvent(BEvent e) {
        _outputEventQueue.add(e);
    }

    public BEvent getOutputEvent() {
        BEvent e = null;
        try {
            e = _outputEventQueue.take();
        } catch (InterruptedException ie) {
            // TODO Auto-generated catch block
            ie.printStackTrace();
        }

        return e;
    }

}
