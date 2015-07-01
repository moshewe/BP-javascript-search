function genSquareTakenBT(move) {
    return function () {
        bsync(none, move, none);
        bsync(none, none, move);
    }
}

for (var i = 0; i < 3; i++) {
    for (var j = 0; j < 3; j++) {
        var move = new Move(i, j);
        var btname = "SquareTaken(" + i + "," + j + ")";
        var func = genSquareTakenBT(move);
        var bt = bpjs.registerBThread(btname, func);
    };
};
