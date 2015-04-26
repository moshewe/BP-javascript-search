bpjs.registerBThread("UpdateDisplay", function () {
    while (true) {
        var move = bsync(none, moves, none);
        var btt = ttt.gui.buttons[move.row][move.col];
        btt.setText(move.displayString());
    }
});