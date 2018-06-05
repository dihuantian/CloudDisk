/*右键菜单和上传服务*/
var xhrOnProgress = function (fun) {
    xhrOnProgress.onprogress = fun; //绑定监听
    //使用闭包实现监听绑
    return function () {
        //通过$.ajaxSettings.xhr();获得XMLHttpRequest对象
        var xhr = $.ajaxSettings.xhr();
        //判断监听函数是否为函数
        if (typeof xhrOnProgress.onprogress !== 'function')
            return xhr;
        //如果有监听函数并且xhr对象支持绑定时就把监听函数绑定上去
        if (xhrOnProgress.onprogress && xhr.upload) {
            xhr.upload.onprogress = xhrOnProgress.onprogress;
        }
        return xhr;
    }
}
/*$('.div1').mouseRight({
    ele: '.div1',
    menu: [{
        itemName: "添加",
        icon: "fa fa-plus",
        callback: function () {
            alert('我是添加')
        }
    }, {
        itemName: "修改",
        icon: "fa fa-files-o",
        callback: function () {
            alert('我是修改')
        }
    }, {
        itemName: "粘贴",
        icon: "fa fa-clipboard",
        callback: function () {
            alert('我是粘贴')
        }
    }, {
        itemName: "删除",
        icon: "fa fa-trash",
        callback: function () {
            alert('我是删除')
        }
    }]
});*/
$('.dropdown-toggle').dropdown();
$('#file-click').click(function (e) {
    $('input[id=file]').click();
});
$('input[id=file]').change(function () {
    var formData = new FormData();
    var folder = $('.btn,btn-primary,dropdown-toggle,upload').attr('id');
    for (var i = 0; $('#file')[0].files.length > i; i++) {
        formData.append('files', $('#file')[0].files[i]);
    }
    formData.append('folderId', folder);
    if (files != "") {
        var title = '正在上传' + $('#file')[0].files.length + '个文件中...';
        var content = ''
        $.ajax({
            url: '/upload/files',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            xhr: xhrOnProgress(function (e) {
                var percent = e.loaded / e.total * 100;
                percent = percent.toFixed(2);
                $('body').find('.progress-bar,progress-bar-striped,active').css('width', percent + "%");
                $('body').find('#percent').text(percent + "%");
                /*if (e.loaded == e.total) {
                    checkProgress();
                }*/
            }),
            success: function (e) {
                if (e == "FOLDER_NOT_EXIT") {
                    title = '上传失败';
                    content = '不存在指定的上传文件夹!';
                } else if (e == "FILE_UPLOAD_SUCCESS") {
                    title = '上传成功';
                }
                $('body').find('#mySmallModalLabel').text(title + ',' + content);
            },
            error: function (e) {
                title = '上传失败';
                content = '连接服务器失败,请重新上传!';
            }
        });
        var html = '';
        html += '<div id="hint" class="modal fade bs-example-modal-sm in" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" style="display: block; padding-right: 17px;">'
        html += '<div class="modal-dialog modal-sm" role="document">';
        html += '<div class="modal-content">';
        html += '<div class="modal-header" style="height: 50px">';
        html += '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>'
        html += '<h4 class="modal-title" style="font-size: 14px" id="mySmallModalLabel">' + title + '</h4>';
        html += '</div>';
        html += '<div class="modal-body">' + content;
        html += '<div class="progress">'
        html += '<div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width: 0%;min-width: 2em;">';
        html += '<span id="percent">0</span>';
        html += '</div>';
        html += '</div>';
        html += '</div>';
        html += '</div>';
        html += '</div>';
        html += '</div>';
        html += '<div class="modal-backdrop fade in"></div>';
        $('body').addClass('modal-open');
        $('body').css('padding-right', 17);
        $('body').append(html);
    }
});

$('body').on('click', '[class=close]', function () {
    $('body').removeClass('modal-open');
    $('body').css('padding-right', 0);
    $('#hint').remove();
    $('.modal-backdrop,fade,in').remove();
    location.reload();
});

