/**
 * Created by alone on 2017/5/9.
 */
$(function () {
    //1 注册，2忘记密码
    var from_which = 0;
//            !!!!!旋转操作无论如何根据都是根据开始位置！！！
    $('.enter_password').hide(0);
    $('#action3').bind('click', function () {
        $('.enter_password').show(500);
        css3Transform(document.getElementsByClassName('content')[0], "rotateY(-90deg)");
        $('.forget_password').hide(500);
    });
    $('#action2').bind('click', function () {
        $('.enter_password').show(500);
        css3Transform(document.getElementsByClassName('content')[0], "rotateY(90deg)");
        $('.register_page').hide(500);
    });

    $('.go_back_login_from_forget').bind('click', function () {
        $('.enter_password').hide(500);
        css3Transform(document.getElementsByClassName('content')[0], "rotateY(0deg)");
        $('.register_page').show(500);
    });
    $('a.go_back_login').bind('click', function () {
        $('.forget_password').show(500);
        css3Transform(document.getElementsByClassName('content')[0], "rotateY(0deg)");
        $('.enter_password').hide(500);
    });

    // 跳转到注册输入密码
    $('.go_enter_password_button').bind('click', function () {
        from_which = 1;
        var name = $('.input_nickname').val();
        var phone = $('.register_input_phone').val();
        var code = $('.register_input_vcode').val();
        var token = $('.token').val();
        if (name===''||phone===''||code===''){
            alert('请填写信息');
            return;
        }
        $.ajax({
            url: 'checkCode.do',
            dataType: 'JSON',
            type: 'post',
            data: {name: name, phone: phone, code: code,token:token},
            success: function (data) {
                var result = data.result;
                if (result === 0) {
                    alert('验证码错误');
                } else if (result === 1) {
                    // 成功后执行以下代码进行跳转
                    $('.forget_password').show(1000);
                    css3Transform(document.getElementsByClassName('content')[0], "rotateY(-180deg)");
                    $('.login').hide(1000);
                    $('.confirm_register_button').html('注册');
                }
            }
        });
    });
  //一键催收小组作业
    $("div#action4>button.login_button").click(function () {
        var content="收货人姓名:<input id='teacher_id' type='text' />"+"</br>"
        fyAlert.alert({
            title   : "一键催收",
            drag    : true,
            content : content,
            btns    : {                  //按钮组
                '确定' : function(obj){
                    obj.destory();   //在页面上
                    var  id=$('input#teacher_id').val();
                    $.ajax({
                        url:'/sendEmailByTeacher.do',
                        type:'post',
                        dataType:'json',
                        data:{'id':id },
                        success: function (data) {
                            var result = data.result;
                            if(result=='0'){
                                fyAlert.msg('催收失败!',{icon:3,animateType:1} );
                                // window.location.href='/';
                            }else if(result=='1'){
                                fyAlert.msg('催收成功!',{icon:1,animateType:2} );
                                // window.location.href='/';

                            }
                        }

                    })
                },
                '取消' : function(obj){
                    obj.destory(); //销毁
                }
            }
        })
    })

// 催作业
    $('.forget_password_button').bind('click', function () {
        from_which = 2;
        // var name = $('.input_nickname').val();
        var name = $('.forget_input_phone').val();
        var time = $('.forget_input_vcode').val();
        if (name===''||time===''){
            alert('请填写信息');
            return;
        }
        $.ajax({
            url: '/sendCode.do',
            dataType: 'JSON',
            type: 'post',
            data: {name: name, time: time,action:'homework'},
            success: function (data) {
                var result = data.result;
                if (result === 0) {
                    alert('催收失败');
                } else if (result === 1) {
                   alert('催收成功');
                }
            }
        });
    });


    //周测
    $('.get_vcode_button').click(function () {
        var name = $('.input_nickname').val();
        var time = $('#time').val();
        $.ajax({
            url: 'sendCode.do',
            dataType: 'JSON',
            type: 'post',
            data: {name:name,time:time,action:'test'},
            success: function (date) {
                var result = date.result;
                if (result == 1) {
                    alert('已提醒'+name+'周测信息');
                } else {
                    alert('提醒失败');
                }
            }

        });
    });


//修改密码成功

    $('.go_back_up').bind('click', function () {
        if (from_which == 1) {
            $('.login').show(1000);
            css3Transform(document.getElementsByClassName('content')[0], "rotateY(-90deg)");
            $('.forget_password').hide(1000);
        } else if (from_which == 2) {
            $('.login').show(1000);
            css3Transform(document.getElementsByClassName('content')[0], "rotateY(90deg)");
            $('.register_page').hide(1000);
        }
    });
    $('.login_icon').animate({width: '10%'}, 0);
    $('.password_icon').animate({width: '10%'}, 0);
    $('.input_username').bind('focus', function () {
        $('.login_icon').animate({width: '0%'}, 500);
    });
    $('.input_username').bind('blur', function () {
        $('.login_icon').animate({width: '10%'}, 500);
    });
    $('.input_password').bind('focus', function () {
        $('.password_icon').animate({width: '0%'}, 500);
    });
    $('.input_password').bind('blur', function () {
        $('.password_icon').animate({width: '10%'}, 500);
    });
    function css3Transform(element, value) {
        var arrPriex = ["o", "ms", "Moz", "webkit", ""];
        var length = arrPriex.length;
        for (var i = 0; i < length; i += 1) {
            element.style[arrPriex[i] + "Transform"]
                = value;
        }
    }

    $('.n_span').animate({width: '10%', height: '70%'}, 0);
    $('.register_phone_svg').animate({width: '10%'}, 0);
    $('.input_nickname').bind('focus', function () {
        $('.n_span').animate({width: '0%', height: '0%'}, 500);
        $('.n_span').html('');
    });
    $('.input_nickname').bind('blur', function () {
        $('.n_span').html('N');
        $('.n_span').animate({width: '10%', height: '70%'}, 500);
    });
    $('.register_input_phone').bind('focus', function () {
        $('.register_phone_svg').animate({width: '0%'}, 500);
    });
    $('.register_input_phone').bind('blur', function () {
        $('.register_phone_svg').animate({width: '10%'}, 500);
    });
    $('.first_enter_password_icon').animate({width: '10%'}, 0);
    $('.confirm_password_icon').animate({width: '10%'}, 0);
    $('.first_enter_password_input').bind('focus', function () {
        $('.first_enter_password_icon').animate({width: '0%'}, 500);
    });
    $('.first_enter_password_input').bind('blur', function () {
        $('.first_enter_password_icon').animate({width: '10%'}, 500);
    });
    $('.confirm_password_input').bind('focus', function () {
        $('.confirm_password_icon').animate({width: '0%'}, 500);
    });
    $('.confirm_password_input').bind('blur', function () {
        $('.confirm_password_icon').animate({width: '10%'}, 500);
    });

    $('.register_phone_svg').animate({width: '10%'}, 0);
    $('.forget_input_phone').bind('focus', function () {
        $('.register_phone_svg').animate({width: '0%'}, 500);
    });
    $('.forget_input_phone').bind('blur', function () {
        $('.register_phone_svg').animate({width: '10%'}, 500);
    });




});