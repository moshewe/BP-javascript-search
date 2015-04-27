bpjs.registerBThread("HotBt", function () {
    java.lang.System.out.println("hotbt started!");
    bsync(hotEvent, none, none);
    bsync(hotEvent, none, none);
});

bpjs.registerBThread("ColdBt", function () {
    java.lang.System.out.println("coldbt started!");
    bsync(coldEvent, none, none);
    bsync(coldEvent, none, none);
    bsync(coldEvent, none, none);
});

bpjs.registerBThread("AlternatorBt", function () {
    java.lang.System.out.println("alternator started!");
    for (i = 0; i < 3; i++) {
        bsync(none, coldEvent, hotEvent);
        bsync(none, hotEvent, coldEvent);
    }
    java.lang.System.out.println("alternator done!");
});