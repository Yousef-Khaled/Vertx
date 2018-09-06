package orange.practice.SuperHeroesCollection.Verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.Json;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.asyncsql.AsyncSQLClient;
import io.vertx.ext.asyncsql.PostgreSQLClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import orange.practice.SuperHeroesCollection.Model.Hero;

public class MainVerticle extends AbstractVerticle {

  private AsyncSQLClient jdbcClient;
  private Future<SQLConnection> future;

  @Override
  public void start(Future<Void> startFuture) throws Exception {


    Class.forName("org.postgresql.Driver");

    jdbcClient = PostgreSQLClient.createNonShared(vertx, new JsonObject()
      .put("url", "jdbc::postgresql://localhost:5432/")
      .put("username", "postgres")
      .put("password", "root")
      .put("driver_class", "org.postgresql.Driver")
      .put("database", "superHero"));


    future = Future.future();

    jdbcClient.getConnection(sqlConnectionAsyncResult -> {
      if (sqlConnectionAsyncResult.succeeded()) {
        future.complete(sqlConnectionAsyncResult.result());
      } else {
        future.fail("failed");
      }
    });


    Router router = Router.router(vertx);


    router.route().handler(BodyHandler.create());

    router.get("/heroes").handler(this::getAll);
    router.post("/heroes").handler(this::addOne);

    vertx.createHttpServer().requestHandler(router::accept).listen(config().getInteger("http.port", 8080), httpServerAsyncResult -> {
      if (httpServerAsyncResult.succeeded()) {
        startFuture.complete();
      } else {
        startFuture.fail(httpServerAsyncResult.cause());
      }
    });

  }

  private void getAll(RoutingContext routingContext) {
    future.setHandler(sqlConnectionAsyncResult -> {
      sqlConnectionAsyncResult.result().query("SELECT * FROM data", resultSetAsyncResult -> {
        routingContext.response().end(new JsonArray(resultSetAsyncResult.result().getRows()).toString());
      });
    });


  }

  private void addOne(RoutingContext routingContext) {
    final Hero hero = Json.decodeValue(routingContext.getBodyAsString(),
      Hero.class);
    hero.setId(Hero.getCOUNTER().get());
    String q = "INSERT INTO data VALUES (" + hero.getId() +" , '" + hero.getName() + "')";
    future.setHandler(sqlConnectionAsyncResult -> {
      sqlConnectionAsyncResult.result().execute(q, resultSetAsyncResult -> {
        routingContext.response()
          .putHeader("content-type", "application/json; charset=utf-8")
          .end("True");
      });
    });

  }

}
