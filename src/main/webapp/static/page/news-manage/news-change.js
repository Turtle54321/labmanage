define([
    '/widget/ui/form-handle/form-handle.js',
    '/widget/ui/utils/utils.js',
    "/widget/ui/dialog/dialog.js"
],function(formHandle,Utils,Dialog){
    var E = window.wangEditor;
    var editorZH = new E('#editorZH');
    var editorEN = new E('#editorEN');

    var $content = $('#content');
    var $econtent = $('#econtent');
    editorZH.customConfig.onchange = function (html) {
        // 监控变化，同步更新到 textarea
        $content.val(html);
    };
    editorEN.customConfig.onchange = function (html) {
        // 监控变化，同步更新到 textarea
        $econtent.val(html);
    };
    editorZH.create();
    editorEN.create();
    // 初始化 textarea 的值
    $content.val(editorZH.txt.html());
    $econtent.val(editorEN.txt.html());

    var $mod = $('.news-change');
    var $form = $mod.find('.form');
    var form = new formHandle({form:$form,uploadType:'ajax',is_check:false});

    form.on('ajax-data',function(data,cb){
        console.log(data)
        var _data = {
            id:data.data['id'],
            type:data.data['type'],
            title:data.data['title'],
            etitle:data.data['etitle'],
            content:data.data['content'],
            econtent:data.data['econtent']
        }
        cb(_data);
    });

    //成功回调
    form.on('ajax-success',function(ret){
        Dialog.tip("提交成功");
        var type = $('#type').val();
        var url;
        if (type == 1){
            url = "/news-manage.htm";
        }
        else if (type == 2){
            url = "/project-manage.htm";
        }
        else if (type == 3){
            url = "/photo-manage.htm";
        }
        else{
            console.log(type+"?whichPage=1&perCount=8");
        }

        setTimeout(function(){//两秒后跳转

            location.href = url+"?whichPage=1&perCount=8";
        },2000);

    })

    //错误回调
    form.on('ajax-error',function(ret){
        Dialog.tip(ret.error_message);
    })

})