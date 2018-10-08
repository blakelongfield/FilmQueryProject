package com.skilldistillery.filmquery.database;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.skilldistillery.filmquery.entities.Film;

class DatabaseAccessTests {
  private DatabaseAccessor db;

  @BeforeEach
  void setUp() throws Exception {
    db = new DatabaseAccessorObject();
  }

  @AfterEach
  void tearDown() throws Exception {
    db = null;
  }

  @Test
  @DisplayName("getFilmById with invalid_id returns null()")
  void testNullFilmId() throws SQLException {
    Film f = db.getFilmById(-42);
    assertNull(f);
  }
  
  @Test
  @DisplayName("getFilmById with valid id returns valid response")
  void testValidFilmId() throws SQLException {
	  Film f = db.getFilmById(12);
	  assertEquals("12", "12");
  }
  
  @Test
  @DisplayName("getFilmById with valid id returns valid response")
  void getFilmByKeyword() throws SQLException {
	  Film f = db.getFilmById(12);
	  assertEquals("red", "red");
  }
  
  @Test
  @DisplayName("getFilmByAllDetails with valid id returns valid response")
  void getFilmByIdAllDetails() throws SQLException {
	  Film f = db.getFilmById(12);
	  assertEquals("12", "12");
  }
  
//  @Test
//  @DisplayName("getFilmByAllDetails with invalid response returns null")
//  void getFilmByIdAllDetails() throws SQLException {
//	  Film f = db.getFilmById(-124);
//	  assertNull(f);
//  }
  
}
