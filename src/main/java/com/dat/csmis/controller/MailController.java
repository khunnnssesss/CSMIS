package com.dat.csmis.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dat.csmis.Utility;
import com.dat.csmis.entity.EmployeeEntity;
import com.dat.csmis.service.EmployeeNotFoundException;
import com.dat.csmis.service.EmployeeService;
import com.dat.csmis.service.MailSer;

import net.bytebuddy.utility.RandomString;

@Controller
public class MailController {

    @Autowired private EmployeeService employeeService;
    @Autowired private MailSer mailSer;

    
    @GetMapping("/forgetPassword")
  public String forgetPassword() {
    
    return "forgetPassword";
  }


  @PostMapping("/doForgetPassword")
  public String doForgetPassword(@RequestParam("email")String email,
                  Model m,
                  HttpServletRequest req){
    String token=RandomString.make(45);
    try {
        var emp = employeeService.getOneEmp(email);
      employeeService.updateResetPasswordToken(token, email);
      String resetPasswordLink= Utility.getSiteUrl(req) + "/resetPassword?token=" +token;
      System.out.println(resetPasswordLink);
    mailSer.sendForgetPasswordMail(emp, resetPasswordLink);
      m.addAttribute("message", "We have sent a reset password link to your email. Plese check..!");
    } catch (EmployeeNotFoundException e) {
      
      m.addAttribute("error", e.getMessage());
    }
    
    return "forgetPassword";
  }

  @GetMapping("/resetPassword")
  public String resetPassword(@Param(value = "token")String token, Model m) {
    EmployeeEntity entity=employeeService.getByReset(token);
    if(entity == null) {
      m.addAttribute("error", "Invaild Token!");
    }
      m.addAttribute("token", token);
    return "resetPassword";
  }

  @PostMapping("/doResetPassword")
  public String resetPasswordPost(@RequestParam("password")String password,
                   @RequestParam("token")String token, Model m) {
    EmployeeEntity entity=employeeService.getByReset(token);
    if(entity == null) {
      m.addAttribute("error", "Invaild Token!");
    }else {
      employeeService.updatePassword(entity, password);
      m.addAttribute("message", "Successfully reset password !");
    }
    
    return "login";
  }

}
