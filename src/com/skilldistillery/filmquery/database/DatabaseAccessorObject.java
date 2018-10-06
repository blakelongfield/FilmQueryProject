package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		String sql = "SELECT id, title, description, release_year, language_id, "
				+ "rental_duration, rental_rate, length, replacement_cost, rating, "
				+ "special_features FROM film WHERE id = ?";

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
			film.setRental_rate(filmResult.getDouble("rental_rate"));
			film.setLength(filmResult.getString("length"));
			film.setReplacement_cost(filmResult.getDouble("replacement_cost"));
			film.setRating(filmResult.getString("rating"));
			film.setSpecial_features(filmResult.getString("special_features"));
			film.setActors(getActorsByFilmId(filmId));
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
	
	

	@Override
	public Film getFilmByKeyword(String keyword) throws SQLException {

		Film film = null;
		String sql = "SELECT id, title, description, release_year, language_id, FROM film WHERE title LIKE '%keyword%'";

		Connection conn = DriverManager.getConnection(URL, user, pass);
		Statement stmt = conn.prepareStatement(sql);
		ResultSet filmResult = stmt.executeQuery(sql);
		
		if (filmResult.next()) {
			film = new Film();
			film.setId(filmResult.getInt("id"));
			film.setTitle(filmResult.getString("title"));
			film.setDescription(filmResult.getString("description"));
			film.setRelease_year(filmResult.getInt("release_year"));
			film.setLanguage_id(filmResult.getInt("language_id"));
			film.setRental_rate(filmResult.getDouble("rental_rate"));
			film.setLength(filmResult.getString("length"));
			film.setReplacement_cost(filmResult.getDouble("replacement_cost"));
			film.setRating(filmResult.getString("rating"));
			film.setSpecial_features(filmResult.getString("special_features"));
		}

		filmResult.close();
		stmt.close();
		conn.close();
		return film;
	}

}