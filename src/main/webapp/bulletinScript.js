

function getBulletinDataNiceFormat() {
    //EXAMPLE DATA
    var exampleData1 = {Title:"Covid-19 announcement", Name:"Professor John", University:"Cornell",
    Department:"LMC", Text:"I will give everyone an A for this class"};

    var exampleData2 = {Title:"Chatroom Office Hours", Name:"Professor Elliot", University:"Georgia Tech",
    Department:"College of Computing", Text:"I will be online to answer any chats in the CS 1301 chatroom M-F from 11:00 am to 3:00 pm"};

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
        createBody(cardBody, currPost);
        bulletinContainer.appendChild(singleCard)
    }
}
function createBody(bodyOutline, currPost) {
    var title = document.createElement("h5");
    title.className = "card-title";
    title.innerText = currPost.Title;

    var name = document.createElement("h6");
    name.className = "card-subtitle mb-2 text-muted";
    name.innerText = currPost.Name;

    var uni = document.createElement("h6");
    uni.className = "card-subtitle mb-2 text-muted";
    uni.innerText = currPost.University;

    var department = document.createElement("h6");
    department.className = "card-subtitle mb-2 text-muted";
    department.innerText = currPost.Department;

    var text = document.createElement("p");
    text.className = "card-text";
    text.style.textAlign = "center";
    text.innerText = currPost.Text;

    bodyOutline.appendChild(title);
    bodyOutline.appendChild(name);
    bodyOutline.appendChild(uni);
    bodyOutline.appendChild(department);
    bodyOutline.appendChild(text);
}
