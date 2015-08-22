importPackage(Packages.bpbwapi.examplebot);

bpjs.registerBThread("OnUnitCreate", function() {
  while (true) {
    var create = bsync(none, unitCreateEvent, none);
    bplog("Got a new unit!");
    var unit = create.getWrappedObject();
    bplog("New unit " + unit.getType());
  }
});

bpjs.registerBThread("OnFrame", function() {
  while (true) {
    bsync(none, aFrameEvent, none);
    bplog("received OnFrame event!");
    bsync(new ListUnitsEvent(), none, none);
  }
});
