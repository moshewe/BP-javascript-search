var turns = bpjs.registerBThread("EnforceTurns", function () {
    while (true) {
        bsync(none, xevents, oevents);
        bsync(none, oevents, xevents);
    }
});

ttt._turns = turns