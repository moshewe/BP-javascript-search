var oldBsync = bsync;

new Object {
    blocking: new EventSet(),

    whileBlocking: function (blocked, f) {
        blocking.push(blocked);
        f();
        return blocking.pop();
    },

    bsync: function (requested, wait, blocked) {
        var e;
        if (!this.blocking.isEmpty()) {
            var eset = new EventSet([blocked, this.blocking]);
            e = oldBsync(requested, wait, eset);
        } else {
            e = oldBsync(requested, wait, blocked);
        }

        return e;
    }
}