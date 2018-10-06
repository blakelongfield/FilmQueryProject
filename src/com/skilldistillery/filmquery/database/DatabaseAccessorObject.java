package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private final String user = "student";
	private final String pass = "student";
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";

	public Film getFilmById(int filmId) throws SQLException {

		Film film = null;

		String sql = "SELECT film.id, title, description, release_year, language_id, language.name, "
				+ "rating FROM film JOIN language on language.id = film.language_id WHERE film.id = ?";

		Connection conn = DriverManager.getConnection(URL, user, pass);
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, filmId);
		ResultSet filmResult = stmt.executeQuery();

		if (filmResult.next()) {
			film = new Film();
			film.setId(filmResult.getInt("id"));
			film.setTitle(filmResult.getString("title"));
			film.setDescription(filmResult.getString("description"));
			film.setRelease_year(filmResult.getInt("release_year"));
			film.setLanguage_id(filmResult.getInt("language_id"));
			film.setLanguage_name(filmResult.getString("language.name"));
//			film.setRental_rate(filmResult.getDouble("rental_rate"));						//commented out in case they are need for future use
//			film.setLength(filmResult.getString("length"));
//			film.setReplacement_cost(filmResult.getDouble("replacement_cost"));
			film.setRating(filmResult.getString("rating"));
//			film.setSpecial_features(filmResult.getString("special_features"));
			film.setActors(getActorsByFilmId(filmId));
		}
		if (film == null) {
			System.out.println("We apologize, but there is no title with that ID in our library.");
			System.exit(0); 																// stretch goal for menu to re-appear for user
		}

		filmResult.close();
		stmt.close();
		conn.close();
		return film;
	}

	public Actor getActorById(int actorId) throws SQLException {

		Actor actor = null;
		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";

		Connection conn = DriverManager.getConnection(URL, user, pass);
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, actorId);
		ResultSet actorResult = stmt.executeQuery();

		if (actorResult.next()) {
			actor = new Actor();
			actor.setId(actorResult.getInt("id"));
			actor.setFirst_name(actorResult.getString("first_name"));
			actor.setLast_name(actorResult.getString("last_name"));
		}

		actorResult.close();
		stmt.close();
		conn.close();
		return actor;

	}

	@Override
	public List<Actor> getActorsByFilmId(int filmId) {
		List<Actor> actorsByFilm = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT actor.id, actor.first_name, actor.last_name, "
					+ "film_actor.film_id FROM actor JOIN film_actor "
					+ "ON film_actor.actor_id = actor.id WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int actorId = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				Actor actor = new Actor(actorId, firstName, lastName);
				actorsByFilm.add(actor);
			}

			rs.close();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return actorsByFilm;
	}

	public List<Film> getFilmByKeyword(String keyword) throws SQLException {
		List<Film> films = new ArrayList<>();

		String sql = "SELECT film.id, title, description, release_year, rating, language_id, language.name FROM film JOIN language on language.id = film.language_id WHERE title like ? OR description like ?";
		Connection conn = DriverManager.getConnection(URL, user, pass);
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, "%" + keyword + "%");
		stmt.setString(2, "%" + keyword + "%");

		ResultSet filmResult = stmt.executeQuery();
		int counter = 1;

		while (filmResult.next()) {
			Film film = new Film();
			film.setId(filmResult.getInt("id"));
			film.setTitle(filmResult.getString("title"));
			film.setDescription(filmResult.getString("description"));
			film.setRelease_year(filmResult.getInt("release_year"));
			film.setRating(filmResult.getString("rating"));
			film.setLanguage_id(filmResult.getInt("language_id"));
			film.setLanguage_name(filmResult.getString("language.name"));
			films.add(film);

			System.out.println(counter + " " + film + "\n");
			counter++;
		}

		if (films.isEmpty()) {
			System.out.println("We apologize, but there is no title with that ID in our library.");
			System.exit(0); 														// stretch goal for menu to re-appear for user
		}

		filmResult.close();
		stmt.close();
		conn.close();

		return films;
	}

}