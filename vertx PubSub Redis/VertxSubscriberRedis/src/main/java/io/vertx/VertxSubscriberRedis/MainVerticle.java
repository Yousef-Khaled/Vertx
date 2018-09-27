package io.vertx.VertxSubscriberRedis;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class MainVerticle extends AbstractVerticle {

  Jedis jedis = new Jedis("192.168.99.100");

  @Override
  public void start(Future<Void> startFuture) throws Exception {

    Runnable runnable = () -> {
      jedis.subscribe(new JedisPubSub() {
        @Override
        public void onMessage(String channel, String message) {
          System.out.println("Channel " + channel + " Message " + message);
        }
      }, "Data");
    };

    Thread subscriberThread = new Thread(runnable);
    subscriberThread.start();

  }

}
