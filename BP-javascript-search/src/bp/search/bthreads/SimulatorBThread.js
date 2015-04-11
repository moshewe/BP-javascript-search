SimulatorBThread = new JavaAdapter(BThread, {
	simulationMode: false,
	oldBsync: this.bsync,
	bsync: function(requestedEvents, waitedEvents, blockedEvents) {
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
	            bplog("received simStart, switching to simulation mode!");
	            simulationMode = true;
	        }
	        return bsync(requestedEvents, simWaitedEvents, blockedEvents);
	    }
	}
});