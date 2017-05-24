var token = "";
var tmp = "";

function formatTime(time) {
    time.setHours(time.getHours() + 3); //почему оно вообще отставало на 3 часа?..
    time = time.toISOString().replace("T", " ").replace("Z", "");
    return time;
}

function sendMessage(message) {
    jQuery.post("/messages", {"message": message, "token": token}, function () {
        getNewMessages();
    }, "application/json");
    jQuery(".chat_new_message").val("");
}

function updateMessagesList(newMessages) {
    var currentMessagesList = jQuery(".chat_messages_list");
    var currentMessages = currentMessagesList.val();
    newMessages.forEach(function (message) {
        currentMessages += message + "\n";
    });
    currentMessagesList.val(currentMessages);
}

function getNewMessages() {
    tmp = formatTime(tmp);
    //console.log(tmp);
    jQuery.get("/messages", {"time": tmp}, function (newMessages) {
        updateMessagesList(newMessages);
    });
    tmp = new Date();
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
        tmp = new Date();
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


