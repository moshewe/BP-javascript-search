package bpbwapi.events;

import bp.BEvent;

/**
 * Created by moshewe on 28/07/2015.
 */
public class SaveGame extends BEvent {
    private final String _saveString;

    public SaveGame(String s) {
        _saveString = s;
    }
}
