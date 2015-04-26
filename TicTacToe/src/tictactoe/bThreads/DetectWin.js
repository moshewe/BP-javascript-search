function genDetectWinBT(first, second, third, winEvent) {
    return function () {
        bsync(none, first, none);
        bsync(none, second, none);
        bsync(none, third, none);
        var eset = new EventSet([moves, Draw]);
        bsync(winEvent, none, eset);
    }
}

var permutations = [[0, 1, 2], [0, 2, 1],
                [1, 0, 2], [1, 2, 0],
                [2, 0, 1], [2, 1, 0]];

[[X, XWin], [O, OWin]].forEach(function (winConf) {
    move = winConf[0];
    winEvent = winConf[1];
    permutations.forEach(function (perm) {
        var btname = winEvent.toString() + "(" +
            first + "," + second "," + third + "," + ")";
        
    });
});