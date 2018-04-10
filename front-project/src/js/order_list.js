define(['jquery', "components", "common", "template", "weui", "updown"], function (jquery, components, common, template, weui, updown) {
    var $orderList = $("#order-box");
    var _payState = '';
    var _paymentId = '2';
    var _logisticsState = '1';
    var page = 0;
    var size = 5;
    $('#order-box').css('height', document.documentElement.clientHeight - 103);
    $(".cover-floor").height(document.documentElement.clientHeight - 108);
    var searchPro = $('#order-box').dropload({
        autoLoad: false,//自动加载
        domDown: {
            domNoData: '<div class="dropload-noData">没有了~</div>'
        },
        loadDownFn: function (me) {//加载更多
            page++;
            var result = '';
            $.ajax({
                type: 'GET',
                url: apiUrl + "/front/order/order/getOrderByPage",
                data: "pageNo=" + page + "&pageSize=" + size + "&payState=" + _payState + "&paymentId=" + _paymentId + "&logisticsState=" + _logisticsState,
                dataType: 'json',
                xhrFields: {
                    withCredentials: true
                },
                crossDomain: true,
                success: function (msg) {
                    var res = msg.res;
                    if (res !== 0 && msg.obj && msg.obj.dataList.length > 0) {
                        msg = msg.obj;
                        for (var i = 0; i < msg.dataList.length; i++) {
                            msg.dataList[i].goodsName = msg.dataList[i].orderDetailsList[0].goodsName;
                            msg.dataList[i].totalAmount = msg.dataList[i].totalAmount / 100;
                            for (var j = 0; j < msg.dataList[i].orderDetailsList.length; j++) {
                                var img = msg.dataList[i].orderDetailsList[j].image;
                                var imgs;
                                if (img.length > 0) {
                                    imgs = img.split(",");
                                    if (imgs.length > 0) {
                                        msg.dataList[i].orderDetailsList[j].image = apiUrlPic + imgs[0];
                                    }
                                }
                            }
                            result = template('order-box-tpl', msg);
                        }

                    }
                    else {
                        // 锁定
                        me.lock();
                        //    me.noData();
                        // 无数据
                    }
                    $orderList.append(result);
                    me.resetload();


                },
                error: function (xhr, type) {
                    // 即使加载出错，也得重置
                    me.resetload();
                }
            });


        }
    });
    DataInt();
    $('.weui-navbar__item').on('click', function () {
        $(this).addClass('weui-bar__item_on').siblings('.weui-bar__item_on').removeClass('weui-bar__item_on');
        $('#order-box').empty();
        _payState = $(this).attr('state');
        _paymentId = $('.condition-choose li:eq(0) span').attr('data-value');
        _logisticsState = $('.condition-choose li:eq(1) span').attr('data-value');
        DataInt();

    });


    /*    function getUserInfo() {
     components.getMsg(apiUrl + "/front/user/user/getUserById").done(function (msg) {
     var res = msg.res;
     if (res == 1) {
     msg = msg.obj;
     var html = template('user-info-tpl', msg);
     $("#user-info").html(html);
     }
     });
     }*/

      $("#order-box").on("click", ".send-modal", function () {
         var _orderNumber = $(this).attr("oNum");
         url = apiUrl + "/front/order/order/buyGoodsAgain?orderNumber=" + _orderNumber;
         components.getMsg(url).done(function (msg) {
         var res = msg.res;
         if (res == 1) {
         window.location.href = '/page/order_submit.html';
         } else {
         $.toast("购买失败", "text");
         }
         });
     });
    var btnType = '';
    $(".condition-choose li").click(function () {
        if ($(this).text() !== btnType) {
            $(".cover-floor").hide();
        }
        btnType = $(this).text();
        $(this).addClass("active").siblings().removeClass("active");
        var index = $(this).index();
        $(".option-box").find("ul").eq(index).show().siblings().hide();
        $(".cover-floor").toggle();
    });
    $(".option-box li").on("click", function () {
        $(this).addClass("active").siblings().removeClass("active");
        var stringValue = $(this).find("span").text();
        var value = $(this).find("span").attr('data-value');
        $(".condition-choose").find("li").eq($(this).parent().index()).find("span").text(stringValue).attr('data-value', value);

        $('#order-box').empty();
        _payState = $('.weui-bar__item_on').attr('state');
        _paymentId = $('.condition-choose li:eq(0) span').attr('data-value');
        _logisticsState = $('.condition-choose li:eq(1) span').attr('data-value');
        DataInt();
        $(".cover-floor").hide();

    });

    function DataInt() {
        page = 0;
        searchPro.unlock();
        searchPro.noData(false);
        searchPro.$domDown.html(searchPro.opts.domDown.domLoad);
        searchPro.loading = true;
        searchPro.opts.loadDownFn(searchPro);
    }
});
