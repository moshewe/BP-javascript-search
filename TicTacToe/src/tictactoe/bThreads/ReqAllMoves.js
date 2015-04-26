var players = [X, O];
for (var i = 2; i >= 0; i--) {
    for (var j = 2; j >= 0; j--) {
        players.forEach(function (player) {
            var move = new player(i, j);
            var func = function () {
                java.lang.System.out.println("in reqmove " + move);
                while (true) {
                    bsync(move, none, none);
                }
            };
            bpjs.registerSimBThread("ReqMove" + move, func);
//            bpjs.registerSimBThread("ReqMove" + move, simulatorbt(func));
        });
    };
};