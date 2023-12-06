package hello.springmvc.basic.request;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * {"username":"hello", "age":20} content-type: application/json
 */
@Slf4j
@RestController
public class RequestBodyJsonController {
	private ObjectMapper objectMapper = new ObjectMapper();

	@PostMapping("/request-body-json-v1")
	public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServletInputStream inputStream = request.getInputStream();
		String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

		log.info("messageBody={}", messageBody);
		HelloData data = objectMapper.readValue(messageBody, HelloData.class);

		log.info("username={}, age={}", data.getUsername(), data.getAge());
		response.getWriter().write("ok");
	}

	/*
	 * @RequestBody HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
	 *
	 * @ResponseBody - 모든 메서드에 @ResponseBody 적용 - 메시지 바디 정보 직접 반환(view 조회X) -
	 * HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
	 */
	@ResponseBody
	@PostMapping("/request-body-json-v2")
	public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {

		log.info("messageBody={}", messageBody);
		HelloData data = objectMapper.readValue(messageBody, HelloData.class);
		log.info("username={}, age={}", data.getUsername(), data.getAge());

		return "ok";
	}

	/*
	 * @RequestBody 생략 불가능(@ModelAttribute 가 적용되어 버림) HttpMessageConverter 사용
	 * ->MappingJackson2HttpMessageConverter (content-type:application/json) 주의!>
	 * HTTP 요청시에 content-type이 application/json인지 꼭!확인해야 한다. 그래야 JSON을 처리할 수 있는 HTTP
	 * 메시지 컨버터가 실행된다
	 */
	@ResponseBody
	@PostMapping("/request-body-json-v3")
	public String requestBodyJsonV3(@RequestBody HelloData data) { // @RequestBody는 생략 불가능!! 생략시 @ModelAttribute가 됨
		log.info("username={}, age={}", data.getUsername(), data.getAge());
		return "ok";
	}

	@ResponseBody
	@PostMapping("/request-body-json-v4")
	public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {
		HelloData data = httpEntity.getBody();
		log.info("username={}, age={}", data.getUsername(), data.getAge());
		return "ok";
	}

	/*
	 * @RequestBody 생략 불가능(@ModelAttribute 가 적용되어 버림) HttpMessageConverter 사용 ->
	 *              MappingJackson2HttpMessageConverter (contenttype:
	 *              application/json)
	 *
	 * @ResponseBody 적용 - 메시지 바디 정보 직접 반환(view 조회X) - HttpMessageConverter 사용 ->
	 *               MappingJackson2HttpMessageConverter 적용 (Accept:
	 *               application/json)
	 */
	@ResponseBody
	@PostMapping("/request-body-json-v5")
	public HelloData requestBodyJsonV5(@RequestBody HelloData data) {
		log.info("username={}, age={}", data.getUsername(), data.getAge());
		return data;
	}
}
