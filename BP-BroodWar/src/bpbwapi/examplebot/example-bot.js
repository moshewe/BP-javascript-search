importPackage(Packages.bpbwapi.examplerobot);

bpjs.registerBThread("OnUnitCreate", function() {
  while (true) {
    var create = bsync(none, unitCreateEvent, none);
    var unit = create.getWrappedEvent();
    bplog("New unit " + unit.getType());
  }
});

bpjs.registerBThread("OnFrame", function() {
  while (true) {
    bsync(none, aFrameEvent, none);
    bsync(new ListUnitsEvent(), none, none);
  }
});
