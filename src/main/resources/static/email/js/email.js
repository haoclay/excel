$(function () {
    $(".login-btn").click(function () {
        var target = $(".send-con").find(".login-user").filter(":first").children('input').val();
        var title = $(".send-con").find(".login-user").filter(":last").children('input').val();
        var content = $(".send-con").find("textarea#editor").val();
         var regex = /(<([^>]+)>)/ig;
         content = content.replace(regex,"");
         alert(target+":"+title+":"+content);

        $.ajax({
            url:'sendEmail',
            type:'post',
            // header:{
            //    'Accept':'application/json',
            //    'Content-Type': 'application/json'
            // },
            // contentType:"application/json",
            dataType:'json',
            data:{'target':target,'title':title,'content':content},
            success: function (data) {
                // var result = JSON.parse(data);
                // data.parseJSON();
               if(data.state==1){
                    alert("success")
               } else {
                   alert("error")
               }
            }
        })
    })
})