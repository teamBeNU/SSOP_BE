package SSOP.ssop.controller;

import SSOP.ssop.domain.KakaoUser;
import SSOP.ssop.domain.OAuthToken;
import SSOP.ssop.service.KakaoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/login/kakao")
public class KakaoController {

    private static final Logger log = LoggerFactory.getLogger(KakaoController.class);

    @Value("${kakao.client.id}")
    private String kakaoClientId;

    private final KakaoService kakaoService;

    @Autowired
    public KakaoController(KakaoService kakaoService) {
        this.kakaoService = kakaoService;
    }

    @GetMapping
    public @ResponseBody String kakaoCallback(String code) throws JsonProcessingException {

        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientId);
        params.add("redirect_uri", "http://localhost:8080/login/kakao");
        params.add("code", code);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        // Http 요청하기 - post방식, 그리고 response변수의 응답 받음
        ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST, kakaoTokenRequest, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);

        // 사용자 정보 가져오기
        HttpHeaders userInfoHeaders = new HttpHeaders();
        userInfoHeaders.add("Authorization", "Bearer " + oauthToken.getAccess_token());
        userInfoHeaders.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(userInfoHeaders);

        ResponseEntity<String> userInfoResponse = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        // 사용자 정보 출력 및 저장
        String userInfoResponseBody = userInfoResponse.getBody();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(userInfoResponseBody);

        KakaoUser kakaoUserInfo = new KakaoUser();
        JsonNode kakaoAccountNode = jsonNode.get("kakao_account");
        if (kakaoAccountNode != null) {
            String birthyear = kakaoAccountNode.get("birthyear") != null ? kakaoAccountNode.get("birthyear").asText() : "";
            String birthday = kakaoAccountNode.get("birthday") != null ? kakaoAccountNode.get("birthday").asText() : "";
            kakaoUserInfo.setBirthyear(birthyear);
            kakaoUserInfo.setBirthday(birthday);
        } else {
            // Handle the case where kakao_account node is missing
        }

        JsonNode propertiesNode = jsonNode.get("properties");
        if (propertiesNode != null) {
            kakaoUserInfo.setName(propertiesNode.get("nickname") != null ? propertiesNode.get("nickname").asText() : "");
        } else {
            // Handle the case where properties node is missing
        }

        kakaoUserInfo.setId(jsonNode.get("id") != null ? jsonNode.get("id").asLong() : 0);
        kakaoUserInfo.setEmail(kakaoAccountNode != null && kakaoAccountNode.get("email") != null ? kakaoAccountNode.get("email").asText() : "");
        kakaoUserInfo.setName(kakaoAccountNode != null ? kakaoAccountNode.get("name").asText() : "");
        kakaoUserInfo.setPhone_number(kakaoAccountNode != null ? kakaoAccountNode.get("phone_number").asText() : "");
        kakaoUserInfo.setBirthyear(kakaoAccountNode != null ? kakaoAccountNode.get("birthyear").asText() : "");
        kakaoUserInfo.setBirthday(kakaoAccountNode != null ? kakaoAccountNode.get("birthday").asText() : "");


        kakaoService.saveOrUpdateUser(kakaoUserInfo);

        return "카카오 로그인 성공\n" +
                "아이디 " + kakaoUserInfo.getId() +
                "이름 " + kakaoUserInfo.getName() +
                "이메일 " + kakaoUserInfo.getEmail() +
                "폰 " + kakaoUserInfo.getPhone_number() +
                "생년 " + kakaoUserInfo.getBirthyear() +
                "월일 " + kakaoUserInfo.getBirthday();
    }
}