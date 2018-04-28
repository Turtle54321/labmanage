(function () {
    var $reset=$("#reset");
    var $exit=$("#exit");
    var userName=$reset.data("name");
    var url=$reset.data("url");

    // $reset.on("click",function(){
    //     var msg="确定修改用户"+userName+"的密码吗？";
    //     if(confirm(msg)){
    //         location.href="/back/user/getDisplayMofidyPwdPage.do?strBackUrl="+url;
    //     }
    // });

    $exit.on("click",function(){
        var msg="确定要退出吗？";
        if(confirm(msg)){
            location.href="/exit.do";
        }
    });

    $(".mod-adming-nav .account-wrap").hover(function() {
         $(this).children("ul").stop(true, true).slideDown(100);
    },function(){
         $(this).children("ul").stop(true, true).slideUp("fast");
    })
})();

