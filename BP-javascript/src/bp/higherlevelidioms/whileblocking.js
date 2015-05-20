var oldBsync = bsync;

new Object {
    blocking: [],

    whileBlocking: function (blocked, f) {
        blocking.push(blocked);
        f();
        return blocking.pop();
    },

    bsync: function (requested, wait, blocked) {
        return oldBsync(requested, wait, blocked.concat(blocking));
    }
}