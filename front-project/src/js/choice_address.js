define(['zepto', "components", "common", "weui", "touch", "template"], function (zepto, components, common, weui, touch, template) {
    var receiveId = components.GetQueryString("rid");
    getData();
    function getData() {
        components.getMsg(apiUrl + "/front/receive/receive/getReceiveByPage").done(function (msg) {
            var res = msg.res;
            if (res == 1) {
                var html = template('address-list-tpl', msg);
                document.getElementById('address-list').innerHTML = html;
                if (!!receiveId && receiveId !== 'null') {
                    $('#address-list .address[rid]').each(function () {
                        addEffect($(this));
                        delAddress($(this).children('p.act'));
                        if ($(this).attr('rid') == receiveId) {
                            $(this).children('ul').addClass('selected');
                        }
                    });
                    setDefultAddress();
                }
                else {
                    $('#address-list .address[rid]').each(function () {
                        $(this).children('ul').addClass('hidden');
                        addEffect($(this));
                        delAddress($(this).children('p.act'));
                    });

                }
                $('a.add').click(function () {
                    window.location.href = "/page/add_address.html?rid=" + receiveId;
                });
                $(".address_list div.address ul li.edit").click(function (e) {
                    e.stopPropagation();
                    var aid = $(this).attr('adid');
                    window.location.href = "/page/add_address.html?rid=" + receiveId + "&id=" + aid;
                });

            } else {
                document.getElementById('address-list').innerHTML = '<div class="weui-loadmore weui-loadmore_line"><span class="weui-loadmore__tips">暂无数据</span></div>';
            }
        });
    }

    function setDefultAddress() {

        $(".address_list div.address ul").click(function (e) {
            var rid = $(this).attr("rid");
            components.getMsg(apiUrl + "/front/receive/receive/setDefaultReceive", "receiveId=" + rid, "post").done(function (msg) {
                var res = msg.res;
                if (res == 1) {
                    window.location.href = "/page/order_submit.html";
                } else {
                    $.alert('网络繁忙。')
                }
            });
        });
    }


    //增加左滑动事件
    function addEffect(item) {
        var img = item.children('ul'), margin;
        new Hammer(item[0], {
            domEvents: true
        });
        item.on("panstart", function (e) {

            margin = parseInt(img.css('transform').replace(/[^0-9\-,]/g, '').split(',')[4], 10);
        });
        item.on("pan", function (e) {
            fnTransition(img, 0);
            var delta = margin + e.originalEvent.gesture.deltaX;
            if (delta >= -70 && delta <= 0) {
                img.css('transform', 'translateX(' + delta + 'px)')
            }
        });
        item.on('panend', function (e) {
            var _absMoveY = Math.abs(parseInt(img.css('transform').replace(/[^0-9\-,]/g, '').split(',')[4], 10));
            fnTransition(img, 300);
            if (_absMoveY > 35) {
                img.css('transform', 'translateX(-70px)')
            }
            else {
                img.css('transform', 'translateX(0px)')
            }
        })
    }

    // css过渡
    function fnTransition(dom, num) {

        dom.css({
            '-webkit-transition': 'all ' + num + 'ms',
            'transition': 'all ' + num + 'ms'
        });
    }


    //delete address
    function delAddress(item) {
        item.on("click", function (e) {
            var rid = $(this).attr("rid");
            $.confirm({
                text: '确定要删除此地址吗？',
                onOK: function () {
                    components.getMsg(apiUrl + "/front/receive/receive/delReceiveById?receiveId=" + rid).done(function (msg) {
                        var res = msg.res;
                        if (res == 1) {
                            $.toast("删除成功", "text");
                            window.location.reload();
                        }
                    });
                }
            });
        });
    }

});