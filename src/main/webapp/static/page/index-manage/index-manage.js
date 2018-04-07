define([
    '/widget/ui/utils/utils.js',
    '/widget/ui/pagination/pagination.js',
    '/widget/ui/form-handle/form-handle.js'
], function (Utils, Pagination, Handle) {
    var $mod = $('.page-admin-check-reconciliation');
    var $form = $mod.find('.form');
    var $pagination = $mod.find('.pagination');
    var $fds = $mod.find('.fds');
    var $depositor = $mod.find('.depositor');
    var listCount = window.PageData.listCount;
    var depositorStatus = window.PageData.depositorStatus;
    var fdsStatus = window.PageData.fdsStatus;
    var pagination;
    var form = new Handle({form: $form, uploadType: 'other', is_check: false});

    var init = function () {
        initDatePicker();
        bindEvent();
        initPagination();
    }();


    // 刷新页面数据回填
    if (fdsStatus != "") {
        $fds.val(fdsStatus);
    }
    if (depositorStatus != "") {
        $depositor.val(depositorStatus);
    }

    //日历插件
    function initDatePicker() {
        $('.datePicker').datetimepicker({
            locale: 'zh-cn',
            format: 'Y-m-d H:i',
            sideBySide: true
        });
    }

    function bindEvent() {
        //表单
        form.on('submit-data', function (data) {
            var _data = $.extend({}, data);
            _data.date = _data.date ? new Date(_data.date) / 1000 : '';
            location.href = '/back/balance/getBalanceRecord.do?' + $.param(_data);

        })

    }

    //分页功能
    function initPagination() {
        var urlData = Utils.getUrlParams();
        var curPage = Number(urlData.whichPage) || 1;

        pagination = new Pagination({
            container: $pagination,
            perCount: 20,
            totalCount: listCount,
            onRefresh: function (curPage, callback) {
                callback(listCount)
            }
        });
        pagination.initPage(curPage);
        pagination.on("refreshinstance", function (cur) {
            var param = $.param($.extend(urlData, {whichPage: cur}))
            location.href = "/back/balance/getBalanceRecord.do?" + param;
        });
    }
});