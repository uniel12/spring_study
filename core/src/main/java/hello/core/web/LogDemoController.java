package hello.core.web;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hello.core.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LogDemoController {
	private final LogDemoService logDemoService;
	private final MyLogger myLogger;
//	private final ObjectProvider<MyLogger> myLoggerProvider;

	@RequestMapping("log-demo")
	@ResponseBody // 뷰 화면 없이 문자 반환
	public String logDemoService(HttpServletRequest request) throws InterruptedException {
		String requestURL = request.getRequestURL().toString();
//		MyLogger myLogger = myLoggerProvider.getObject();
		System.out.println("myLogger = "+myLogger.getClass());
		myLogger.setRequestURL(requestURL);
		myLogger.log("controller test");
		Thread.sleep(1000);
		logDemoService.logic("testId");
		return "OK";
	}
}