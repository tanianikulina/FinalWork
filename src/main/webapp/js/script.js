function sendMessage(message) {
    jQuery.ajax("/messages", {
        complete: function () { getNewMessages(); },
        contentType: 'application/json; charset=UTF-8',
        data: message,
        method: "POST"
    });
    jQuery.post("/messages", message, function () {getNewMessages()})
}

function updateMessagesList(newMessages) {
    var currentMessagesList = jQuery(".chat__messages_list");
    var currentMessages = currentMessagesList.val();
    newMessages.forEach(function (message) {
        currentMessages += message + "\n";
    });
    currentMessagesList.val(currentMessages)
}

function getNewMessages() {
    var now = (new Date()).toISOString();
    jQuery.get("/messages?date_time=" + now, null, function (newMessages) {
        updateMessagesList(newMessages);
    });
}

jQuery.ready(function () {
    jQuery(".chat__submit_button").click(function () {
        sendMessage(jQuery(".chat__new_message").val());
    });
    setInterval(getNewMessages, 5000)
});
