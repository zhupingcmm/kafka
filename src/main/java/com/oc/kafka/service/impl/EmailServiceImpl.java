package com.oc.kafka.service.impl;

import com.oc.kafka.service.EmailService;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Template;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.util.Map;

/**
 * @Author: pzhu
 * @Date: 2023/9/6 20:49
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendMessage(SimpleMailMessage simpleMailMessage) {
        javaMailSender.send(simpleMailMessage);
    }

    @SneakyThrows
    @Override
    public void senTemplateMail(String subject, Map<String, Object> params) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom("13774495234@163.com");
        helper.setTo("591679403@qq.com");

        freemarker.template.Configuration configuration = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_19);
        TemplateLoader templateLoader = new ClassTemplateLoader(this.getClass(), "/templates/");
        configuration.setTemplateLoader(templateLoader);
        Template template = configuration.getTemplate("mail.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, params);


        helper.setSubject(subject);
        helper.setText(html, true);//重点，默认为false，显示原始html代码，无效果

        javaMailSender.send(mimeMessage);
    }
}
