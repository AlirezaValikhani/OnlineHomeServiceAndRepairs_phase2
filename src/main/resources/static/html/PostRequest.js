$(document).ready(
    function (){
        $("customerRegister").submit(function(event){
            event.preventDefault();
            ajaxPost();
        });

        function ajaxPost() {
            var formData = {
                firstName : $("#firstName").val(),
                lastName : $("#lastName").val(),
                email : $("#email").val(),
                nationalCode : $("#nationalCode").val(),
                password : $("#password").val(),
                balance : $("#balance").val()
            }
        }
        $.ajax({
            type: "POST",
            url: "save",
            data: {
                id: $(this).val(), // < note use of 'this' here
                access_token: $("#access_token").val()
            },
            success: function(result) {
                alert('ok');
            },
            error: function(result) {
                alert('error');
            }
        });
    }