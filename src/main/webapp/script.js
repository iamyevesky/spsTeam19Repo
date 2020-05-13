function loadPage(){
    fetch("/login").then(response => response.json()).then(object =>
    {
        console.log(object);
        buildList();
    });
}

function buildList(){
    var loginButton = document.getElementById("signIn");
    var signUpButton =  document.getElementById("signUp");
    loginButton.onclick = function(){login()};
    signUpButton.onclick = function(){signUp()};
}

function login(){
    windows.location.replace(loginArrayURL[0]);
}

function signUp(){
    windows.location.replace(loginArray[1]);
}