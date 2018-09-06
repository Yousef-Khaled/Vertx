package orange.practice.SuperHeroesCollection.App;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import orange.practice.SuperHeroesCollection.Verticle.MainVerticle;

public class Main {

  public static void main(String[] args) {

    Vertx vertx = Vertx.vertx();

    MainVerticle mainVerticle = new MainVerticle();

    DeploymentOptions deploymentOptions = new DeploymentOptions()
      .setConfig(new JsonObject()
        .put("http.port", 8082));

    vertx.deployVerticle(mainVerticle, deploymentOptions);
  }

}
