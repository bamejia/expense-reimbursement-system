const baseUrl = "http://localhost:8080/p1";

const loginUrl = baseUrl + "/login";
const userUrl = baseUrl + "/users";
const reinbursementUrl = baseUrl + "/reimbursements"

// const breadUrl = itemsUrl + "?type=bread";
// const muffinUrl = itemsUrl + "?type=muffin";

function performAjaxGetRequest(url, callback){
    const xhr = new XMLHttpRequest();
    xhr.open("GET", url);
    xhr.onreadystatechange = function(){
        if(xhr.readyState==4){
            if(xhr.status >= 200 && xhr.status < 300){
                callback(xhr.response); // this is going to be the response body of our http response  (JSON)
            }else{
                // console.log("status: " + xhr.status);
                console.warn("status was not in the 200s");
            }
        }
    }
    xhr.send();
}

function performAjaxPostRequest(url, payload, successCallback, failureCallback){
    const xhr = new XMLHttpRequest();
    xhr.open("POST", url);
    xhr.onreadystatechange = function(){
        if(xhr.readyState==4 ){
            if(xhr.status>199 && xhr.status<300){
                successCallback(xhr.response); // this is going to be the response body of our http response  (JSON)
            } else {
                if(failureCallback){
                    failureCallback()
                } else{
                    console.error("An error occurred while attempting to create a new record")
                }
            }
        }
    }
    xhr.send(payload);
}

function performAjaxPutRequest(url, payload, successCallback, failureCallback){
    const xhr = new XMLHttpRequest();
    xhr.open("PUT", url);
    xhr.onreadystatechange = function(){
        if(xhr.readyState==4 ){
            if(xhr.status>199 && xhr.status<300){
                successCallback(xhr.response); // this is going to be the response body of our http response  (JSON)
            } else {
                if(failureCallback){
                    failureCallback()
                } else{
                    console.error("An error occurred while attempting to create a new record")
                }
            }
        }
    }
    xhr.send(payload);
}


// function getToken(callback){
    
//     performAjaxGetRequest(loginUrl, callback);
// }

// function getManagerToken(callback){
//     performAjaxGetRequest(managerLogin, callback);
// }
// sessionStorage.getItem("token")
function getUserInfo(token, callback){
    performAjaxGetRequest(userUrl + `?token=${token}`, callback);
}

function getReimbursementInfo(token, rToken, callback){
    performAjaxGetRequest(reinbursementUrl + 
        `?token=${token}&reimbursementToken=${rToken.id}:${rToken.amount}:${rToken.type}`, callback);
}

function createReimbursementRequest(token, reimbursementToken, successCallback, failureCallback){
    payload = JSON.stringify({token, reimbursementToken});
    // console.log(payload);
    performAjaxPutRequest(reinbursementUrl, payload, successCallback, failureCallback);
}

function resolveReimbursementRequest(token, reimbursementToken, successCallback, failureCallback){
    payload = JSON.stringify({token, reimbursementToken});
    performAjaxPostRequest(reinbursementUrl, payload, successCallback, failureCallback);
}