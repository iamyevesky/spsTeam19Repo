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


function load_departments() {

    //load departments based on college name
    var universityValue = document.getElementById("inputUni").value;
    var arrDepartments = getDepartments(universityValue);
    var string="<option></option>";       
    for(i = 0; i < arrDepartments.length; i++) {
        string += "<option value='" + arrDepartments[i] + "'>" + arrDepartments[i] + "</option>";
    }
    document.getElementById("departmentOutput").innerHTML = string;
    document.getElementById("courseOutput").innerHTML = "";


}

function load_courses() {
    //load courses based on department name
    var departmentValue = document.getElementById("departmentOutput").value;
    var arrCourses = getCourses(departmentValue);
    var courseStrings = "<option></option>"; 
    for(i = 0; i < arrCourses.length; i++) {
        courseStrings += "<option value='" + arrCourses[i] + "'>" + arrCourses[i] + "</option>";
    }
    document.getElementById("courseOutput").innerHTML = courseStrings;
    console.log(document.getElementById("courseOutput"));
    
}

//load based on backend 
function createUniOptions() {
    var uniDropdown = document.getElementById("inputUni");
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
function getCourses(departmentName) {

    var allCourses = [];
    for (i = 0; i < exampleBackend.length; i++) {
        //only check courses of same department
        if (exampleBackend[i].department == departmentName) {
            currCourseName = exampleBackend[i].name;

            //check to see if curr courses name has already been added to list
            if (!allCourses.includes(currCourseName)) {
                allCourses.push(currCourseName);
            }
        }
        
    }
    return allCourses;
}