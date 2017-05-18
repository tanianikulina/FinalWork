var token = "";

function sendMessage(message) {
    jQuery.post("/messages", {"message": message, "token": token}, function () {
        getNewMessages();
    }, "application/json");
    jQuery(".chat_new_message").val("");
}

function updateMessagesList(newMessages) {
    var currentMessagesList = jQuery(".chat_messages_list");
    jQuery(".chat_messages_list").val("");
    var currentMessages = currentMessagesList.val();
    newMessages.forEach(function (message) {
        currentMessages += message + "\n";
    });
//    $.each(newMessages, function (message) {
//        currentMessages += message + "\n";
//    });
    currentMessagesList.val(currentMessages);
}

function getNewMessages() {
    jQuery.get("/messages", null, function (newMessages) {
        updateMessagesList(newMessages);
    });
}

jQuery(document).ready(function () {
    jQuery(".chat_submit_button").click(function () {
        sendMessage(jQuery(".chat_new_message").val());
    });
    jQuery(".chat_login_form_button").click(function () {
        authorize();
    });
});

function authorize() {
    jQuery.post("/auth", {
        login: jQuery(".chat_login_form_login").val(),
        password: jQuery(".chat_login_form_password").val()
    }, function(result) {
        token = result;
        jQuery(".chat_login_form").hide();
        jQuery(".chat_messages_form").show();
        jQuery.get("/messages", null, function (newMessages) {
            currentMessages = "";
            newMessages.forEach(function (message) {
                currentMessages += message + "\n";
            });
            jQuery(".chat_messages_list").val(currentMessages);
        });
        setInterval(getNewMessages, 2000);
    });
}


