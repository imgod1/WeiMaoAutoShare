package com.weimao.imgod.gk.weimaoautoshare.bean;

/**
 * 项目名称：WeiMaoAutoShare
 * 类描述：
 * 创建人：gk
 * 创建时间：2016/9/12 11:06
 * 修改人：gk
 * 修改时间：2016/9/12 11:06
 * 修改备注：
 */
public class LoginBean extends BaseBean {

    /**
     * avatar :
     * hasCerified_h : 0
     * hasCerified_l : 0
     * hasCertified : 0
     * id : 13136
     * mobile : 14756780888
     * nickname : 未设置
     * realName : 未设置
     * score : 50
     */

    private UserBean user;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        private String avatar;
        private int hasCerified_h;
        private int hasCerified_l;
        private int hasCertified;
        private int id;
        private String mobile;
        private String nickname;
        private String realName;
        private int score;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getHasCerified_h() {
            return hasCerified_h;
        }

        public void setHasCerified_h(int hasCerified_h) {
            this.hasCerified_h = hasCerified_h;
        }

        public int getHasCerified_l() {
            return hasCerified_l;
        }

        public void setHasCerified_l(int hasCerified_l) {
            this.hasCerified_l = hasCerified_l;
        }

        public int getHasCertified() {
            return hasCertified;
        }

        public void setHasCertified(int hasCertified) {
            this.hasCertified = hasCertified;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }
}
