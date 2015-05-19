function genDetectWinBT(first, second, third, winEvent) {
    return function () {
        bsync(none, first, none);
        bsync(none, second, none);
        bsync(none, third, none);
        var eset = new EventSet([moves, Draw]);
        bsync(winEvent, none, eset);
    }
}

function genWinBTName(winEvent, first, second, third) {
    return "Detect" + winEvent + "(" + first + "," + second + "," + third + ")";
}

var permutations = [[0, 1, 2], [0, 2, 1],
                [1, 0, 2], [1, 2, 0],
                [2, 0, 1], [2, 1, 0]];

[[X, XWin], [O, OWin]].forEach(function (winConf) {
    var move = winConf[0];
    var winEvent = winConf[1];
    //    java.lang.System.out.println(move);
    //    java.lang.System.out.println(winEvent);
    for (var row = 2; row >= 0; row--) {
        permutations.forEach(function (perm) {
            var first = new move(row, perm[0]);
            var second = new move(row, perm[1]);
            var third = new move(row, perm[2]);
            //                java.lang.System.out.println(restStr);
            var btname = genWinBTName(winEvent, first, second, third);
            var func = genDetectWinBT(first, second, third, winEvent);
            bpjs.registerBThread(btname, func);
        });
    };

    for (var col = 2; col >= 0; col--) {
        permutations.forEach(function (perm) {
            var first = new move(col, perm[0]);
            var second = new move(col, perm[1]);
            var third = new move(col, perm[2]);
            var btname = genWinBTName(winEvent, first, second, third);
            var func = genDetectWinBT(first, second, third, winEvent);
            bpjs.registerBThread(btname, func);
        });
    };

    //    diagonal
    permutations.forEach(function (perm) {
        var first = new move(perm[0], perm[0]);
        var second = new move(perm[1], perm[1]);
        var third = new move(perm[2], perm[2]);
        var btname = genWinBTName(winEvent, first, second, third);
        var func = genDetectWinBT(first, second, third, winEvent);
        bpjs.registerBThread(btname, func);
    });

    //    reverse diagonal
    permutations.forEach(function (perm) {
        var first = new move(2 - perm[0], 2 - perm[0]);
        var second = new move(2 - perm[1], 2 - perm[1]);
        var third = new move(2 - perm[2], 2 - perm[2]);
        var btname = genWinBTName(winEvent, first, second, third);
        var func = genDetectWinBT(first, second, third, winEvent);
        bpjs.registerBThread(btname, func);
    });
});