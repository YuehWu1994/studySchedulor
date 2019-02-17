function handleTransactionResult(resultDataString) {
    resultDataJson = resultDataString;

    console.log("hello")
    alert(resultDataJson["output"]);
    window.location.replace("receiveResponse.html");
    
    //$("#insert_error_message").text(resultDataJson["message"]);
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
        "api/calendar",
        // Serialize the cart form to the data sent by POST request
        $("#calendar").serialize(),
        (resultDataString) => handleTransactionResult(resultDataString)
    );
}


$("#calendar").submit((event) => handleUserinput(event));