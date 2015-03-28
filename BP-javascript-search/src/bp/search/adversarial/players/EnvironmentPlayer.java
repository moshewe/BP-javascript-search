package bp.search.adversarial.players;

import bp.search.adversarial.BPPlayer;

public class EnvironmentPlayer extends BPPlayer {
	
	public static EnvironmentPlayer instance = new EnvironmentPlayer();

	protected EnvironmentPlayer(){
		
	}

	public static EnvironmentPlayer getInstance() {
		return instance;
	}
}
