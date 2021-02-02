document.getElementById("login-nav-opt").addEventListener("click", openModal);
document.getElementById("close-btn").addEventListener("click", closeModal);
document.getElementById("submit-btn").addEventListener("click", attemptLogin);

function openModal(){
    if(!sessionStorage.getItem("token")){
        $('#loginModal').modal('show');
    }else{
        log.warn("You cannot login while logged in")
    }
}

function closeModal(){
    $('#loginModal').modal('hide');
}

function attemptLogin(){
    let type = null
    let employeeAccountTypeInput = document.getElementById("employee-account-type");
    let managerAccountTypeInput = document.getElementById("manager-account-type");

    if(employeeAccountTypeInput.checked){
        type = employeeAccountTypeInput.value;
    }else if(managerAccountTypeInput.checked){
        type = managerAccountTypeInput.value;
    }
    let username = document.getElementById("username-input").value;
    let password = document.getElementById("password-input").value;
    // let credentials = {type, username, password};

    // console.log(credentials);
    performAjaxPostRequest(loginUrl, JSON.stringify({type, username, password}), handleSuccessfulLogin, handleUnsuccessfulLogin);
}

function handleSuccessfulLogin(responseText){
    console.log("Success! You're logged in");
    document.getElementById("error-msg").hidden = true;
    closeModal();
    sessionStorage.setItem("token", responseText);
    updateDisplay()
    toggleLoginToLogout();
}

function handleUnsuccessfulLogin(){
    console.log("Login unsuccessful");
    document.getElementById("error-msg").hidden = false;
}

function toggleLoginToLogout(){
    let loginBtn = document.getElementById("login-nav-opt");
    loginBtn.innerText = "Log out";
    loginBtn.removeEventListener("click", openModal);
    loginBtn.addEventListener("click", logout);
}

function logout(){
    sessionStorage.removeItem("token");
    toggleLogoutToLogin();
    updateDisplay();
}

function toggleLogoutToLogin(){
    let loginBtn = document.getElementById("login-nav-opt");
    loginBtn.innerText = "Log in";
    loginBtn.addEventListener("click", openModal);
    loginBtn.removeEventListener("click", logout);
}

/*
window.onload = function(){
    let token = sessionStorage.getItem("token");
    // if this token is null, redirect
    // send an ajax request validating the token
    // if token valid? stay on page
    // if token is invalid, redirect
}
*/