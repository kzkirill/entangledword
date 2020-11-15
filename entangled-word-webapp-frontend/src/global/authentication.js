const loginHelper = (function () {
    const storageKey = 'authUser.id';

    return {
        getLoggedIn: function () { return localStorage.getItem(storageKey) },
        setLoggedIn: function (user) { localStorage.setItem(storageKey, user.id) },
        logout: function (newUser) { localStorage.removeItem(storageKey) }
    }
})()

export { loginHelper }