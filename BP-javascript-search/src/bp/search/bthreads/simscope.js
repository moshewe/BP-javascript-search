var oldBsync = bsync;
new Object {
    bsync: function (requested, waited, blocked) {
//        java.lang.System.out.println("in simulation bthread bsync");
//        java.lang.System.out.println("req = " + requested);
//        java.lang.System.out.println("waited = " + waited);
//        java.lang.System.out.println("simStart = " + simStart);
        if (_simMode) {
            return oldBsync(requested, waited, blocked);
        } else {
            var eset = new EventSet([requested, waited, simStart]);
//            java.lang.System.out.println("eset = " + eset);
            var ev = oldBsync(none, eset, none);
            if (ev == simStart) {
//                bplog("setting simMode = true");
                _simMode = true;
                return bsync(requested, waited, blocked);
            }
            return ev;
        }
    }
}