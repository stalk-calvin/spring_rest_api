package hello;

import java.util.concurrent.atomic.AtomicLong;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(
            value = "/greeting",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        Greeting g = new Greeting(counter.incrementAndGet(),
                String.format(template, name));
        log.info("/api/greeting called: " + g.toString());
        return g;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Response defaultCall() {
        return new Response("Default call!");
    }
}