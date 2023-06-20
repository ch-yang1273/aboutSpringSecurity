package study.security.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class TempRestController {

    @GetMapping("/messages")
    public String apiMessage() {
        return "messages ok";
    }
}
