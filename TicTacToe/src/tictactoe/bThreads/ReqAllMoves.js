var players = [X, O];
for (var i = 2; i >= 0; i--) {
    for (var j = 2; j >= 0; j--) {
        for (var p = players.length - 1; p >= 0; p--) {
            var player = players[p];
            var move = new player(i, j);
            //            java.lang.System.out.println("registering move to require:" + players[p] + i + j);
            bpjs.registerSimBThread(SimulatorBThread(function () {
                java.lang.System.out.println("inside body function!");
                java.lang.System.out.println("this = " + this);
                while (true) {
                    bsync(move, none, none);
                }
            }));
        };
    };
};