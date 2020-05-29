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
}

function setUpUserPage(json){
    const jsonData = json;
    var name = document.createElement("h3");
    name.innerText = String(jsonData.user.username);
    var uni = document.createElement("h6");
    uni.innerText = String(jsonData.user.college.name);
    var classification = document.createElement("h6");
    console.log(jsonData.classes);
    
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

function loadClasses() {
    var classContainer = document.getElementById("classes-container");
    console.log(jsonObject);
    var classArray = jsonObject.classes;
    for (i = 0; i < classArray.length; i++) {
        classContainer.append(createSingleCourseCard(classArray[i]));
    }
}

function createSingleCourseCard(course) {
    var outerBox = document.createElement("div");
    outerBox.classList.add("card", "mb-3");

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