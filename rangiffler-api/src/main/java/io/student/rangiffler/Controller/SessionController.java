package io.student.rangiffler.Controller;

import io.student.rangiffler.model.SessionJson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/session")
public class SessionController {

    @GetMapping
    public SessionJson session() {
        return new SessionJson(
                null,
                null,
                null
        );
    }
}
