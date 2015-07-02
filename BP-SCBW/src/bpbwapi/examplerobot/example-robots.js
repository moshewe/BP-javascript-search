bpjs.registerBThread("OnUnitCreate", function() {
  while (true) {
    var create = bsync(unitCreateEvent, none, none);
    var unit = create.getWrappedEvent();
    bplog("New unit " + unit.getType());
  }
});

bpjs.registerBThread("OnFrame", function() {
  while (true) {
    bsync(onFrameEvent, none, none);
    bsync(new ListUnitsEvent(), none, none);
  }
});
