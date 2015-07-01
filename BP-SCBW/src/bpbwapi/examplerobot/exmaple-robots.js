bpjs.registerBThread("OnUnitCreate", function() {
  while (true) {
    var create = bsync(unitCreate, none, none);
    var unit = create.getWrappedEvent();
    bplog("New unit " + unit.getType());
  }
});

bpjs.registerBThread("OnFrame", function() {
  while (true) {
    bsync(frameEvent, none, none);
    bsync(new ListUnitsEvent(), none, none);
  }
});
