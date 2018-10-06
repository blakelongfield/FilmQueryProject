package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.InputMismatchException;
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

	public void startUserInterface(Scanner input) throws SQLException {
		String userInput;

		do {
			System.out.println("How would you like to proceed?");
			System.out.println("1. Look up film by its id");
			System.out.println("2. Look up a film by a search keyword");
			System.out.println("3. Exit the application");
			userInput = input.next();

			switch (userInput) {
			case "1":
				System.out.print("\nPlease enter a film id: ");
				Film film = db.getFilmById(input.nextInt());
				if (film == null) {
					System.out.println();
					break;
				}
				System.out.println(film + "\n");
				
//				//Sub-menu for film id (stretch goal)
//				System.out.println("Would you like to...");
//				System.out.println("1. Return to the main menu");
//				System.out.println("2. View all film details");
//				String subMenuSelection = input.next();
				break;
			case "2":
				System.out.print("\nPlease enter your keyword: ");
				db.getFilmByKeyword(input.next());
				System.out.println();
				break;
			case "3":
				System.exit(0);
			default:
				System.out.println("Please enter a valid response");
				userInput = input.next();
				break;
			}
		} while (!userInput.equals("3"));
	}
}
