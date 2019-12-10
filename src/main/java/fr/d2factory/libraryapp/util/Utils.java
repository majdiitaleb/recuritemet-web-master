package fr.d2factory.libraryapp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.core.JsonParser;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.ISBN;

public class Utils {

	public Utils() {

	}

	public List<Book> getBooks() {

		List<Book> books = new ArrayList<Book>();

		JSONParser parser = new JSONParser();
		InputStream inputStream = null;
		JSONObject json = null;
		try {
			inputStream = getClass().getClassLoader().getResourceAsStream("books.json");

			if (inputStream != null) {
				BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
				StringBuilder responseStrBuilder = new StringBuilder();

				String inputStr;
				while ((inputStr = streamReader.readLine()) != null)
					responseStrBuilder.append(inputStr);

				Object obj = parser.parse(responseStrBuilder.toString());
				JSONArray bookList = (JSONArray) obj;
				for (Object o : bookList) {

					JSONObject bookObject = (JSONObject) o;
					String title = (String) bookObject.get("title");
					String author = (String) bookObject.get("author");
					Object isbnObject = (Object) bookObject.get("isbn");
					JSONObject isbnnObject = (JSONObject) isbnObject;
					Long isbnCode = (Long) isbnnObject.get("isbnCode");


					ISBN isbn = new ISBN(isbnCode);
					Book book = new Book(title, author, isbn);
					books.add(book);

				}

			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}

	
		return books;
	}

	public static long differenceBetweenDatesInDays(LocalDate firstDate, LocalDate secondDate) {
		final long days = ChronoUnit.DAYS.between(firstDate, secondDate);
		return days;
	}

	public static void main(String[] args) {

		LocalDate date1 = LocalDate.now(), date2 = null;
		date2 = date1.plusDays(3);

		System.out.println(differenceBetweenDatesInDays(date1, date2));
		new Utils().getBooks();
	}
}
