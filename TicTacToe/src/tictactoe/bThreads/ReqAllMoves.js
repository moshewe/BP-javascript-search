function ReqMove(player, x, y) {
    var move = new player(x, y);
    return SimulatorBThread(function () {
        while (true) {
            bsync(move, none, none);
        }
    });
};

var players = [X, O];
for (var i = 2; i >= 0; i--) {
    for (var j = 2; j >= 0; j--) {
        for (var p = players.length - 1; p >= 0; p--) {
            var move = new ReqMove(players[p], i, j)
                //java.lang.System.out.println("registering move to require:" + i + j);
            bpjs.registerSimBThread(move);
        };
    };
};