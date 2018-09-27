import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;

public class KeysAndHashes extends AbstractVerticle {

    private RedisOptions config = new RedisOptions()
            .setHost("192.168.99.100");
    private RedisClient redisClient;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        redisClient = RedisClient.create(vertx, config);

        redisClient.set("HELLO", "Hello World!", voidAsyncResult -> {
            if (voidAsyncResult.succeeded()) {
                System.out.println("Key Is Set");
            } else {
                System.out.println("Key Wasn't Set");
            }
        });

        redisClient.get("HELLO", stringAsyncResult -> {
            if (stringAsyncResult.succeeded()) {
                System.out.println(stringAsyncResult
                        .result());
            } else {
                System.out.println("Problem getting key");
            }
        });


        redisClient.del("HELLO", longAsyncResult -> {
            if (longAsyncResult.succeeded()) {
                System.out.println("Key Was Deleted");
            } else {
                System.out.println("Key Wasn't Deleted");
            }
        });


        redisClient.mset(new JsonObject().put("SuperHero1", "Saitama").put("SuperHero2", "Goku"), stringAsyncResult -> {
            if (stringAsyncResult.succeeded()) {
                System.out.println("Added M Keys");
            } else {
                System.out.println("Problem Adding M keys");
            }
        });

        redisClient.get("SuperHero1", stringAsyncResult -> {
            if (stringAsyncResult.succeeded()) {
                System.out.println(stringAsyncResult.result());
            } else {
                System.out.println("Problem getting M Keys");
            }
        });

        redisClient.get("SuperHero2", stringAsyncResult -> {
            if (stringAsyncResult.succeeded()) {
                System.out.println(stringAsyncResult.result());
            } else {
                System.out.println("Problem getting M Keys");
            }
        });

        redisClient.hset("BigKey", "SmallKey1", "Value1", longAsyncResult -> {
            if (longAsyncResult.succeeded()) {
                System.out.println("H Big key set");
            } else {
                System.out.println("H Big key wasn't set");
            }
        });

        redisClient.hset("BigKey", "SmallKey2", "Value2", longAsyncResult -> {
            if (longAsyncResult.succeeded()) {
                System.out.println("H Big key set");
            } else {
                System.out.println("H Big key wasn't set");
            }
        });

        redisClient.hget("BigKey" , "SmallKey1" , stringAsyncResult -> {
            if (stringAsyncResult.succeeded()){
                System.out.println(stringAsyncResult.result());
            }else{
                System.out.println("Error Getting SmallKey1");
            }
        });


        redisClient.hget("BigKey" , "SmallKey2" , stringAsyncResult -> {
            if (stringAsyncResult.succeeded()){
                System.out.println(stringAsyncResult.result());
            }else{
                System.out.println("Error Getting SmallKey2");
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
        vertx.deployVerticle(KeysAndHashes.class.getName());
    }

}
