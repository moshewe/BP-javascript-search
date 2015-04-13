/*
 * Copyright (C) 2014 admin.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;

/**
 * A simple {@link Publisher} {@link NodeMain}.
 */
public class Talker extends AbstractNodeMain {

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("rosjava/talker");
    }

    @Override
    public void onStart(final ConnectedNode connectedNode) {
        final Publisher<std_msgs.String> publisher =
                connectedNode.newPublisher("chatter", std_msgs.String._TYPE);
        // This CancellableLoop will be canceled automatically when the node shuts
        // down.
        connectedNode.executeCancellableLoop(new CancellableLoop() {
            private int sequenceNumber;

            @Override
            protected void setup() {
                sequenceNumber = 0;
            }

            @Override
            protected void loop() throws InterruptedException {

                // Creates and enters a Context. The Context stores information
                // about the execution environment of a script.
                Context cx = Context.enter();
                try {
                    // Initialize the standard objects (Object, Function, etc.)
                    // This must be done before scripts can be executed. Returns
                    // a scope object that we use in later calls.
                    Scriptable scope = cx.initStandardObjects();
                    scope.put("publisher", scope, publisher);
                    scope.put("sequenceNumber", scope, sequenceNumber);

                    // Collect the arguments into a single string.
                    String s = "str = publisher.newMessage();" +
                               "str.setData('Hello JS ' + sequenceNumber);"+
                               "publisher.publish(str);"+
                               "sequenceNumber;";

                    // Now evaluate the string we've colected.
                    Object result = cx.evaluateString(scope, s, "str.js", 1, null);

                    // Convert the result to a string and print it.
                    System.err.println(Context.toString(result));

                } finally {
                    // Exit from the context.
                    Context.exit();
                }


                //std_msgs.String str = publisher.newMessage();
                //str.setData("Hello World " + sequenceNumber);
                //publisher.publish(str);
                sequenceNumber++;
                Thread.sleep(1000);
            }
        });
    }
}
