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
		  //...
		  String sql = "SELECT id, title, description, release_year, language_id, "
		  		+ "rental_duration, rental_rate, length, replacement_cost, rating, "
		  		+ "special_features FROM film WHERE id = ?";
		  
		  Connection conn = DriverManager.getConnection(URL, user, pass);
		  PreparedStatement stmt = conn.prepareStatement(sql);
		  stmt.setInt(1,filmId);
		  ResultSet filmResult = stmt.executeQuery();
		  if (filmResult.next()) {
		    film = new Film(); //Create the object
		    // Here is our mapping of query columns to our object fields:
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
		  //...
		  return film;
	}

@Override
public Actor getActorById(int actorId) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public List<Actor> getActorsByFilmId(int filmId) {
	// TODO Auto-generated method stub
	return null;
}

}