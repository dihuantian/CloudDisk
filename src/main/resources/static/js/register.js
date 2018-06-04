$('.lang-btn').click(function () {
    var email = $('#email').val();
    if (!isEmail(email)) {
        $('#email_error').removeClass('hide');
        $('#register_mes').text('请输入正确的邮箱!');
        return;
    }
    var password1 = $('#passport').val(), passwrod2 = $('#passport2').val(), code = $('#veri-code').val();
    if (checkCode(code) && checkPass(password1, passwrod2)) {
        var data = {'email': email, 'password': password1, 'code': code};
        $.ajax({
            url: '/security/register',
            type: 'post',
            data: data,
            async: true,
            success: function (data) {
                console.log(data);
                if (data == 'REGISTER_CODE_ERROR') {
                    $('#code_error').removeClass('hide');
                } else if (data == "REGISTER_EMAIL_EXIT") {
                    $('#email_error').removeClass('hide');
                } else if (data == "REGISTER_SUCCESS") {
                    $('#register_mes').text('注册成功!');
                    $('#email_error').removeClass('hide');
                } else if (data == "USER_LOGIN_STATUS") {
                    $('#email_error').removeClass('hide');
                    $('#register_mes').text('注册失败,用户处于登录状态!!');
                }
            },
            error: function (data) {
                console.log(data);
                $('#email_error').removeClass('hide');
                $('#register_mes').text('网络连接超时!');
            }
        })
    }
});

function checkPass(password1, password2) {
    if (password1.length > 6 && password2.length > 6) {
        if (password1 == password2) {
            return true;
        }
    }
    $('.tel-warn,confirmpwd-err,hide').removeClass('hide');
    $('.tel-warn,pwd-err,hide').removeClass('hide');
    return false
}

function checkCode(code) {
    if (code == '' || code.length < 6 || code.length > 6) {
        $('#code_error').removeClass('hide');
        $('#code_error_mes').text('验证码必须是6位!');
        return false;
    }
    if (code.length == 6) {
        return true;
    }
}

$('.send').click(function () {
    var already = $(this).attr('disabled');
    var email = $('#email').val();
    if (already != undefined || email == '') {
        if (email == '') {
            $('#email_error').removeClass('hide');
            $('#register_mes').text('邮箱不能为空!');
        }
        else {
            $('#code_error').removeClass('hide');
            $('#code_error_mes').text('验证码已经发送,请稍后!');
        }
    } else {
        if (!isEmail(email)) {
            $('#email_error').removeClass('hide');
            $('#register_mes').text('请输入正确的邮箱!');
            return;
        }
        $.ajax({
            url: '/email/verification/code',
            type: 'POST',
            data: {email: email},
            async: true,
            success: function (data) {
                if (data == 'true') {
                    $('#code_error').removeClass('hide');
                    $('#code_error_mes').text('验证码已经发送,请稍后!');
                    $('.send').attr("disabled", true);
                    startTimerA();
                }
                else if (data == 'false') {
                    $('#code_error').removeClass('hide');
                    $('#code_error_mes').text('验证码发送失败!');
                }
                else {
                    $(this).text('剩余:' + data + '秒');
                }
            },
            error: function () {
                $('#email_error').removeClass('hide');
                $('#register_mes').text('网络连接超时!');
            }
        })
    }
});

function startTimerA() {
    $('body').everyTime('1s', 'A', function () {
        $.ajax({
            url: '/email/verification/timeout',
            type: 'POST',
            async: true,
            success: function (data) {
                if (data >= 0) {
                    $('#code_error_mes').text('剩余:' + data + '秒');
                } else if (data < 0) {
                    $('.send').text('发送验证码');
                    $('.send').removeAttr("disabled");
                    $('#code_error').addClass('hide');
                    stopTimer('A');
                }
            }
            ,
            error:
                function () {
                    $('#email_error').removeClass('hide');
                    $('#register_mes').text('网络连接超时!');
                }
        })
    });
}

function stopTimer(name) {
    $('body').stopTime(name);
}

function isEmail(val) {
    var pattern = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
    var domains = ["qq.com", "163.com", "vip.163.com", "263.net", "yeah.net", "sohu.com", "sina.cn", "sina.com", "eyou.com", "gmail.com", "hotmail.com", "42du.cn"];
    if (pattern.test(val)) {
        var domain = val.substring(val.indexOf("@") + 1);
        for (var i = 0; i < domains.length; i++) {
            if (domain == domains[i]) {
                return true;
            }
        }
    }
    return false;
}
