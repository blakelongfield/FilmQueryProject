package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
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
			System.out.println("1. Look up a film by its ID");
			System.out.println("2. Look up a film by a search keyword");
			System.out.println("3. Exit the application");
			userInput = input.next();

			switch (userInput) { // main-menu starts here
			case "1":
				System.out.print("\nPlease enter a film ID: ");
				try {
					int selection = input.nextInt();
					Film film = db.getFilmById(selection);
					if (film == null) {
						System.out.println();
						break;
					}
					System.out.println(film + "\n");
					System.out.println("Would you like to..."); // sub-menu starts here
					System.out.println("1. Return to the main menu");
					System.out.println("2. View all film details");
					String subMenuSelection = input.next();
					switch (subMenuSelection) { // inner-switch menu starts here
					case "1":
						System.out.println();
						break;
					case "2":
						db.getFilmByIdAllDetails(selection).toStringAllDetails();
						break;
					default:
						System.out.println("Please enter a valid response");
						subMenuSelection = input.next();
						break;
					}
				} catch (InputMismatchException ime) {
					System.out.println("\nPlease enter a valid response \n");
					input.next();
				}
				break; // case 1 in outer switch breaks here
			case "2":
				System.out.print("\nPlease enter your keyword: ");
				db.getFilmByKeyword(input.next());
				System.out.println();
				break;
			case "3":
				System.exit(0);
			default:
				System.out.println("\nPlease enter a valid response\n");
				break;
			}
		} while (!userInput.equals("3"));
	}
	
//	public String subMenuSelection(Scanner input, int selection) throws SQLException {
//		System.out.println("Would you like to..."); // sub-menu starts here
//		System.out.println("1. Return to the main menu");
//		System.out.println("2. View all film details");
//		String subMenuSelection = input.next();
//		switch (subMenuSelection) { // inner-switch menu starts here
//		case "1":
//			System.out.println();
//			break;
//		case "2":
//			db.getFilmByIdAllDetails(selection).toStringAllDetails();
//			break;
//		default:
//			System.out.println("Please enter a valid response");
//			subMenuSelection = input.next();
//			break;
//		}
//		return "";
//	}
}
