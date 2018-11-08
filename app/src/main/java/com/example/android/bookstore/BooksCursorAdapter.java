package com.example.android.bookstore;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.bookstore.data.BookContract;
import com.example.android.bookstore.data.BookContract.BookEntry;


/**
 * {@link BooksCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of book data as its data source. This adapter knows
 * how to create list items for each row of book data in the {@link Cursor}.
 */
public class BooksCursorAdapter extends CursorAdapter {


    /**
     * Constructs a new {@link BooksCursorAdapter}.
     *
     * @param context The context
     * @param cursor       The cursor from which to get the data.
     */
    public BooksCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0 );

    }


    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the book data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current book can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        //Find individual views to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);
        final TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
        final TextView soldButton = (TextView) view.findViewById(R.id.sale_button);
        final TextView saleTextView = (TextView) view.findViewById(R.id.sale_view);

        //Find columns of book attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_NAME);
        int priceColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_QUANTITY);
        int idColumnIndex = cursor.getColumnIndex(BookEntry. _ID);

        //Read the book attributes from the cursor for the current book
        String bookName = cursor.getString(nameColumnIndex);
        int bookPrice = cursor.getInt(priceColumnIndex);
        int bookQuantity = cursor.getInt(quantityColumnIndex);

        //Update the TextViews with the attributes of the current book
        nameTextView.setText(bookName);
        priceTextView.setText(Integer.toString(bookPrice));
        quantityTextView.setText(Integer.toString(bookQuantity));
        saleTextView.setText(cursor.getString(idColumnIndex));

        //set up Click Listener on the soldButton
        soldButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //get current quantity from TextView
                int updateQuantity = Integer.parseInt(quantityTextView.getText().toString().trim());
                if (updateQuantity > 0) {
                    updateQuantity -= 1;
                    quantityTextView.setText(Integer.toString(updateQuantity));
                    //get id from view
                    long id_number = Integer.parseInt(saleTextView.getText().toString());
                    Uri bookSelected = ContentUris.withAppendedId(BookEntry.CONTENT_URI, id_number);
                    ContentValues values = new ContentValues();
                    values.put(BookEntry.COLUMN_BOOK_QUANTITY, quantityTextView.getText().toString());
                    //update database
                    int rowsAffected = context.getContentResolver().update(bookSelected, values, null, null);
                    //confirm whether quantity was updated or not
                    if (rowsAffected == 0) {
                        Toast.makeText(context, R.string.quantity_update_error, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, R.string.quantity_updated, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, R.string.sale_not_possible, Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

}
