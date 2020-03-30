

function getBulletinData() {
    //fetch('/data').then(response => response.json()).then((data) =>

    //EXAMPLE DATA
    var exampleData1 = {Name:"Professor John", University:"Cornell", Department:"LMC",
    Course:"LMC 1101", Title:"Testing 1", 
    Text:"I will give everyone an A for this class"};

    var exampleData2 = {Name:"Alan", University:"Georgia Tech", Department:"College of Computing",
    Course:"CS 1301", Title:"Chatroom Office Hours", 
    Text:"I will be online to answer any chats in the CS 1301 chatroom M-F from 11:00 am to 3:00 pm"};
    
    var exampleBackend = [exampleData1, exampleData2];

    //building list of posts in html bulletin page
    const bulletinContainer = document.getElementById('bulletin-Container');

    var test = document.createElement("ul");
    for (i = 0; i < exampleBackend.length; i++) {
        var test = document.createElement("ul")
        const currPost = exampleBackend[i];
        for (const property in currPost) {
            test.appendChild(createListElement(property, currPost[property]));
        }
        bulletinContainer.appendChild(test)
        bulletinContainer.appendChild(document.createElement("br"));
    }
}

// Creates an <li> element containing text.
function createListElement(title, text) {
  const liElement = document.createElement('li');
  liElement.className = "list-group-item";
  liElement.innerText = title.concat(" : ", text);
  return liElement;
}