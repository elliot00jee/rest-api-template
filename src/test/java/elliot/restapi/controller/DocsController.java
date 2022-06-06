package elliot.restapi.controller;

import elliot.restapi.util.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/docs")
public class DocsController {

    @GetMapping("/common-response")
    public ResponseEntity<?> getCommonResponse() {
        return ResponseUtils.success(new HashMap<>().put("docs", "common"), "result message");
    }
}
