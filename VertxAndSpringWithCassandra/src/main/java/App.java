
import io.vertx.core.Vertx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import verticles.MainVerticle;

import javax.annotation.PostConstruct;


@SpringBootApplication
@ComponentScan(basePackages = {"config", "verticles"})
public class App {

    @Autowired
    MainVerticle mainVerticle;

    public static void main(String[] args) {

        SpringApplication.run(App.class, args);

    }

    @PostConstruct
    void deploy() {
        Vertx.vertx().deployVerticle(mainVerticle);
    }

}
