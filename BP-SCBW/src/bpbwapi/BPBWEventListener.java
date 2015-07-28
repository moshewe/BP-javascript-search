package bpbwapi;

import bp.BProgramControls;
import bpbwapi.events.*;
import bwapi.DefaultBWListener;
import bwapi.Player;
import bwapi.Position;
import bwapi.Unit;
import bwta.BWTA;

/**
 * Created by moshewe on 30/06/2015.
 */
public class BPBWEventListener extends DefaultBWListener {

    private final BPBWRobot _robot;
    protected BWJavascriptApplication _app;

    public BPBWEventListener(BPBWRobot robot, BWJavascriptApplication app) {
        this._app = app;
        _robot = robot;
    }

    @Override
    public void onStart() {
        //Use BWTA to analyze map
        bplog("Analyzing map...");
        BWTA.readMap();
        BWTA.analyze();
        bplog("Map data ready");
        _robot.setGameFromMirror();
        _robot.setPlayerFromGame();
        _robot.setupActuationService();
        _robot.startActuation();
        _app.fire(new StartEvent());
    }

    @Override
    public void onFrame() {
        _app.fire(new FrameEvent());
    }

    @Override
    public void onUnitCreate(Unit unit) {
        _app.fire(new UnitCreate(unit));
    }

    @Override
    public void onEnd(boolean b) {
        _app.fire(new OnEndEvent(b));
    }

    @Override
    public void onSendText(String s) {
        _app.fire(new SendText(s));
    }

    @Override
    public void onReceiveText(Player player, String s) {
        _app.fire(new ReceiveText(player,s));
    }

    @Override
    public void onPlayerLeft(Player player) {
        _app.fire(new PlayerLeft(player));
    }

    @Override
    public void onNukeDetect(Position position) {
        _app.fire(new NukeDetect(position));
    }

    @Override
    public void onUnitDiscover(Unit unit) {
        _app.fire(new UnitDiscover(unit));
    }

    @Override
    public void onUnitEvade(Unit unit) {
        _app.fire(new UnitEvade(unit));
    }

    @Override
    public void onUnitShow(Unit unit) {
        _app.fire(new UnitShow(unit));
    }

    @Override
    public void onUnitHide(Unit unit) {
        _app.fire(new UnitHide(unit));
    }

    @Override
    public void onUnitDestroy(Unit unit) {
        _app.fire(new UnitDestroy(unit));
    }

    @Override
    public void onUnitMorph(Unit unit) {
        _app.fire(new UnitMorph(unit));
    }

    @Override
    public void onUnitRenegade(Unit unit) {
        _app.fire(new UnitRenegade(unit));
    }

    @Override
    public void onSaveGame(String s) {
        _app.fire(new SaveGame(s));
    }

    @Override
    public void onUnitComplete(Unit unit) {
        _app.fire(new UnitComplete(unit));
    }

    @Override
    public void onPlayerDropped(Player player) {
        _app.fire(new PlayerDropped(player));
    }

    private void bplog(String s) {
        if (BProgramControls.debugMode)
            System.out.println(getClass().getSimpleName() + ": " + s);
    }

}
