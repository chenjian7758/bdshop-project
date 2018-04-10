package com.bigdatan.b2c.controller.front.pay;



/**
 * 
 *
 * 15-08-22
 * 微信统一下单接口参数
 *
 */
//@Root(name="xml")
public class WxPlaceOrder {
//@Element(required=false)
private String appid;//公众账号ID
//@Element(required=false)
private String mch_id;//商户号
//@Element(required=false)
private String nonce_str;//随机串
//@Element(required=false)
private String sign;//签名
//@Element(required=false)
private String body;//商品描述
//@Element(required=false)
private String out_trade_no;//商户订单号
//@Element(required=false)
private Integer total_fee; //总金额 单位为分 只能为整数
//@Element(required=false)
private String spbill_create_ip;//终端IP  APP和网页支付提交用户端ip
//@Element(required=false)
private String notify_url;//通知地址
//@Element(required=false)
private String trade_type;//交易类型
//@Element(required=false)
private String openid;//用户标识
//@Element(required=false)
private String return_code;//微信返回通信码
//@Element(required=false)
private String prepay_id;//预下单回话标识。统一下单接口返回
//@Element(required=false)
private String timeStamp;
//@Element(required=false)
private String signType;

public String getTimeStamp() {
	return timeStamp;
}
public void setTimeStamp(String timeStamp) {
	this.timeStamp = timeStamp;
}
public String getSignType() {
	return signType;
}
public void setSignType(String signType) {
	this.signType = signType;
}
public String getPrepay_id() {
	return prepay_id;
}
public void setPrepay_id(String prepay_id) {
	this.prepay_id = prepay_id;
}
public String getReturn_code() {
	return return_code;
}
public void setReturn_code(String return_code) {
	this.return_code = return_code;
}
public String getAppid() {
	return appid;
}
public void setAppid(String appid) {
	this.appid = appid;
}
public String getMch_id() {
	return mch_id;
}
public void setMch_id(String mch_id) {
	this.mch_id = mch_id;
}
public String getNonce_str() {
	return nonce_str;
}
public void setNonce_str(String nonce_str) {
	this.nonce_str = nonce_str;
}
public String getSign() {
	return sign;
}
public void setSign(String sign) {
	this.sign = sign;
}
public String getBody() {
	return body;
}
public void setBody(String body) {
	this.body = body;
}
public String getOut_trade_no() {
	return out_trade_no;
}
public void setOut_trade_no(String out_trade_no) {
	this.out_trade_no = out_trade_no;
}
public Integer getTotal_fee() {
	return total_fee;
}
public void setTotal_fee(Integer total_fee) {
	this.total_fee = total_fee;
}
public String getSpbill_create_ip() {
	return spbill_create_ip;
}
public void setSpbill_create_ip(String spbill_create_ip) {
	this.spbill_create_ip = spbill_create_ip;
}
public String getNotify_url() {
	return notify_url;
}
public void setNotify_url(String notify_url) {
	this.notify_url = notify_url;
}
public String getTrade_type() {
	return trade_type;
}
public void setTrade_type(String trade_type) {
	this.trade_type = trade_type;
}
public String getOpenid() {
	return openid;
}
public void setOpenid(String openid) {
	this.openid = openid;
}
}
