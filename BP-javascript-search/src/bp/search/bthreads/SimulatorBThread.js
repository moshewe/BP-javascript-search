function SimulatorBThread(bodyFunc) {
    return function () {
        var simulationMode = false;
        var oldBsync = this.bsync;
        java.lang.System.out.println("started simulation bthread!");
        java.lang.System.out.println("this = " + this);
        java.lang.System.out.println("simulationMode = " + simulationMode);
        java.lang.System.out.println("oldBsync = " + oldBsync);
//        java.lang.System.out.println("this.bsync = " + this.bsync);
        //            java.lang.System.out.println("bodyFunc = " + bodyFunc);
        //            java.lang.System.out.println("boundedBodyFunc = " + boundedBodyFunc);
        //            var boundedBodyFunc = bodyFunc.bind(this);
        //            boundedBodyFunc.call();
        with({
            bsync: function (requestedEvents, waitedEvents, blockedEvents) {
                java.lang.System.out.println("in SimulatorBThread bsync");
                if (simulationMode) {
                    var ev = oldBsync(requestedEvents, waitedEvents, blockedEvents);
                    if (ev instanceof SimEndEvent) {
                        simulationMode = false;
                    }
                    return bsync(requestedEvents, waitedEvents, blockedEvents);
                } else {
                    var simWaitedEvents = new EventSet(waitedEvents, new SimStartEvent());
                    var ev = oldBsync(none, simWaitedEvents, blockedEvents);
                    if (ev instanceof SimStartEvent) {
                        java.lang.System.out.println("received simStart, switching to simulation mode!");
                        simulationMode = true;
                    }
                    return bsync(requestedEvents, simWaitedEvents, blockedEvents);
                }
            },
        }) {
            java.lang.System.out.println("bsync = " + bsync);
            bodyFunc.bind(this);
        }
    };
}