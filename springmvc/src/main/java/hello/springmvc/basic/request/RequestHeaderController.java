package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Locale;
@RestController
@Slf4j
public class RequestHeaderController {

	@RequestMapping("/headers")
	public String headers(HttpServletRequest request,
						  HttpServletResponse response,
						  HttpMethod httpMethod, 
						  Locale locale,
						  @RequestHeader MultiValueMap<String, String> headerMap, //MAP과 유사한데, 하나의 키에 여러 값을 받을 수 있음
						  @RequestHeader("host") String host,
						  @CookieValue(value = "myCookie", required = false) String cookie
						  ) {
		log.info("request={}", request);
		log.info("response={}", response);
		log.info("httpMethod={}", httpMethod);
		log.info("locale={}", locale);
		log.info("headerMap={}", headerMap);
		log.info("header host={}", host);
		log.info("myCookie={}", cookie);
		return "ok";
	}
}
