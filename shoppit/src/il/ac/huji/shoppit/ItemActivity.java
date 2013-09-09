package il.ac.huji.shoppit;

import java.util.HashMap;

import com.parse.CountCallback;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseCloud;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ItemActivity extends Activity {

	public final static String EXTRA_ITEM_ID = "il.ac.huji.shoppit.ITEM_ID";

	private TextView nameTextView;
	private TextView priceTextView;
	private TextView categoryTextView;
	private CheckBox likeCheckBox;
	private TextView likesCountTextView;
	private Integer likesCount;
	private ParseImageView imageView;
	private ListView commentsListView;

	private Button addCommentButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item);

		// The placeholder will be used before and during the fetch, to be replaced by the fetched image
		// data.
		//		ParseImageView imageView = (ParseImageView) findViewById(R.id.photoParseImageView);
		//		imageView.setPlaceholder(getResources().getDrawable(R.drawable.placeholder));


		// TODO - this is bad. We're downloading the item again here. See the note in ItemAdapter.java
		// for more info.

		Intent intent = getIntent();
		final String itemId = intent.getStringExtra(EXTRA_ITEM_ID);

		ParseQuery<Item> query = ParseQuery.getQuery("Item");
		query.getInBackground(itemId, new GetCallback<Item>() {
			public void done(Item item, ParseException e) {
				if (e == null) {
					setupViews(item);
				} else {
					//					objectRetrievalFailed();
					Log.e("ITEM_ACTIIVTY", "Failed to load object.");
				}
			}
		});

		nameTextView = (TextView) findViewById(R.id.nameTextView);
		priceTextView = (TextView) findViewById(R.id.priceTextView);
		categoryTextView = (TextView) findViewById(R.id.categoryTextView);
		likeCheckBox = (CheckBox) findViewById(R.id.likeCheckBox);
		likesCountTextView = (TextView) findViewById(R.id.likesCountTextView);
		imageView = (ParseImageView) findViewById(R.id.photoParseImageView);
		commentsListView = (ListView) findViewById(R.id.commentsListView);
		addCommentButton = (Button) findViewById(R.id.addCommentButton);

		likeCheckBox.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (likeCheckBox.isChecked()) {
					likesCount++;
				} else {
					likesCount--;
				}
				likesCountTextView.setText(likesCount.toString());
				
				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put("itemId", itemId);
				params.put("like", likeCheckBox.isChecked());

				ParseCloud.callFunctionInBackground("likeItem", params, new FunctionCallback<String>() {
					public void done(String message, ParseException e) {
						if (e == null) {
							Log.i("ITEM_ACTIVITY", message);
						} else {
							Log.i("ITEM_ACTIVITY", "ERROR: " + e.getMessage());
						}
					}
				});
			}
		});
		
		addCommentButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fm = getFragmentManager();
		        CommentDialog testDialog = new CommentDialog();
		        testDialog.setRetainInstance(true);
		        testDialog.show(fm, "fragment_comment_dialog");
			}
			
		});

	}

	protected void setupViews(Item item) {
		
		nameTextView.setText(item.getName());
		priceTextView.setText(String.valueOf(item.getPrice()));
		categoryTextView.setText(item.getMainCategory());

		// get the number of users that like the item
		ParseQuery<ParseUser> queryLikesCount = item.getLikesRelation().getQuery();
		queryLikesCount.countInBackground(new CountCallback() {
			public void done(int count, ParseException e) {
				if (e == null) {
					likesCount = count;
					likesCountTextView.setText(likesCount.toString());
				} else {
					Log.e("LIKE_CHECK_BOX",  "error2 with the likesCount query: " + e.getMessage());
				}
			}
		});

		// check if user has liked the object
		ParseQuery<ParseUser> queryUserLikes = item.getLikesRelation().getQuery();
		queryUserLikes.whereEqualTo("objectId", ParseUser.getCurrentUser().get("objectId"));

		queryUserLikes.countInBackground(new CountCallback() {
			public void done(int count, ParseException e) {
				if (e == null) {
					if (count == 1) {
						likeCheckBox.setChecked(true);
					} else if (count == 0) {
						likeCheckBox.setChecked(false);
					} else {
						Log.e("LIKE_CHECK_BOX", "error1 with the likes query");
					}
				} else {
					Log.e("LIKE_CHECK_BOX",  "error2 with the likes query: " + e.getMessage());
				}
			}
		});


		ParseFile photoFile = item.getPhotoFile();
		if (photoFile != null) {
			imageView.setParseFile(photoFile);
			imageView.loadInBackground(new GetDataCallback() {
				@Override
				public void done(byte[] data, ParseException e) {
					// nothing to do
				}
			});
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.item, menu);
		return true;
	}
	

}
