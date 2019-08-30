package api_gateway.controller;

import api_gateway.model.Greeting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@RestController
@RequestMapping("/api")
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(
            value = "/v1/greeting",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Greeting greetingv1(@RequestParam(value="name", defaultValue="Lee") String name) {
        Greeting g = new Greeting(counter.incrementAndGet(),
                String.format(template, name));
        log.info("/api/v1/greeting called: " + g.toString());
        return g;
    }

    @RequestMapping(
            value = "/v2/greeting",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Greeting greetingv2(@RequestParam(value="name", defaultValue="Calvin") String name) {
        Greeting g = new Greeting(counter.incrementAndGet(),
                String.format(template, name));
        log.info("/api/v2/greeting called: " + g.toString());
        return g;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Response defaultCall() {
        return new Response("Default call!");
    }
}