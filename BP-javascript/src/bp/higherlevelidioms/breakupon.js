var oldBsync = bsync;

new Object {
    waiting: [],

    breakupon: function (breaker, f) {
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
        var e = oldBsync(requested, wait.concat(breaker), blocked);
        if (e === breaker) {
            throw e;
        }

        return e;
    }
}