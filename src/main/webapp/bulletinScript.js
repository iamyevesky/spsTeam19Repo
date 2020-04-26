var course1 = {
    name: "CS 1301",
    department: "College of Computing",
    college: "Georgia Tech",
};

var course2 = {
    name: "CS 1331",
    department: "College of Computing",
    college: "Georgia Tech",
};
var course3 = {
    name: "Math 2550",
    department: "Mathematics",
    college: "Georgia Tech",
};
var course4 = {
    name: "LMC 1301",
    department: "Arts",
    college: "UGA",
};

var course5 = {
    name: "LMC 3000",
    department: "Arts",
    college: "UGA",
};
var course6 = {
    name: "Film 2120",
    department: "Film",
    college: "UGA",
};

var exampleBackend = [course1, course2, course3, course4, course5, course6];
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
        createDepartmentPosts(departmentsArr[i], departmentTitle);
        bulletinContainer.appendChild(departmentTitle);
        var spacer = document.createElement("BR");
        bulletinContainer.appendChild(spacer);
    }
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