$('#upload-div').hover(function () {
    $('#upload-div').addClass("open")
    $('.btn,btn-primary,dropdown-toggle').attr('aria-expanded', true);
}, function () {

});
$('.div-content').css('width', $(document.body).width() - 225);

function checkProgress() {
    $.ajax({
        url: '/upload/progress',
        type: 'GET',
        processData: false,
        contentType: false,
        dataType: 'json',
        async: true,
        success: function (e) {
            if (e != null) {
                if (e.pContentLength == e.pBytesRead) {
                    $('body').find('#mySmallModalLabel').text("上传完成!");
                }
            }
        },
        error: function (e) {

        }
    });
}

var flag = false;
/*创建新的文件夹*/
$('#new-folder').click(function () {
    var html = '';
    html += '<tr data-index="-1" class="folder">';
    html += '<td style="">';
    html += '<span style="padding-left: 30px;line-height: 30px;font-size: 14px" title="文件夹名称">';
    html += '<span class="glyphicon glyphicon-folder-close" aria-hidden="true" style="color: #eea236;font-size: 25px;position: relative;top: 6px;">';
    html += '</span>';
    html += '<span class="folder" style="padding-left: 14px;">文件夹名称</span>';
    html += '</td>';
    html += '<td style="">';
    html += '<span style="line-height: 30px;font-size: 14px;">--</span>';
    html += '</td>';
    html += '<td style="">';
    html += '<span class="create-time" style="line-height: 30px;font-size: 14px;">--</span>';
    html += '</td>';
    html += '</tr>';
    if (!flag) {
        $('tbody').prepend(html);
        var newNode = $('tbody').children('tr').first().offset();
        $('#folder_name').css('top', newNode.top + 6);
        $('#folder_name').css('left', newNode.left + 17);
        $('#folder_name').css('display', 'block');
        flag = true;
    }
});

$('.rloaB9m').click(function () {
    if (($('tbody').children('tr').first()).attr('data-index') == -1) {
        $('#folder_name').css('display', 'none');
        $('tbody').children('tr').first().remove();
        flag = false;
    }
});

$('.fy51lb').click(function () {
    if (($('tbody').children('tr').first()).attr('data-index') == -1) {
        var filename = $('.GadHyA').val();
        if (filename == '') {

        } else {
            var parentFolder = $('#new-folder').attr('name');
            var folderName = $('.GadHyA').val();
            $.ajax({
                url: '/folder/add/',
                type: 'POST',
                data: {'parentFolder': parentFolder, 'folderName': folderName},
                dataType: 'json',
                async: true,
                success: function (data) {
                    if (data != null) {
                        $('#folder_name').css('display', 'none');
                        var nodes = $('tbody').children('tr').first();
                        nodes.attr('id', data.folderId)
                        nodes.find('.folder').text(data.folderName);
                        var date = new Date(data.createTime.toString());
                        var str = date.getFullYear() + "-" + date.getMonth() + 1 + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
                        nodes.find('.create-time').text(str);
                    }
                    flag = false;
                    location.reload();
                },
                error: function (data) {

                }
            });
        }
    }
});

$('#table').on('click-row.bs.table', function (e, row, element) {
    var folderId = $(element).attr('id');
    var type = $(element).attr('class');
    if (folderId != undefined && folderId != "" && type == "folder") {
        window.location.href = "/folder/id/" + folderId;
    }
});

var fileId = undefined;
/*文件分享*/

$('#create-link').click(function () {
    if (fileId != undefined) {
        $.ajax({
            url: '/share/add/' + fileId,
            type: 'GET',
            dataType: 'json',
            async: true,
            success: function (data) {
                if (data.hasOwnProperty('shareId')) {
                    $('#create-share').addClass('validity-section');
                    $('#share-message').removeClass('validity-section')
                    $('#exampleInputName2').val(data.password);
                    $('#exampleInputAmount').val(data.sharePath);
                }
            },
            error: function (data) {

            }
        });
    }
});

