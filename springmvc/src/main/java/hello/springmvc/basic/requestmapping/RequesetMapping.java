package hello.springmvc.basic.requestmapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RequesetMapping {

	@RequestMapping({ "/hello-basic", "/hello-go" }) // 모두 허용 GET, HEAD, POST, PUT, PATCH, DELETE
	public String helloBasic() {
		log.info("helloBasic");
		return "ok";
	}

	/*
	 * method 특정 HTTP 메서드 요청만 허용 GET, HEAD, POST, PUT, PATCH, DELETE
	 */
	@RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET) //@GetMapping
	public String mappingGetV1() {
		log.info("mappingGetV1");
		return "ok";
	}

	/*
	 * 편리한 축약 애노테이션
	 * 
	 * @GetMapping
	 * @PostMapping
	 * @PutMapping
	 * @DeleteMapping
	 * @PatchMapping
	 */
	@GetMapping(value = "/mapping-get-v2")
	public String mappingGetV2() {
		log.info("mapping-get-v2");
		return "ok";
	}
	
	/*
	 * PathVariable 사용
	 * 변수명이 같으면 생략 가능
	 * @PathVariable("userId") String userId -> @PathVariable userId
	 * /mapping/userA
	 */
	@GetMapping("/mapping/{userId}")
	public String mappingPath(@PathVariable("userId") String data) {
		log.info("mappingPath userId={}",data);
		return "ok";
	}
	
	/*
	 * 
	 * PathVariable 사용 다중
	 */
	@GetMapping("/mapping/users/{userId}/orders/{orderId}")
	public String mappingPath(@PathVariable String userId, @PathVariable Long orderId) {
		log.info("mappingPath userId={}, orderId={}", userId, orderId);
		return "ok";
	}
	
	/*
	 * 파라미터로 추가 매핑(잘 사용X)
	 * params="mode",
	 * params="!mode"
	 * params="mode=debug"
	 * params="mode!=debug" (! = )
	 * params = {"mode=debug","data=good"}
	 */
	@GetMapping(value = "/mapping-param", params = "mode=debug")
	public String mappingParam() {
		log.info("mappingParam");
		return "ok";
	}
	
	/*
	 * 특정 헤더로 추가 매핑
	 * headers="mode",
	 * headers="!mode"
	 * headers="mode=debug"
	 * headers="mode!=debug" (! = )
	 */
	@GetMapping(value = "/mapping-header", headers = "mode=debug")
	public String mappingHeader() {
		log.info("mappingHeader");
		return "ok";
	}
	
	/*
	 * Content-Type 헤더 기반 추가 매핑 Media Type
	 * consumes="application/json"
	 * consumes="!application/json"
	 * consumes="application/*"
	 * consumes="*\/*"
	 * HTTP의 Content-Type(요청이나 응답의 본문에 포함된 데이터의 형식) - consumes로 받는 데이터의 졸류 확인
	 * MediaType.APPLICATION_JSON_VALUE
	 */
	@PostMapping(value = "/mapping-consume", consumes =  "application/json")
	public String mappingConsumes() {
		log.info("mappingConsumes");
		return "ok";
	}
	
	/*
	 * Accept 헤더 기반 Media Type
	 * produces = "text/html"
	 * produces = "!text/html"
	 * produces = "text/*"
	 * produces = "*\/*"
	 * HTTP의 Accept(클라이언트가 받아들일 수 있는 응답 형식) - produces로 제공할 수 있는 데이터인지 확인
	 */
	@PostMapping(value = "/mapping-produce", produces = "text/html")
	public String mappingProduces() {
		log.info("mappingProduces");
		return "ok";
	}
	
}
