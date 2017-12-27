package com.dlwx.onedrop.utiles;

/**
 * Created by Administrator on 2017/11/9/009.
 */

public class HtttpUrlUtils {
    public static String BaseUrl = "http://39.106.68.124/index.php/Mobile/";
    public static String Url = "http://39.106.68.124/index.php/Mobile/";
    public static String ImgAuth = BaseUrl + "Mobile/verify";//获取图形验证码
    public static String GetAuth = BaseUrl + "Register/sms";//获取短信验证码
    public static String Register = BaseUrl + "Register/user_add";//注册
    public static String Login = BaseUrl + "Login/login";//登录
    public static String ClassID = BaseUrl + "Shop/index";//首页
    public static String ForgitPwd = BaseUrl + "Register/setnewpassword";//忘记密码
    public static String GetPersionMEss = Url + "Water/personal_info";//获取个人信息
    public static String UpPic = BaseUrl + "Mobile/uploadFile";//上传图片
    public static String UpRevampMess = Url + "Water/edit_info";//上传修改的信息
    public static String upVersion = BaseUrl + "Mobile/check_version";//版本升级
    public static String BankCardList = Url + "Water/bankcard_list";//银行卡列表
    public static String delete_bank = Url + "Water/del_bank";//删除银行卡
    public static String GetIndex = BaseUrl + "Shop/index";//根据Id获取分类ID
    public static String UserAggress = Url + "Public/user_agreement";//用户协议
    public static String About_WE = Url + "Public/about_us";//关于我们
    public static String KeFuMess = Url + "Public/Customer_center";//客服信息
    public static String About_WE_Desc = Url + "Public/about_us_info";//关于我们详情
    public static String HelpCenterList = Url + "Public/help_center";//帮助中心列表
    public static String HelpCenDesc = Url + "Public/help_center_info";//帮助中心详情
    public static String MyTeamCenter = Url + "Water/my_team";//我的团队中心列表
    public static String AddressList = Url + "Water/addr_list";//收货地址列表
    public static String AddAddress = Url + "Water/add_addr";//添加/修改收货地址
    public static String DeleteAdd = Url + "Water/del_addr";//删除收货地址
    public static String GetBankList = Url + "Public/bank_list";//银行列表
    public static String AddBank = Url + "Water/add_bancard";//添加银行卡
    public static String SetPatPwd = Url + "Water/set_tx_pwd";//设置提现密码
    public static String TiXianPwdCheck = Url + "Water/validate_txpwd";//体现密码检验
    public static String startTixian = Url + "Water/Withdrawals";//开始提现
    public static String MyCommiss = BaseUrl + "Order/my_yongjin";//我的佣金
    public static String consume_record = BaseUrl + "Order/consume_record";//我的提现纪录
    public static String GetYE = Url + "Water/use_price";//账户余额
    public static String ProductDesc = BaseUrl + "Shop/productinfo";//商品详情
    public static String AllEcaluate = BaseUrl + "Shop/allpingjia";//全部评价
    public static String AddCat = BaseUrl + "Buy/addcar";//加入购物车
    public static String AffOrder = BaseUrl + "Buy/query_order";//确认订单页面接口
    public static String submitOrder = BaseUrl + "Buy/addorder";//提交订单
    public static String shopcats = BaseUrl + "Buy/showcar";//购物车列表
    public static String RevampCatNum = BaseUrl + "Buy/editcar";//修改购物车商品数量
    public static String DeleteCat = BaseUrl + "Buy/editcar";//删除购物车
    public static String MyOrderData = BaseUrl + "Order/myorder";//我的订单
    public static String OrderDesc = BaseUrl + "Order/orderinfo";//订单详情
    public static String UpEvaluate = BaseUrl + "Order/pingjia";//评价
    public static String DeleteImg = BaseUrl + "Mobile/delimg";//删除图片
    public static String ToPay = BaseUrl + "Bill/topay";//去支付
    public static String Shouhuo = BaseUrl + "Order/set_shouhuo";//收货
    public static String SysMess = Url + "Water/message_list";//消息列表
    public static String SysMessDesc = Url + "Water/message_info";//系统消息详情
    public static String MyLeve = BaseUrl + "User/myvip";//我的级别
    public static String MessNum = Url + "Water/unreadnum";//纬度消息数量
    public static String PaySuccess = Url + "Public/be_agent";//成为代理
    public static String LogOut = Url + "Login/logout";//退出登录
    public static String RevampPhone = Url + "Water/edit_phone";//修改手机号
    public static String Set_Default = Url + "Water/set_default";//设置为默认地址
    public static String GetCode = Url + "water/qr_codepic";//获取邀请码
    public static String MyEarn = Url + "User/my_shouyi";//我的收益
}
