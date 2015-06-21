package bprobocode;

import bprobocode.events.input.battleEvents.*;
import robocode.control.events.*;

/**
 * Created by orelmosheweinstock on 6/17/15.
 */
public class BPBattleAdaptor extends BattleAdaptor {
    private final BPRobocodeApplication _app;

    public BPBattleAdaptor(BPRobocodeApplication app) {
        _app = app;
    }

    @Override
    public void onBattleStarted(BattleStartedEvent event) {
        _app.fire(new OnBattleStarted(event));
    }

    @Override
    public void onBattleFinished(BattleFinishedEvent event) {
        _app.fire(new OnBattleFinished(event));
    }

    @Override
    public void onBattleCompleted(BattleCompletedEvent event) {
        _app.fire(new OnBattleCompleted(event));
    }

    @Override
    public void onBattlePaused(BattlePausedEvent event) {
        _app.fire(new OnBattlePaused(event));
    }

    @Override
    public void onBattleResumed(BattleResumedEvent event) {
        _app.fire(new OnBattleResumed(event));
    }

    @Override
    public void onRoundStarted(RoundStartedEvent event) {
        _app.fire(new OnRoundStarted(event));
    }

    @Override
    public void onRoundEnded(RoundEndedEvent event) {
        _app.fire(new OnRoundEnded(event));
    }

    @Override
    public void onTurnStarted(TurnStartedEvent event) {
        _app.fire(new OnTurnStarted(event));
    }

    @Override
    public void onTurnEnded(TurnEndedEvent event) {
        _app.fire(new OnTurnEnded(event));
    }

    @Override
    public void onBattleMessage(BattleMessageEvent event) {
        _app.fire(new OnBattleMessage(event));
    }

    @Override
    public void onBattleError(BattleErrorEvent event) {
        _app.fire(new OnBattleError(event));
    }
}
