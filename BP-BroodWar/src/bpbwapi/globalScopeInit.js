importPackage(Packages.bwapi);
importPackage(Packages.bwapi.events.input);

function bw_heartbeat(){
  while(true){
    bsync(onFrameEvent, none, none);
  }
}

unitCreateEvents = new EventsOfClass(UnitCreate);

bpjs.registerSimBThread("BW-Heartbeat", bw_heartbeat);
