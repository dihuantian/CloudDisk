$('.g-button,g-button-blue-large').click(function () {
    var password = $('input[name=password]').val();
    var shareId = $('input[id=shareId]').val();
    var address = '/share/verify/' + shareId + '/' + password;
    if (password != undefined && password != "") {
        if (password.length == 6) {
            $.ajax({
                url: address,
                type: 'GET',
                async: true,
                success: function (data) {
                    if (data == "SHARE_VERIFY_THROUGH") {
                        window.location.href = "/share/get/" + shareId;
                    } else if (data == "SHARE_PASSWORD_ERROR") {
                        $('#lgscQ850').text("提取密码错误!");
                    }
                },
                error: function (data) {
                    $('#lgscQ850').text("服务器异常!");
                }
            });
        } else {
            $('#lgscQ850').text("密码长度必须为6个字符!");
        }
    } else {
        $('#lgscQ850').text("密码不能空!");
    }
});