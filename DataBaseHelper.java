package com.example.advancepizza.dataBase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.advancepizza.Activity.MainActivity;
import com.example.advancepizza.obects.Order;
import com.example.advancepizza.obects.PizzaMenu;
import com.example.advancepizza.obects.SpecialOffers;
import com.example.advancepizza.obects.User;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    // create our table in data base
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Enable foreign key constraints
        db.execSQL("PRAGMA foreign_keys=ON;");

        // Create User table
        String createUserTableSQL = "CREATE TABLE User (" +
                "email TEXT PRIMARY KEY," +
                "phone_number TEXT NOT NULL," +
                "first_name TEXT NOT NULL," +
                "last_name TEXT NOT NULL," +
                "gender TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "authorities TEXT NOT NULL," +
                "profile_photo BLOB" +
                ")";
        db.execSQL(createUserTableSQL);

        // Create PizzaMenu table
        String createPizzaMenuTableSQL = "CREATE TABLE PizzaMenu (" +
                "pizzaId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "pizzaType TEXT NOT NULL," +
                "Description TEXT NOT NULL," +
                "size TEXT NOT NULL," +
                "price TEXT NOT NULL," +
                "category TEXT NOT NULL" +
                ")";
        db.execSQL(createPizzaMenuTableSQL);

        // Create Order table
        // Create OrderName table
        String createOrderNameTableSQL = "CREATE TABLE OrderName (" +
                "orderNameId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "customerEmail TEXT NOT NULL," + // Linking to User table via email
                "FOREIGN KEY (customerEmail) REFERENCES User(email)" +
                ")";
        db.execSQL(createOrderNameTableSQL);

