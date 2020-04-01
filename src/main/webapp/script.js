//login form function
function getLoginForm(){
    fetch("/login").then(response => response.text()).then(textHTML =>
    {
        var loginSection = document.getElementById("loginSection");
        loginSection.innerHTML = textHTML;
    });
}
//name form function
function getNameForm(){
    fetch("/name").then(response => response.text()).then(textHTML =>
    {
        var loginSection = document.getElementById("loginSection");
        loginSection.innerHTML = textHTML;
    });
}
