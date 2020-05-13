

function loadPage(){
    fetch("/login").then(response => response.json()).then(object =>
    {
        var loginButton = document.getElementById("signIn");
        loginButton.addEventListener("click", object[0]);
        var signUpButton =  document.getElementById("signUp");
        signUpButton.addEventListener("click", object[1]);
    });
}