function sendMessage(message) {
    jQuery.post("/messages", message, function () {
    getNewMessages();
    }, "application/json");
}

function updateMessagesList(newMessages) {
    var currentMessagesList = jQuery(".chat_messages_list");
    var currentMessages = currentMessagesList.val();
    //var currentMessages = "";
    var newMessageList = JSON.parse(newMessages);
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

function getOldMessages() {
    jQuery.get("/messages?first_time", null, function (newMessages) {
        updateMessagesList(newMessages);
    });
}

jQuery(document).ready(function () {
    jQuery(".chat_submit_button").click(function () {
        sendMessage(jQuery(".chat_new_message").val());
    });
    setInterval(getNewMessages, 5000)
});

function authorize() {
    jQuery.post("/auth", {
        login: jQuery(".chat_login_form_login").val();
        password: jQuery(".chat_login_form_password").val();
    }, function(result) {
        token = result;
        jQuery(".chat_login_form").hide();
        jQuery(".chat_messages_form").show();
        getOldMessages();
    });
}
