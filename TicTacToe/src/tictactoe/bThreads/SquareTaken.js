[0, 1, 2].forEach(function (row) {
    [0, 1, 2].forEach(function (col) {
        var move = new Move(row, col);
        var btname = "SquareTaken(" + row + "," + col + ")";
        var bt = bpjs.registerBThread(btname, function () {
            this.move = move;
            bsync(none, move, none);
            bsync(none, none, move);
        });
    });
});