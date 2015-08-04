importPackage(Packages.bwapi);
importPackage(Packages.bpbwapi.events);

function bw_heartbeat(){
  while(true){
    bsync(onFrameEvent, none, none);
  }
}

unitCreateEvent = new EventsOfClass(UnitCreate);
onFrameEvent = new EventsOfClass(FrameEvent);