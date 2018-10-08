package com.example.android.bookinventory.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


/**
 * API Contract for the Books app.
 */
public final class BookContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private BookContract(){}

    public static final String CONTENT_AUTHORITY = "com.example.android.bookinventory";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     */
    public static final String PATH_BOOKS = "books";

    /**
     * Inner class that defines constant values for the books database table.
     * Each entry in the table represents a single book.
     */
    public static final class BookEntry implements BaseColumns{

        /** The content URI to access the book data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BOOKS);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of books.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single book.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;

        /** Name of database table for books */
        public final static String TABLE_NAME = "books";

        /**
         * Unique ID number for the book (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Title of the book.
         *
         * Type: TEXT
         */
        public final static String COLUMN_BOOK_TITLE = "title";

        /**
         * Author of the book.
         *
         * Type: TEXT
         */
        public final static String COLUMN_BOOK_AUTHOR = "author";

        /**
         * Price of the book.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_BOOK_PRICE = "price";

        /**
         * Quantity of the book.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_BOOK_QUANTITY = "quantity";

        /**
         * Supplier Phone Number.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_SUPPLIER_PHONE_NUMBER = "phone_number";

        /**
         * Book Supplier.
         *
         * The only possible values are {@link #AMAZON}, {@link #BARNES_AND_NOBLES},
         * or {@link #BOOKS_A_MILLION}.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_BOOK_SUPPLIER = "supplier";

        /**
         * Possible values for the supplier of the book.
         */
        public static final int AMAZON = 0;
        public static final int BARNES_AND_NOBLES = 1;
        public static final int BOOKS_A_MILLION = 2;


        /**
         * Returns whether or not the supplier is {@link #AMAZON}, {@link #BARNES_AND_NOBLES},
         * or {@link #BOOKS_A_MILLION}.
         */
        public static boolean isValidSupplier(int supplier) {

            if (supplier == AMAZON || supplier == BARNES_AND_NOBLES || supplier == BOOKS_A_MILLION) {
                return true;
            }
            return false;
        }
    }
}
