package tictactoe.events;

import bp.BEvent;
import bp.eventSets.EventsOfClass;

public class StaticEvents {
	static public BEvent draw = new BEvent("Draw") {
	};

	static public BEvent XWin = new BEvent("XWin") {
	};

	static public BEvent OWin = new BEvent("OWin") {
	};

	static public BEvent gameOver = new BEvent("GameOver") {
	};

	public static final EventsOfClass Moves = new EventsOfClass(Move.class);
	public static final EventsOfClass XEvents = new EventsOfClass(X.class);
	public static final EventsOfClass OEvents = new EventsOfClass(O.class);
}
