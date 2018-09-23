import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.http.HttpServer;

public class MyWorkingVertx extends AbstractVerticle {

    @Override
    public void start() throws Exception {

        long timerID = vertx.setPeriodic(1, id -> {
            System.out.println("And every second this is printed");
        });

        System.out.println("First this is printed");
    }


}
