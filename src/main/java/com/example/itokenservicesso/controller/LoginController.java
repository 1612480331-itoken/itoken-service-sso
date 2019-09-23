package com.example.itokenservicesso.controller;


import com.example.itokenservicesso.entity.User;
import com.example.itokenservicesso.service.consumer.RedisService;
import com.example.itokenservicesso.service.provider.LoginService;
import com.example.itokenservicesso.utils.CookieUtil;
import com.example.itokenservicesso.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Controller
public class LoginController {


    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisService redisService;

    /**
     * 跳转登录页
     */
    @GetMapping("login")
    public String login(HttpServletRequest request,
                        Model model,
                        @RequestParam(required = false) String url
    ) {
        String token = CookieUtil.getCookie(request, "token");
        //token不为空可能已登录,从redis获取账号
        if (token != null && token.trim().length() != 0) {
            String loginCode = redisService.get(token);
            //如果账号不为空，从redis获取该账号的个人信息
            if (loginCode != null && loginCode.trim().length() != 0) {
                String json = redisService.get(loginCode);
                if (json != null && json.trim().length() != 0) {
                    try {
                        User user = JsonUtil.stringToObject(json, User.class);

                        //已登录
                        if (user != null) {
                            if (url != null && url.trim().length() != 0) {
                                return "redirect:" + url;
                            }
                        }
                        //将登录信息传到登录页
                        model.addAttribute("user", user);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return "login";
    }


    /**
     * 登录业务
     *
     * @param loginCode
     * @param password
     * @return
     */
    @PostMapping("login")
    public String login(String loginCode,
                        String password,
                        @RequestParam(required = false) String url,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        RedirectAttributes redirectAttributes) {
        User user = loginService.login(loginCode, password);
        //登录成功
        if (user != null) {

            String token = UUID.randomUUID().toString();
            //将token放入缓存
            String result = redisService.put(token, loginCode, 60 * 60 * 24);
            //如果redisService没有熔断，也就是返回ok,才能执行
            if (result != null && result.equals("ok")) {
                CookieUtil.setCookie(response, "token", token, 60 * 60 * 24);
                if (url != null && !url.trim().equals(""))
                    return "redirect:" + url;
            }
            //熔断后返回错误提示
            else {
                redirectAttributes.addFlashAttribute("message", "服务器异常");
            }

        }
        //登录失败
        else {
            redirectAttributes.addFlashAttribute("message", "用户名或密码错误");
        }
        return "redirect:/login";
    }

    @GetMapping("logout")
    public String logOut(HttpServletRequest request,
                         HttpServletResponse response,
                         @RequestParam(required = false) String url,
                         Model model) {
        CookieUtil.deleteCookie(response, "token");
        return login(request, model, url);
    }

}
