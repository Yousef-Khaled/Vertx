import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;

public class ListVerticle extends AbstractVerticle {

  private RedisOptions config = new RedisOptions()
    .setHost("192.168.99.100");

  private RedisClient redisClient;

  @Override
  public void start(Future<Void> startFuture) throws Exception {


    //lists
    redisClient = RedisClient.create(vertx, config);

    redisClient.lpush("MARVEL", "IronMan", longAsyncResult -> {
      if (longAsyncResult.succeeded()) {
        System.out.println("IronMan pushed to MARVEL");
      } else {
        System.out.println("IronMan wasn't pushed to MARVEL");
      }
    });

    redisClient.lpush("MARVEL", "SpiderMan", longAsyncResult -> {
      if (longAsyncResult.succeeded()) {
        System.out.println("SpiderMan pushed to MARVEL");
      } else {
        System.out.println("SpiderMan wasn't pushed to MARVEL");
      }
    });
    redisClient.lpush("MARVEL", "Hulk", longAsyncResult -> {
      if (longAsyncResult.succeeded()) {
        System.out.println("Hulk pushed to MARVEL");
      } else {
        System.out.println("Hulk wasn't pushed to MARVEL");
      }
    });

    redisClient.lpush("MARVEL", "BlackWidow", longAsyncResult -> {
      if (longAsyncResult.succeeded()) {
        System.out.println("BlackWidow pushed to MARVEL");
      } else {
        System.out.println("BlackWidow wasn't pushed to MARVEL");
      }
    });

    redisClient.lrange("MARVEL", 0, -1, jsonArrayAsyncResult -> {
      if (jsonArrayAsyncResult.succeeded()) {
        System.out.println(jsonArrayAsyncResult.result());
      } else {
        System.out.println("Problem with getting the list");
      }
    });

    redisClient.save(stringAsyncResult -> {
      if (stringAsyncResult.succeeded()) {
        System.out.println("Saving successful");
      } else {
        System.out.println("Saving wasn't successful");
      }
    });

  }


  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(ListVerticle.class.getName());
  }

}
