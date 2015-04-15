function SimulatorBThread(bodyFunc) {
    var wrapper = {
        simulationMode: false,
        oldBsync: this.bsync,
        bsync: function (requestedEvents, waitedEvents, blockedEvents) {
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
        }
    }

    return function () {
        with(wrapper) {
            bodyFunc.call();
        }
    }
};