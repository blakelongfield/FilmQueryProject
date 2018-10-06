package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {
  
  DatabaseAccessor db = new DatabaseAccessorObject();

  public FilmQueryApp() throws ClassNotFoundException {
	  Class.forName("com.mysql.jdbc.Driver");
  }
  
  public static void main(String[] args) throws ClassNotFoundException, SQLException {
    FilmQueryApp app = new FilmQueryApp();
    app.test();
//    app.launch();
  } 

  private void test() throws SQLException {
    Film film = db.getFilmById(1);
    Actor actor = db.getActorById(1);
    List<Actor> actorsByFilm = db.getActorsByFilmId(1);
    System.out.println(film);
    System.out.println();
    System.out.println(actor);
    System.out.println();
    System.out.println(actorsByFilm);
  }

  private void launch() {
    Scanner input = new Scanner(System.in);
    
    //Navigation window
    System.out.println("");
    System.out.println("");
    System.out.println("");
    
    startUserInterface(input);
    
    input.close();
  }

  private void startUserInterface(Scanner input) {
    
  }

}
