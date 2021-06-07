document.getElementById("resolve-requests").addEventListener("click", resolveRequests);

window.onload = function () {
    // getTest((x) => console.log(x));
    // getManagerAccount(updateDisplay)
    // sessionStorage.setItem("token", "1:employee")
    // sessionStorage.removeItem("token");
    // getPendingRequestsByEmployeeId(0);
    // getResolvedRequestsByEmployeeId(0);
    getAllPendingRequests();
    updateDisplay();
}

function updateDisplay(){
    updateNavbar();
}

function updateNavbar(){
    let token = sessionStorage.getItem("token");
    if(!token){
        document.getElementById('employee-requests-nav-opt').setAttribute("hidden", "true");
        document.getElementById('manager-requests-nav-opt').setAttribute("hidden", "true");
        document.getElementById('home-nav-opt').setAttribute("hidden", "true");
    }else{
        document.getElementById('home-nav-opt').removeAttribute("hidden");
        let parsedToken = token.split(":");
        if(parsedToken[1] == "employee"){
            document.getElementById('employee-requests-nav-opt').removeAttribute("hidden");
        }else if(parsedToken[1] == "manager"){
            document.getElementById('manager-requests-nav-opt').removeAttribute("hidden");
        }
    }
}

function getAllPendingRequests(){
    let token = sessionStorage.getItem("token");
    let id = 0;
    let amount = 0;
    let type = "ALL_PENDING_REQUESTS"
    let rToken = {id, amount, type};

    getReimbursementInfo(token, rToken, renderPendingRequests)
}

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

    let pendingRequestNode = document.getElementById("pending-requests");
    pendingRequests = JSON.parse(jsonPendingRequests);
  
    for (let request of pendingRequests) {
        let breakLine = document.createElement("br");
    
        let requestSpan = document.createElement("span");
        let requestIdStr = `Request ID: ${request.id}`.padEnd(18, " ");
        let employeeIdStr = `| Employee ID: ${request.employeeId}`.padEnd(20, " ");
        let amountStr = `| Amount: ${request.amount}`.padEnd(16, " ");
        let statusStr = `| Status: ${request.status}`.padEnd(18, " ");

        let requestSpanText = requestIdStr + employeeIdStr + amountStr + statusStr;
        requestSpanText = requestSpanText.padEnd(120, ".");
        requestSpanText += " ";
        // requestSpanText = requestSpanText.split('').map(function(c) { return c === ' ' ? '&nbsp;' : c }).join('');
        requestSpan.textContent = requestSpanText;
        requestSpan.setAttribute("style", "font-family:monospace");
        requestSpan.style.whiteSpace = "pre";
        
        let requestAcceptCheckbox = document.createElement("input");
        requestAcceptCheckbox.value = request.id;
        requestAcceptCheckbox.type = "radio";
        requestAcceptCheckbox.name = "request-checkbox-" + request.id;
        requestAcceptCheckbox.id = "request-checkbox-accept-" + request.id;
        requestAcceptCheckbox.className = "request-checkbox-accept";

        let requestAcceptLabel = document.createElement("label");
        requestAcceptLabel.textContent = "Accept".padEnd(8, " ");
        requestAcceptLabel.style.whiteSpace = "pre";

        let requestDeclineCheckbox = document.createElement("input");
        requestDeclineCheckbox.value = request.id;
        requestDeclineCheckbox.type = "radio";
        requestDeclineCheckbox.name = "request-checkbox-" + request.id;
        requestDeclineCheckbox.id = "request-checkbox-decline-" + request.id;
        requestDeclineCheckbox.className = "request-checkbox-decline";

        let requestDeclineLabel = document.createElement("label");
        requestDeclineLabel.innerText = "Decline";
    
        pendingRequestNode.append(requestSpan, requestAcceptCheckbox, requestAcceptLabel,
            requestDeclineCheckbox, requestDeclineLabel, breakLine);
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

// function createRequest(){

//     let token = sessionStorage.getItem("token");
//     let id = 0
//     let amount = document.getElementById("reimbursement-amount").value;
//     let type = "CREATE_REQUEST";
//     let reimbursementToken = `${id}:${amount}:${type}`;

//     createReimbursementRequest(token, reimbursementToken, requestCreationSuccessful, requestCreationFailed);
// }

function resolveRequests(){
    let acceptedPendingRequests = document.getElementsByClassName("request-checkbox-accept");
    let declinedPendingRequests = document.getElementsByClassName("request-checkbox-decline");
    let token = sessionStorage.getItem("token")

    console.log(acceptedPendingRequests);
    console.log(declinedPendingRequests);

    for(let request of acceptedPendingRequests){
        console.log(request);
        if(request.checked){
            let id = request.value
            let amount = 0;
            let type = "ACCEPT_REQUEST";
            let reimbursementToken = `${id}:${amount}:${type}`;

            resolveReimbursementRequest(token, reimbursementToken, requestCreationSuccessful, requestCreationFailed);
        }
    }
    for(let request of declinedPendingRequests){
        console.log(request)
        if(request.checked){
            let id = request.value
            let amount = 0;
            let type = "DECLINE_REQUEST";
            let reimbursementToken = `${id}:${amount}:${type}`;

            resolveReimbursementRequest(token, reimbursementToken, requestCreationSuccessful, requestCreationFailed);
        }
    }

    location.reload();
}

function requestCreationSuccessful(){
    window.confirm("Succesfully resolved request!");
}

function requestCreationFailed(){
    window.confirm("Unable to resolve request!");
}