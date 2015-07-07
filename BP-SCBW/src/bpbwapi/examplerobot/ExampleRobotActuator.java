package bpbwapi.examplerobot;

import bpbwapi.BWActuator;
import bwapi.*;
import bwapi.Text.Size.Enum;

/**
 * Created by orelmosheweinstock on 7/1/15.
 */
public class ExampleRobotActuator extends BWActuator {

    public ExampleRobotActuator(Mirror mirror) {
        super(mirror);
    }

    public void visit(ListUnitsEvent event) {
        bplog("visiting ListUnitsEvent");
        _game.setTextSize(Enum.Large);
        _game.drawTextScreen(10, 10, "Playing as " + _self.getName() + " - " + _self.getRace());

        StringBuilder units = new StringBuilder("My units:\n");

        //iterate through my units
        for (Unit myUnit : _self.getUnits()) {
            units.append(myUnit.getType()).append(" ").append(myUnit.getTilePosition()).append("\n");

            //if there's enough minerals, train an SCV
            if (myUnit.getType() == UnitType.Terran_Command_Center && _self.minerals() >= 50) {
                myUnit.train(UnitType.Terran_SCV);
            }

            //if it's a drone and it's idle, send it to the closest mineral patch
            if (myUnit.getType().isWorker() && myUnit.isIdle()) {
                Unit closestMineral = null;

                //find the closest mineral
                for (Unit neutralUnit : _game.neutral().getUnits()) {
                    if (neutralUnit.getType().isMineralField()) {
                        if (closestMineral == null || myUnit.getDistance(neutralUnit) < myUnit.getDistance(closestMineral)) {
                            closestMineral = neutralUnit;
                        }
                    }
                }

                //if a mineral patch was found, send the drone to gather it
                if (closestMineral != null) {
                    myUnit.gather(closestMineral, false);
                }
            }
        }

        //draw my units on screen
        _game.drawTextScreen(10, 25, units.toString());
    }

}
