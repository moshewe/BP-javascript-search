package bp;

import bp.eventSets.EventSetInterface;
import bp.eventSets.RequestableInterface;
import bp.tasks.*;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.*;

import static bp.BProgramControls.debugMode;

public abstract class BPApplication implements Cloneable, Serializable {

    /**
     * A collection containing all the be-threads in the system. A be-thread
     * adds itself to the list either - in its constructor and removes itself
     * when its run() function finishes - or a Java thread adds itself and
     * removes itself explicitly
     */
    public transient Collection<BThread> _bthreads;
    /**
     * Stores the strings of the events that occurred in this run
     */
    public transient Deque<BEvent> eventLog = new LinkedList<>();
    /**
     * Program _name is set to be the simple class _name by default.
     */
    protected transient String _name = this.getClass().getSimpleName();
    private Arbiter _arbiter;
    private volatile BlockingQueue<BEvent> _inputEventQueue;
    private volatile BlockingQueue<BEvent> _outputEventQueue;
    protected ExecutorService _executor;
    private boolean _started = false;

    public void setArbiter(Arbiter arbiter) {
        this._arbiter = arbiter;
        arbiter.setProgram(this);
    }

    public BPApplication() {
        _bthreads = new ArrayList<BThread>();
        _arbiter = new Arbiter();
        _arbiter.setProgram(this);
        _inputEventQueue = new LinkedBlockingQueue<>();
        _outputEventQueue = new LinkedBlockingQueue<>();
        _executor = new ForkJoinPool();
    }

    public void bplog(String string) {
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
     * @return the given bprogram _name
     */
    public String getName() {
        return _name;
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
     * Sets the error that occurred during the run, to make BPApplication terminate
     * at the next bSync and print the error.
     *
     * @param error An Object of the error occurred during the run - better have
     *              an informative toString().
     */
    public static void setError(Object error) {
    }

    public void setName(String name) {
        this._name = name;
    }

    @Override
    public String toString() {
        return _name;
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

    public void bthreadCleanup() {
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
    public void triggerEvent(BEvent lastEvent) {
        String st;
        if (lastEvent != null) {
            eventLog.add(lastEvent);
            st = new String("Event #" + eventLog.size() + ": " + getLastEvent());
            bplog(st);
            bplog(">> starting bthread wakeup");
            // Interrupt and notify the be-threads that need to be
            // awaken
            Collection<ResumeBThread> resumes
                    = new LinkedList<>();
            for (BThread bt : _bthreads) {
                resumes.add(new ResumeBThread(bt, lastEvent));
            }
            List<Future<Void>> futures = null;
            try {
                futures = _executor.invokeAll(resumes);
                for (Future future : futures) {
                    future.get();
                }
            } catch (InterruptedException e) {
                bplog("INVOKING BTHREAD RESUMES INTERRUPTED");
                e.printStackTrace();
            } catch (ExecutionException e) {
                bplog("EXCEPTION WHILE EXECUTING BTHREAD");
                e.printStackTrace();
            }
            bplog("<< finished bthread wakeup");
        } else { // lastEvent == null -> deadlock?
            st = new String(
                    "No events chosen. Waiting for external event or stuck in bsync...?");
            bplog(st);
        }
    }

    public BEvent getLastEvent() {
        return eventLog.peekLast();
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

    /**
     * a method that sends events as input for the application
     *
     * @param e
     */
    public void fire(BEvent e) {
//        bplog(e + " fired!");
        _inputEventQueue.add(e);
    }

    public BEvent getInputEvent() {
        BEvent e = null;
        try {
            e = _inputEventQueue.take();
            bplog("dequeued input event " + e);
        } catch (InterruptedException ie) {
            // TODO Auto-generated catch block
            ie.printStackTrace();
        }

        return e;
    }

    public void emit(BEvent e) {
        bplog("emitted " + e);
        _outputEventQueue.add(e);
    }

    public BEvent readOutputEvent() throws InterruptedException {
        BEvent take = _outputEventQueue.take();
        return take;
    }

    public void start() throws InterruptedException {
        bplog("********* Starting " + _bthreads.size()
                + " scenarios  **************");

        Collection<StartBThread> startBtTasks =
                new ArrayList<>(_bthreads.size());
        for (BThread bt : _bthreads) {
            startBtTasks.add(new StartBThread(bt));
        }

        _executor.invokeAll(startBtTasks);
        bplog("********* " + _bthreads.size()
                + " scenarios started **************");
        _started = true;
        _executor.execute(new EventLoopTask(this, _arbiter));
    }

    public void registerBThread(BThread bt) {
        add(bt);
        if (_started) {
            _executor.execute(new StartBThread(bt));
        }
    }

}
