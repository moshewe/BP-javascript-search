package bpbwapi.events;

import bp.BEvent;
import bwapi.Player;

/**
 * Created by moshewe on 28/07/2015.
 */
public class ReceiveText extends BEvent {
    private final Player _player;
    private final String _text;

    public ReceiveText(Player player, String text) {
        _player = player;
        _text = text;
    }
}
