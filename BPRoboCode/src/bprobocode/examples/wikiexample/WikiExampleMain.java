package bprobocode.examples.wikiexample;

import bprobocode.BPRobocodeApplication;
import bprobocode.BPRobocodeEngine;

/**
 * Created by orelmosheweinstock on 6/21/15.
 */
public class WikiExampleMain {

    public static void main(String[] args) {
        BPRobocodeApplication app = new WikiExampleApp();
        WikiExampleRobot rob = new WikiExampleRobot(app);
        BPRobocodeEngine engine = new BPRobocodeEngine(app);
    }
}
