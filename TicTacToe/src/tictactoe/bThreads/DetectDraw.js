bpjs.registerBThread("DetectDraw", function() {
  var loopfunc = function(index) {
    bsync(none, moves, none);
    bplog("counted " + index + " moves...")
  };

  bploop(1, 9, 1, loopfunc);
  bplog("reached a draw!")
  bsync(draw, none, moves);
});
