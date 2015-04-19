function SimulatorBThread(bodyFunc) {
    return function () {
        simulationMode = false;
        oldBsync = this.bsync;
        bsync = function (requestedEvents, waitedEvents, blockedEvents) {
            java.lang.System.out.println("in SimulatorBThread bsync");
            var simWaitedEvents = new EventSet(waitedEvents, new SimStartEvent());
            if (simulationMode) {
                var ev = oldBsync(requestedEvents, waitedEvents, blockedEvents);
                if (ev instanceof SimEndEvent) {
                    simulationMode = false;
                }
                return bsync(requestedEvents, waitedEvents, blockedEvents);
            } else {
                var ev = oldBsync(none, simWaitedEvents, blockedEvents);
                if (ev instanceof SimStartEvent) {
                    java.lang.System.out.println("received simStart, switching to simulation mode!");
                    simulationMode = true;
                }
                return bsync(requestedEvents, simWaitedEvents, blockedEvents);
            }
        };
        java.lang.System.out.println("started simulation bthread!");
        java.lang.System.out.println("simulationMode = " + simulationMode);
        java.lang.System.out.println("oldBsync = " + oldBsync);
        java.lang.System.out.println("bsync = " + bsync);
        boundedBodyFunc = bodyFunc.bind(this);
        java.lang.System.out.println("body function is bound!");
        boundedBodyFunc.call();
    }
};