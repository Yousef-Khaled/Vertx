package orange.practice.workingWithFutures;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;


/*
* First It Print "sync 1" .
* Till The Server Starts It Prints "sync 2" .
* When the Server Starts Or Fails The Future Object Print The Server State .
* When The Server Get Requested The Server Prints "sync 1" Till The end Request Method Called .
* */

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {

    Future<String> future = Future.future();

    System.out.println("sync 1");

    vertx.createHttpServer().requestHandler(httpServerRequest -> {

      System.out.println("Out of sync 1");
      httpServerRequest.response().end("Hello !");

    }).listen(8080, httpServerAsyncResult -> {

      if (httpServerAsyncResult.succeeded()) {

        future.complete("Out of sync 2");

      } else {

        future.fail(httpServerAsyncResult.cause());

      }
    });

    future.setHandler(stringAsyncResult -> {

      if (stringAsyncResult.succeeded()) {

        System.out.println(future.result());

      } else {

        System.out.println(future.cause().getMessage());

      }

    });

    System.out.println("sync 2");
  }


}
