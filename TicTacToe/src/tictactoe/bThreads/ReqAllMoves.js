var players = [X, O];
for (var i = 2; i >= 0; i--) {
    for (var j = 2; j >= 0; j--) {
        players.forEach(function (player) {
            var move = new Move(i,j);
            var playerMove = new player(i, j);
            var func = function () {
                bsync(playerMove, move, none);
//                bplog("move is " + move.toString());
//                bsync(none, none, move);
            };
            bpjs.registerSimBThread("ReqMove" + playerMove, func);
        });
    };
};
                //                    java.lang.System.out.println("");