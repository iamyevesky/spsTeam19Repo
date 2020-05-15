var jsonObject;
function loadPage(){
    fetch("/getInfoIndex").then(response => response.json()).then(object =>
    {
        console.log(object);
        jsonObject = object;
        createPageHTML();
    });
}

function createPageHTML() {

    var status = jsonObject.status;
    var registered = jsonObject.register;
    var login = jsonObject.login;
    var logout = jsonObject.logout;
    var user = jsonObject.user;
    var contToAccount = jsonObject.continue;

    //if not logged in, button reads "log in"
    //if logged in, button reads "log out"
    // status = true if logged in
    var loginButton = document.getElementById('signIn');
    if (status) {
        loginButton.innerHTML = "Log out"
    } else {
        loginButton.innerHTML = "Log in with Google account"
    }
    controlButtonDisplay();
}

function login() {
    var status = jsonObject.status;
    var logout = jsonObject.logout;
    var login = jsonObject.login;
    //if logged in, status = true
    if (status) {
        location.replace(logout);
    } else {
        location.replace(login);
    }
    
}
function signUp() {

}
function contToAccount() {
    var contToAccount = jsonObject.continue;
    var status = jsonObject.status;
    var registered = jsonObject.register;
    //only when logged in
    if (status && registered) {
        location.replace(contToAccount);
    }
}

// 1. If user is not logged in, -> show only login button
// 2. if user is logged in, but not registered -> show only logout & create Account buttons
// 3. if user is logged i and registered -> show only logout & continue to account buttons

function controlButtonDisplay() {
    var status = jsonObject.status;
    var registered = jsonObject.register;
    var login = jsonObject.login;
    var logout = jsonObject.logout;
    var user = jsonObject.user;
    var contToAccount = jsonObject.continue;
    var signUpButton = document.getElementById("signUp");
    var contAccButton = document.getElementById("contToAccount");

    if (!status) {
        //Case 1.
        signUpButton.style.display = "none";
        contAccButton.style.display = "none";
    } else {
        if (!registered) {
            //Case 2.
            contAccButton.style.display = "none";
        } else {
            //Case 3.
            signUpButton.style.display = "none";
        }
    }

}