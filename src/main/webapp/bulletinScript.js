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
        var departmentTitle = document.createElement("h4");
        departmentTitle.innerHTML = departmentsArr[i].name;
        console.log(departmentsArr[i]);
        bulletinContainer.appendChild(departmentTitle);
        createDepartmentPosts(departmentsArr[i], departmentTitle);
        bulletinContainer.appendChild(createPostLink(departmentsArr[i]));
        var spacer = document.createElement("BR");
        bulletinContainer.appendChild(spacer);

    }
}
function createPostLink(currDepartment) {
    var f = document.createElement("form");
    f.setAttribute('method',"POST");
    f.setAttribute('action',"/departmentPostTest");

    var i = document.createElement("input"); //input element, text
    i.setAttribute('type',"text");
    i.setAttribute('name',"title");
    i.setAttribute('placeholder', "Title");

    var i2 = document.createElement("input"); //input element, text
    i2.setAttribute('type',"text");
    i2.setAttribute('name',"body");
    i2.setAttribute('placeholder', "body");

    var i3 = document.createElement("input"); //input element, text
    i3.setAttribute('type',"hidden");
    i3.setAttribute('name',"departmentID");
    i3.setAttribute('value',currDepartment.key);

    var s = document.createElement("input"); //input element, Submit button
    s.setAttribute('type',"submit");
    s.setAttribute('value',"Submit");

    f.appendChild(i);
    f.appendChild(i2);
    f.appendChild(i3);
    f.appendChild(s);
    // var link = document.createElement("h6");
    // var str = "Create a bulletin post for " + currDepartment.name;
    // var result = str.link("/createPost.html");
    // link.innerHTML = result;
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
