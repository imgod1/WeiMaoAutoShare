package com.weimao.imgod.gk.weimaoautoshare.app;

/**
 * 项目名称：WeiMaoAutoShare
 * 类描述：常量类
 * 创建人：gk
 * 创建时间：2016/9/12 9:56
 * 修改人：gk
 * 修改时间：2016/9/12 9:56
 * 修改备注：
 */
public class Constants {
    /**
     * 发送短信验证码的网址:结果:{"status":"200","code":"797813"}
     */
    public static final String SEND_MSG_URL = "http://www.qihumoney.com/tinycat/tinycat/front/user/sendMsg?flag=0&mobile=";

    /**
     * user.valcode=797813&user.mobile=14756780888&user.spreadCode=&user.password=B4B361151DF33ED13C5DDD5F67311098
     * 注册的网址 结果:{"id":13136,"status":"200"}
     */
    public static final String SIGNIN_URL = "http://www.qihumoney.com/tinycat/tinycat/front/user/userRegister?";

    /**
     * 登录的url 结果:{"status":"200","user":{"avatar":"","hasCerified_h":0,"hasCerified_l":0,"hasCertified":0,"id":13136,"mobile":"14756780888","nickname":"未设置","realName":"未设置","score":50}}
     */
    public static String LOGIN_URL = "http://www.qihumoney.com/tinycat/tinycat/front/user/userLogin?user.mobile=14756780888&user.password=B4B361151DF33ED13C5DDD5F67311098";

    /**
     * 每日签到url 结果:{"status":"200","score":2}
     */
    public static String DAY_SIGN_URL = "http://www.qihumoney.com/tinycat/tinycat/front/user/daySign?user.id=13136";
    /**
     * 分享的地址
     */
    public static final String SHARE_URL = "http://www.qihumoney.com/tinycat/tinycat/front/customer/sharing?customer.id=";
    public static String USE_URL = "";//真正用到的url,
}
