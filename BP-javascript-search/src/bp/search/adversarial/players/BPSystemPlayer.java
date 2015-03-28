package bp.search.adversarial.players;

import bp.search.adversarial.BPPlayer;

public class BPSystemPlayer extends BPPlayer {

	public static BPSystemPlayer instance = new BPSystemPlayer();

	public static BPSystemPlayer getInstance() {
		return instance;
	}

	protected BPSystemPlayer() {

	}
}
