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
		// app.test();
		app.launch();
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

	private void launch() throws SQLException {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) throws SQLException {
		System.out.println("How would you like to proceed?");
		System.out.println("1. Look up film by its id");
		System.out.println("2. Look up a film by a search keyword");
		System.out.println("3. Exit the application");
		String userInput = input.next();

		while (true) {
			if (userInput.equals("1")) {
				System.out.print("\nPlease enter a film id: ");
				Film film = db.getFilmById(input.nextInt());
				System.out.println(film);
			}

			if (userInput.equals("2")) {
				System.out.print("\nPlease enter your keyword: ");
				Film keywordFilm = db.getFilmByKeyword(input.next());
				System.out.println(keywordFilm);
			}

			if (userInput.equals("3")) {
				System.exit(0);
			}

			else {
				userInput = input.next();
			}
		}
	}
}
