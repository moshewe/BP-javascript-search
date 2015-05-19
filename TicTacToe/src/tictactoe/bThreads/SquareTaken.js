[0, 1, 2].forEach(function (row) {
    [0, 1, 2].forEach(function (col) {
        var move = new Move(row, col);
        var btname = "SquareTaken(" + row + "," + col + ")";
        var func = function () {
            bplog(" move = " + move);
            bsync(none, move, none);
            bsync(none, none, move);
        };
        var bt = bpjs.registerBThread(btname, func);
        bpjs._squaresTaken.add(bt);
        //        java.lang.System.out.println("added " + btname + " to _squaresTaken");
    });
});