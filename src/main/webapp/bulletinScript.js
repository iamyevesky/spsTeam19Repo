var jsonObject;

function loadBulletinPage() {
    fetch("/getInfoPost").then(response => response.json()).then(object =>
    {
        jsonObject = object;
        loadCollegePosts();
    });
}
function loadCollegePosts() {
    //redirects to home page if not logged in
    if (!jsonObject.status) {
        window.location.replace("index.html");
    }
    console.log(jsonObject);

    const bulletinContainer = document.getElementById('bulletin-new-container');
    var collegeHeader = document.createElement("h3");
    collegeHeader.innerHTML = jsonObject.user.college.name;
    collegeHeader.style.marginTop = "25px";
    collegeHeader.style.marginBottom = "25px";
    collegeHeader.style.textAlign = "center";
    bulletinContainer.appendChild(collegeHeader);

    var bulletinPosts = jsonObject.posts;

    //create individual posts from post array
    for (i = 0; i < bulletinPosts.length; i++) {
        var singleCard = document.createElement("div");
        singleCard.className = "card";
        singleCard.style.width = "50rem";
        singleCard.style.margin = "0 auto";
        singleCard.style.marginTop = "30px";

        var cardBody = document.createElement("div");
        cardBody.className = "card-body";
        
        singleCard.appendChild(cardBody);

        const currPost = bulletinPosts[i];
        createBody(cardBody, currPost);
        bulletinContainer.appendChild(singleCard);
    }
    const formContainer = document.getElementById('form-container');
    formContainer.appendChild(createFormPost());

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
function createFormPost() {

    var f = document.createElement("form");
    f.setAttribute('method',"POST");
    f.setAttribute('action',"/getInfoPost");

    //title
    var titleDiv = document.createElement("div");
    titleDiv.setAttribute('class',"form-group");

    var titleLabel = document.createElement("label");
    titleLabel.innerHTML = "Title:";
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

    //hidden college entry with value set as user's college
    var collegeEntry = document.createElement("input"); //input element, text
    collegeEntry.setAttribute('type',"hidden");
    collegeEntry.setAttribute('name',"college");
    collegeEntry.setAttribute('value',jsonObject.user.college.name);

    //hidden user entry with value set as user object
    var userEntry = document.createElement("input"); //input element, text
    userEntry.setAttribute('type',"hidden");
    userEntry.setAttribute('name',"user");
    userEntry.setAttribute('value',jsonObject.user);

    //input element, Submit button
    var s = document.createElement("input");
    s.setAttribute('type',"submit");
    s.setAttribute('value',"Submit");
    s.classList.add("btn","btn-success");

    f.appendChild(titleDiv);
    f.appendChild(bodyDiv);
    f.appendChild(collegeEntry);
    f.appendChild(userEntry);
    f.appendChild(s);
    return f;
}

function createBody(bodyOutline, currPost) {
    console.log(currPost.date);
    var utcseconds = currPost.date.seconds;
    console.log(utcseconds);
    var d = new Date(0);
    d.setUTCSeconds(utcseconds);

    var username = document.createElement("h6");
    username.className = "card-title";
    username.innerText = currPost.user.username;

    var bulletinDate = document.createElement("h6");
    bulletinDate.className = "card-title";
    bulletinDate.innerText = d.toLocaleDateString();

    var bulletinTime = document.createElement("h6");
    bulletinTime.className = "card-title";
    var timeNoSeconds = d.toLocaleTimeString().replace(/(.*)\D\d+/, '$1');
    bulletinTime.innerText = timeNoSeconds;

    var title = document.createElement("h5");
    title.className = "card-title";
    title.style.textAlign = "center";
    title.innerText = currPost.title;

    var text = document.createElement("p");
    text.className = "card-text";
    text.style.textAlign = "center";
    text.innerText = currPost.body;

    bodyOutline.appendChild(username);
    bodyOutline.appendChild(bulletinDate);
    bodyOutline.appendChild(bulletinTime);
    bodyOutline.appendChild(title);
    bodyOutline.appendChild(text);
}
