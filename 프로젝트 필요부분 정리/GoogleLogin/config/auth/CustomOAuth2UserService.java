package config.auth;

import com.mieumje.springboot.config.auth.dto.OAuthAttributes;
import com.mieumje.springboot.config.auth.dto.SessionUser;
import com.mieumje.springboot.domain.user.User;
import com.mieumje.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements  OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        /** registrationId
         *  현재 로그인 진행 중인 서비스를 구분하는 코드. 구글 로그인에는 불필요한 필드지만,
            추후에 네이버 로그인을 구현했을때, 구글 로그인인지 네이버 로그인 인지 구분할 때 사용한다.

         *  userNameAttributeName
            OAuth2 로그인 진행 시 키가 되는 필드 값. PK와 같은 의미.
            구글은 기본적으로 코드를 지원하지만, 네이버 카카오 등은 지원핮지 않는다. 구글의 기본 코드는 "sub"
            추후 네이버 로그인과 구글 로그인을 동시 지원할 때 사용.  
         */
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        /** OAuthAttributes 
            OAuthUserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
            네이버 로그인에서 이 클래스를 사용한다.

         *  SessionUser
            세션에 사용자 정보를 저장하기 위한 Dto 클래스이다.
         */        
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }


    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
