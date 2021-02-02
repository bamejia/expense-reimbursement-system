document.getElementById("create-request-button").addEventListener("click", createRequest);

window.onload = function () {
    // getTest((x) => console.log(x));
    // getManagerAccount(updateDisplay)
    // sessionStorage.setItem("token", "1:employee")
    // sessionStorage.removeItem("token");
    getPendingRequestsByEmployeeId(0);
    getResolvedRequestsByEmployeeId(0);
    updateDisplay();
}

function updateDisplay(){
    // const homeHeading = document.getElementById("home-heading");
    // homeHeading.innerText = head_text;
    
    // const homeBody = document.getElementById("home-body");
    // homeBody.innerText = head_text;

    updateNavbar();
    // displayLoggedInUser();
}

function updateNavbar(){
    let token = sessionStorage.getItem("token");
    if(!token){
        document.getElementById('employee-requests-nav-opt').setAttribute("hidden", "true");
        document.getElementById('manager-requests-nav-opt').setAttribute("hidden", "true");
        document.getElementById('home-nav-opt').setAttribute("hidden", "true");
        // document.getElementById('login-nav-opt').removeAttribute("hidden");
    }else{
        document.getElementById('home-nav-opt').removeAttribute("hidden");
        let parsedToken = token.split(":");
        if(parsedToken[1] = "employee"){
            document.getElementById('employee-requests-nav-opt').removeAttribute("hidden");
        }else if(parsedToken[1] = "manager"){
            document.getElementById('manager-requests-nav-opt').removeAttribute("hidden");
        }
        // toggleLoginToLogout();
        // document.getElementById('login-nav-opt').removeAttribute("hidden");
        // document.getElementById('login-nav-opt').setAttribute("hidden", "true");
    }
}

// function displayLoggedInUser(){
//     const token = sessionStorage.getItem("token");
//     // ex. 1:ADMIN
//     // const parsedToken = token.split(":");
//     // const loggedInID = parsedToken[0];
//     if(token){
//         getUserInfo(token, function(usersJSON){
//             const user = JSON.parse(usersJSON);
//             // document.getElementById("greeting-header").innerText = "Welcome to Revature Bakery, "+ user.firstName + " " + user.lastName;
//             document.getElementById("home-heading").innerText = `Hello, ${user.firstName} ${user.lastName}`;
//         });
//     }else{
//         document.getElementById("home-heading").innerText = `Please login to view more options`;
//     }
// }

// function removeLoggedInUserGreeting(){
//     document.getElementById("greeting-header").innerText = `Welcome to ERS!`;

// }

function getPendingRequestsByEmployeeId(id){
    let token = sessionStorage.getItem("token");
    let amount = 0;
    let type = "PENDING_EMPLOYEE_REQUESTS"
    let rToken = {id, amount, type};

    getReimbursementInfo(token, rToken, renderPendingRequests)
}

function getResolvedRequestsByEmployeeId(id){
    let token = sessionStorage.getItem("token");
    let amount = 0;
    let type = "RESOLVED_EMPLOYEE_REQUESTS"
    let rToken = {id, amount, type};

    getReimbursementInfo(token, rToken, renderResolvedRequests)
}

function renderPendingRequests(jsonPendingRequests){

    pendingRequests = JSON.parse(jsonPendingRequests);
  
    for (let request of pendingRequests) {
    //   let muffinCheckbox = document.createElement("input");
    //   muffinCheckbox.value = muffin.id;
    //   muffinCheckbox.type = "checkbox";
    //   muffinCheckbox.className = "muffin-checkbox";
  
      let breakLine = document.createElement("br");
  
      let requestLabel = document.createElement("label");
      requestLabel.innerText =
        `ID: ${request.id} | Amount: ${request.amount} | Status: ${request.status}`;
      let pendingRequestNode = document.getElementById("pending-requests");
  
      pendingRequestNode.append(requestLabel, breakLine);
    }
}

function renderResolvedRequests(jsonResolvedRequests){
  
    resolvedRequests = JSON.parse(jsonResolvedRequests);
  
    for (let request of resolvedRequests) {
  
      let breakLine = document.createElement("br");
  
      let requestLabel = document.createElement("label");
      requestLabel.innerText =
        `ID: ${request.id} | Amount: ${request.amount} | Status: ${request.status}`;
      let resolvedRequestNode = document.getElementById("resolved-requests");
  
      resolvedRequestNode.append(requestLabel, breakLine);
    }
}

function createRequest(){

    let token = sessionStorage.getItem("token");
    let id = 0
    let amount = document.getElementById("reimbursement-amount").value;
    let type = "CREATE_REQUEST";
    let reimbursementToken = `${id}:${amount}:${type}`;

    createReimbursementRequest(token, reimbursementToken, requestCreationSuccessful, requestCreationFailed);
}

function requestCreationSuccessful(){
    window.confirm("Succesfully created request!");
}

function requestCreationFailed(){
    window.confirm("Unable to create request!");
}