var jsonObject;

function loadBulletinPage() {
    fetch("/getInfo").then(response => response.json()).then(object =>
    {
        jsonObject = object;
        loadDepartments();
    });
}
function loadDepartments() {
    var departmentsArr = jsonObject.departments;
    console.log(departmentsArr);
    const bulletinContainer = document.getElementById('bulletin-new-container');
    for (i = 0; i < departmentsArr.length; i++) {
        //title
        var departmentTitle = document.createElement("h4");
        departmentTitle.innerHTML = departmentsArr[i].name;
        //departmentTitle.style.marginTop = "70px";
        console.log(departmentsArr[i]);
        bulletinContainer.appendChild(departmentTitle);
        //posts
        createDepartmentPosts(departmentsArr[i], departmentTitle);
        bulletinContainer.appendChild(document.createElement("BR"));
        //form
        var header = document.createElement("h5");
        header.innerHTML = "Create a post in " + departmentsArr[i].name;
        bulletinContainer.appendChild(header);
        bulletinContainer.appendChild(document.createElement("BR"))
        bulletinContainer.appendChild(createPostLink(departmentsArr[i]));
        //spacer
        var spacer = document.createElement("hr");
        spacer.style.marginTop = "40px";
        spacer.style.marginBottom = "40px";
        bulletinContainer.appendChild(spacer);
    }
}
function createPostLink(currDepartment) {

    var f = document.createElement("form");
    f.setAttribute('method',"POST");
    f.setAttribute('action',"/departmentPostTest");

    //title
    var titleDiv = document.createElement("div");
    titleDiv.setAttribute('class',"form-group");

    var titleLabel = document.createElement("label");
    titleLabel.innerHTML = "Title";
    titleDiv.appendChild(titleLabel);

    var titleEntry = document.createElement("input"); //input element, text
    titleEntry.setAttribute('type',"text");
    titleEntry.setAttribute('name',"title");
    titleEntry.setAttribute('class',"form-control");
    titleEntry.setAttribute('placeholder', "Title");
    titleDiv.appendChild(titleEntry);

    //body
    var bodyDiv = document.createElement("div");
    bodyDiv.setAttribute('class',"form-group");

    var bodyLabel = document.createElement("label");
    bodyLabel.innerHTML = "Text:";
    bodyDiv.appendChild(bodyLabel);

    var bodyEntry = document.createElement("input"); //input element, text
    bodyEntry.setAttribute('type',"text");
    bodyEntry.setAttribute('name',"body");
    bodyEntry.setAttribute('placeholder', "body");
    bodyEntry.setAttribute('class',"form-control");
    bodyDiv.appendChild(bodyEntry);

    //hidden department entry with key already set as value
    var departmentEntry = document.createElement("input"); //input element, text
    departmentEntry.setAttribute('type',"hidden");
    departmentEntry.setAttribute('name',"departmentID");
    departmentEntry.setAttribute('value',currDepartment.key);

    var s = document.createElement("input"); //input element, Submit button
    s.setAttribute('type',"submit");
    s.setAttribute('value',"Submit");

    f.appendChild(titleDiv);
    f.appendChild(bodyDiv);
    f.appendChild(departmentEntry);
    f.appendChild(s);
    return f;
}
function createDepartmentPosts(departmentObject, departmentContainer) {    
    fetch("/departmentPostTest?departmentID="+departmentObject.key).then(response => response.json()).then(object =>
    {
        console.log(object);
        for (i = 0; i < object.length; i++) {
            var singleCard = document.createElement("div");
            singleCard.className = "card";
            singleCard.style.width = "50rem";
            singleCard.style.margin = "0 auto";
            singleCard.style.marginTop = "30px";

            var cardBody = document.createElement("div");
            cardBody.className = "card-body";
        
            singleCard.appendChild(cardBody);

            const currPost = object[i];
            createBody(cardBody, currPost);
            departmentContainer.appendChild(singleCard);
        }
    });
}

function createBody(bodyOutline, currPost) {
    var title = document.createElement("h5");
    title.className = "card-title";
    title.innerText = currPost.title;

    var text = document.createElement("p");
    text.className = "card-text";
    text.style.textAlign = "center";
    text.innerText = currPost.body;

    bodyOutline.appendChild(title);
    bodyOutline.appendChild(text);
}
