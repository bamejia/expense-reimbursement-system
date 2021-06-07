let user = null;

window.onload = function () {
    // getTest((x) => console.log(x));
    // getManagerAccount(updateDisplay)
    // sessionStorage.setItem("token", "1:employee")
    // sessionStorage.removeItem("token");
    updateDisplay();
}

function updateDisplay(){
    // const homeHeading = document.getElementById("home-heading");
    // homeHeading.innerText = head_text;
    
    // const homeBody = document.getElementById("home-body");
    // homeBody.innerText = head_text;
    
    updateNavbar();
    displayLoggedInUser();
}

function updateNavbar(){
    let token = sessionStorage.getItem("token");
    if(!token){
        document.getElementById('employee-requests-nav-opt').setAttribute("hidden", "true");
        document.getElementById('manager-requests-nav-opt').setAttribute("hidden", "true");
        document.getElementById('home-nav-opt').setAttribute("hidden", "true");
        document.getElementById('login-nav-opt').removeAttribute("hidden");
    }else{
        document.getElementById('home-nav-opt').removeAttribute("hidden");
        let parsedToken = token.split(":");
        if(parsedToken[1] == "employee"){
            document.getElementById('employee-requests-nav-opt').removeAttribute("hidden");
        }else if(parsedToken[1] == "manager"){
            document.getElementById('manager-requests-nav-opt').removeAttribute("hidden");
        }
        toggleLoginToLogout();
        document.getElementById('login-nav-opt').removeAttribute("hidden");
        // document.getElementById('login-nav-opt').setAttribute("hidden", "true");
    }
}

function displayLoggedInUser(){
    const token = sessionStorage.getItem("token");
    // ex. 1:ADMIN
    // const parsedToken = token.split(":");
    // const loggedInID = parsedToken[0];
    if(token){
        getUserInfo(token, function(usersJSON){
            const user = JSON.parse(usersJSON);
            // document.getElementById("greeting-header").innerText = "Welcome to Revature Bakery, "+ user.firstName + " " + user.lastName;
            document.getElementById("home-heading").innerText = `Hello, ${user.firstName} ${user.lastName}`;
        });
    }else{
        document.getElementById("home-heading").innerText = `Please login to view more options`;
    }
}

function removeLoggedInUserGreeting(){
    document.getElementById("greeting-header").innerText = `Welcome to ERS!`;

}
