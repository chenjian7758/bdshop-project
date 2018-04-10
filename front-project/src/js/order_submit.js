define(['jquery', "components", "bootstrap", "common", "timepicker", "template", "weui"], function (jquery, components, bootstrap, common, timepicker, template, weui) {
    var orderNumber = components.GetQueryString("id");
    var mark = false;
    template.helper('formatPrice', function (price, type) {
        if (price || price == 0) {
            price = price / 100;
            if (type == 'true') {
                if (price == 0) {
                    price = "包邮"
                } else {
                    price = "¥" + price
                }
                return price;
            }
        }
    });
    getDefaultAddress();
    getPayment();
    getGoods();


    $("#order_submit").click(function () {
        var url = apiUrl + "/front/order/order/addOrder";
        var receiveId = $("#receiveId").val();
        var invoiceTag = $("#invoiceTag").val();
        if (invoiceTag) {
            invoiceTag = "0";
        }
        var data = $("#order-submit-box").serialize();
        var sendTime = $("#showSendTime").val();
        $(".payStyle").each(function () {
            if ($(this).is(":checked")) {
                mark = true;
            }
        });
        if (!mark) {
            $.toast("请选择付款方式", "text");
        }
        else if (getStrLength(sendTime) == 0) {
            $.toast("请选择配送时间", "text");
        } else {
            components.getMsg(url, data, "post").done(function (msg) {
                var res = msg.res;
                if (res == 1) {
                    var paymentId = document.getElementsByName("payment.paymentId");
                    for (var i = 0; i < paymentId.length; i++) {
                        if (paymentId[i].checked) {
                            paymentId = paymentId[i].value;
                        }
                    }
                    if (paymentId == 2) {
                        window.location.href = '/page/pay_check.html?orderNumber=' + msg.result + '&price=' + $("#total-money").text();
                    } else {
                        window.location.href = '/page/order_submit_success.html';
                    }
                } else {
                    $.toast("出错啦！", "text");
                }
            });
        }
    });



    $('.shopTip').click(function(){
        $parent = $(this).parent('div.order_info');
        if($parent.hasClass('shopShow')){
            $parent.removeClass('shopShow');
        }else{
            $parent.addClass('shopShow');
        }
    });
// 得到字符串长度
    function getStrLength(str) {
        return str.replace(/[^\x00-xff]/g, "xx").length;
    }

    function getDefaultAddress() {
        components.getMsg(apiUrl + "/front/receive/receive/queryDefaultReceive").done(function (msg) {
            var res = msg.res;
            if (res == 1) {
                //msg = msg.obj;
                var html = template('order-address-tpl', msg);
                document.querySelector("#order-address").innerHTML = html;
            }
        });
    }

    function getPayment() {
        components.getMsg(apiUrl + "/front/payment/payment/queryUserPayment").done(function (msg2) {
            var res = msg2.res;
            if (res == 1) {
                //msg = msg.obj;
                var html = template('order-payment-tpl', msg2);
                document.querySelector("#order-payment").innerHTML = html;
            }
        });
    }

    function getGoods() {
        components.getMsg(apiUrl + "/front/shoppingcart/shoppingcart/queryShoppingCartInfo").done(function (msg3) {
            var res = msg3.res;
            if (res == 1) {
                //msg = msg.obj;
                var html = template('order-goods-tpl', msg3);
                document.querySelector("#order-goods").innerHTML = html;
                html = template('order-amount-tpl', msg3);
                document.querySelector("#order-amount").innerHTML = html;
                html = template('order-amount2-tpl', msg3);
                document.querySelector("#order-amount2").innerHTML = html;
                html = template('order-amount3-tpl', msg3);
                document.querySelector("#order-amount3").innerHTML = html;
               var shopCount=msg3.obj.shoppingCartItem;
               var num=0;
               for(var k=shopCount.length;k--;){
                   num+=shopCount[k].quantity
               }
               var htmlCount=template('shop-allcount-tpl',{'num':num});
                console.log(htmlCount)
                $('#allcount').html(htmlCount)
            }
        });
    }

    addSendTime();
    function addSendTime() {
        $("#addSendTime").on("click", function () {
            var $body = $('body');
            document.title = '配送时间';
            var $iframe = $("");
            $iframe.on('load', function () {
                setTimeout(function () {
                    $iframe.off('load').remove();
                }, 0);
            }).appendTo($body);
            $("#dialogDetail").modal({
                show: true
            });
            var initDate = GetDateStr(1);
            $("#datetimepicker").datetimepicker({
                format: 'yyyy-mm-dd', /*此属性是显示顺序，还有显示顺序是mm-dd-yyyy*/
                autoclose: true,//自动关闭
                minView: 2,//最精准的时间选择为日期0-分 1-时 2-日 3-月
                weekStart: 0,
                startDate: initDate
            }).on('changeDate', gotoDate);
            function gotoDate(date) {
                var sendTime = date.date.getFullYear().toString() + "-" + (date.date.getMonth() + 1).toString() + "-" + date.date.getDate().toString()
                $("#dialogDetail").modal("hide");
                $("#showSendTime").val(sendTime);
                $("#sendTime").val(date.date);
                var $body = $('body');
                document.title = '提交订单';
                var $iframe = $("");
                $iframe.on('load', function () {
                    setTimeout(function () {
                        $iframe.off('load').remove();
                    }, 0);
                }).appendTo($body);
            }

        });
        function GetDateStr(AddDayCount) {
            var dd = new Date();
            dd.setDate(dd.getDate() + AddDayCount);//获取AddDayCount天后的日期
            var y = dd.getFullYear();
            var m = dd.getMonth() + 1;//获取当前月份的日期
            var d = dd.getDate();
            return y + "-" + m + "-" + d;
        }

    }
});
