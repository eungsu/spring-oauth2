package com.example.security.oauth2;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.model.User;
import com.example.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        OAuth2UserInfo userInfo = new OAuth2UserInfo(attributes);

        String username = userInfo.getUsername(provider);
        String email = userInfo.getEmail(provider);
        String nickname = userInfo.getNickname(provider);

        Optional<User> optional = userRepository.findByUsername(username);
        User user = null;
        if (optional.isEmpty()) {
            user = User.builder()
                .username(username)
                .email(email)
                .nickname(nickname)
                .build();

            userRepository.save(user);
        } else {
            user = optional.get();
        }

        return new CustomOAuth2User(user, attributes);
    }
}