$('#share-message-close').click(function () {
    $('#share-message').addClass('validity-section');
    $('#create-share').removeClass('validity-section')
    $('#exampleInputName2').val("");
    $('#exampleInputAmount').val("");
    fileId = undefined;
});

var shareId = undefined;
$(function () {
    $('.share').click(function () {
        fileId = $(this).attr('fileId');
    });

    $('.closeShare').click(function () {
        shareId = $(this).attr('shareId');
    });

    $('#confirmCloseShare').click(function () {
        if (shareId != undefined) {
            $.ajax({
                url: '/share/delete/' + shareId,
                type: 'GET',
                dataType: 'json',
                async: true,
                success: function (data) {
                    if (data) {
                        $('#closeConfirmFrame').click();
                        location.reload();
                    } else {
                        $('.modal-body > p:first-child').text("取消分享失败。")
                        $('.modal-body > p:last-child').text("未知原因");
                    }
                },
                error: function (data) {
                    alert("服务器异常!")
                }
            });
        }
    });


    /*删除*/

    $('.deleteState').click(function () {
        var fileId = $(this).attr('fileId');
        var state = $(this).attr('state');
        if (fileId == undefined || fileId == '') {

        } else {
            $.ajax({
                url: '/file/delete/' + state + '/' + fileId,
                type: 'GET',
                async: true,
                success: function (data) {
                    var html = '<span class="reminder" style="background: #3b8cff;display: inline-block;width: 220px;height: 50px;\n' +
                        'line-height: 50px; color: #ffffff; position: absolute;left:calc(50% - 110px);top: 20px;text-align: center;\n' +
                        'border: 1px solid #3b8cff;box-shadow:0 0 8px 2px #3b8cff">data</span>'
                    $('body').append(html);
                    removeTr(fileId);
                    countdown();
                },
                error: function (data) {
                    var html = '<span class="reminder" style="background: #3b8cff;display: inline-block;width: 220px;height: 50px;\n' +
                        'line-height: 50px; color: #ffffff; position: absolute;left:calc(50% - 110px);top: 20px;text-align: center;\n' +
                        'border: 1px solid #3b8cff;box-shadow:0 0 8px 2px #3b8cff">服务器异常</span>'
                    $('body').append(html);
                    countdown();
                }
            });
        }
    });
    var clipboard = new ClipboardJS('#cpoy-share');
    clipboard.on('success', function (e) {
        alert("复制成功");
    });

    clipboard.on('error', function (e) {
        console.log(e);
    });
})

/*用户相关*/
$('#user').hover(function () {
    var css = $('#nav').hasClass('nav-mini');
    if (css) {
        $('.user-frame').css('display', 'block');
        $('.user-frame').css('top', 20);
        $('.user-frame').css('left', 80)
    } else {
        $('.user-frame').css('display', 'block');
        $('.user-frame').css('top', 20);
        $('.user-frame').css('left', 230)
    }

})

$('#nav').click(function () {
    var css = $(this).hasClass('nav-mini');
    if (css) {
        $('.user-frame').css('top', 20);
        $('.user-frame').css('left', 80)
    } else {
        $('.user-frame').css('top', 20);
        $('.user-frame').css('left', 230)
    }
});

$('.user-open-close').on('click hover', function () {
    $('.user-frame').css('display', 'none');
});


function countdown() {
    $('body').oneTime('3s', 'Q', function () {
        $('.reminder').click();
    });
}

$('body').on('click', '.reminder', function () {
    $(this).remove();
    $('body').stopTime('Q');
});

function removeTr(id) {
    var fileId = '#' + id;
    $('#table').find('' + fileId + '').remove();
}

/*复制*/
var clipboard = new ClipboardJS('#copy-link-pass', {
    text: function () {
        var link = "分享链接:" + $('#exampleInputAmount').val();
        var pass = "密码:" + $('#exampleInputName2').val();
        return link +  pass;;
    }
});
clipboard.on('success', function (e) {
    alert("复制成功");
});

clipboard.on('error', function (e) {
    console.log(e);
});