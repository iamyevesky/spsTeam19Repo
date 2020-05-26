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
            const parsed = getActiveChatIndex();
            //the request is completed, now check its status
            if(ajaxRequest.status == 200)
            {
                var jsonResponse = JSON.parse(ajaxRequest.responseText);
                console.log(jsonResponse);
                clearChatHistory();
                if (isNaN(parsed)) {return}
                buildMsgHistory(jsonResponse.chats[parsed].messages);
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

function buildMsgHistory(pastMessageData) {
    var outline = document.getElementById("msg-container");
    for (i = 0; i < pastMessageData.length; i++) {
        var currMsgObj = pastMessageData[i];
        msg = currMsgObj.message;
        var senderUsername = currMsgObj.sender.username;

        //get date and time in nice format
        var timeSeconds = currMsgObj.time.seconds;
        var d = new Date(0);
        d.setUTCSeconds(timeSeconds);
        var timeNoSeconds = d.toLocaleTimeString().replace(/(.*)\D\d+/, '$1');
        var dayMY = d.toLocaleDateString();

        //create bootstrap outline
        var msgDiv = document.createElement("div");
        msgDiv.className = "outgoing_msg";

        //TODO: CURRENTLY ONLY SHOWS AS IF USER SENT ALL MESSAGES
        //CHANGE DEPENDING ON USER WHO SENT IT
        var innerTextDiv = document.createElement("div");
        innerTextDiv.className = "sent_msg";

        //populate text with past message
        var text = document.createElement("p");
        text.innerHTML = msg;

        //add username above text
        var spanUsername = document.createElement("span")
        spanUsername.innerText = senderUsername;
        spanUsername.classList.add("chat_username");

        //add time/date below text
        var spanTime = document.createElement("span")
        spanTime.innerText = timeNoSeconds + " | " + dayMY;
        spanTime.classList.add("time_date");

        //append text to bootstrap outline
        innerTextDiv.appendChild(spanUsername);
        innerTextDiv.appendChild(text);
        innerTextDiv.appendChild(spanTime);
        msgDiv.appendChild(innerTextDiv);
        outline.appendChild(msgDiv);
    }
}

function handleSend()
{
    const parsed = getActiveChatIndex();
    if (isNaN(parsed)) {return false;}
    var key = messageInfo.chats[parsed].key+"";
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/sendMessage?chatKey="+key+"&message="+document.messageForm.elements.namedItem('message').value+"", true);

    xhr.onreadystatechange = function() { // Call a function when the state changes.
        if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
        }
    }
    xhr.send();
    console.log("Chat sent");
    return false;    
}

function clearChatHistory() {
    var node = document.getElementById("msg-container");
    node.innerHTML = "";
}
