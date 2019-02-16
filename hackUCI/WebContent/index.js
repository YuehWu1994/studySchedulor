function handleTransactionResult(resultDataString) {
    resultDataJson = JSON.parse(resultDataString);

    console.log("handle transaction response");
    console.log(resultDataJson);
    console.log(resultDataJson["status"]);

    // If login succeeds, it will redirect the user to index.html
    if (resultDataJson["status"] === "success") {
        window.location.replace("confirmation.html");
    } else {
        // If login fails, the web page will display 
        // error messages on <div> with id "login_error_message"
        console.log("show error message");
        console.log(resultDataJson["message"]);
        $("#transaction_error_message").text(resultDataJson["message"]);
    }
}

function handleCheckout(cartEvent) {
    console.log("submit Transactional Info");
    /**
     * When users click the submit button, the browser will not direct
     * users to the url defined in HTML form. Instead, it will call this
     * event handler when the event is triggered.
     */
    cartEvent.preventDefault();

    $.get(
        "api/checkout",
        // Serialize the cart form to the data sent by POST request
        $("#checkout").serialize(),
        (resultDataString) => handleTransactionResult(resultDataString)
    );
}


function handleProfData(profData) {

    let prof = jQuery("#prof");
    
    let profStr = "";
    profStr += "<label for='InputProf'>Professor</label>"
    profStr += "<select class='form-control'>"
	
    for (let i = 0; i < profData.length; i++) {
    	profStr += "<option>" + profData[i]["tFname"] + " " + profData[i]["tLname"] + "</option>";
    }
    profStr += "</select>";
    prof.append(profStr);
}

jQuery.ajax({
    dataType: "json", // Setting return data type
    method: "GET", // Setting request method
    url: "api/prof", // Setting request url, which is mapped by StarsServlet in Stars.java
    success: (resultData) => handleProfData(resultData) // Setting callback function to handle data returned successfully by the StarsServlet
});



$("#checkout").submit((event) => handleCheckout(event));