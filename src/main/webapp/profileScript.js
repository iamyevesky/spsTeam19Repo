var jsonObject;
var totalUserSentimentScore = 0;
var totalUserComments = 0;
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

    fetch("/createAccount").then(response => response.json()).then(object =>
    {
        console.log(object);
        var infoSection = document.getElementById("infoSection");
        createForm(object);
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
    classContainer.append(createUserSentimentScore());
    for (i = 0; i < classArray.length; i++) {
        classContainer.append(createSingleCourseCard(classArray[i]));
    }
} 

function createSingleCourseCard(course) {
    console.log(course);
    var outerBox = document.createElement("div"); 
    outerBox.classList.add("card", "mb-4");
    outerBox.style.width = "30rem";

    var coursename = document.createElement("div");
    coursename.className = "card-header";
    coursename.innerText = course.name;
    //if chat is a DM, make chatName be the other person's Name
        if (course.isDM) {
            var currUsersInChat = course.users;
            var userKey = jsonObject.user.key;
            //find the name of the other person in the DM 
            for (k = 0; k < currUsersInChat.length; k++) {
                if (userKey != currUsersInChat[k].key) {
                    coursename.innerText = currUsersInChat[k].username;
                }
            }
        }

    var textOuterBox = document.createElement("div");
    textOuterBox.classList.add("card-body", "text-secondary");

    var sentimentScoresArr = calculateSentimentScore(course);

    var overallCourseSentiment = document.createElement("h5");
    overallCourseSentiment.className = "card-title";
    overallCourseSentiment.innerText = "Chat Sentiment Score: " + sentimentScoresArr[0];

    var userCourseSentiment = document.createElement("h5");
    userCourseSentiment.className = "card-title";
    userCourseSentiment.innerText = "User Sentiment Score In Chat: " + sentimentScoresArr[1];

    textOuterBox.appendChild(overallCourseSentiment);
    textOuterBox.appendChild(userCourseSentiment);
    outerBox.appendChild(coursename);
    outerBox.appendChild(textOuterBox);
    return outerBox;
}

function calculateSentimentScore(course) {
    
    var totalSentiment = 0;
    var userSentiment = 0;
    courseMessageArr = course.messages;
    for (m = 0; m < courseMessageArr.length; m++) {
        console.log(courseMessageArr[m].sentiment);
        totalSentiment += courseMessageArr[m].sentiment;
        if (courseMessageArr[m].sender.key == jsonObject.user.key) {
            console.log(courseMessageArr[m]);
            userSentiment += courseMessageArr[m].sentiment;
            totalUserComments++;
            totalUserSentimentScore += courseMessageArr[m].sentiment;
        }
    }

    // avoid divide by 0 error 
    if (courseMessageArr.length == 0) {
        return [0, 0];
    } 
    totalSentiment = totalSentiment / courseMessageArr.length;
    userSentiment = userSentiment / courseMessageArr.length;

    return [totalSentiment, userSentiment];
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

// Create dropdown options for all colleges
function createForm(obj){
    // set curr uni as the set value
    var currCollegeKey = jsonObject.user.college.key;
    console.log(currCollegeKey);

    var list = document.getElementById("inputUni");

    list.setAttribute("name", "collegeKey");
    list.classList.add("form-control");

    //add college option from list to select tag
    for(i = 0; i < obj.length; i++) {
        var singleCollegeOption = document.createElement("OPTION");
        singleCollegeOption.innerHTML = obj[i].name;
        singleCollegeOption.setAttribute("value", obj[i].key);
        if (currCollegeKey == obj[i].key) {
            singleCollegeOption.setAttribute("selected", "selected");
        }
        list.appendChild(singleCollegeOption);
    }

}

function createUserSentimentScore() {
    var outerBox = document.createElement("div"); 
    outerBox.classList.add("card", "mb-4");
    outerBox.style.width = "30rem";

    var coursename = document.createElement("div");
    coursename.className = "card-header";
    coursename.innerText = jsonObject.user.username;

    var textOuterBox = document.createElement("div");
    textOuterBox.classList.add("card-body", "text-secondary");

    // avoid divide by zero error
    console.log(totalUserComments);
    console.log(totalUserSentimentScore);
    if (totalUserComments != 0) {
        totalUserSentimentScore /= totalUserComments;
    } else {
        totalUserSentimentScore = 0;
    }

    var userCourseSentiment = document.createElement("h5");
    userCourseSentiment.className = "card-title";
    userCourseSentiment.innerText = "Your Overall Sentiment Score: " + totalUserSentimentScore;

    textOuterBox.appendChild(userCourseSentiment);
    outerBox.appendChild(coursename);
    outerBox.appendChild(textOuterBox);
    return outerBox;
}