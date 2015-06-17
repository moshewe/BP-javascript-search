var _bsync = bsync;
new Object {
  simMode: false,
  bsync: function(requested, waited, blocked) {
    if (this.simMode) {
      return _bsync(requested, waited, blocked);
    } else {
      var eset = new EventSet([requested, waited, simStart]);
      var ev = _bsync(none, eset, blocked);
      if (ev == simStart) {
        /*//                bplog("setting simMode = true");*/
        this.simMode = true;
        return bsync(requested, waited, blocked);
      }
      return ev;
    }
  }
}
