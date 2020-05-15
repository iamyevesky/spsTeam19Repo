function getInfo(){
    fetch("/createAccount").then(response => response.json()).then(object =>
    {
        console.log(object);
        var infoSection = document.getElementById("infoSection");
        buildList(infoSection, object);
    });
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
    
    //name label and input
    let nameLabel = document.createElement("LABEL");
    let username = document.createElement("INPUT");
    username.setAttribute("type", "text");
    username.setAttribute("placeholder", "Your name here");
    username.setAttribute("autofocus", true);
    username.setAttribute("name", "username");
    nameLabel.htmlFor = "username";
    nameLabel.innerText = "Name: ";

    //college label and input
    let listLabel = document.createElement("label");
    let list = document.createElement("select");
    list.setAttribute("name", "college")

    var i;
    //add college option from list to select tag
    for(i = 0; i < jsonObject.length; i++) {
        var singleCollegeOption = document.createElement("OPTION");
        singleCollegeOption.innerHTML = jsonObject[i].name;
        singleCollegeOption.setAttribute("value", jsonObject[i].key);
        list.appendChild(singleCollegeOption);
    }

    // list.setAttribute("name", "college");
    // list.setAttribute("multiple", false);
    // listLabel.htmlFor = "college";
    // listLabel.innerText = "College: ";
    // //list.setAttribute("length", 5);
    // var i;
    // for (i = 0; i < jsonObject.length; i++){
    //     var option = new Option(jsonObject[i].name, jsonObject[i].key);
    //     list.options.add(option);
    // }
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