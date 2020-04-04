

function getBulletinDataNiceFormat() {
    //EXAMPLE DATA
    var exampleData1 = {Title:"Covid-19 announcement", Name:"Professor John", University:"Cornell",
    Department:"LMC", Course:"LMC 1101",  
    Text:"I will give everyone an A for this class"};

    var exampleData2 = {Title:"Chatroom Office Hours", Name:"Professor Elliot", University:"Georgia Tech",
    Department:"College of Computing", Course:"CS 1301",  
    Text:"I will be online to answer any chats in the CS 1301 chatroom M-F from 11:00 am to 3:00 pm"};

    var exampleBackend = [exampleData1, exampleData2];

    //building list of posts in html bulletin page
    const bulletinContainer = document.getElementById('bulletin-new-container');

    for (i = 0; i < exampleBackend.length; i++) {
        var singleCard = document.createElement("div");
        singleCard.className = "card";
        singleCard.style.width = "50rem";
        singleCard.style.margin = "0 auto";
        singleCard.style.marginTop = "30px";

        var cardBody = document.createElement("div");
        cardBody.className = "card-body";
        
        singleCard.appendChild(cardBody);

        const currPost = exampleBackend[i];
        for (const property in currPost) {
            cardBody.appendChild(createPostElement(property, currPost[property]));
        }
        bulletinContainer.appendChild(singleCard)
    }
}

// Creates an html element depending on its property
function createPostElement(property, text) {
    var lineItem;
    if (property == "Title") {
        lineItem = document.createElement("h5");
        lineItem.className = "card-title";
    } else if (property == "Text"){
        lineItem = document.createElement("p");
        lineItem.className = "card-text";
        lineItem.style.textAlign = "center";
    } else {
        lineItem= document.createElement("h6");
        lineItem.className = "card-subtitle mb-2 text-muted";
    }
    lineItem.innerText = text;
    return lineItem;
}