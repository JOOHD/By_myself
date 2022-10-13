package config.auth;

import com.mieumje.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()                   // h2-console 화면을 사용하기 위해 해당 옵션을 diable
                .headers().frameOptions().disable() //                      ""
                .and()
                .authorizeRequests() // URL 별 권한 관리를 설정하는 옵션의 시작점, 이 부분이 선언되어야 andMatchers 옵션 사용 가능
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // USER 권한을 가진 사람만 가능하도록 했다.
                            /** .antMatchers()
                                권한 관리 대상을 지정하는 옵션 URL, HTTP 메소드별로 관리 가능
                                "/"등 지정된 URL은 permitAll() 옵션을 통해 전체 열람 권한을 주었다.
                                "api/Vl/**" 주소를 가진 API는 USER 권한을 가진 사람만 가능하도록 했다.
                             */
                .anyRequest().authenticated()       // 설정된 값들 이외 나머지 URL authenicated()를 추가해 나머지 URL들은 모두 인증된 사용자만 허용(로그인 한 사용자)    
                .and()
                .logout()                           // 로그아웃 기능에 대한 설정의 진입점
                .logoutSuccessUrl("/")              // 로그아웃 성공 시 "/"주소로 리다이렉트.
                .and()
                .oauth2Login()                      
                .userInfoEndpoint()                 // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정을 담당
                .userService(customOAuth2UserService); 
                /* 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록.
                   리소스 서버(즉, 소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시 할 수있다. 
                */
    }
}
