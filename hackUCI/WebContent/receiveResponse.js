function handleTransactionResult(resultDataString) {
    resultDataJson = resultDataString;


    // If login succeeds, it will redirect the user to index.html
    if (resultDataJson["status"] === "success") {
    	console.log("show success message");
        window.location.replace("index.html");
    } else {
        console.log("show error message");
    }
    
    $("#insert_error_message").text(resultDataJson["message"]);
}

function handleUserinput(cartEvent) {
    console.log("submit Transactional Info");
    /**
     * When users click the submit button, the browser will not direct
     * users to the url defined in HTML form. Instead, it will call this
     * event handler when the event is triggered.
     */
    cartEvent.preventDefault();
    

    $.get(
        "api/userInput",
        // Serialize the cart form to the data sent by POST request
        $("#userInput").serialize(),
        (resultDataString) => handleTransactionResult(resultDataString)
    );
}

function handleProfData(profData) {
    let prof = jQuery("#prof");
    let profStr = "";
    for (let i = 0; i < profData.length; i++) {
    	profStr += "<option>" + profData[i]["tFname"] + " " + profData[i]["tLname"] + "</option>";
    }
    prof.append(profStr);
}

jQuery.ajax({
    dataType: "json", // Setting return data type
    method: "GET", // Setting request method
    url: "api/prof", // Setting request url, which is mapped by StarsServlet in Stars.java
    success: (resultData) => handleProfData(resultData) // Setting callback function to handle data returned successfully by the StarsServlet
});


$("#userInput").submit((event) => handleUserinput(event));