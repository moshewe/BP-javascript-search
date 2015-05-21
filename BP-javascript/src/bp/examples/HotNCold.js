bpjs.registerBThread("HotBt", function () {
    bsync(hotEvent, none, none);
    bsync(hotEvent, none, none);
    bsync(hotEvent, none, none);
});

bpjs.registerBThread("ColdBt", function () {
    bsync(coldEvent, none, none);
    bsync(coldEvent, none, none);
    bsync(coldEvent, none, none);
});

bpjs.registerBThread("AlternatorBt", function () {
    for (i = 0; i < 3; i++) {
        bsync(none, coldEvent, hotEvent);
        bsync(none, hotEvent, coldEvent);
    }
    bsync(allDone, none, none);
});