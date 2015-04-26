bpjs.registerBThread("EnforceTurns", function () {
    while (true) {
        bsync(none, xevents, oevents);
        bsync(none, oevents, xevents);
    }
});