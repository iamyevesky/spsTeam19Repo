var jsonObject;

var exampleClasses = [
    "LMC 2500",
    "GT Computer Science Department",
    "UGA CS 2001",
    "GT CS 1331"
]


var userProfile = {
    Name : "Alan Matias",
    College: "Georgia Tech",
    Classification: "student",
    Email: "10alanm@gmail.com",
    ClassList: exampleClasses
}


function loadProfileData() {
    loadProfile();
    loadClasses();
}

function loadProfile() {
    fetch("/getInfo").then(response => response.json()).then(object =>
    {
        console.log(object);
        jsonObject = object;
        console.log(jsonObject);
        setUpUserPage(jsonObject);
    });
}

function setUpUserPage(jsonData){
    var name = document.createElement("h3");
    name.innerText = String(jsonData.username);
    var uni = document.createElement("h6");
    uni.innerText = jsonData.college.name;
    var classification = document.createElement("h6");
    
    if (jsonData.isProf)
    {
        classification.innerText = "Professor";
    }
    else
    {
        classification.innerText = "Student";
    }
    var email = document.createElement("h6");
    email.innerText = String(jsonData.email);
    var spacer = document.createElement("BR");

    //change nickname link
    var changeNicknameP = document.createElement("p");
    changeNicknameP.innerText = "Change your nickname: ";
    var anchor = document.createElement("A");
    var link = document.createTextNode("here");
    anchor.setAttribute("href", "#");
    anchor.appendChild(link);
    changeNicknameP.appendChild(anchor);

    var profileContainer = document.getElementById("profile-container");
    profileContainer.appendChild(name);
    profileContainer.appendChild(uni);
    profileContainer.appendChild(classification);
    profileContainer.appendChild(email);
    profileContainer.appendChild(spacer);
    profileContainer.appendChild(changeNicknameP);
}

function loadClasses() {
    var classContainer = document.getElementById("classes-container");

    var outerBox = document.createElement("div");
    outerBox.classList.add("card", "mb-3");

    var coursename = document.createElement("div");
    coursename.className = "card-header";
    coursename.innerText = "Testing";

    var textOuterBox = document.createElement("div");
    textOuterBox.classList.add("card-body", "text-secondary");

    var courseTextInside = document.createElement("h5");
    courseTextInside.className = "card-title";
    courseTextInside.innerText = "Sentiment Score: Test";

    textOuterBox.appendChild(courseTextInside);
    outerBox.appendChild(coursename);
    outerBox.appendChild(textOuterBox);
    console.log(outerBox);
    classContainer.append(outerBox);

}