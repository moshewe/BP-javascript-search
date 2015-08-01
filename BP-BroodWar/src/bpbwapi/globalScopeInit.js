importPackage(Packages.bwapi);
importPackage(Packages.bwapi.events.input);

function bw_heartbeat(){
  while(true){
    bsync(onFrameEvent, none, none);
  }
}

bpjs.registerSimBThread("BW-Heartbeat", bw_heartbeat);
