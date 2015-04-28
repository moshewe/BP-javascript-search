[0, 1, 2].forEach(function (row) {
    [0, 1, 2].forEach(function (col) {
        var move = new Move(row, col);
        var btname = "SquareTaken" + move;
        bpjs.registerBThread(btname, function () {
            bsync(none, move, none);
            bsync(none, none, move);
        });
    });
});