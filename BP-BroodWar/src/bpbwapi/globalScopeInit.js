importPackage(Packages.bwapi);
importPackage(Packages.bpbwapi.events);

function bw_heartbeat(){
  while(true){
    bsync(onFrameEvent, none, none);
  }
}

unitCreateEvents = new EventsOfClass(UnitCreate);

bpjs.registerSimBThread("BW-Heartbeat", bw_heartbeat);
