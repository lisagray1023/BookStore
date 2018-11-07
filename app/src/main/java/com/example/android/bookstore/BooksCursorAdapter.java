package com.example.android.bookstore;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bookstore.data.BookContract;

import java.util.Set;

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
    public void bindView(View view, Context context, Cursor cursor) {
        //Find individual views to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);

        //Find columns of book attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_NAME);
        int priceColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_QUANTITY);

        //Read the book attributes from the cursor for the current book
        String bookName = cursor.getString(nameColumnIndex);
        int bookPrice = cursor.getInt(priceColumnIndex);
        int bookQuantity = cursor.getInt(quantityColumnIndex);

        //Update the TextViews with the attributes of the current book
        nameTextView.setText(bookName);
        priceTextView.setText(Integer.toString(bookPrice));
        quantityTextView.setText(Integer.toString(bookQuantity));
    }


    /**helper method to reduce the quantity of books when one is sold
    public int bookSold(View view, Cursor cursor) {

     //Find sold button view
     Button soldButton = (Button) view.findViewById(R.id.sale_button);

     //Find column for quantity
      int quantityColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_QUANTITY);

      //Extract the value in the quantity attribute and store it in a variable
        int bookQuantity = cursor.getInt(quantityColumnIndex);

        //Update the quantity value to represent 1 book sold
        if (bookQuantity == 0) {
            Toast.makeText(this, getString(R.string.sale_not_possible), Toast.LENGTH_SHORT).show();
            return bookQuantity;
        } else {
            int newBookQuantity = bookQuantity - 1;
            return newBookQuantity;
        }




     //Get writeable database to update the data
     SQLiteDatabase database = mDbHelper.getWritableDatabase();

     int quantityColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_QUANTITY);

     //find quantity value and assign to a variable
     int bookQuantity = cursor.getInt(quantityColumnIndex);

     //reduce quantity by one
     if (bookQuantity > 0) {
     int newBookQuantity = bookQuantity - 1;
     return newBookQuantity;
     } else if (bookQuantity == 0)
     Toast.makeText("Book cannot be sold if quantity is 0", MainActivity, Toast.LENGTH_SHORT).show();
     else {
     return bookQuantity;
     }
     }





    //Set up on item click listener on sold button
     soldButton.setOnItemClickListener (new View.OnClickListener() {
    @Override
    public void onClick(View v) {
    //Form the content URI that represents the specific book whose sold button was clicked
    //and append ID
    Uri currentBookUri = ContentUris.withAppendedId(BookEntry.CONTENT_URI, id);

    //Call the bookSold method and pass in the Uri
    bookSold(currentBookUri, values, null, null);
    }
    }); */
}
