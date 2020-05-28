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
    //create row first
    var row1 = document.createElement("div");
    row1.classList.add("form-group", "row");
    var inputDiv = document.createElement("div");
    inputDiv.classList.add("col-sm-9");

    var nameLabel = document.createElement("LABEL");
    var username = document.createElement("INPUT");

    username.setAttribute("type", "text");
    username.setAttribute("placeholder", "Your name here");
    username.setAttribute("autofocus", true);
    username.setAttribute("id", "username");
    username.setAttribute("name", "username");

    nameLabel.htmlFor = "username";
    nameLabel.innerText = "Name: ";

    nameLabel.classList.add('col-sm-3', 'col-form-label');
    username.classList.add("form-control");
    
    inputDiv.appendChild(username);
    row1.appendChild(nameLabel);
    row1.appendChild(inputDiv);

    //college label and input
    var row2 = document.createElement("div");
    row2.classList.add("form-group", "row");
    var selectDiv = document.createElement("div");
    selectDiv.classList.add("col-sm-9");

    var listLabel = document.createElement("label");
    var list = document.createElement("select");
    list.setAttribute("name", "college")
    listLabel.htmlFor = "college";
    listLabel.innerText = "College: ";
    listLabel.classList.add('col-sm-3', 'col-form-label');
    list.classList.add("form-control");
    selectDiv.appendChild(list);
    row2.appendChild(listLabel);
    row2.appendChild(selectDiv);


    var i;
    //add college option from list to select tag
    for(i = 0; i < jsonObject.length; i++) {
        var singleCollegeOption = document.createElement("OPTION");
        singleCollegeOption.innerHTML = jsonObject[i].name;
        singleCollegeOption.setAttribute("value", jsonObject[i].key);
        list.appendChild(singleCollegeOption);
    }

    form.appendChild(row1);
    form.appendChild(document.createElement("br"));
    form.appendChild(row2);
    form.appendChild(document.createElement("br"));
    form.appendChild(generateSubmitElement());
    return form;
}

function generateSubmitElement(){
    var element = document.createElement("input");
    element.type = "submit";
    element.value = "Create Account";
    element.classList.add("btn", "btn-success");
    var ediv = document.createElement("div");
    ediv.classList.add("col", "text-center");
    ediv.appendChild(element);
    return ediv;
}