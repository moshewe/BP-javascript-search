package bpbwapi.events;

import bp.BEvent;

/**
 * Created by moshewe on 28/07/2015.
 */
public class SendText extends BEvent {
    private final String _text;

    public SendText(String text) {
        _text = text;
    }
}
