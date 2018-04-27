define([
    '/widget/ui/utils/utils.js',
    "/widget/ui/dialog/dialog.js",
    '/widget/ui/form-handle/form-handle.js',
    '/widget/ui/pagination/pagination.js'
],function(Utils,Dialog,formHandle,Pagination){
    var $mod = $('.resource-manage');
    var $pagination = $mod.find('.pagination');
    var listCount = window.PageData.listCount;
    var init = function(){
        bindEvent();
        initPagination();
    }()

    function initPagination(){
        var urlData = Utils.getUrlParams();
        var curPage = Number(urlData.whichPage);
        console.log(curPage);
        pagination = new Pagination({
            container:$pagination,
            perCount:8,
            totalCount:listCount,
            onRefresh:function(curPage,callback){
                callback(listCount);
            }
        })
        pagination.initPage(curPage);
        pagination.on("refreshinstance", function (cur) {
            var param = $.param($.extend(urlData,{whichPage:cur}));
            console.log(param);
            location.href = "/resource-manage.do?" + param;
        });
    }

    function bindEvent(){

        // 删除新闻
        $mod.find(".delete").on("click",function(){
            var id=$(this).data("id");
            console.log(id);
            var str="确认删除吗？";
            if(confirm(str)){
                $.ajax({
                    url:"resource-delete.do",
                    data:{
                        "resourceId":id
                    },
                    dataType:"json",
                    success:function(ret){
                        if(ret.data&&ret.error_no==0){
                            Dialog.tip("已删除");
                            setTimeout(function(){//两秒后跳转
                                window.location.reload();
                            },2000);

                        }else{
                            Dialog.tip(ret.error_message);
                        }
                    },
                    error:function(){
                        Dialog.tip("网络出错，请稍后再试");
                    }
                })
            }
        });
        //新闻修改
        $mod.find(".update").on("click",function(){
            var id=$(this).data("id");
            location.href = "/resource-change-page.htm?resourceId=" + id;
        });
    }

})