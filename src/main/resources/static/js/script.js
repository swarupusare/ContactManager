//function toggleResetPswd(e){
//    e.preventDefault();
//    $('#logreg-forms .form-signin').toggle() // display:block or none
//    $('#logreg-forms .form-reset').toggle() // display:block or none
//}
//
//function toggleSignUp(e){
//    e.preventDefault();
//    $('#logreg-forms .form-signin').toggle(); // display:block or none
//    $('#logreg-forms .form-signup').toggle(); // display:block or none
//}
//
//$(()=>{
//    // Login Register Form
//    $('#logreg-forms #forgot_pswd').click(toggleResetPswd);
//    $('#logreg-forms #cancel_reset').click(toggleResetPswd);
//    $('#logreg-forms #btn-signup').click(toggleSignUp);
//    $('#logreg-forms #cancel_signup').click(toggleSignUp);
//})
function togglesidebar() {


}
const toggleSidebar = () => {

    let v = $(".sidebar").css("visibility");
    if (v === "visible") {
        $(".sidebar").css("display", "none");
        $(".content").css("margin-left", "0%");
        $(".content").css("width", "100%");
        $(".sidebar").css("visibility", "hidden");
    } else {
        $(".sidebar").css("display", "block");
        $(".content").css("margin-left", "20%");
        $(".content .mar").css("margin", "10px");


        $(".sidebar").css("visibility", "visible");
    }
//    if ($(".sidebar").is(":visible")) {
//        $(".sidebar").css("display", "none");
//        $(".content").css("margin-left", "0%");
//        $(".content").css("width", "100%");
//    } else {
//        $(".sidebar").css("display", "block");
//        $(".content").css("margin-left", "20%");
//    }
};
$("#formpayment").submit(function (e) {
    e.preventDefault();
    console.log("Payment Started...");
    let amount = $("#amountField").val();
    console.log(amount);
    if (amount === "" || amount === null) {
        alert("Please Enter Your Amount!!!");
        return;
    }
    $.ajax(
            {
                url: 'makepayment',
                data: JSON.stringify({amount: amount, info: 'order_request'}),
                contentType: 'application/json',
                type: 'POST',
                dataType: 'json',
                success: function (response) {
                    //this function invoke when success
                    console.log(response);
                    if (response.status === "created") {
                        var options = {
                            "key": "rzp_test_3cu0AsMC3QBebI", // Enter the Key ID generated from the Dashboard
                            "amount": response.amount, // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
                            "currency": "INR",
                            "name": "Digital Contact Manager",
                            "description": "Test Transaction",
                            "image": "/Img/my.jpg",
                            "order_id": response.id, //This is a sample Order ID. Pass the `id` obtained in the response of Step 1
                            "handler": function (response) {
                                alert(response.razorpay_payment_id);
                                alert(response.razorpay_order_id);
                                alert(response.razorpay_signature);
                            },
                            "prefill": {
                                "name": "Gaurav Kumar",
                                "email": "gaurav.kumar@example.com",
                                "contact": "9999999999"
                            },
                            "notes": {
                                "address": "Razorpay Corporate Office"
                            },
                            "theme": {
                                "color": "#3399cc"
                            }
                        };
                        var rzp1 = new Razorpay(options);
                        rzp1.on('payment.failed', function (response) {
                            alert(response.error.code);
                            alert(response.error.description);
                            alert(response.error.source);
                            alert(response.error.step);
                            alert(response.error.reason);
                            alert(response.error.metadata.order_id);
                            alert(response.error.metadata.payment_id);
                        });
                         rzp1.open();
                        
                    }
                },
                error: function (error) {
                    //invoked when error
                    console.log(error);
                    alert('something went wrong!');

                }

            }
    );

});