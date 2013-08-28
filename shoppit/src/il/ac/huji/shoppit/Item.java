package il.ac.huji.shoppit;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseFile;
import com.parse.ParseUser;

/*
 * An extension of ParseObject that makes
 * it more convenient to access information
 * about a given Item 
 */

@ParseClassName("Item")
public class Item extends ParseObject {

	public Item() {
		// A default constructor is required.
	}

	public String getName() {
		return getString("name");
	}

	public void setName(String name) {
		put("name", name);
	}

	public ParseUser getAuthor() {
		return getParseUser("author");
	}

	public void setAuthor(ParseUser user) {
		put("author", user);
	}

	public String getRating() {
		return getString("rating");
	}

	public void setRating(String rating) {
		put("rating", rating);
	}

	public ParseFile getPhotoFile() {
		return getParseFile("photo");
	}

	public void setPhotoFile(ParseFile file) {
		put("photo", file);
	}

}