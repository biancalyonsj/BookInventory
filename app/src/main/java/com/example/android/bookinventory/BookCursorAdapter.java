package com.example.android.bookinventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bookinventory.data.BookContract.BookEntry;

/**
 * {@link BookCursorAdapter} is an adapter for the list view
 * that uses a {@link Cursor} of book data as its data source. This adapter knows
 * how to create list items for each row of book data in the {@link Cursor}.
 */
public class BookCursorAdapter extends CursorAdapter {

    Context mQuantityContext;

    /**
     * Constructs a new {@link BookCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 );
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
        // Context of the quantity
        mQuantityContext = context;

        // Find individual views that we want to modify in the list item layout
        TextView titleTextView = (TextView) view.findViewById(R.id.title);
        TextView authorTextView = (TextView) view.findViewById(R.id.author);
        TextView priceTextView = (TextView) view.findViewById(R.id.price_text);
        ImageButton saleImageButton = (ImageButton) view.findViewById(R.id.sale_button);
        final TextView quantityTextView = (TextView) view.findViewById(R.id.quantity_text);

        // Find the columns of book attributes that we're interested in
        int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
        int titleColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_TITLE);
        int authorColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_AUTHOR);
        int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_QUANTITY);

        // Read the pet attributes from the Cursor for the current book
        final String id = cursor.getString(idColumnIndex);
        String bookTitle = cursor.getString(titleColumnIndex);
        String bookAuthor = cursor.getString(authorColumnIndex);
        String bookPrice = cursor.getString(priceColumnIndex);
        final int bookQuantity = cursor.getInt(quantityColumnIndex);

        // If the book author is empty string or null, then use some default text
        // that says "Unknown author", so the TextView isn't blank.
        if (TextUtils.isEmpty(bookAuthor)) {
            bookAuthor = context.getString(R.string.unknown_author);
        }

        // Update the TextViews with the attributes for the current book
        titleTextView.setText(bookTitle);
        authorTextView.setText(bookAuthor);
        priceTextView.setText(bookPrice);
        quantityTextView.setText(String.valueOf(bookQuantity));

        // When the user clicks the sale button, decrease the quantity
        // by one if applicable
        saleImageButton.setOnClickListener(new View.OnClickListener() {
            int newBookQuantity;
            @Override
            public void onClick(View v){
                if (bookQuantity >= 1 ){
                    newBookQuantity = bookQuantity -1;
                    ContentValues values = new ContentValues();
                    values.put(BookEntry.COLUMN_BOOK_QUANTITY, newBookQuantity);
                    quantityTextView.setText(newBookQuantity +"");
                    Uri currentBookUri = Uri.withAppendedPath(BookEntry.CONTENT_URI, id);
                    mQuantityContext.getContentResolver().update(currentBookUri, values, null, null);

                } else {
                    Toast.makeText(mQuantityContext, "This Book is out of stock", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
