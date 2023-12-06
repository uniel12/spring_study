package hello.springmvc.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController // RestController : return 이 그냥 문자열 (http의 메세지 body에 직접 담아서 보내는 것임), controller : return이 view 이름
@Slf4j
public class LogTestController {
	
//	private final Logger log = LoggerFactory.getLogger(getClass()); // @Slf4j 롬복 대체 가능
	
	@RequestMapping("/log-test")
	public String logTest() {
		String name = "Spring";
		System.out.println("name = "+name);
		
		log.trace("trace log={}", name);
		log.debug("debug log={}", name);
		log.info("  info log={}", name);
		log.warn(" 	warn log={}", name);
		log.error("error log={}", name);
		
		return "ok";
	}
}
