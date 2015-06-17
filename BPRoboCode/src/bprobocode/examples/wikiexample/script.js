bpjs.registerBThread("NavAndGunDirection", function() {
  while (true) {
    bsync(new Ahead(100), none, none);
    bsync(new TurnGunRight(360), none, none);
    bsync(new Back(100), none, none);
    bsync(new TurnGunLeft(360), none, none);
  }
});

var scannedRobotEvents = new EventsOfClass(BPScannedRobot);
bpjs.registerBThread("FireWhenScanned", function() {
  while (true) {
    bsync(none, scannedRobotEvents, none);
    bsync(new Fire(1), none, none);
  }
});
