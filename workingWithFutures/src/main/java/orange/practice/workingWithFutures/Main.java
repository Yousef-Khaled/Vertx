package orange.practice.workingWithFutures;

import io.vertx.core.Vertx;

public class Main {

  public static void main(String[] args) {

    Vertx vertx = Vertx.vertx();

    vertx.deployVerticle(WebClientAndFutures.class.getName());

    vertx.createHttpServer();

  }
}
