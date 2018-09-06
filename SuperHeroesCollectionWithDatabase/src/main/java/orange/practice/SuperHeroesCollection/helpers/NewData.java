package orange.practice.SuperHeroesCollection.helpers;

import orange.practice.SuperHeroesCollection.Model.Hero;

import java.util.HashMap;


public class NewData {
  private HashMap<Integer, Hero> heroes;

  public NewData() {
    heroes = new HashMap<>();
  }

  public void SomeData() {
    Hero spiderman = new Hero("Spider-Man");
    Hero batman = new Hero("Batman");
    Hero ironMan = new Hero("Iron-Man");
    heroes.put(spiderman.getId(), spiderman);
    heroes.put(batman.getId(), batman);
    heroes.put(ironMan.getId(), ironMan);
  }

  public HashMap<Integer, Hero> getHeroes() {
    return heroes;
  }
}
