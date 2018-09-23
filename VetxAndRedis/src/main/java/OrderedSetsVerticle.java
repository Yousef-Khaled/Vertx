import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;

public class OrderedSetsVerticle extends AbstractVerticle {

    private RedisOptions config = new RedisOptions()
            .setHost("192.168.99.100");

    private RedisClient redisClient;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        redisClient = RedisClient.create(vertx, config);
        redisClient.zadd("Anime", 8, "Naruto", longAsyncResult -> {
            if (longAsyncResult.succeeded()) {
                System.out.println("Anime was added");
            } else {
                System.out.println("Anime Wasn't Added");
            }

        });
        redisClient.zadd("Anime", 10, "One Piece", longAsyncResult -> {
            if (longAsyncResult.succeeded()) {
                System.out.println("Anime was added");
            } else {
                System.out.println("Anime Wasn't Added");
            }

        });
        redisClient.zadd("Anime", 9, "One Punch Man", longAsyncResult -> {
            if (longAsyncResult.succeeded()) {
                System.out.println("Anime was added");
            } else {
                System.out.println("Anime Wasn't Added");
            }

        });

        redisClient.zrange("Anime", 0, -1, jsonArrayAsyncResult -> {
            if (jsonArrayAsyncResult.succeeded()) {
                System.out.println(jsonArrayAsyncResult.result());
            } else {
                System.out.println("Something went wrong");
            }
        });

        redisClient.zrem("Anime", "Naruto", longAsyncResult -> {
            if (longAsyncResult.succeeded()) {
                System.out.println("Anime removed");
            } else {
                System.out.println("Anime wasn't removed");
            }
        });

        redisClient.zrange("Anime", 0, -1, jsonArrayAsyncResult -> {
            if (jsonArrayAsyncResult.succeeded()) {
                System.out.println(jsonArrayAsyncResult.result());
            } else {
                System.out.println("Something went wrong");
            }
        });
    }

    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(OrderedSetsVerticle.class.getName());
    }
}
