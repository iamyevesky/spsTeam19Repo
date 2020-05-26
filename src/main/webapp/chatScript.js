    var messageInfo;
function loadChatsNew() {
    fetch("/sendMessage").then(response => response.json()).then(object =>
    {
        console.log(object);
        messageInfo = object;
        appendChatNameToSidebar(object);
    }
    );
}

function appendChatNameToSidebar(jsonObj) {
    console.log(jsonObj.chats);
    const chatContainer = document.getElementById('chat-container');
    for(index in jsonObj.chats)
    {
        console.log(index);
        var outerContainer1 = document.createElement("div");
        outerContainer1.className = "chat_list";

        //update the active chat when clicked on and load messages
        outerContainer1.addEventListener("click", function() {
            //deactivate current active chat class
            var activeChat = document.getElementsByClassName("chat_list active_chat");
            if (activeChat[0] != null) {
                activeChat[0].className = "chat_list";
            }
            //update chat that was clicked on to become active
            this.className = "chat_list active_chat"; 
            var htmlColl = activeChat[0];
            var chatName = htmlColl.firstChild.firstChild.firstChild.innerText;
            console.log(getActiveChatIndex());
        });

        var chatName = jsonObj.chats[index].name;
        var chatValue = index;

        //build bootstrap outline
        var outerContainer2 = document.createElement("div");
        outerContainer2.className = "chat_people";
        var innerContainer = document.createElement("div")
        innerContainer.className = "chat_ib";


        outerContainer1.appendChild(outerContainer2);
        outerContainer2.appendChild(innerContainer);

        // buildSingleChat(innerContainer, exampleBackend[i]);
        var title = document.createElement("h5");
        title.innerText = chatName;
        innerContainer.appendChild(title);

        chatContainer.appendChild(outerContainer1);
        
    }
    getChats();
}
function getActiveChatIndex() {
    //get name of currently clicked chat by class name "active_chat"
    var activeChat = document.getElementsByClassName("chat_list active_chat");
    var htmlColl = activeChat[0];
    var chatName = htmlColl.firstChild.firstChild.firstChild.innerText;
    console.log(chatName);

    //search backend and find index based on name
    var chatsArr = messageInfo.chats;
    console.log(chatsArr);
    for (i = 0; i < chatsArr.length; i++) {
        if (chatName === chatsArr[i].name) {
            return i;
        }
    }

}
function getChats(){
    var ajaxRequest = new XMLHttpRequest();
    ajaxRequest.onreadystatechange = function()
    {
        if(ajaxRequest.readyState == 4)
        {
            //the request is completed, now check its status
            if(ajaxRequest.status == 200)
            {
                var jsonResponse = JSON.parse(ajaxRequest.responseText);
                console.log(jsonResponse);
                // var divElement = document.getElementById("messages");
                // divElement.innerHTML = '';
                const parsed = getActiveChatIndex();
                if (isNaN(parsed)) {return}
                for (index in jsonResponse.chats[parsed].messages)
                {

                    var jsonMessage =  jsonResponse.chats[parsed].messages[index];
                    console.log(jsonMessage);
                    // var messageEntity = document.createElement("div");
                    // messageEntity.innerHTML = '';
                    // var name = document.createElement("p");
                    // name.innerText = jsonMessage.sender.username + "";
                    // messageEntity.appendChild(name);
                    // var messageText = document.createElement("p");
                    // messageText.innerText = jsonMessage.message + "";
                    // messageEntity.appendChild(messageText);
                    // divElement.appendChild(messageEntity);
                }
            }
            else
            {
                console.log("Status error: " + ajaxRequest.status);
            }
	    }
        else
        {
            console.log("Ignored readyState: " + ajaxRequest.readyState);
	    }
	}
    ajaxRequest.open('GET', '/sendMessage');
    ajaxRequest.send();
    setTimeout(getChats, 1000);
}






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
    innContainer.appendChild(title);
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