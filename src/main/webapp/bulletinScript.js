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

function load_departments(uniID, depID) {

    //load departments based on college name
    var universityValue = document.getElementById(uniID).value;
    var arrDepartments = getDepartments(universityValue);
    var string="<option></option>";       
    for(i = 0; i < arrDepartments.length; i++) {
        string += "<option value='" + arrDepartments[i] + "'>" + arrDepartments[i] + "</option>";
    }
    document.getElementById(depID).innerHTML = string;
}

function getDepartments(uniName) {

    var allDepartments = [];
    for (i = 0; i < exampleBackend.length; i++) {
        //only check departments of same college
        if (exampleBackend[i].college == uniName) {
            currDepName = exampleBackend[i].department;

            //check to see if curr department name has already been added to list
            if (!allDepartments.includes(currDepName)) {
                allDepartments.push(currDepName);
            }
        }
        
    }
    return allDepartments;
}

//load based on backend 
function createUniOptions() {
    var uniDropdown = document.getElementById("universitySelection");
    var universityStrings = "";
    var allUniversities = [];
    for (i = 0; i < exampleBackend.length; i++) {
        currUniName = exampleBackend[i].college;

        //check to see if curr university name has already been added to dropdown
        if (!allUniversities.includes(currUniName)) {
            allUniversities.push(currUniName);
            universityStrings += "<option value='" + currUniName + "'>" + currUniName + "</option>";
        }
        
    }
    uniDropdown.innerHTML += universityStrings;
}