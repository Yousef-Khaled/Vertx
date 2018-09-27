package io.vertx.VetxPublisherRedis;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class MainVerticle extends AbstractVerticle {

  Jedis jedis = new Jedis("192.168.99.100");
  private static int count = 0;

  @Override
  public void start(Future<Void> startFuture) throws Exception {

    vertx.createHttpServer().requestHandler(handler -> {

      ++count;

      vertx.executeBlocking(handlerB -> {
        for (int i = 0; i < 10; i++) {
          jedis.publish("Data", "SomeData " + i);
        }
        handlerB.complete("done sending");
      }, asyncResult -> {
        if (asyncResult.succeeded()) {
          System.out.println("Massages Sent");
        } else {
          System.out.println("Publisher Failed");

        }
      });

      System.out.println(count);
      handler.response().end("Thank you");
    }).listen(8082, httpServerAsyncResult -> {
      if (httpServerAsyncResult.succeeded()) {
        System.out.println("Server Started Successfully");
      } else {
        System.out.println("Something Went Wrong!");
      }
    });
  }

}
