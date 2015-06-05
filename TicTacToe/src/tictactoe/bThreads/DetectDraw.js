bpjs.registerBThread("DetectDraw", function() {
  var i;
  for (i = 1; i < 10; i++) {
    bsync(none, moves, none);
    bplog("counted " + i + " moves...")
  }

  bsync(draw, none, moves);
});
