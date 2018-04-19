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
    console.log('change');
    $econtent.val(html);
};
editorZH.create();
editorEN.create();
// 初始化 textarea 的值
$content.val(editorZH.txt.html());
$econtent.val(editorEN.txt.html());
layui.use('form', function(){
    var form = layui.form;
    form.on('submit(*)', function(data){
        console.log('data: '+data);
        $.ajax({
            url:"/back/transfer/create_transfer_recharge.do",
            data:{
                "accountId":id,
                "userId":name,
                "amount":amount,
                "whichPage":1
            },
            dataType:"json",
            success:function(ret){
                if(ret.data&&ret.data.status==1){
                    $(".mask").hide();
                    Dialog.tip("提交成功!");
                }else{
                    $(".mask").hide();
                    Dialog.tip(ret.error_message);
                }
            },
            error:function(){
                $(".mask").hide();
                Dialog.tip("网络出错，请稍后再试");
            }
        })
        return true; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
});