package hello.springMVC.basic.request;

import hello.springMVC.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Controller
@Slf4j
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");
    }

    @RequestMapping("/request-param-v2")
    @ResponseBody
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge
    ){
        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    @RequestMapping("/request-param-v3")
    @ResponseBody
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age //HTTP 파라미터와 변수명이 동일하면 생략 가능
    ){
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @RequestMapping("/request-param-v4")
    @ResponseBody
    public String requestParamV4(String username, int age) { //단순 타입이면 @RequestParam도 생략가능
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @RequestMapping("/request-param-required")
    @ResponseBody
    public String requestParamRequired(
            @RequestParam(required = true) String username,
            @RequestParam(required = false) int age //required는 기본은 true, falsse의 경우는 값이 없어도 좋다
    ){ //int는 null이 불가, Integer는 null 가능
        //null과 ""(빈문자)는 다르다. ?username= 이렇게 하면 ""로 나온다
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @RequestMapping("/request-param-default")
    @ResponseBody
    public String requestParamDefault(@RequestParam(defaultValue = "guest") String username,
                                      @RequestParam(defaultValue = "-1") int age
    ) { //defaultValue를 사용하면 required는 의미가 없다. 값을 넣지 않더라도 기본값이 들어가기 때문에
        //""(빈문자열)도 기본값으로 대체해준다.
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @RequestMapping("/request-param-map")
    @ResponseBody
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age "));
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1( @RequestParam("username") String username,
                                    @RequestParam("age") int age )
    {
        HelloData helloData = new HelloData();
        helloData.setUsername(username);
        helloData.setAge(age);

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v1-1")
    public String modelAttributeV1_1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        //@RequestParam은 기본형으로 인식
        //@ModelAttribute은 직접 만든 클래스로 인식
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }
}
