var jsonObject;

function logout() {
    fetch("/getUserInfo").then(response => response.json()).then(object =>
    {
        jsonObject = object;
        createLogoutLink();
    });
}

function createLogoutLink() {
    var logout = jsonObject.logout;
    location.replace(logout);
}