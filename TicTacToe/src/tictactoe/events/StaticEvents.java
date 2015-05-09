package tictactoe.events;

import bp.BEvent;
import bp.eventSets.EventsOfClass;

public class StaticEvents {
    static public BEvent draw = new BEvent("Draw", true);
    static public BEvent XWin = new BEvent("XWin", true);
    static public BEvent OWin = new BEvent("OWin", true);
    static public BEvent gameOver = new BEvent("GameOver", true);

    public static final EventsOfClass Moves = new EventsOfClass(Move.class);
    public static final EventsOfClass XEvents = new EventsOfClass(X.class);
    public static final EventsOfClass OEvents = new EventsOfClass(O.class);
}
