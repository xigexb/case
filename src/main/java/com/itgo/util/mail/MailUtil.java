package com.itgo.util.mail;

import com.itgo.bean.MainBean;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * Create by xigexb
 *
 * @versio 1.0.0
 * @Author xigexb
 * @date 2019/6/18 15:52
 * @Description desc:java邮件工具类
 */
public class MailUtil {

    /**
     * 发件人的邮箱的 SMTP 服务器地址
     */
    private static String HOST;

    /**
     * 发送邮箱
     */
    private static String sendMail;

    /**
     * 发送邮箱密码
     */
    private static String sendMailPwd;

    /**
     * 需要请求认证
     */
    private static String AUTH;

    private static String protocol;

    /**
     * Session
     */
    private static Session session;

    /**
     * // 设置为debug模式, 可以查看详细的发送 log
     */
    private static Boolean isDebug;

    private static final String DEF_CHARSET = "utf-8";

    private static final String DEF_MAIL_TYPE = "text/html;charset=UTF-8";

    /**
     * init  mail config
     * @param path
     */
    public static void init(String path) {
        try {
            if (path == null || "".equals(path)) {
                throw new FileNotFoundException("not found config  file ，the path is " + path);
            }
            InputStream inputStream = MailUtil.class.getClassLoader().getResourceAsStream(path);
            Properties properties = new Properties();
            properties.load(inputStream);
            if(properties.containsKey("host")){
                String host = properties.getProperty("host");
                HOST = host;
            }
            if(properties.containsKey("mail")){
                String mail = properties.getProperty("mail");
                sendMail = mail;
            }
            if(properties.containsKey("password")){
                String password = properties.getProperty("password");
                sendMailPwd = password;
            }
            AUTH = "true";
            protocol = "smtp";
            isDebug = true;
            // 1. 创建参数配置, 用于连接邮件服务器的参数配置
            Properties props = new Properties();                       // 参数配置
            props.setProperty("mail.transport.protocol", protocol);   // 使用的协议（JavaMail规范要求）
            props.setProperty("mail.smtp.host", HOST);                // 发件人的邮箱的 SMTP 服务器地址
            props.setProperty("mail.smtp.auth", AUTH);                // 需要请求认证
            // 2. 根据配置创建会话对象, 用于和邮件服务器交互
            session = Session.getInstance(props);
            session.setDebug(isDebug);

            // PS: 某些邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证 (为了提高安全性, 邮箱支持SSL连接, 也可以自己开启),
            //     如果无法连接邮件服务器, 仔细查看控制台打印的 log, 如果有有类似 “连接失败, 要求 SSL 安全连接” 等错误,
            //     打开下面 /* ... */ 之间的注释代码, 开启 SSL 安全连接。
        /*
        // SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
        //                  需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
        //                  QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
        */
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String sendSimpleMail(MainBean mainBean) {
        try {
            String msg = checkSendMail(mainBean);
            if(!"ok".equals(msg)){
                return msg;
            }
            String msg2 = checkMailConfig();
            if(!"ok".equals(msg2)){
                return msg2;
            }
            //check mail config

            //make  mail message
            MimeMessage message = new MimeMessage(session);
            // 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
            message.setFrom(new InternetAddress(sendMail, mainBean.getTitle(), DEF_CHARSET));
            // 3. To: 收件人
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(mainBean.getToMail(), mainBean.getUserName(), DEF_CHARSET));
            // 4. Subject: 邮件主题
            message.setSubject(mainBean.getSubject(), DEF_CHARSET);
            // 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
            message.setContent(mainBean.getContent(), DEF_MAIL_TYPE);
            message.setSentDate(new Date());
            // 7. 保存设置
            message.saveChanges();
            Transport transport = session.getTransport();
            transport.connect(sendMail, sendMailPwd);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return "ok";
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检查发送邮件信息
     * @param mainBean
     * @return
     */
    private static String checkSendMail(MainBean mainBean){
        StringBuffer msg = new StringBuffer("为空:)");
        if(mainBean == null){
            return msg.insert(0,"邮件").toString();
        }
        if(mainBean.getToMail() == null || "".equals(mainBean.getToMail())){
            return msg.insert(0,"收件人邮件地址").toString();
        }else {
            boolean isOk = mainBean.getToMail().indexOf("@")!=-1;
            if(!isOk){
                return "收件人邮件不合法:)";
            }
        }
        if(mainBean.getSubject() == null || "".equals(mainBean.getSubject())){
            return msg.insert(0,"主题").toString();
        }
        if(mainBean.getUserName() == null || "".equals(mainBean.getUserName())){
            return msg.insert(0,"收件人").toString();
        }
        if(mainBean.getTitle() == null || "".equals(mainBean.getTitle())){
            return msg.insert(0,"发送标识").toString();
        }
        return "ok";
    }


    /**
     * 检查邮件配置
     * @param
     * @return
     */
    private static String checkMailConfig(){
        StringBuffer msg = new StringBuffer("为空:)");

        if(sendMail== null || "".equals(sendMail)){
            return msg.insert(0,"系统发件方邮件配置邮箱").toString();
        }
        if(sendMailPwd== null || "".equals(sendMailPwd)){
            return msg.insert(0,"系统发件方邮件配置密码").toString();
        }
        if(HOST== null || "".equals(HOST)){
            return msg.insert(0,"系统发件方邮件配置主机").toString();
        }
        return "ok";
    }




}
