package trvoid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping(value="/{name}")
    public Greeting sayHello(@PathVariable String name) {
        return new Greeting(name, "Hello from Server");
    }
}
