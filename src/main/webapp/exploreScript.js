function getAvailableChats() {
    fetch("/getInfoPost").then(response => response.json()).then(object =>
    {
        checkIfLoggedIn(object);
    });

    fetch("/createChat").then(response => response.json()).then(object =>
        {
            console.log(object);
            appendCollegeChat(object);
            createTitle(object);
        }
    );
}

function createTitle(jsonObj) {
    userUniName = jsonObj.user.college.name;
    var uniTitle = document.getElementById("uniTitle");
    uniTitle.innerText = userUniName;
    uniTitle.style.textAlign = "center";
}

function appendCollegeChat(jsonObj) {
    var chatInitial = document.getElementById("chat-container");
    var chatContainer = document.createElement("div");
    chatContainer.classList.add("row");
    chatInitial.appendChild(chatContainer);
    for (i = 0; i < jsonObj.chats.length; i++){
        var colContainer = document.createElement("div");
        colContainer.classList.add("column");
        var singleCard = document.createElement("div");
        singleCard.classList.add("card");
        colContainer.appendChild(singleCard);

        var cardBody = document.createElement("div");
        cardBody.className = "card-body";
        singleCard.appendChild(cardBody);

        const currChat = jsonObj.chats[i];
        createBody(cardBody, currChat);
        chatContainer.appendChild(colContainer);
    }
}

function createBody(bodyOutline, currPost) {
    const chatKey = currPost.key + "";
    const chatName = currPost.name + "";

    const form = document.createElement("FORM");
    form.action = "/updateChat";
    form.method = "POST";

    //chat title
    const chatNameTitle = document.createElement("h5");
    chatNameTitle.innerText = chatName;
    chatNameTitle.className = "card-title";
    chatNameTitle.style.textAlign = "center";

    //input element, Submit button
    var sDiv = document.createElement("div");
    sDiv.style.textAlign = "center";

    var s = document.createElement("input");
    s.setAttribute('type',"submit");
    s.setAttribute('value',"Join Chat!");
    s.style.color = "white";
    s.style.backgroundColor = "#05728f";
    s.style.marginTop = "25px";
    s.classList.add("btn");
    sDiv.appendChild(s);

    //hidden attributes needed for servlet
    const hidden = document.createElement("INPUT");
    hidden.setAttribute("type", "hidden");
    hidden.setAttribute("name", "chatKey");
    hidden.setAttribute("value", chatKey);
    form.appendChild(hidden);

    form.appendChild(chatNameTitle);
    form.appendChild(sDiv);
    bodyOutline.appendChild(form);
}

function checkIfLoggedIn(jsonObj) {
    //redirects to home page if not logged in
    if (!jsonObj.status) {
        window.location.replace("index.html");
    }
}