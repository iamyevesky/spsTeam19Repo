var jsonObject;

function loadProfile() {
    fetch("/getUserInfo").then(response => response.json()).then(object =>
    {
        checkIfLoggedIn(object);
        jsonObject = object;
        console.log(jsonObject);
        console.log(typeof jsonObject)
        setUpUserPage(jsonObject);
        
    });

    fetch("/sendMessage").then(response => response.json()).then(object =>
    {
        console.log(object);
        loadChats(object);
        loadModalProfileData(object);
    });
}

function setUpUserPage(json){
    const jsonData = json;
    var name = document.createElement("h3");
    name.innerText = String(jsonData.user.username);
    var uni = document.createElement("h6");
    uni.innerText = String(jsonData.user.college.name);
    var classification = document.createElement("h6");
    
    if (jsonData?.isProf)
    {
        classification.innerText = "Professor";
    }
    else
    {
        classification.innerText = "Student";
    }
    var email = document.createElement("h6");
    email.innerText = String(jsonData.user.email);
    var spacer = document.createElement("BR");

    var profileContainer = document.getElementById("profile-container");
    profileContainer.appendChild(name);
    profileContainer.appendChild(uni);
    profileContainer.appendChild(classification);
    profileContainer.appendChild(email);
    profileContainer.appendChild(spacer);
}

function loadChats(chatsObj) {
    var classContainer = document.getElementById("chatsContainer");
    var classArray = chatsObj.chats;
    console.log(classArray);
    for (i = 0; i < classArray.length; i++) {
        classContainer.append(createSingleCourseCard(classArray[i]));
    }
} 

function createSingleCourseCard(course) {
    var outerBox = document.createElement("div"); 
    outerBox.classList.add("card", "mb-4");
    outerBox.style.width = "30rem";

    var coursename = document.createElement("div");
    coursename.className = "card-header";
    coursename.innerText = course.name;

    var textOuterBox = document.createElement("div");
    textOuterBox.classList.add("card-body", "text-secondary");

    var courseTextInside = document.createElement("h5");
    courseTextInside.className = "card-title";
    //TODO: incorporate real sentiment score here
    courseTextInside.innerText = "Sentiment Score: ";

    textOuterBox.appendChild(courseTextInside);
    outerBox.appendChild(coursename);
    outerBox.appendChild(textOuterBox);
    return outerBox;
}

function checkIfLoggedIn(jsonObj) {
    //redirects to home page if not logged in
    if (!jsonObj.status) {
        window.location.replace("index.html");
    }
}

function loadModalProfileData(jsonObj) {
    var name = jsonObj.user.username;
    var profInput = document.getElementById("inputName");
    profInput.value = name;
}