// Create Order table
        String createOrderTableSQL = "CREATE TABLE `Order` (" +
                "orderId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "pizzaId INTEGER NOT NULL," +
                "orderNameId INTEGER NOT NULL," + // Linking to OrderName table via orderNameId
                "quantity INTEGER NOT NULL," +
                "totalPrice DOULBE NOT NULL," +
                "size TEXT NOT NULL," +
                "dateOfOrder TEXT NOT NULL," +
                "FOREIGN KEY (pizzaId) REFERENCES PizzaMenu(pizzaId)," +
                "FOREIGN KEY (orderNameId) REFERENCES OrderName(orderNameId)" +
                ")";
        db.execSQL(createOrderTableSQL);

        // Create SpecialOffers table
        String createSpecialOffersTableSQL = "CREATE TABLE SpecialOffers (" +
                "OfferID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "pizzaID INTEGER NOT NULL," +
                "price TEXT NOT NULL," +
                "startOffer TEXT NOT NULL," +
                "endOffer TEXT NOT NULL," +
                "FOREIGN KEY (pizzaID) REFERENCES PizzaMenu(pizzaId)" +
                ")";
        db.execSQL(createSpecialOffersTableSQL);

        // Create YourFavorites table
        String createYourFavoritesTableSQL = "CREATE TABLE YourFavorites (" +
                "favoritesId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "customerEmail TEXT NOT NULL," + // Linking to User table via email
                "pizzaID INTEGER NOT NULL," +
                "FOREIGN KEY (customerEmail) REFERENCES User(email)," +
                "FOREIGN KEY (pizzaID) REFERENCES PizzaMenu(pizzaId)" +
                ")";
        db.execSQL(createYourFavoritesTableSQL);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // insert new user to data base
    public void insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", user.getEmail());
        values.put("phone_number", user.getPhone());
        values.put("first_name", user.getFirstName());
        values.put("last_name", user.getLastName());
        values.put("gender", user.getGender());
        values.put("password", user.getPassword());
        values.put("authorities", user.getAuthorities());
        long result = db.insert("user", null, values);
    }


    // Function to check if a user exists and if the password is correct
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE email = ? AND password = ?", new String[]{email, password});
        return cursor.getCount() > 0;
    }
    // Function to check if a user exists and if the password is correct
    public boolean checkUserEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE email = ?", new String[]{email});
        return cursor.moveToFirst();
    }
    // gew the user object by his email
    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE email = ?", new String[]{email});

        User user = null;
        if (cursor.moveToFirst()) {

            // Extract user details from the cursor
            @SuppressLint("Range") String userEmail = cursor.getString(cursor.getColumnIndex("email"));
            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("phone_number"));
            @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex("first_name"));
            @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex("last_name"));
            @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex("gender"));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("password"));
            @SuppressLint("Range") String authorities = cursor.getString(cursor.getColumnIndex("authorities"));

            // Create a User object
            user = new User(userEmail,  firstName, lastName,phone,  password, authorities,gender);
        }

        cursor.close();
        return user;
    }
    // Method to update the phone number for a user
    public void updateUserPhoneNumber(String userEmail, String newPhoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("phone_number", newPhoneNumber);
        db.update("User", values, "email = ?", new String[]{userEmail});
    }
    // Method to update the first name for a user
    public void updateUserFirstName(String userEmail, String newFirstName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("first_name", newFirstName);
        db.update("User", values, "email = ?", new String[]{userEmail});
    }
    // Method to update the last name for a user
    public void updateUserLastName(String userEmail, String newLastName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("last_name", newLastName);
        db.update("User", values, "email = ?", new String[]{userEmail});
    }

    // Method to update the password for a user
    public void updateUserPassword(String userEmail, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", newPassword);
        db.update("User", values, "email = ?", new String[]{userEmail});
    }


    public void updateProfilePhotoByEmail(String email,byte[] newPhotoData ) {
        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap newPhoto = BitmapFactory.decodeByteArray(newPhotoData, 0, newPhotoData.length);
        // Compress the image
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        newPhoto.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream); // Adjust the quality as needed
        newPhotoData = byteArrayOutputStream.toByteArray();

        ContentValues values = new ContentValues();
        values.put("profile_photo", newPhotoData);

        int rowsAffected = db.update("User", values, "email = ?", new String[]{email});

        if (rowsAffected > 0) {
            Log.d("Database", "Profile photo updated successfully for email: " + email);
        } else {
            Log.d("Database", "Failed to update profile photo for email: " + email);
        }

        db.close();
    }

    public byte[] getProfilePhotoByEmail(String email) {
        byte[] profilePhotoData = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT profile_photo FROM User WHERE email = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        if (cursor != null && cursor.moveToFirst()) {
            profilePhotoData = cursor.getBlob(0);
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();
        return profilePhotoData;
    }
    public void addPizzaMenu(PizzaMenu pizzaMenu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pizzaType", pizzaMenu.getPizzaType());
        values.put("Description", pizzaMenu.getDescription());
        values.put("size", pizzaMenu.getSize());
        values.put("price", pizzaMenu.getPrice());
        values.put("category", pizzaMenu.getCategory());

        // Inserting Row
        long result = db.insert("PizzaMenu", null, values);
        db.close(); // Closing database connection
    }

    public boolean isPizzaExist(String pizzaType, String size) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the selection criteria
        String selection = "pizzaType = ? AND size = ?";

        // Define the selection arguments
        String[] selectionArgs = {pizzaType, size};

        // Execute the query
        Cursor cursor = db.query("PizzaMenu", null, selection, selectionArgs, null, null, null);

        // Check if any rows are returned
        boolean exists = cursor.getCount() > 0;

        // Close the cursor and database connection
        cursor.close();
        db.close();

        // Return the result
        return exists;
    }
    public List<PizzaMenu> getAllPizzaMenu() {
        List<PizzaMenu> pizzaMenuList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PizzaMenu", null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int pizzaId = cursor.getInt(cursor.getColumnIndex("pizzaId"));
                @SuppressLint("Range") String pizzaType = cursor.getString(cursor.getColumnIndex("pizzaType"));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("Description"));
                @SuppressLint("Range") String size = cursor.getString(cursor.getColumnIndex("size"));
                @SuppressLint("Range") String price = cursor.getString(cursor.getColumnIndex("price"));
                @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex("category"));
                PizzaMenu pizzaMenu = new PizzaMenu(pizzaId, pizzaType, description, size, price, category);
                pizzaMenuList.add(pizzaMenu);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return pizzaMenuList;
    }

    public void addFavoriteMenu(String customerEmail, int pizzaId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("customerEmail", customerEmail);
        values.put("pizzaID", pizzaId);

        // Inserting Row
        db.insert("YourFavorites", null, values);
        // Closing database connection
        db.close();
    }
    // Method to remove a favorite menu item from the YourFavorites table
    public void removeFavoriteMenu(String customerEmail, int pizzaId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = "customerEmail = ? AND pizzaID = ?";
        String[] selectionArgs = { customerEmail, String.valueOf(pizzaId) };

        // Deleting Row
        db.delete("YourFavorites", selection, selectionArgs);
        // Closing database connection
        db.close();
    }
    @SuppressLint("Range")
    public boolean isPizzaInFavorites(String customerEmail, int pizzaId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) > 0 AS is_favorite " +
                        "FROM YourFavorites " +
                        "WHERE customerEmail = ? AND pizzaID = ?",
                new String[]{customerEmail, String.valueOf(pizzaId)});

        boolean isFavorite = false;
        if (cursor.moveToFirst()) {
            isFavorite = cursor.getInt(cursor.getColumnIndex("is_favorite")) == 1;
        }

        cursor.close();
        db.close();

        return isFavorite;
    }
    public double getPriceOfPizza(String name, String size) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        double price = -1;

        try {
            // Get readable database
            db = this.getReadableDatabase();

            // Define the query
            String query = "SELECT price FROM PizzaMenu WHERE pizzaType = ? AND size = ?";

            // Execute the query
            cursor = db.rawQuery(query, new String[]{name, size});

            // Check if the result is not empty
            if (cursor != null && cursor.moveToFirst()) {
                // Get the price from the result
                @SuppressLint("Range") String priceString = cursor.getString(cursor.getColumnIndex("price"));
                price = Double.parseDouble(priceString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the cursor and the database
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return price;
    }



    public PizzaMenu getPizzaMenuById(int pizzaId) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        PizzaMenu pizzaMenu = null;

        try {
            // Get readable database
            db = this.getReadableDatabase();

            // Define the query
            String query = "SELECT * FROM PizzaMenu WHERE pizzaId = ?";

            // Execute the query
            cursor = db.rawQuery(query, new String[]{String.valueOf(pizzaId)});

            // Check if the result is not empty
            if (cursor != null && cursor.moveToFirst()) {
                // Get the details from the result
                @SuppressLint("Range") String pizzaType = cursor.getString(cursor.getColumnIndex("pizzaType"));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("Description"));
                @SuppressLint("Range") String size = cursor.getString(cursor.getColumnIndex("size"));
                @SuppressLint("Range") String price = cursor.getString(cursor.getColumnIndex("price"));
                @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex("category"));

                // Create PizzaMenu object
                pizzaMenu = new PizzaMenu(pizzaId, pizzaType, description, size, price, category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the cursor and the database
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return pizzaMenu;
    }
    public PizzaMenu getPizzaMenuByNameAndSize(String name, String size) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        PizzaMenu pizzaMenu = null;

        try {
            // Get readable database
            db = this.getReadableDatabase();

            // Define the query
            String query = "SELECT * FROM PizzaMenu WHERE pizzaType = ? AND size = ?";

            // Execute the query
            cursor = db.rawQuery(query, new String[]{name, size});

            // Check if the result is not empty
            if (cursor != null && cursor.moveToFirst()) {
                // Get the details from the result
                @SuppressLint("Range") int pizzaId = cursor.getInt(cursor.getColumnIndex("pizzaId"));
                @SuppressLint("Range") String pizzaType = cursor.getString(cursor.getColumnIndex("pizzaType"));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("Description"));
                @SuppressLint("Range") String sizeValue = cursor.getString(cursor.getColumnIndex("size"));
                @SuppressLint("Range") String price = cursor.getString(cursor.getColumnIndex("price"));
                @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex("category"));

                // Create PizzaMenu object
                pizzaMenu = new PizzaMenu(pizzaId, pizzaType, description, sizeValue, price, category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the cursor and the database
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return pizzaMenu;
    }


    public List<PizzaMenu> getAllPizzaMenueDistinctType(){
        List<PizzaMenu> pizzaMenuList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM PizzaMenu " +
                "WHERE pizzaId IN (SELECT MIN(pizzaId) FROM PizzaMenu GROUP BY pizzaType)";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int pizzaId = cursor.getInt(cursor.getColumnIndex("pizzaId"));
                @SuppressLint("Range") String pizzaType = cursor.getString(cursor.getColumnIndex("pizzaType"));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("Description"));
                @SuppressLint("Range") String size = cursor.getString(cursor.getColumnIndex("size"));
                @SuppressLint("Range") String price = cursor.getString(cursor.getColumnIndex("price"));
                @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex("category"));
                PizzaMenu pizzaMenu = new PizzaMenu(pizzaId, pizzaType, description, size, price, category);
                pizzaMenuList.add(pizzaMenu);
            } while (cursor.moveToNext());
        }
        return  pizzaMenuList;
    }

    public void insertOrder(Order order) {
        SQLiteDatabase db = null;

        try {
            // Get writable database
            db = this.getWritableDatabase();

            // Prepare content values
            ContentValues values = new ContentValues();
            values.put("pizzaId", order.getPizzaId());
            values.put("orderNameId", order.getOrderNameId());
            values.put("quantity", order.getQuantity());
            values.put("totalPrice", order.getTotalPrice());
            values.put("size", order.getSize());
            values.put("dateOfOrder", order.getDateOfOrder());

            // Insert the row
            long result = db.insert("`Order`", null, values);
            if (result == -1) {
                System.out.println("Failed to insert order");
            } else {
                System.out.println("Order inserted successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the database
            if (db != null) {
                db.close();
            }
        }
    }

    public int getLastOrderNameId() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        int lastOrderNameId = -1;

        try {
            db = this.getReadableDatabase();
            String query = "SELECT orderNameId FROM OrderName ORDER BY orderNameId DESC LIMIT 1";
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                lastOrderNameId = cursor.getInt(cursor.getColumnIndexOrThrow("orderNameId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return lastOrderNameId;
    }

    public void insertOrderName(String customerEmail, int orderNameId) {
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("orderNameId", orderNameId);
            values.put("customerEmail", customerEmail);

            db.insert("OrderName", null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }
    public List<Integer> getAllOrderNameIdsByEmail(String email) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        List<Integer> orderNameIds = new ArrayList<>();

        try {
            // Get readable database
            db = this.getReadableDatabase();
            String query = "SELECT orderNameId FROM OrderName WHERE customerEmail = ?";
            cursor = db.rawQuery(query, new String[]{email});

            // Iterate over the results and add them to the list
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int orderNameId = cursor.getInt(cursor.getColumnIndex("orderNameId"));
                    orderNameIds.add(orderNameId);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the cursor and the database
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return orderNameIds;
    }
    public List<Order> getAllOrdersByOrderNameId(int orderNameId) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        List<Order> orders = new ArrayList<>();

        try {
            // Get readable database
            db = this.getReadableDatabase();
            String query = "SELECT * FROM `Order` WHERE orderNameId = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(orderNameId)});

            // Iterate over the results and create Order objects to add to the list
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int orderId = cursor.getInt(cursor.getColumnIndex("orderId"));
                    @SuppressLint("Range") int pizzaId = cursor.getInt(cursor.getColumnIndex("pizzaId"));
                    @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex("quantity"));
                    @SuppressLint("Range") Double totalPrice = cursor.getDouble(cursor.getColumnIndex("totalPrice"));
                    @SuppressLint("Range") String size = cursor.getString(cursor.getColumnIndex("size"));
                    @SuppressLint("Range") String dateOfOrder = cursor.getString(cursor.getColumnIndex("dateOfOrder"));

                    // Constructing the Order object with retrieved values
                    Order order = new Order(orderId, pizzaId, orderNameId, quantity, totalPrice, size, dateOfOrder);
                    orders.add(order);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the cursor and the database
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
            return orders;
        }
    }

    public List<Integer> getAllOrderIds() {
        List<Integer> orderIds = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            // Get readable database
            db = this.getReadableDatabase();

            // Define the query to get all orderId values
            String query = "SELECT orderId FROM `Order`";

            // Execute the query
            cursor = db.rawQuery(query, null);

            // Iterate over the results and add the orderId to the list
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int orderId = cursor.getInt(cursor.getColumnIndex("orderId"));
                    orderIds.add(orderId);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the cursor and the database
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return orderIds;
    }
    public User getUserByOrderNameId(int orderNameId) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        User user = null;

        try {
            // Get readable database
            db = this.getReadableDatabase();

            // Step 1: Query the OrderName table to get the customerEmail
            String queryOrderName = "SELECT customerEmail FROM OrderName WHERE orderNameId = ?";
            cursor = db.rawQuery(queryOrderName, new String[]{String.valueOf(orderNameId)});

            if (cursor.moveToFirst()) {
                @SuppressLint("Range") String customerEmail = cursor.getString(cursor.getColumnIndex("customerEmail"));
                cursor.close(); // Close cursor before reusing for the next query

                // Step 2: Query the User table to get the user details
                String queryUser = "SELECT * FROM User WHERE email = ?";
                cursor = db.rawQuery(queryUser, new String[]{customerEmail});

                if (cursor.moveToFirst()) {
                    @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));
                    @SuppressLint("Range") String phoneNumber = cursor.getString(cursor.getColumnIndex("phone_number"));
                    @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex("first_name"));
                    @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex("last_name"));
                    @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex("gender"));
                    @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("password"));
                    @SuppressLint("Range") String authorities = cursor.getString(cursor.getColumnIndex("authorities"));

                    // Create a User object with the retrieved details
                    user = new User(email, firstName, lastName, phoneNumber, password, authorities, gender);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the cursor and the database
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return user;
    }


    public boolean addSpecialOffer(SpecialOffers specialOffer) {
        SQLiteDatabase db = null;
        boolean isAdded = false;

        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("pizzaID", specialOffer.getPizzaID());
            values.put("price", specialOffer.getPrice());
            values.put("startOffer", specialOffer.getStartOfer());
            values.put("endOffer", specialOffer.getEndOfer());

            long result = db.insert("SpecialOffers", null, values);
            if (result != -1) {
                isAdded = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return isAdded;
    }
    public List<SpecialOffers> getOffersByPizzaType(String pizzaType) {
        List<SpecialOffers> offers = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            // Get readable database
            db = this.getReadableDatabase();

            // Subquery to fetch all pizza IDs for the specified pizza type
            String subquery = "SELECT pizzaId FROM PizzaMenu WHERE pizzaType = ?";

            // Define the main query to get offers for the pizza IDs retrieved by the subquery
            String query = "SELECT * FROM SpecialOffers WHERE pizzaID IN (" + subquery + ")";

            // Execute the query
            cursor = db.rawQuery(query, new String[]{pizzaType});

            // Iterate over the results and create SpecialOffer objects to add to the list
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int offerId = cursor.getInt(cursor.getColumnIndex("OfferID"));
                    @SuppressLint("Range") int pizzaId = cursor.getInt(cursor.getColumnIndex("pizzaID"));
                    @SuppressLint("Range") String price = cursor.getString(cursor.getColumnIndex("price"));
                    @SuppressLint("Range") String startOffer = cursor.getString(cursor.getColumnIndex("startOffer"));
                    @SuppressLint("Range") String endOffer = cursor.getString(cursor.getColumnIndex("endOffer"));

                    // Create SpecialOffer object
                    SpecialOffers offer = new SpecialOffers(offerId, pizzaId, price, startOffer, endOffer);
                    offers.add(offer);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the cursor and the database
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return offers;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM `Order`", null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int orderId = cursor.getInt(cursor.getColumnIndex("orderId"));
                @SuppressLint("Range") int pizzaId = cursor.getInt(cursor.getColumnIndex("pizzaId"));
                @SuppressLint("Range") int orderNameId = cursor.getInt(cursor.getColumnIndex("orderNameId"));
                @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex("quantity"));
                @SuppressLint("Range") double totalPrice = cursor.getDouble(cursor.getColumnIndex("totalPrice"));
                @SuppressLint("Range") String size = cursor.getString(cursor.getColumnIndex("size"));
                @SuppressLint("Range") String dateOfOrder = cursor.getString(cursor.getColumnIndex("dateOfOrder"));

                Order order = new Order(orderId, pizzaId, orderNameId, quantity, totalPrice, size, dateOfOrder);
                orders.add(order);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return orders;
    }



}


