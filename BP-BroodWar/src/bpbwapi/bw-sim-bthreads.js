java.lang.System.out.println("Registering BroodWar simulation bthreads...");
function bw_heartbeat() {
  while (true) {
    bsync(aFrameEvent, none, none);
  }
}

bpjs.registerSimBThread("BW-Heartbeat", bw_heartbeat);
java.lang.System.out.println("Done registering BroodWar simulation bthreads!");
