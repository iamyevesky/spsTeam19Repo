function getInfo(){
    fetch("/createAccount").then(response => response.json()).then(object =>
    {
        var infoSection = document.getElementById("infoSection");
        buildList(infoSection, object);
    })
}

function buildList(htmlObject, jsonObject){
    htmlObject.innerHTML = '';
    if (jsonObject.length == 0){
        let element = document.createElement("p");
        element.innerText = "You are not logged in. Please login to create an account";
        htmlObject.appendChild(element);
    }
    else htmlObject.appendChild(createForm(jsonObject));
}

function createForm(jsonObject){
    var form = document.createElement("FORM");
    form.action = "/createAccount";
    form.method = "POST";
    form.innerHTML = '';

    let nameLabel = document.createElement("LABEL");
    let username = document.createElement("INPUT");
    username.setAttribute("type", "text");
    username.setAttribute("value", "Your name here");
    username.setAttribute("autofocus", true);
    username.setAttribute("name", "username");
    nameLabel.htmlFor = "username";
    nameLabel.innerText = "Name: ";


    let listLabel = document.createElement("LABEL");
    let list = document.createElement("SELECT");
    list.setAttribute("name", "collegeList");
    list.setAttribute("multiple", false);
    listLabel.htmlFor = "collegeList";
    listLabel.innerText = "College: ";
    //list.setAttribute("length", 5);
    var i;
    for (i = 0; i < jsonObject.length; i++){
        var option = new Option(jsonObject[i]["name"], jsonObject[i]["key"]);
        list.options.add(option);
    }
    form.appendChild(nameLabel);
    form.appendChild(username);
    form.appendChild(document.createElement("br"));
    form.appendChild(listLabel);
    form.appendChild(list);
    form.appendChild(document.createElement("br"));
    form.appendChild(generateSubmitElement());
    return form;
}

function generateSubmitElement(){
    var element = document.createElement("input");
    element.type = "submit";
    element.value = "Create Account";
    return element;
}