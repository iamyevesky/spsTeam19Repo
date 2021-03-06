var messageInfo;
var totalUserSentiment;

function loadChatsNew() {
    fetch("/getInfoPost").then(response => response.json()).then(object =>
    {
        checkIfLoggedIn(object);
    });

    fetch("/sendMessage").then(response => response.json()).then(object =>
    {
        console.log(object);
        messageInfo = object;
        appendChatNameToSidebar(object);
        getChats(); 
    });
}

function appendChatNameToSidebar(jsonObj) {
    console.log(jsonObj.chats);
    const chatContainer = document.getElementById('chat-container');
    chatContainer.innerHTML = '';
    for(index in jsonObj.chats)
    {
        console.log(index);
        var outerContainer1 = document.createElement("div");
        outerContainer1.className = "chat_list";

        //update the active chat when clicked on and load messages
        outerContainer1.addEventListener("click", function() {
            //deactivate current active chat class
            console.log("testing deactivation");
            var activeChat = document.getElementsByClassName("chat_list active_chat");
            if (activeChat[0] != null) {
                activeChat[0].className = "chat_list";
            }
            //update chat that was clicked on to become
            this.className = "chat_list active_chat"; 

            //update chat name dropdown title
            var htmlColl = activeChat[0];
            var chatName = htmlColl.firstChild.firstChild.firstChild.innerText;
            var chatTitleDropdown = document.getElementById("dropdownMenuButton");
            chatTitleDropdown.innerText = chatName;
            chatTitleDropdown.style.display = "block";

            //show text ability only after user clicks a chat
            var textInput = document.getElementById("typeChatInput");
            textInput.style.display = "block";
        });

        var chatName = jsonObj.chats[index].name;
        //if chat is a DM, make chatName be the other person's Name
        if (jsonObj.chats[index].isDM) {
            var currUsersInChat = jsonObj.chats[index].users;
            var userKey = messageInfo.user.key;
            //find the name of the other person in the DM 
            for (k = 0; k < currUsersInChat.length; k++) {
                if (userKey != currUsersInChat[k].key) {
                    console.log(currUsersInChat[k].key);
                    chatName = currUsersInChat[k].username;
                }
            }
        }

        var chatKey = jsonObj.chats[index].key;

        //build bootstrap outline
        var outerContainer2 = document.createElement("div");
        outerContainer2.className = "chat_people";
        var innerContainer = document.createElement("div")
        innerContainer.className = "chat_ib";


        outerContainer1.appendChild(outerContainer2);
        outerContainer2.appendChild(innerContainer);

        var title = document.createElement("h5");
        title.setAttribute("id", chatKey);
        title.innerText = chatName;
        innerContainer.appendChild(title);

        chatContainer.appendChild(outerContainer1);
        
    }
}
function getActiveChatIndex() {
    //get name of currently clicked chat by class name "active_chat"
    var activeChat = document.getElementsByClassName("chat_list active_chat");
    var htmlColl = activeChat[0];
    var chatName = htmlColl.firstChild.firstChild.firstChild.innerText;
    var activeChatIdKey  = htmlColl.firstChild.firstChild.firstChild.id;

    //search backend and find index based on chat Key
    var chatsArr = messageInfo.chats;
    console.log(chatsArr);
    for (i = 0; i < chatsArr.length; i++) {
        if (activeChatIdKey === chatsArr[i].key) {
            console.log(chatsArr[i]);
            console.log(i);
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
                messageInfo = jsonResponse;
                console.log(jsonResponse);
                clearChatHistory();
                if (isNaN(parsed)) {return}
                updateChatNamesSidebar(jsonResponse.chats);
                //appendChatNameToSidebar(jsonResponse);
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
    setTimeout(getChats, 100);
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
    clearInputValue();
    return false;    
}

function clearChatHistory() {
    var node = document.getElementById("msg-container");
    node.innerHTML = "";
}

//used to clear input field for typing after submitting
function clearInputValue() {
    var msgInput = document.getElementById("messageField");
    msgInput.value = "";

    //also set active chat correctly
    var allChats = document.getElementsByClassName("chat_list");
    allChats[0].click();

}

function checkIfLoggedIn(jsonObj) {
    //redirects to home page if not logged in
    if (!jsonObj.status) {
        window.location.replace("index.html");
    }
}

function updateChatNamesSidebar(jsonChats) {
    var currChatNames = document.getElementById("chat-container").childNodes;
    for (i = 0; i < currChatNames.length; i++) {
        chatHeaderElement = currChatNames[i].firstChild.firstChild.firstChild;
        chatHeaderElement.innerText = jsonChats[i].name;
        console.log(jsonChats[i]);

        //if DM make chat name be other persons name
        if (jsonChats[i].isDM) {
            var currUsersInChat = jsonChats[i].users;
            var userKey = messageInfo.user.key;
            //find the name of the other person in the DM 
            for (k = 0; k < currUsersInChat.length; k++) {
                if (userKey != currUsersInChat[k].key) {
                    chatHeaderElement.innerText = currUsersInChat[k].username;
                }
            }
        }
        chatHeaderElement.setAttribute("id", jsonChats[i].key);
    }
}

function loadPeopleModal() {
    //get user info to ensure they cannot DM themself
    var userKey = messageInfo.user.key;

    //load members of chat
    var pplContainer = document.getElementById("modal-ppl-container");
    pplContainer.innerText = "";
    var k = getActiveChatIndex();
    var currChat = messageInfo.chats[k];
    console.log(currChat);
    var isCurrChatDM = currChat.isDM;
    console.log(isCurrChatDM);
    var chatPeopleArr = currChat.users;
    for (j = 0; j < chatPeopleArr.length; j++) {
        var currUserKey = chatPeopleArr[j].key;

        // create row div
        var rdiv = document.createElement("div");
        rdiv.classList.add("row");
        rdiv.style.padding = "0px 0px 15px 0px";

        // create column for name
        var nameCDiv = document.createElement("div");
        nameCDiv.classList.add("col");

        // get name and instantiate in p element
        var name = chatPeopleArr[j].username;
        var nameP = document.createElement("p");
        nameP.innerText = name;

        // create column for dm form
        var dmCdiv = document.createElement("div");
        dmCdiv.classList.add("col");

        // skip user's ability to DM themself or if it is a DM chat
        if (currUserKey != userKey)  {
            if (!isCurrChatDM) {
                //create form to send to create chat servlet
            var f = document.createElement("form");
            f.setAttribute('method',"POST");
            f.setAttribute('action',"/createChat");

            //hidden name attribute change this in servlet bc not used
            var tempName = document.createElement("input"); //input element, text
            tempName.setAttribute('type',"hidden");
            tempName.setAttribute('name',"name");
            tempName.setAttribute('value', "DM Name");

            //hidden isDm attribute set to true
            var isDMAttr = document.createElement("input"); //input element, text
            isDMAttr.setAttribute('type',"hidden");
            isDMAttr.setAttribute('name',"isDM");
            isDMAttr.setAttribute('value', true);

            //hidden key of person getting DM'd
            var targetPerson = document.createElement("input"); //input element, text
            targetPerson.setAttribute('type',"hidden");
            targetPerson.setAttribute('name',"to");
            targetPerson.setAttribute('value', currUserKey);

            //create dm button
            var dmButton = document.createElement("BUTTON");
            dmButton.innerHTML = "Direct Message"
            dmButton.classList.add("btn", "color_blue")
            dmButton.setAttribute("type", "submit");

            //add elements to form
            f.appendChild(isDMAttr);
            f.appendChild(tempName);
            f.appendChild(targetPerson);
            f.appendChild(dmButton);

            //add form to dm column div
            dmCdiv.appendChild(f);
            }

           
        }
        

        // set up html
        nameCDiv.appendChild(nameP);
        rdiv.appendChild(nameCDiv);
        rdiv.appendChild(dmCdiv);

        pplContainer.appendChild(rdiv);
        console.log(chatPeopleArr);
    }

    //update chat title
    var pplTitle = document.getElementById("peopleModalTitle");
    var currChatName = currChat.name;
    pplTitle.innerText = "Members of " + currChatName;
}