package il.ac.huji.shoppit;

import java.util.Arrays;
import java.util.HashMap;

import org.json.JSONArray;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseFile;
import com.parse.ParseRelation;
import com.parse.ParseUser;

/*
 * An extension of ParseObject that makes
 * it more convenient to access information
 * about a given Item 
 */

@ParseClassName("Item")
public class Item extends ParseObject {
	
	private static HashMap<String,String> currencies = new HashMap<String,String>();
	
	static void fillMap() {
		currencies.put("USD", "$");
		currencies.put("NIS", "₪");
		currencies.put("GBP", "£");
		currencies.put("EUR", "€");
	}

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

	public ParseFile getPhotoFile() {
		return getParseFile("photo");
	}

	public void setPhotoFile(ParseFile file) {
		put("photo", file);
	}
	
	public String getBarcode() {
		return getString("barcode");
	}

	public void setBarcode(String barcode) {
		put("barcode", barcode);
	}
	
	public double getPrice() {
		return getDouble("price");
	}

	public void setPrice(double price) {
		put("price", price);
	}
	
	public String getCurrency() {
		return getString("currency");
		//String currencyName = getString("currency");
		//String currencySymbol = currencies.get(currencyName);
		//return currencySymbol == null ? currencyName : currencySymbol;
	}

	public void setCurrency(String currency) {
		put("currency", currency);
	}

	public ParseGeoPoint getLocation() {
		return getParseGeoPoint("location");
	}

	public void setLocation(ParseGeoPoint point) {
		put("location", point);
	}
	
	public ParseRelation<ParseUser> getLikesRelation() {
		return getRelation("likes");
	}
	
	public int getLikesCount() {
		return getInt("likesCount");
	}
	
	// use Cloud function to like an Item

	public String getMainCategory() {
		return getString("mainCategory");
	}

	public void setMainCategory(String category) {
		put("mainCategory", category);
	}
	
	public JSONArray getKeywords() {
		return getJSONArray("keywords");
	}

	public void setKeywords(String[] keywords) {
		addAllUnique("keywords", Arrays.asList(keywords));
	}

	public Shop getShop() {
		return (Shop) getParseObject("shop");
	}
	
	public void setShop(Shop shop) {
		put("shop", shop);
	}
	
	
	
}