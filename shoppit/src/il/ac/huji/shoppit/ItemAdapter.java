package il.ac.huji.shoppit;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQueryAdapter;
import com.parse.ParseException;

public class ItemAdapter extends ParseQueryAdapter<Item> {
	
	private final static String TAG = "ItemAdapter";
	
	private Context mContext;

	private TextView nameTextView;
	private TextView priceTextView;
	private TextView currencyTextView;
	private ParseImageView imageView;

	public ItemAdapter(Context context, ParseQueryAdapter.QueryFactory<Item> queryFactory) {
		super(context, queryFactory);
		
		mContext = context;
	}

	@Override
	public View getItemView(final Item item, View v, ViewGroup parent) {

		if (v == null) {
			v = View.inflate(getContext(), R.layout.item_list, null);
		}

		super.getItemView(item, v, parent);
	
		nameTextView = (TextView) v.findViewById(R.id.text1);
		priceTextView = (TextView) v.findViewById(R.id.priceTextView);
		currencyTextView = (TextView) v.findViewById(R.id.currencyTextView);
		imageView = (ParseImageView) v.findViewById(R.id.icon);
		
		nameTextView.setText(item.getName());		
		priceTextView.setText(String.valueOf(item.getPrice()));
		currencyTextView.setText(String.valueOf(item.getCurrency()));
		
		imageView.setPlaceholder(mContext.getResources().getDrawable(R.drawable.placeholder));
		
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

		v.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GeneralInfo.itemHolder = item;
				Intent intent = new Intent(mContext, ItemActivity.class);
				mContext.startActivity(intent);
			}
		});

		return v;
	}

}