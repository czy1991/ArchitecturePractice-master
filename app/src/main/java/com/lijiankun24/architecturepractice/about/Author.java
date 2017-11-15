package com.lijiankun24.architecturepractice.about;

/**
 * 类 <code>${CLASS_NAME}</code>
 * <p>
 * 描述：
 * </p>
 * 创建日期：2017年11月15日
 *
 * @author zhaoyong.chen@ehking.com
 * @version 1.0
 */

public class Author {

    private String github = null;

    private String weibo = null;

    private String blog = null;

    private String mail = null;

    private String jianshu = null;

    Author() {
        github = "https://github.com/czy1991/ArchitecturePractice-master";
        weibo = "";
        blog = "";
        mail = "2402531440@qq.com";
        jianshu = "";
    }

    String getGithub() {
        return github;
    }

    String getWeibo() {
        return weibo;
    }

    String getBlog() {
        return blog;
    }

    String getMail() {
        return mail;
    }

    String getJianshu() {
        return jianshu;
    }
}
