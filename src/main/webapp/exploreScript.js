var chatsUserJoined;

function getAvailableChats() {
    checkLogin();
}

function checkLogin() {
    fetch("/getInfoPost").then(response => response.json()).then(object =>
    {
        if (!object.status) {
        window.location.replace("index.html");
        }
        instantiateVar();
    });
}

function instantiateVar() {
    fetch("/sendMessage").then(response => response.json()).then(object =>
    {
        console.log(object);
        chatsUserJoined = object.chats;
        loadChatsToJoin();
    });
}

function loadChatsToJoin() {
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
    console.log(jsonObj.chats);

    for (i = 0; i < jsonObj.chats.length; i++){
        const currChat = jsonObj.chats[i];
        console.log(currChat);
        console.log(isUserAlreadyInChat(currChat));
        
        var colContainer = document.createElement("div");
        colContainer.classList.add("column");
        var singleCard = document.createElement("div");
        singleCard.classList.add("card");
        colContainer.appendChild(singleCard);

        var cardBody = document.createElement("div");
        cardBody.className = "card-body";
        singleCard.appendChild(cardBody);

        //
        createBody(cardBody, currChat, isUserAlreadyInChat(currChat));
        chatContainer.appendChild(colContainer);
       
        
    }
}

function createBody(bodyOutline, currPost, inChatAlready) {
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
    if (inChatAlready) {
        s.setAttribute('value',"Leave Chat");
        s.style.backgroundColor = "#f05348";
    } else {
        s.setAttribute('value',"Join Chat!");
        s.style.backgroundColor = "#05728f";
    }
    s.style.color = "white";
    s.style.marginTop = "25px";
    s.classList.add("btn");
    sDiv.appendChild(s);

    //hidden attributes needed for servlet

    //chat key
    const hidden = document.createElement("INPUT");
    hidden.setAttribute("type", "hidden");
    hidden.setAttribute("name", "chatKey");
    hidden.setAttribute("value", chatKey);
    form.appendChild(hidden);

    //bool if user wants to leave or join chat
    const boolKind = document.createElement("INPUT");
    boolKind.setAttribute("type", "hidden");
    boolKind.setAttribute("name", "userChoice");
    boolKind.setAttribute("value", inChatAlready);
    form.appendChild(boolKind);
    


    form.appendChild(chatNameTitle);
    form.appendChild(sDiv);
    bodyOutline.appendChild(form);
}

// returns true if user is already in the chat, false if new chat to user
function isUserAlreadyInChat(chatObj) {
    console.log(chatsUserJoined);
    for (j = 0; j < chatsUserJoined.length; j++) {
        if (chatObj.key === chatsUserJoined[j].key) {
            console.log(chatsUserJoined[j].key);
            console.log(chatObj.key);
            return true;
        }
    }
    return false;
}