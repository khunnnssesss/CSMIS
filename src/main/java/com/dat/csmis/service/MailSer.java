package com.dat.csmis.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.dat.csmis.entity.EmployeeEntity;
import com.dat.csmis.model.DailyDatePrice;
import com.dat.csmis.model.WeeklyLists;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


@Service
public class MailSer {
    
    @Autowired JavaMailSender sender;
    @Autowired private Configuration configuration;


    // Send User Name and Password to New Employee
    @Async
    public String sendEmail(String mail,String name) {
		
		MimeMessage message = sender.createMimeMessage();

        Map<String, Object> model = new HashMap<>();
		model.put("userName", mail);
		model.put("password", "12345");
        
		try {
			// set mediaType
			MimeMessageHelper helper = new MimeMessageHelper(message);
			// add attachment
			

			Template t = configuration.getTemplate("emailUsernameAndPassword.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

			helper.setTo(mail);
			helper.setText(html, true);
			helper.setSubject("User Name and Password for DAT Catering Service App");
			helper.setFrom("yawyaw00898@gmail.com");
			sender.send(message);

		

		} catch (Exception e) {
			System.out.println("error :: "+e);
		}

		return "sent!!!";
	}
	

    // Send Forget Password Link
    public void sendForgetPasswordMail(EmployeeEntity emp, String resetPasswordLink) {

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message);
        
        Map<String, Object> model = new HashMap<>();
		model.put("userName", emp.getName());
		model.put("userEmail", emp.getEmail());
        model.put("userPosition",emp.getDivision());
        model.put("urlss",resetPasswordLink);

        try {

            Template t = configuration.getTemplate("emailForgetpassword.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

          helper.setFrom("CSMISserver.11@dat.com", "CSMIS support");
          helper.setTo(emp.getEmail());
          
          String subject="Here is the link to reset your password.";
         ;
          helper.setSubject(subject);
          helper.setText(html, true);
          sender.send(message);
        } catch (UnsupportedEncodingException | MessagingException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TemplateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
      }



    //   Send Weekly Registered Day Lists
    @Async
    public String sendWeeklyEmail(List<WeeklyLists> weeklylists,String mail){

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message);
        
        Map<String, List<WeeklyLists>> model = new HashMap<>();
		model.put("weeklyLists", weeklylists);

        try {

            Template t = configuration.getTemplate("emailRegisterForWeek.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

          helper.setFrom("CSMISserver.11@dat.com", "CSMIS support");
          helper.setTo(mail);
          
          String subject="Here is the registered days for Next Week!";
         ;
          helper.setSubject(subject);
          helper.setText(html, true);
          sender.send(message);
        } catch (UnsupportedEncodingException | MessagingException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TemplateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "SEND !!!!";
    }

    // Send Monthly Total Cost for per Employee
    @Async
    public String sendMonthlyEmail(List<DailyDatePrice> dailyobj,String mail){

        System.out.println("dail yobj :: ");
        System.out.println(dailyobj);
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message);
        
        Map<String, List<DailyDatePrice>> model = new HashMap<>();
		model.put("dailyLists", dailyobj);

        try {

            Template t = configuration.getTemplate("emailMonthlyCost.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

          helper.setFrom("CSMISserver.11@dat.com", "CSMIS support");
          helper.setTo(mail);
          
          String subject="Here is total cost detail for this Month!";
         ;
          helper.setSubject(subject);
          helper.setText(html, true);
          sender.send(message);
        } catch (UnsupportedEncodingException | MessagingException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TemplateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return "Monthly Email sent to all Employees!!!";
    }

}
