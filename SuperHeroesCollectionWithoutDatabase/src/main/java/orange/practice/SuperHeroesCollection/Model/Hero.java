package orange.practice.SuperHeroesCollection.Model;

import java.util.concurrent.atomic.AtomicInteger;

public class Hero {

  private static final AtomicInteger COUNTER = new AtomicInteger();

  private int id;

  private String name;

  public Hero() {
    id = COUNTER.getAndIncrement();
  }

  public Hero(String name) {
    id = COUNTER.getAndIncrement();
    this.name = name;
  }

  public static AtomicInteger getCOUNTER() {
    return COUNTER;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }


  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
