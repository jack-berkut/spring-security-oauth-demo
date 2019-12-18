package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) {
        // 静的リソース（CSSやJavaScriptなど）はSpring Security の対象外にする
        web.ignoring().antMatchers("/css/**", "/js/**", "/fonts/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            // ログイン画面はログインしていない状態でもアクセスできる
            .antMatchers("/login").permitAll()
            // その他の画面はログイン必須
            .anyRequest().authenticated()
            .and()
            // ログインはOAuth 2.0 のフローを利用する
            .oauth2Login()
            // Spring Security デフォルトのログイン画面ではなく独自のログイン画面を表示する
            .loginPage("/login");
    }
}
