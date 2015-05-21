var oldBsync = bsync;

new Object {
    breakEvents: new EventSet(),

    breakupon: function (breaker, f) {
        this.breaker = breaker;
        try {
            waiting.push(breaker);
            f();
            return waiting.pop();
        } catch (thrownEvent) {
            waiting.pop();
            if (thrownEvent != breaker) {
                throw thrownEvent;
            }
            return thrownEvent;
        }
    },

    bsync: function (requested, wait, blocked) {
        var e;
        if (!this.breakEvents.isEmpty()) {
            var eset = new EventSet([wait, this.breakEvents]);
            e = oldBsync(requested, eset, blocked);
            if (e === this.breaker) {
                throw e;
            }
        } else {
            e = oldBsync(requested, wait, blocked);
        }

        return e;
    }
}