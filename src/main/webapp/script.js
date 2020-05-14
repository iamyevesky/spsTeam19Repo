var loginArrayURL;
function loadPage(){
    fetch("/login").then(response => response.json()).then(object =>
    {
        console.log(object);
        loginArrayURL = object;
        control(object);
    });
}

function control(object){
    if (!object[0] && !object[1]){
        location.replace("/chat.html");
    }
    else if(!object[0] && object[1])
    {
        location.replace("/createAccount.html");
    }
    else if(object[0])
    {
        //do nothing
    }
}

function login(){
    location.replace(loginArrayURL[2]);
}

function signUp(){
    location.replace(loginArrayURL[3]);
}