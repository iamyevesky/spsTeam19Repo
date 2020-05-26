var messageInfo;
function getRooms(){
    fetch("/sendMessage").then(response => response.json()).then(object =>
    {
        console.log(object);
        messageInfo = object;
        console.log(messageInfo);
        htmlObj = document.getElementById("chats");
        htmlObj.innerHTML = '';
        appendChat(htmlObj, object);
    }
    );
}

function handleSend()
{
    const parsed = parseInt(document.getElementById("chatList").value, 10);
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

function appendChat(htmlObj, object){
    var select = document.createElement("SELECT");
    select.setAttribute("autofocus", true);
    select.setAttribute("id", "chatList");
    console.log(object.chats);
    for(index in object.chats)
    {
        var singleChatOption = document.createElement("OPTION");
        singleChatOption.innerHTML = object.chats[index].name + "";
        singleChatOption.setAttribute("value", index);
        select.appendChild(singleChatOption);                
    }
    htmlObj.appendChild(select);
    getChats();
}

function getChats(){
    var ajaxRequest = new XMLHttpRequest();
    console.log(document.getElementById("chatList").value);
    ajaxRequest.onreadystatechange = function()
    {
        if(ajaxRequest.readyState == 4)
        {
            //the request is completed, now check its status
            if(ajaxRequest.status == 200)
            {
                var jsonResponse = JSON.parse(ajaxRequest.responseText);
                console.log(jsonResponse);
                var divElement = document.getElementById("messages");
                divElement.innerHTML = '';
                console.log(document.getElementById("chatList").value);
                const parsed = parseInt(document.getElementById("chatList").value, 10);
                console.log(parsed);
                if (isNaN(parsed)) {return}
                for (index in jsonResponse.chats[parsed].messages)
                {
                    var jsonMessage =  jsonResponse.chats[parsed].messages[index];
                    console.log(jsonMessage);
                    var messageEntity = document.createElement("div");
                    messageEntity.innerHTML = '';
                    var name = document.createElement("p");
                    name.innerText = jsonMessage.sender.username + "";
                    messageEntity.appendChild(name);
                    var messageText = document.createElement("p");
                    messageText.innerText = jsonMessage.message + "";
                    messageEntity.appendChild(messageText);
                    divElement.appendChild(messageEntity);
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
