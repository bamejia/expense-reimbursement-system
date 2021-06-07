document.getElementById("create-request-button").addEventListener("click", createRequest);

window.onload = function () {
    // getTest((x) => console.log(x));
    // getManagerAccount(updateDisplay)
    // sessionStorage.setItem("token", "1:employee")
    // sessionStorage.removeItem("token");
    updateRequestTables();
    updateNavbar();
    toggleLoginToLogout
}

function updateRequestTables(){
    // const homeHeading = document.getElementById("home-heading");
    // homeHeading.innerText = head_text;
    
    // const homeBody = document.getElementById("home-body");
    // homeBody.innerText = head_text;
    let pendingRequestNode = document.getElementById("pending-requests");
    // console.log(pendingRequestNode.children)
    // console.log(pendingRequestNode.innerHTML);
    // for(let child of pendingRequestNode.children){
    //     child.remove();
    //     // child.parentElement.removeChild(child);
    // }
    // console.log(pendingRequestNode.childNodes)
    pendingRequestNode.innerHTML = pendingRequestNode.innerHTML.replace(new RegExp(".*", 'i'), "");
    // console.log(pendingRequestNode.innerHTML);
    let id = sessionStorage.getItem("token").split(":")[0];
    getPendingRequestsByEmployeeId(id);
    getResolvedRequestsByEmployeeId(id);
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
        document.getElementById('login-nav-opt').removeAttribute("hidden");
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
    pendingRequests.sort((a, b) => a.id - b.id);

    for (let request of pendingRequests) {
    //   let muffinCheckbox = document.createElement("input");
    //   muffinCheckbox.value = muffin.id;
    //   muffinCheckbox.type = "checkbox";
    //   muffinCheckbox.className = "muffin-checkbox";
  
      let breakLine = document.createElement("br");
  
      let requestLabel = document.createElement("label");
      requestLabel.style.whiteSpace = "pre";
      requestLabel.style.fontFamily = "monospace"
      let requestIdStr = `Request ID: ${request.id}`.padEnd(18, " ");
      let amountStr = `| Amount: ${request.amount}`.padEnd(16, " ");
      let statusStr = `| Status: ${request.status}`.padEnd(18, " ");
      requestLabel.innerText = requestIdStr + amountStr + statusStr;

      let pendingRequestNode = document.getElementById("pending-requests");
      pendingRequestNode.append(requestLabel, breakLine);
    }
}

function renderResolvedRequests(jsonResolvedRequests){
  
    resolvedRequests = JSON.parse(jsonResolvedRequests);
    resolvedRequests.sort((a, b) => a.id - b.id);
  
    for (let request of resolvedRequests) {
  
      let breakLine = document.createElement("br");
  
      let requestLabel = document.createElement("label");
      requestLabel.style.whiteSpace = "pre";
      requestLabel.style.fontFamily = "monospace"
      let requestIdStr = `Request ID: ${request.id}`.padEnd(18, " ");
      let amountStr = `| Amount: ${request.amount}`.padEnd(16, " ");
      let statusStr = `| Status: ${request.status}`.padEnd(18, " ");
      requestLabel.innerText = requestIdStr + amountStr + statusStr;

      let resolvedRequestNode = document.getElementById("resolved-requests");
      resolvedRequestNode.append(requestLabel, breakLine);
    }
}

function createRequest(){

    let token = sessionStorage.getItem("token");
    let request_id = 0
    let amount = document.getElementById("reimbursement-amount").value;
    let type = "CREATE_REQUEST";
    let reimbursementToken = `${request_id}:${amount}:${type}`;

    createReimbursementRequest(token, reimbursementToken, requestCreationSuccessful, requestCreationFailed);
}

function requestCreationSuccessful(){
    window.alert("Succesfully created request!");
    updateRequestTables();
}

function requestCreationFailed(){
    window.alert("Unable to create request!");
}