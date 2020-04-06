//EXAMPLE CHAT HISTORY DATA
    var s1 = [
        "hello",
        "whats up",
        "hello",
        "whats up",
        "hello",
        "whats up",
        "hello",
        "whats up",
        "hello",
        "whats up",
    ];
    var s2 = [
        "hows it going",
        "pretty good",
        "testing",
        "testing1",
        "hello1",
        "hello1",
        "whats up",
        "hello",
        "whats up",
    ];

    //EXAMPLE DATA
    var exampleChat1 = {
        Title: "GT CS 1301",
        LastChat: "When is the final?",
        chatHistory: s1,
    };
    var exampleChat2 = {
        Title: "GT CS 1331",
        LastChat: "How are office hours going to be conducted",
        chatHistory: s2,
    };
    var exampleChat3 = {
        Title: "GT LMC 2500",
        LastChat: "Where is the zoom link?",
        chatHistory: s1,
    };
    var exampleChat4 = {
        Title: "GT Computer Science Department",
        LastChat: "New changes to grade modes and deadlines",
        chatHistory: s2,
    };
    var exampleBackend = [exampleChat1, exampleChat2, exampleChat3, exampleChat4];




function loadChats() {
    const chatContainer = document.getElementById('chat-container');

    for (i = 0; i < exampleBackend.length; i++) {
        var outerContainer1 = document.createElement("div");
        outerContainer1.className = "chat_list";

        //update active chats when clicked on
        outerContainer1.addEventListener("click", function() {
            //deactivate current active chat class
            var activeChat = document.getElementsByClassName("chat_list active_chat");
            if (activeChat[0] != null) {
                activeChat[0].className = "chat_list";
            }
            //update chat that was clicked on to become active
            this.className = "chat_list active_chat"; 
            clearChatHistory();
            var chatName = this.firstChild.firstChild.firstChild.innerText;
            var arrayOfChats = findChatBackend(chatName);
            buildChatHistory(arrayOfChats);
        });

        //build bootstrap outline
        var outerContainer2 = document.createElement("div");
        outerContainer2.className = "chat_people";
        var innerContainer = document.createElement("div")
        innerContainer.className = "chat_ib";


        outerContainer1.appendChild(outerContainer2);
        outerContainer2.appendChild(innerContainer);

        buildSingleChat(innerContainer, exampleBackend[i]);

        chatContainer.appendChild(outerContainer1);
    }
}

function buildSingleChat(innContainer, chatData) {
    var title = document.createElement("h5");
    title.innerText = chatData.Title;
    var chatLast = document.createElement("p");
    chatLast.innerText = chatData.LastChat;

    innContainer.appendChild(title);
    innContainer.appendChild(chatLast);
}

function clearChatHistory() {
    var node = document.getElementById("msg-container");
    node.innerHTML = "";
}

function buildChatHistory(pastMessageData) {
    var outline = document.getElementById("msg-container");
    for (const msg of pastMessageData) {
        //create bootstrap outline
        var msgDiv = document.createElement("div");
        msgDiv.className = "outgoing_msg";
        var innerTextDiv = document.createElement("div");
        innerTextDiv.className = "sent_msg";

        //populate text with past message
        var text = document.createElement("p");
        text.innerHTML = msg;

        //append text to bootstrap outline
        innerTextDiv.appendChild(text);
        msgDiv.appendChild(innerTextDiv);
        outline.appendChild(msgDiv);
    }
}

function findChatBackend(chatName) {
    for (i = 0; i < exampleBackend.length; i++) {
        var backendChatName = exampleBackend[i].Title;
        if (chatName == backendChatName) {
            return exampleBackend[i].chatHistory;
        }
    }
    return null;
}