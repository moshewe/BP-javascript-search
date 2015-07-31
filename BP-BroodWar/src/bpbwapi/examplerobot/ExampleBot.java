package bpbwapi.examplerobot;

import bpbwapi.BPBroodWarBot;
import bpbwapi.BWActuatorFactory;

/**
 * Created by moshewe on 27/07/2015.
 */
public class ExampleBot extends BPBroodWarBot {

    public ExampleBot(ExampleBotApp app) {
        super(app);
        setBWListener(new ExampleListener(app, this));
    }

    public static void main(String[] args) throws InterruptedException {
        ExampleBotApp app = new ExampleBotApp();
        final ExampleBot ex = new ExampleBot(app);
        ex.start();
    }

    /**
     * a mechanism that takes events automaticly from output queue
     * operates the relevant actuator (the one that executes the action in the real world)
     * by event Type
     */
    @Override
    public void setupActuationService() {
        super.setupActuationService();
        BWActuatorFactory<ListUnitsActuator> factory =
                new BWActuatorFactory<>(ListUnitsActuator.class,
                        _game);
        _actService.register(ListUnitsEvent.class, factory);// event type - "list units"
    }
}
