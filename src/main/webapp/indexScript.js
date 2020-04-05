function loadChats() {

    //EXAMPLE DATA
    var exampleChat1 = {
        Title: "GT CS 1301",
        LastChat: "When is the final?",
    };
    var exampleChat2 = {
        Title: "GT CS 1331",
        LastChat: "How are office hours going to be conducted",
    };
    var exampleChat3 = {
        Title: "GT LMC 2500",
        LastChat: "Where is the zoom link?",
    };
    var exampleChat4 = {
        Title: "GT Computer Science Department",
        LastChat: "New changes to grade modes and deadlines",
    };
    var exampleBackend = [exampleChat1, exampleChat2, exampleChat3, exampleChat4];

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
        });

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
