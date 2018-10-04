package verticles;

import Entities.Person;


import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import config.CassandraConfig;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import io.vertx.ext.web.handler.BodyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class MainVerticle extends AbstractVerticle {

    @Autowired
    private CassandraConfig cassandraConfig;

    private MappingManager mappingManager;
    private Mapper<Person> mapper;

    private Router router;

    @Override
    public void start(Future<Void> startFuture) throws Exception {

        setUpRouter();
        setMapping();

        vertx.createHttpServer().requestHandler(router::accept).listen(8080, httpServerAsyncResult -> {
            if (httpServerAsyncResult.succeeded()) {
                System.out.println("Server started !");
            } else {
                System.out.println(httpServerAsyncResult.cause());
            }
        });

    }

    private void setMapping() {
        mappingManager = new MappingManager(cassandraConfig.getSession());
        mapper = mappingManager.mapper(Person.class);
    }

    private void setUpRouter() {
        router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.get("/persons").handler(this::getAll);
        router.get("/persons/:id").handler(this::getOne);
        router.post("/persons").handler(this::addOne);
        router.put("/persons/:id").handler(this::update);
        router.delete("/persons/:id").handler(this::delete);
    }

    private void getAll(RoutingContext routingContext) {

        SimpleStatement simpleStatement = new SimpleStatement("SELECT * FROM person");

        ResultSetFuture resultSetstatement = cassandraConfig.getSession().executeAsync(simpleStatement);

        Future<ResultSet> resultSetFuture = Future.future();

        Futures.addCallback(resultSetstatement, new FutureCallback<ResultSet>() {
            @Override
            public void onSuccess(ResultSet rows) {
                resultSetFuture.complete(rows);
            }

            @Override
            public void onFailure(Throwable throwable) {
                resultSetFuture.fail(throwable);
            }
        });

        resultSetFuture.setHandler(resultSetAsyncResult -> {
            if (resultSetAsyncResult.succeeded()) {
                routingContext.response().end(resultSetAsyncResult.result().all().toString());
            } else {
                routingContext.response().end(resultSetAsyncResult.cause().getMessage());
            }
        });

    }

    private void getOne(RoutingContext routingContext) {

        SimpleStatement simpleStatement = new SimpleStatement("SELECT * FROM person WHERE person_id = '" +
                routingContext.request().getParam("id") + "'");

        ResultSetFuture resultSetstatement = cassandraConfig.getSession().executeAsync(simpleStatement);

        Future<ResultSet> resultSetFuture = Future.future();

        Futures.addCallback(resultSetstatement, new FutureCallback<ResultSet>() {
            @Override
            public void onSuccess(ResultSet rows) {
                resultSetFuture.complete(rows);
            }

            @Override
            public void onFailure(Throwable throwable) {
                resultSetFuture.fail(throwable);
            }
        });

        resultSetFuture.setHandler(resultSetAsyncResult -> {
            if (resultSetAsyncResult.succeeded()) {
                routingContext.response().end(resultSetAsyncResult.result().all().toString());
            } else {
                routingContext.response().end(resultSetAsyncResult.cause().getMessage());
            }
        });

    }

    private void addOne(RoutingContext routingContext) {

        JsonObject person = routingContext.getBodyAsJson();
        ListenableFuture<Void> future = mapper.saveAsync(new Person(person.getString
                ("person_id"), person.getString("name"), person.getInteger("age"),
                person.getString("status")));
        routingContext.response().end(person.toString());
    }

    private void update(RoutingContext routingContext) {

        JsonObject person = routingContext.getBodyAsJson();
        mapper.saveAsync(new Person(routingContext.request().getParam("id").toString()
                , person.getString("name"), person.getInteger("age")
                , person.getString("status")));

        routingContext.response().end(person.toString());
    }

    public void delete(RoutingContext routingContext) {

        Person p = new Person();
        p.setPerson_id(routingContext.request().getParam("id"));
        mapper.deleteAsync(p);

        routingContext.response().end("Deleted !");
    }
}

