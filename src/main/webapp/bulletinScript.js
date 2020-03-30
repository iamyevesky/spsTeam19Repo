

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

    //building li in html bulletin page
    const bulletinPost = document.getElementById('bulletin-Container');
    console.log(bulletinPost);

    var i;
    for (i = 0; i < exampleBackend.length; i++) {
        const currPost = exampleBackend[i];
        for (const property in currPost) {
            bulletinPost.appendChild(createListElement(property, currPost[property]));
        }
    }


    // for (const property in exampleData) {
    //     bulletinPost.appendChild(createListElement(property, exampleData1[property]));
    // }
}

// Creates an <li> element containing text.
function createListElement(title, text) {
  const liElement = document.createElement('li');
  liElement.className = "list-group-item";
  liElement.innerText = title.concat(" : ", text);
  return liElement;
}