package orange.practice.SuperHeroesCollection.Verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import orange.practice.SuperHeroesCollection.Model.Hero;
import orange.practice.SuperHeroesCollection.helpers.NewData;

public class MainVerticle extends AbstractVerticle {

  private NewData data;

  @Override
  public void start(Future<Void> startFuture) throws Exception {
    data = new NewData();
    data.SomeData();

    Router router = Router.router(vertx);


    router.route().handler(BodyHandler.create());

    router.get("/heroes").handler(this::getAll);
    router.get("/heroes/:id").handler(this::getOne);
    router.post("/heroes").handler(this::addOne);
    router.delete("/heroes/:id").handler(this::delete);
    router.put("/heroes/:id").handler(this::update);



    vertx.createHttpServer().requestHandler(router::accept).listen(config().getInteger("http.port", 8080), httpServerAsyncResult -> {
      if (httpServerAsyncResult.succeeded()) {
        startFuture.complete();
      } else {
        startFuture.fail(httpServerAsyncResult.cause());
      }
    });

  }

  private void getAll(RoutingContext routingContext) {
    routingContext.response()
      .putHeader("content-type", "application/json; charset=utf-8")
      .end(Json.encodePrettily(data.getHeroes()));
  }

  private void addOne(RoutingContext routingContext) {
    final Hero hero = Json.decodeValue(routingContext.getBodyAsString(),
      Hero.class);
    hero.setId(Hero.getCOUNTER().get());
    data.getHeroes().put(hero.getId(), hero);
    routingContext.response()
      .putHeader("content-type", "application/json; charset=utf-8")
      .end(Json.encodePrettily(hero));
  }

  private void delete(RoutingContext routingContext) {
    String id = routingContext.request().getParam("id");
    data.getHeroes().remove(new Integer(id));
    routingContext.response().end("True");
  }

  private void getOne(RoutingContext routingContext) {
    String id = routingContext.request().getParam("id");
    routingContext.
      response().
      putHeader("content-type", "application/json; charset=utf-8")
      .end(Json.encodePrettily(data.getHeroes().get(new Integer(id))));
  }

  private void update(RoutingContext routingContext) {
    String id = routingContext.request().getParam("id");
    Hero hero = Json.decodeValue(routingContext.getBodyAsString(), Hero.class);
    hero.setId(new Integer(id));
    data.getHeroes().put(new Integer(id), hero);
    routingContext.response()
      .putHeader("content-type", "application/json; charset=utf-8")
      .end(Json.encodePrettily(hero));
  }
}
