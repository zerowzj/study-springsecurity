package study.springsecurity.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class WebSecurityCfg extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * HTTP请求安全处理
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //登录
        http.formLogin() //定义当需要用户登录时候，转到的登录页面
                .loginPage("/login")       //设置登录跳转页面controller，也可以直接跳转页面
                .loginProcessingUrl("/login") //自定义登录页面的表单提交地址
                .failureUrl("/login/error=") // 自定义登入失败页面，前端可以通过url中是否有error来提供友好的用户登入提示
                .defaultSuccessUrl("/index")
        ;
        //注销
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
        ;
        //认证及授权
        http.authorizeRequests()
                .antMatchers("/", "/login.html")
                .permitAll()
                .anyRequest()  //默认其它的请求都需要认证，这里一定要添加
                .authenticated()
        ;
        //认证及授权
        http.exceptionHandling()
                .accessDeniedPage("/403")
        ;
        //其他
        http.csrf().disable()
        ;
    }

    /**
     * WEB安全
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
    }

    /**
     * 身份验证管理器
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}
