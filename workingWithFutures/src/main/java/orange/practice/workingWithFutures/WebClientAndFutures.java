package orange.practice.workingWithFutures;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;


public class WebClientAndFutures extends AbstractVerticle {

  @Override
  public void start() throws Exception {

    WebClient client = WebClient.create(vertx);

    Future<JsonArray> future = Future.future();

    client
      .get(80, "mysafeinfo.com", "/api/data?list=englishmonarchs&format=json")
      .send(res -> {
        if (res.succeeded()) {
          future.complete(res.result().bodyAsJsonArray());
        } else {
          future.complete(new JsonArray().add(new JsonObject().put("fail", res.cause())));
        }
      });


    future.setHandler(jsonArrayAsyncResult -> {
      vertx.createHttpServer().requestHandler(httpServerRequest -> {
        httpServerRequest.response().end(future.result().toString());
      }).listen(8080, httpServerAsyncResult -> {

      });
    });
  }
}
