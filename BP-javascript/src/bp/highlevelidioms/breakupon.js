var _bsync = bsync;

new Object {
  breakEvents: new EventStack(),

  breakupon: function(breaker, f) {
    this.breaker = breaker;
    try {
      breakEvents.push(breaker);
      f();
      return breakEvents.pop();
    } catch (thrownEvent) {
      breakEvents.pop();
      if (thrownEvent != breaker) {
        throw thrownEvent;
      }
      return thrownEvent;
    }
  },

  bsync: function(requested, wait, blocked) {
    var e;
    if (!this.breakEvents.isEmpty()) {
      var eset = new EventSet([wait, this.breakEvents]);
      e = _bsync(requested, eset, blocked);
      if (e === this.breaker) {
        throw e;
      }
    } else {
      e = _bsync(requested, wait, blocked);
    }

    return e;
  }
}
