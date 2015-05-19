//[0, 1, 2].forEach(function (row) {
//    [0, 1, 2].forEach(function (col) {
//        var move = new Move(row, col);
//        var btname = "SquareTaken(" + row + "," + col + ")";
//        var func = function () {
//            bplog(" move = " + move);
//            bsync(none, move, none);
//            bsync(none, none, move);
//        };
//        var bt = bpjs.registerBThread(btname, func);
//        bpjs._squaresTaken.add(bt);
//        //        java.lang.System.out.println("added " + btname + " to _squaresTaken");
//    });
//});

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
        bpjs._squaresTaken.add(bt);
    };
};