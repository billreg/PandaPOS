package com.floki.sqlite;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;


import com.floki.entity.Category;
import com.floki.entity.Consumption;
import com.floki.entity.Inventory;
import com.floki.entity.Product;
import com.floki.entity.QuantityPerCategory;
import com.floki.entity.ResumeGeneralVentas;
import com.floki.entity.Sale;
import com.floki.entity.Subcategory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SqliteHelperJarvis extends SQLiteOpenHelper {
    private static Boolean isUpdate = false;
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "FlokiDB.sqlite";

    // -- TABLES OF DATABASE HARBY --
    private static final String TABLE_CATEGORIES = "CATEGORIES";
    private static final String TABLE_SUBCATEGORIES = "SUBCATEGORIES";
    private static final String TABLE_PRODUCTS = "PRODUCTS";
    private static final String TABLE_INVENTORY = "INVENTORY";
    private static final String TABLE_SALES = "SALES";
    private static final String TABLE_CONSUMPTION= "CONSUMPTIONS";



    // CATEGORIES Table Columns
    private static final String KEY_CATEGORY_categoryId = "categoryId";
    private static final String KEY_CATEGORY_fkBusinessId = "fkBusinessId";
    private static final String KEY_CATEGORY_name = "name";
    private static final String KEY_CATEGORY_active = "active";

    // SUBCATEGORIES Table Columns
    private static final String KEY_SUBCATEGORY_subcategoryId = "subcategoryId";
    private static final String KEY_SUBCATEGORY_fkcategoryId = "fkCategoryId";
    private static final String KEY_SUBCATEGORY_fkBusinessId = "fkBusinessId";
    private static final String KEY_SUBCATEGORY_name = "name";
    private static final String KEY_SUBCATEGORY_active = "active";



    // PRODUCTS Table Columns
    private static final String KEY_PRODUCT_productId = "productId";
    private static final String KEY_PRODUCT_fkBusinessId = "fkBusinessId";
    private static final String KEY_PRODUCT_fkCategoryId = "fkCategoryId";
    private static final String KEY_PRODUCT_fkSubcategoryId = "fkSubcategoryId";
    private static final String KEY_PRODUCT_fkBrandId = "fkBrandId";
    private static final String KEY_PRODUCT_fkSupplierId = "fkSupplierId";

    private static final String KEY_PRODUCT_barcode= "barcode";
    private static final String KEY_PRODUCT_nameProduct = "nameProduct";

    private static final String KEY_PRODUCT_presentation = "presentation";
    private static final String KEY_PRODUCT_volumeWeight = "volumeWeight";
    private static final String KEY_PRODUCT_isDrink= "isDrink";


    private static final String KEY_PRODUCT_quantityPerPackage = "quantityPerPackage";

    private static final String KEY_PRODUCT_unitPurchasePriceXmayor = "unitPurchasePriceXmayor";
    private static final String KEY_PRODUCT_unitSalePriceXmayor = "unitSalePriceXmayor";
    private static final String KEY_PRODUCT_unitSalePriceXminor = "unitSalePriceXminor";

    private static final String KEY_PRODUCT_image = "image";
    private static final String KEY_PRODUCT_description = "description";
    private static final String KEY_PRODUCT_active = "active";


    // INVENTORY TABLE COLUMNS
    private static final String KEY_INVENTORY_inventoryId = "inventoryId";
    private static final String KEY_INVENTORY_fkProductId = "fkProductId";
    private static final String KEY_INVENTORY_fkBusinessId = "fkBusinessId";
    private static final String KEY_INVENTORY_nameProduc = "nameProduc";
    private static final String KEY_INVENTORY_purchasePriceUnit = "purchasePriceUnit";
    private static final String KEY_INVENTORY_quantityUnits = "quantityUnits";
    private static final String KEY_INVENTORY_quantityRemainning = "quantityRemainning";
    private static final String KEY_INVENTORY_active = "active";

    // SALES Table Columns/**/
    private static final String KEY_SALES_saleId = "saleId";
    private static final String KEY_SALES_fkBusinessId = "fkBusinessId";
    private static final String KEY_SALES_fkEmployeeId = "fkEmployeeId";
    private static final String KEY_SALES_fkClientId = "fkClientId";

    private static final String KEY_SALES_saleDate = "saleDate";
    private static final String KEY_SALES_voucherType = "voucherType";
    private static final String KEY_SALES_voucherNumber = "voucherNumber";
    private static final String KEY_SALES_payType = "payType";

    private static final String KEY_SALES_total = "total";
    private static final String KEY_SALES_igv = "igv";
    private static final String KEY_SALES_descripcion = "descripcion";
    private static final String KEY_SALES_estado = "estado";


    // CONSUMPTION Table Columns
    private static final String KEY_CONSUMPTION_consumptionId = "consumptionId";
    private static final String KEY_CONSUMPTION_fkBusinessId = "fkBusinessId";
    private static final String KEY_CONSUMPTION_fkSaleId = "fkSaleId";
    private static final String KEY_CONSUMPTION_fkCategoryId = "fkCategoryId";
    private static final String KEY_CONSUMPTION_fkSubcategoryId = "fkSubcategoryId";
    private static final String KEY_CONSUMPTION_fkProductId = "fkProductId";

    private static final String KEY_CONSUMPTION_nameProduct = "nameProduct";
    private static final String KEY_CONSUMPTION_quantityPerPackage = "quantityPerPackage";


    private static final String KEY_CONSUMPTION_pricePurchaseUnitXmayor = "pricePurchaseUnitXmayor";
    private static final String KEY_CONSUMPTION_priceSaleUnitXmayor = "priceSaleUnitXmayor";
    private static final String KEY_CONSUMPTION_priceSaleUnitXminor = "priceSaleUnitXminor";

    private static final String KEY_CONSUMPTION_typeSale= "typeSale";
    private static final String KEY_CONSUMPTION_salePrice = "salePrice";
    private static final String KEY_CONSUMPTION_quantity = "quantity";
    private static final String KEY_CONSUMPTION_totalSale = "totalSale";

    private static final String KEY_CONSUMPTION_icedDrink = "icedDrink";
    private static final String KEY_CONSUMPTION_quantityIIDD = "quantityIIDD";
    private static final String KEY_CONSUMPTION_priceIIDD = "priceIIDD";
    private static final String KEY_CONSUMPTION_totalIIDD = "totalIIDD";

    private static final String KEY_CONSUMPTION_totalConsumption = "totalConsumption";
    private static final String KEY_CONSUMPTION_consumptionDate = "consumptionDate";
    private static final String KEY_CONSUMPTION_active = "active";


    // TAGS
    public static final String TAG_ADD_CATEGORY = "ERROR ADD CATEGORY";
    public static final String TAG_ADD_CATEGORIES = "ERROR ADD CATEGORIES";
    public static final String TAG_LIST_CATEGORIES = "ERROR GET CATEGORIES BY CATEGORY";
    public static final String TAG_ADD_PRODUCT = "ERROR ADD PRODUCT";
    public static final String TAG_ADD_SALE = "ERROR ADD SALE";
    public static final String TAG_ADD_SALES ="ERROR ADD SALES";
    public static final String TAG_ADD_CONSUMPTION = "ERROR ADD CONSUMPTION";
    public static final String TAG_ADD_LIST_CONSUMPTIOS = "ERROR ADD LIST CONSUMPTIOS";
    public static final String TAG_LIST_COMSUMPTIOS = "ERROR LISTAR CONSUMPTIOS";
    public static final String TAG_RESUME_GENERAL_COMSUMPTIOS = "ERROR RESUME GENERAL";
    public static final String TAG_ERROR_UPDATE_PRODUCT = "ERROR TO UPDATE PRODUCT";

    private static final
    String CATEGORIES_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS "+TABLE_CATEGORIES+"("+
                    KEY_CATEGORY_categoryId+" INTEGER PRIMARY KEY,"+
                    KEY_CATEGORY_fkBusinessId+" INTEGER,"+
                    KEY_CATEGORY_name+" TXT,"+
                    KEY_CATEGORY_active+" INTEGER)";

    private static final
    String SUBCATEGORIES_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS "+TABLE_SUBCATEGORIES+"("+
                    KEY_SUBCATEGORY_subcategoryId+" INTEGER PRIMARY KEY,"+
                    KEY_SUBCATEGORY_fkcategoryId+" INTEGER,"+
                    KEY_SUBCATEGORY_fkBusinessId+" INTEGER,"+
                    KEY_SUBCATEGORY_name+" TXT,"+
                    KEY_SUBCATEGORY_active+" INTEGER)";

    private static final
    String PRODUCTS_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS "+TABLE_PRODUCTS+"("+
                    KEY_PRODUCT_productId+" INTEGER PRIMARY KEY,"+
                    KEY_PRODUCT_fkBusinessId+" INTEGER,"+
                    KEY_PRODUCT_fkCategoryId+" INTEGER,"+
                    KEY_PRODUCT_fkSubcategoryId+" INTEGER,"+
                    KEY_PRODUCT_fkBrandId+" INTEGER,"+
                    KEY_PRODUCT_fkSupplierId+" INTEGER,"+

                    KEY_PRODUCT_barcode+" TXT,"+
                    KEY_PRODUCT_nameProduct+" TXT,"+

                    KEY_PRODUCT_presentation+" TXT,"+
                    KEY_PRODUCT_volumeWeight+" TXT,"+
                    KEY_PRODUCT_isDrink+" INTEGER,"+

                    KEY_PRODUCT_quantityPerPackage+" INTEGER,"+

                    KEY_PRODUCT_unitPurchasePriceXmayor+" REAL,"+
                    KEY_PRODUCT_unitSalePriceXmayor+" REAL,"+
                    KEY_PRODUCT_unitSalePriceXminor+" REAL,"+

                    KEY_PRODUCT_image+" TXT,"+
                    KEY_PRODUCT_description+" TXT,"+
                    KEY_PRODUCT_active+" INTEGER)";

    private static final
    String  INVENTORY_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS "+TABLE_INVENTORY+"("+
                    KEY_INVENTORY_inventoryId+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    KEY_INVENTORY_fkProductId+" INTEGER,"+
                    KEY_INVENTORY_fkBusinessId+" INTEGER,"+
                    KEY_INVENTORY_nameProduc+" TXT,"+
                    KEY_INVENTORY_purchasePriceUnit+" REAL,"+
                    KEY_INVENTORY_quantityUnits+" INTEGER,"+
                    KEY_INVENTORY_quantityRemainning+" INTEGER,"+
                    KEY_INVENTORY_active+" INTEGER)";

    private static final
    String SALES_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS "+TABLE_SALES+"("+
                    KEY_SALES_saleId+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    KEY_SALES_fkBusinessId+" INTEGER,"+
                    KEY_SALES_fkEmployeeId+" INTEGER,"+
                    KEY_SALES_fkClientId+" INTEGER," +

                    KEY_SALES_saleDate+" TXT," +
                    KEY_SALES_voucherType+" TXT," +
                    KEY_SALES_voucherNumber+" TXT," +
                    KEY_SALES_payType+" TXT," +

                    KEY_SALES_total+" INTEGER," +
                    KEY_SALES_igv+" INTEGER," +
                    KEY_SALES_descripcion+" TXT," +
                    KEY_SALES_estado+" INTEGER)";

    private static final
    String CONSUMPTION_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS "+TABLE_CONSUMPTION+"("+
                    KEY_CONSUMPTION_consumptionId+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    KEY_CONSUMPTION_fkBusinessId+" INTEGER,"+
                    KEY_CONSUMPTION_fkSaleId+" INTEGER," +
                    KEY_CONSUMPTION_fkCategoryId+" INTEGER,"+
                    KEY_CONSUMPTION_fkSubcategoryId+" INTEGER,"+
                    KEY_CONSUMPTION_fkProductId+" INTEGER,"+

                    KEY_CONSUMPTION_nameProduct+" TXT," +
                    KEY_CONSUMPTION_quantityPerPackage+" INTEGER," +

                    KEY_CONSUMPTION_pricePurchaseUnitXmayor+" REAL," +
                    KEY_CONSUMPTION_priceSaleUnitXmayor+" REAL," +
                    KEY_CONSUMPTION_priceSaleUnitXminor+" REAL," +


                    KEY_CONSUMPTION_typeSale+" TXT," +
                    KEY_CONSUMPTION_salePrice+" REAL," +
                    KEY_CONSUMPTION_quantity+" INTEGER," +
                    KEY_CONSUMPTION_totalSale+" REAL," +

                    KEY_CONSUMPTION_icedDrink +" INTEGER," +
                    KEY_CONSUMPTION_quantityIIDD +" INTEGER," +
                    KEY_CONSUMPTION_priceIIDD +" INTEGER," +
                    KEY_CONSUMPTION_totalIIDD+" REAL," +


                    KEY_CONSUMPTION_totalConsumption+" REAL," +
                    KEY_CONSUMPTION_consumptionDate+" txt," +
                    KEY_CONSUMPTION_active+" INTEGER)";

    private static final String DROPE_CATEGORIES = "DROP TABLE IF EXISTS " + TABLE_CATEGORIES;
    private static final String DROPE_SUBCATEGORIES = "DROP TABLE IF EXISTS " + TABLE_SUBCATEGORIES;
    private static final String DROPE_PRODUCTS = "DROP TABLE IF EXISTS " + TABLE_PRODUCTS;
    private static final String DROPE_INVENTORY = "DROP TABLE IF EXISTS " + TABLE_INVENTORY;
    private static final String DROPE_SALES = "DROP TABLE IF EXISTS " + TABLE_SALES;
    private static final String DROPE_CONSUMPTION = "DROP TABLE IF EXISTS " + TABLE_CONSUMPTION;



    private static SqliteHelperJarvis sInstance;

    public static synchronized SqliteHelperJarvis getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SqliteHelperJarvis(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private SqliteHelperJarvis(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CATEGORIES_TABLE_CREATE);
        db.execSQL(SUBCATEGORIES_TABLE_CREATE);
        db.execSQL(PRODUCTS_TABLE_CREATE);
        db.execSQL(INVENTORY_TABLE_CREATE);
        db.execSQL(SALES_TABLE_CREATE);
        db.execSQL(CONSUMPTION_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db2, int i, int i1) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(DROPE_CATEGORIES);
        db.execSQL(DROPE_SUBCATEGORIES);
        db.execSQL(DROPE_PRODUCTS);
        db.execSQL(DROPE_INVENTORY);
        db.execSQL(DROPE_SALES);
        db.execSQL(DROPE_CONSUMPTION);
        onCreate(db);
    }

    public static Boolean getIsUpdate() {
        return isUpdate;
    }

    public static void setIsUpdate(Boolean isUpdate) {
        SqliteHelperJarvis.isUpdate = isUpdate;
    }

    public void deleteDataBase(Context context){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(DROPE_CATEGORIES);
        db.execSQL(DROPE_SUBCATEGORIES);
        db.execSQL(DROPE_PRODUCTS);
        db.execSQL(DROPE_INVENTORY);
        db.execSQL(DROPE_SALES);
        db.execSQL(DROPE_CONSUMPTION);
        onCreate(db);
    }





    // -----------------------  methods for category ------------------------------------
    public void addCategory(Category category) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues ValuesCategory = new ContentValues();
            ValuesCategory.put(KEY_CATEGORY_categoryId, category.getCategoryId());
            ValuesCategory.put(KEY_CATEGORY_name, category.getName());
            ValuesCategory.put(KEY_CATEGORY_active, category.getActive());
            db.insertOrThrow(TABLE_CATEGORIES, null, ValuesCategory);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG_ADD_CATEGORY, "Error while trying to add category to database");
        } finally {
            db.endTransaction();
        }
    }

    public void updateCategory(Category category) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues ValuesCategory = new ContentValues();
            ValuesCategory.put(KEY_CATEGORY_categoryId, category.getCategoryId());
            ValuesCategory.put(KEY_CATEGORY_name, category.getName());
            ValuesCategory.put(KEY_CATEGORY_active, category.getActive());

            db.update(TABLE_CATEGORIES, ValuesCategory, KEY_CATEGORY_categoryId + "= ?", new String[]{category.getCategoryId().toString()});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG_ADD_CATEGORY, "Error while trying to update category to database");
        } finally {
            db.endTransaction();
        }
    }

    public  void addCategories(List<Category> listCategories){
        try{
            for (Category category : listCategories) {
                addCategory(category);
            }
        }catch (Exception e){
            Log.d(TAG_ADD_CATEGORIES, "Error while trying to add categories to database");
        }
    }

    @SuppressLint("Range")
    public  Category getByCategoryId(int categoryId){
        SQLiteDatabase db = getReadableDatabase();
        Category category = new Category();
        String CATEGORY_SELECT_QUERY =  "SELECT * FROM "+TABLE_CATEGORIES+" where categoryId = "+ categoryId;
        Cursor cursorCategories = db.rawQuery(CATEGORY_SELECT_QUERY, null);
        try{
            if(cursorCategories.moveToFirst()){
                do {
                    category.setCategoryId(cursorCategories.getInt(cursorCategories.getColumnIndex(KEY_CATEGORY_categoryId)));
                    category.setName(cursorCategories.getString(cursorCategories.getColumnIndex(KEY_CATEGORY_name)));
                    if (cursorCategories.getInt(cursorCategories.getColumnIndex(KEY_CATEGORY_active)) == 1) {
                        category.setActive(true);
                    }
                    if (cursorCategories.getInt(cursorCategories.getColumnIndex(KEY_CATEGORY_active)) == 0) {
                        category.setActive(false);
                    }
                }while (cursorCategories.moveToNext());
            }
        }catch (Exception e){
            Log.d(TAG_LIST_CATEGORIES, "Error while trying to add category to database");
        }finally {
            if (cursorCategories != null && !cursorCategories.isClosed()) {
                cursorCategories.close();
            }
        }
        return category;
    }

    @SuppressLint({"Range", "LongLogTag"})
    public List<Category> listCategoriesByCategoryId(int limitInf, int limitSup){
        SQLiteDatabase db = getReadableDatabase();
        List<Category> listCategories = new ArrayList<>();
        String CATEGORY_SELECT_QUERY =  "SELECT * FROM "+TABLE_CATEGORIES+" where categoryId BETWEEN "+limitInf +" AND "+limitSup ;
        Cursor cursorCategories = db.rawQuery(CATEGORY_SELECT_QUERY, null);
        try{
            if(cursorCategories.moveToFirst()){
                do {
                    Category category = new Category();
                    category.setCategoryId(cursorCategories.getInt(cursorCategories.getColumnIndex(KEY_CATEGORY_categoryId)));
                    category.setName(cursorCategories.getString(cursorCategories.getColumnIndex(KEY_CATEGORY_name)));
                    if (cursorCategories.getInt(cursorCategories.getColumnIndex(KEY_CATEGORY_active)) == 1) {
                        category.setActive(true);
                    }
                    if (cursorCategories.getInt(cursorCategories.getColumnIndex(KEY_CATEGORY_active)) == 0) {
                        category.setActive(false);
                    }
                    listCategories.add(category);
                }while (cursorCategories.moveToNext());
            }
        }catch (Exception e){
            Log.d(TAG_LIST_CATEGORIES, "Error while trying to add category to database");
        }finally {
            if (cursorCategories != null && !cursorCategories.isClosed()) {
                cursorCategories.close();
            }
        }
        return listCategories;
    }


    @SuppressLint({"Range", "LongLogTag"})
    public List<Category> listCategoriesByActive(){
        SQLiteDatabase db = getReadableDatabase();
        List<Category> listCategories = new ArrayList<>();
        String CATEGORY_SELECT_QUERY =  "SELECT * FROM "+TABLE_CATEGORIES+" where active = 1" ;
        Cursor cursorCategories = db.rawQuery(CATEGORY_SELECT_QUERY, null);
        try{
            if(cursorCategories.moveToFirst()){
                do {
                    Category category = new Category();
                    category.setCategoryId(cursorCategories.getInt(cursorCategories.getColumnIndex(KEY_CATEGORY_categoryId)));
                    category.setName(cursorCategories.getString(cursorCategories.getColumnIndex(KEY_CATEGORY_name)));
                    if (cursorCategories.getInt(cursorCategories.getColumnIndex(KEY_CATEGORY_active)) == 1) {
                        category.setActive(true);
                    }
                    if (cursorCategories.getInt(cursorCategories.getColumnIndex(KEY_CATEGORY_active)) == 0) {
                        category.setActive(false);
                    }
                    listCategories.add(category);
                }while (cursorCategories.moveToNext());
            }
        }catch (Exception e){
            Log.d(TAG_LIST_CATEGORIES, "Error while trying to add category to database");
        }finally {
            if (cursorCategories != null && !cursorCategories.isClosed()) {
                cursorCategories.close();
            }
        }
        return listCategories;
    }



    // -----------------------  methods for subcategory ------------------------------------
    public void addSubcategory(Subcategory subcategory) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues ValuesCategory = new ContentValues();
            ValuesCategory.put(KEY_SUBCATEGORY_subcategoryId, subcategory.getSubcategoryId());
            ValuesCategory.put(KEY_SUBCATEGORY_fkcategoryId, subcategory.getFkCategoryId());
            ValuesCategory.put(KEY_SUBCATEGORY_name, subcategory.getName());
            ValuesCategory.put(KEY_SUBCATEGORY_active, subcategory.getActive());

            db.insertOrThrow(TABLE_SUBCATEGORIES, null, ValuesCategory);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG_ADD_CATEGORY, "Error while trying add category to database");
        } finally {
            db.endTransaction();
        }
    }

    public void updateSubcategory(Subcategory subcategory) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues ValuesCategory = new ContentValues();
            ValuesCategory.put(KEY_SUBCATEGORY_subcategoryId, subcategory.getSubcategoryId());
            ValuesCategory.put(KEY_SUBCATEGORY_fkcategoryId, subcategory.getFkCategoryId());
            ValuesCategory.put(KEY_SUBCATEGORY_name, subcategory.getName());
            ValuesCategory.put(KEY_SUBCATEGORY_active, subcategory.getActive());

            db.update(TABLE_SUBCATEGORIES, ValuesCategory, KEY_SUBCATEGORY_subcategoryId + "= ?", new String[]{subcategory.getSubcategoryId().toString()});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG_ADD_CATEGORY, "Error while trying add category to database");
        } finally {
            db.endTransaction();
        }
    }

    public  void addSubcategories(List<Subcategory> listSubcategories){
        try{
            for (Subcategory subcategory : listSubcategories) {
                addSubcategory(subcategory);
            }
        }catch (Exception e){
            Log.d(TAG_ADD_CATEGORIES, "Error while trying add categories to database");
        }
    }

    @SuppressLint("Range")
    public Subcategory getSubcategoryByName(String subcategoryName){
        SQLiteDatabase db = getReadableDatabase();
        Subcategory subcategory = new Subcategory();
        String SUBCATEGORY_SELECT_QUERY =  "SELECT * FROM "+TABLE_SUBCATEGORIES+" where name = \""+subcategoryName+"\";" ;
        Cursor cursorCategories = db.rawQuery(SUBCATEGORY_SELECT_QUERY, null);
        try{
            if(cursorCategories.moveToFirst()){
                do {

                    subcategory.setSubcategoryId(cursorCategories.getInt(cursorCategories.getColumnIndex(KEY_SUBCATEGORY_subcategoryId)));
                    subcategory.setFkCategoryId(cursorCategories.getInt(cursorCategories.getColumnIndex(KEY_SUBCATEGORY_fkcategoryId)));
                    subcategory.setName(cursorCategories.getString(cursorCategories.getColumnIndex(KEY_SUBCATEGORY_name)));
                    if (cursorCategories.getInt(cursorCategories.getColumnIndex(KEY_SUBCATEGORY_active)) == 1) {
                        subcategory.setActive(true);
                    }
                    if (cursorCategories.getInt(cursorCategories.getColumnIndex(KEY_SUBCATEGORY_active)) == 0) {
                        subcategory.setActive(false);
                    }
                }while (cursorCategories.moveToNext());
            }
        }catch (Exception e){
            Log.d(TAG_LIST_CATEGORIES, "Error while trying to add category to database");
        }finally {
            if (cursorCategories != null && !cursorCategories.isClosed()) {
                cursorCategories.close();
            }
        }
        return subcategory;
    }

    @SuppressLint({"Range", "LongLogTag"})
    public List<Subcategory> listSubcategoriesByCategoryId(int categoryId){
        SQLiteDatabase db = getReadableDatabase();
        List<Subcategory> listSubategories = new ArrayList<>();
        String SUBCATEGORY_SELECT_QUERY =  "SELECT * FROM "+TABLE_SUBCATEGORIES+" where fkCategoryId = "+categoryId ;
        Cursor cursorCategories = db.rawQuery(SUBCATEGORY_SELECT_QUERY, null);
        try{
            if(cursorCategories.moveToFirst()){
                do {
                    Subcategory subcategory = new Subcategory();
                    subcategory.setSubcategoryId(cursorCategories.getInt(cursorCategories.getColumnIndex(KEY_SUBCATEGORY_subcategoryId)));
                    subcategory.setFkCategoryId(cursorCategories.getInt(cursorCategories.getColumnIndex(KEY_SUBCATEGORY_fkcategoryId)));
                    subcategory.setName(cursorCategories.getString(cursorCategories.getColumnIndex(KEY_SUBCATEGORY_name)));
                    if (cursorCategories.getInt(cursorCategories.getColumnIndex(KEY_SUBCATEGORY_active)) == 1) {
                        subcategory.setActive(true);
                    }
                    if (cursorCategories.getInt(cursorCategories.getColumnIndex(KEY_SUBCATEGORY_active)) == 0) {
                        subcategory.setActive(false);
                    }

                    listSubategories.add(subcategory);
                }while (cursorCategories.moveToNext());
            }
        }catch (Exception e){
            Log.d(TAG_LIST_CATEGORIES, "Error while trying add category to database");
        }finally {
            if (cursorCategories != null && !cursorCategories.isClosed()) {
                cursorCategories.close();
            }
        }
        return listSubategories;
    }

    @SuppressLint({"Range", "LongLogTag"})
    public List<Subcategory> listSubcategoriesByCategoryIdAndActive(int categoryId){
        SQLiteDatabase db = getReadableDatabase();
        List<Subcategory> listSubategories = new ArrayList<>();
        String SUBCATEGORY_SELECT_QUERY =  "SELECT * FROM "+TABLE_SUBCATEGORIES+" where fkCategoryId = "+ categoryId + " and active = 1" ;
        Cursor cursorCategories = db.rawQuery(SUBCATEGORY_SELECT_QUERY, null);
        try{
            if(cursorCategories.moveToFirst()){
                do {
                    Subcategory subcategory = new Subcategory();
                    subcategory.setSubcategoryId(cursorCategories.getInt(cursorCategories.getColumnIndex(KEY_SUBCATEGORY_subcategoryId)));
                    subcategory.setFkCategoryId(cursorCategories.getInt(cursorCategories.getColumnIndex(KEY_SUBCATEGORY_fkcategoryId)));
                    subcategory.setName(cursorCategories.getString(cursorCategories.getColumnIndex(KEY_SUBCATEGORY_name)));
                    if (cursorCategories.getInt(cursorCategories.getColumnIndex(KEY_SUBCATEGORY_active)) == 1) {
                        subcategory.setActive(true);
                    }
                    if (cursorCategories.getInt(cursorCategories.getColumnIndex(KEY_SUBCATEGORY_active)) == 0) {
                        subcategory.setActive(false);
                    }

                    listSubategories.add(subcategory);
                }while (cursorCategories.moveToNext());
            }
        }catch (Exception e){
            Log.d(TAG_LIST_CATEGORIES, "Error while trying add category to database");
        }finally {
            if (cursorCategories != null && !cursorCategories.isClosed()) {
                cursorCategories.close();
            }
        }
        return listSubategories;
    }


    @SuppressLint({"Range", "LongLogTag"})
    public List<Subcategory> listSubcategoriesByActive(){
        SQLiteDatabase db = getReadableDatabase();
        List<Subcategory> listSubategories = new ArrayList<>();
        String SUBCATEGORY_SELECT_QUERY =  "SELECT * FROM "+TABLE_SUBCATEGORIES+" where active = 1" ;
        Cursor cursorCategories = db.rawQuery(SUBCATEGORY_SELECT_QUERY, null);
        try{
            if(cursorCategories.moveToFirst()){
                do {
                    Subcategory subcategory = new Subcategory();
                    subcategory.setSubcategoryId(cursorCategories.getInt(cursorCategories.getColumnIndex(KEY_SUBCATEGORY_subcategoryId)));
                    subcategory.setFkCategoryId(cursorCategories.getInt(cursorCategories.getColumnIndex(KEY_SUBCATEGORY_fkcategoryId)));
                    subcategory.setName(cursorCategories.getString(cursorCategories.getColumnIndex(KEY_SUBCATEGORY_name)));
                    if (cursorCategories.getInt(cursorCategories.getColumnIndex(KEY_SUBCATEGORY_active)) == 1) {
                        subcategory.setActive(true);
                    }
                    if (cursorCategories.getInt(cursorCategories.getColumnIndex(KEY_SUBCATEGORY_active)) == 0) {
                        subcategory.setActive(false);
                    }

                    listSubategories.add(subcategory);
                }while (cursorCategories.moveToNext());
            }
        }catch (Exception e){
            Log.d(TAG_LIST_CATEGORIES, "Error while trying to add category to database");
        }finally {
            if (cursorCategories != null && !cursorCategories.isClosed()) {
                cursorCategories.close();
            }
        }
        return listSubategories;
    }




    // -----------------------  methods for products ------------------------------------
    public void addProduct(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues ValuesProduct = new ContentValues();
            ValuesProduct.put(KEY_PRODUCT_productId, product.getProductId());
            ValuesProduct.put(KEY_PRODUCT_fkBusinessId, product.getFkBusinessId());
            ValuesProduct.put(KEY_PRODUCT_fkCategoryId, product.getFkCategoryId());
            ValuesProduct.put(KEY_PRODUCT_fkSubcategoryId, product.getFkSubcategoryId());
            ValuesProduct.put(KEY_PRODUCT_fkBrandId, product.getFkBrandId());
            ValuesProduct.put(KEY_PRODUCT_fkSupplierId, product.getFkSupplierId());

            ValuesProduct.put(KEY_PRODUCT_barcode, product.getBarcode());
            ValuesProduct.put(KEY_PRODUCT_nameProduct, product.getNameProduct());
            ValuesProduct.put(KEY_PRODUCT_presentation, product.getPresentation());
            ValuesProduct.put(KEY_PRODUCT_volumeWeight, product.getVolumeWeight());
            ValuesProduct.put(KEY_PRODUCT_isDrink, product.getDrink());
            ValuesProduct.put(KEY_PRODUCT_quantityPerPackage, product.getQuantityPerPackage());

            ValuesProduct.put(KEY_PRODUCT_unitPurchasePriceXmayor, product.getUnitPurchasePriceXmayor());
            ValuesProduct.put(KEY_PRODUCT_unitSalePriceXmayor, product.getUnitSalePriceXmayor());
            ValuesProduct.put(KEY_PRODUCT_unitSalePriceXminor, product.getUnitSalePriceXminor());
            ValuesProduct.put(KEY_PRODUCT_image, product.getImage());
            ValuesProduct.put(KEY_PRODUCT_description, product.getDescription());
            ValuesProduct.put(KEY_PRODUCT_active, product.getActive());

            db.insertOrThrow(TABLE_PRODUCTS, null, ValuesProduct);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG_ADD_PRODUCT, "Error while trying to add product to database");
        } finally {
            db.endTransaction();
        }
    }

    public void updateProduct(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues ValuesProduct = new ContentValues();
            ValuesProduct.put(KEY_PRODUCT_productId, product.getProductId());
            ValuesProduct.put(KEY_PRODUCT_fkBusinessId, product.getFkBusinessId());
            ValuesProduct.put(KEY_PRODUCT_fkCategoryId, product.getFkCategoryId());
            ValuesProduct.put(KEY_PRODUCT_fkSubcategoryId, product.getFkSubcategoryId());
            ValuesProduct.put(KEY_PRODUCT_fkBrandId, product.getFkBrandId());
            ValuesProduct.put(KEY_PRODUCT_fkSupplierId, product.getFkSupplierId());

            ValuesProduct.put(KEY_PRODUCT_barcode, product.getBarcode());
            ValuesProduct.put(KEY_PRODUCT_nameProduct, product.getNameProduct());
            ValuesProduct.put(KEY_PRODUCT_presentation, product.getPresentation());
            ValuesProduct.put(KEY_PRODUCT_volumeWeight, product.getVolumeWeight());
            ValuesProduct.put(KEY_PRODUCT_isDrink, product.getDrink());

            ValuesProduct.put(KEY_PRODUCT_quantityPerPackage, product.getQuantityPerPackage());

            ValuesProduct.put(KEY_PRODUCT_unitPurchasePriceXmayor, product.getUnitPurchasePriceXmayor());
            ValuesProduct.put(KEY_PRODUCT_unitSalePriceXmayor, product.getUnitSalePriceXmayor());
            ValuesProduct.put(KEY_PRODUCT_unitSalePriceXminor, product.getUnitSalePriceXminor());
            ValuesProduct.put(KEY_PRODUCT_image, product.getImage());
            ValuesProduct.put(KEY_PRODUCT_description, product.getDescription());
            ValuesProduct.put(KEY_PRODUCT_active, product.getActive());

            db.update(TABLE_PRODUCTS, ValuesProduct, KEY_PRODUCT_productId + "= ?", new String[]{product.getProductId().toString()});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG_ERROR_UPDATE_PRODUCT, "Error while trying to add product to database");
        } finally {
            db.endTransaction();
        }
    }

    public  void addProducts(List<Product> listproducts){
        try{
            for (Product product : listproducts) {
                addProduct(product);
            }
        }catch (Exception e){
            Log.d(TAG_ADD_CATEGORIES, "Error while trying to add categories to database");
        }
    }

    @SuppressLint({"Range", "LongLogTag"})
    public List<Product> listProductsByCategoryId(int categoryId){
        SQLiteDatabase db = getReadableDatabase();
        List<Product> listProducts = new ArrayList<>();
        String PRODUCT_SELECT_QUERY =  "SELECT * FROM products WHERE fkSubcategoryId ="+categoryId;
        Cursor cursorProducts = db.rawQuery(PRODUCT_SELECT_QUERY, null);
        try{
            if(cursorProducts.moveToFirst()){
                do {
                    Product product = new Product();
                    product.setProductId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_productId)));
                    product.setFkBusinessId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_fkBusinessId)));
                    product.setFkCategoryId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_fkCategoryId)));
                    product.setFkSubcategoryId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_fkSubcategoryId)));
                    product.setFkBrandId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_fkBrandId)));
                    product.setFkSupplierId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_fkSupplierId)));

                    product.setBarcode(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_barcode)));
                    product.setNameProduct(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_nameProduct)));

                    product.setPresentation(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_presentation)));
                    product.setVolumeWeight(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_volumeWeight)));

                    if (cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_isDrink)) == 1) {
                        product.setDrink(true);
                    }
                    if (cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_isDrink)) == 0) {
                        product.setDrink(false);
                    }

                    product.setQuantityPerPackage(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_quantityPerPackage)));

                    product.setUnitPurchasePriceXmayor(cursorProducts.getFloat(cursorProducts.getColumnIndex(KEY_PRODUCT_unitPurchasePriceXmayor)));
                    product.setUnitSalePriceXmayor(cursorProducts.getFloat(cursorProducts.getColumnIndex(KEY_PRODUCT_unitSalePriceXmayor)));
                    product.setUnitSalePriceXminor(cursorProducts.getFloat(cursorProducts.getColumnIndex(KEY_PRODUCT_unitSalePriceXminor)));
                    product.setImage(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_image)));
                    product.setDescription(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_description)));

                    if (cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_active)) == 1) {
                        product.setActive(true);
                    }
                    if (cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_active)) == 0) {
                        product.setActive(false);
                    }

                    listProducts.add(product);
                }while (cursorProducts.moveToNext());
            }
        }catch (Exception e){
            Log.d(TAG_LIST_CATEGORIES, "Error while trying to add category to database");
        }finally {
            if (cursorProducts != null && !cursorProducts.isClosed()) {
                cursorProducts.close();
            }
        }
        return listProducts;
    }

    @SuppressLint({"Range", "LongLogTag"})
    public Product getProductByProductId(int productId){
        SQLiteDatabase db = getReadableDatabase();
        Product product = new Product();
        String PRODUCT_NAME_CONTAINING_SELECT_QUERY =  "select * from PRODUCTS where productId ="+productId;
        Cursor cursorProducts = db.rawQuery(PRODUCT_NAME_CONTAINING_SELECT_QUERY, null);
        try{
            if(cursorProducts.moveToFirst()){
                do {
                    product.setProductId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_productId)));
                    product.setFkBusinessId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_fkBusinessId)));
                    product.setFkCategoryId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_fkCategoryId)));
                    product.setFkSubcategoryId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_fkSubcategoryId)));
                    product.setFkBrandId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_fkBrandId)));
                    product.setFkSupplierId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_fkSupplierId)));

                    product.setBarcode(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_barcode)));
                    product.setNameProduct(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_nameProduct)));

                    product.setPresentation(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_presentation)));
                    product.setVolumeWeight(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_volumeWeight)));

                    if (cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_isDrink)) == 1) {
                        product.setDrink(true);
                    }
                    if (cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_isDrink)) == 0) {
                        product.setDrink(false);
                    }

                    product.setQuantityPerPackage(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_quantityPerPackage)));

                    product.setUnitPurchasePriceXmayor(cursorProducts.getFloat(cursorProducts.getColumnIndex(KEY_PRODUCT_unitPurchasePriceXmayor)));
                    product.setUnitSalePriceXmayor(cursorProducts.getFloat(cursorProducts.getColumnIndex(KEY_PRODUCT_unitSalePriceXmayor)));
                    product.setUnitSalePriceXminor(cursorProducts.getFloat(cursorProducts.getColumnIndex(KEY_PRODUCT_unitSalePriceXminor)));
                    product.setImage(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_image)));
                    product.setDescription(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_description)));

                    if (cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_active)) == 1) {
                        product.setActive(true);
                    }
                    if (cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_active)) == 0) {
                        product.setActive(false);
                    }
                }while (cursorProducts.moveToNext());
            }
        }catch (Exception e){
            Log.d(TAG_LIST_CATEGORIES, "Error while trying to add category to database");
        }finally {
            if (cursorProducts != null && !cursorProducts.isClosed()) {
                cursorProducts.close();
            }
        }
        return product;
    }

    @SuppressLint({"Range", "LongLogTag"})
    public List<Product>  listProductByFrequency(List<Integer> listFrquency){
        List<Product> listProducts = new ArrayList<>();
        for (Integer fqr: listFrquency){
            listProducts.add(getProductByProductId(fqr));
        }
        return listProducts;
    }

    @SuppressLint({"Range", "LongLogTag"})
    public List<Product> listProductsByName(String nameContaining){
        SQLiteDatabase db = getReadableDatabase();
        List<Product> listProducts = new ArrayList<>();
        String PRODUCT_NAME_CONTAINING_SELECT_QUERY =  "SELECT * FROM PRODUCTS where nameProduct Like \"%"+nameContaining+"%\";";
        Cursor cursorProducts = db.rawQuery(PRODUCT_NAME_CONTAINING_SELECT_QUERY, null);
        try{
            if(cursorProducts.moveToFirst()){
                do {
                    Product product = new Product();
                    product.setProductId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_productId)));
                    product.setFkBusinessId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_fkBusinessId)));
                    product.setFkCategoryId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_fkCategoryId)));
                    product.setFkSubcategoryId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_fkSubcategoryId)));
                    product.setFkBrandId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_fkBrandId)));
                    product.setFkSupplierId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_fkSupplierId)));

                    product.setBarcode(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_barcode)));
                    product.setNameProduct(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_nameProduct)));

                    product.setPresentation(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_presentation)));
                    product.setVolumeWeight(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_volumeWeight)));

                    if (cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_isDrink)) == 1) {
                        product.setDrink(true);
                    }
                    if (cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_isDrink)) == 0) {
                        product.setDrink(false);
                    }

                    product.setQuantityPerPackage(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_quantityPerPackage)));

                    product.setUnitPurchasePriceXmayor(cursorProducts.getFloat(cursorProducts.getColumnIndex(KEY_PRODUCT_unitPurchasePriceXmayor)));
                    product.setUnitSalePriceXmayor(cursorProducts.getFloat(cursorProducts.getColumnIndex(KEY_PRODUCT_unitSalePriceXmayor)));
                    product.setUnitSalePriceXminor(cursorProducts.getFloat(cursorProducts.getColumnIndex(KEY_PRODUCT_unitSalePriceXminor)));
                    product.setImage(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_image)));
                    product.setDescription(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_description)));

                    if (cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_active)) == 1) {
                        product.setActive(true);
                    }
                    if (cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_active)) == 0) {
                        product.setActive(false);
                    }

                    listProducts.add(product);
                }while (cursorProducts.moveToNext());
            }
        }catch (Exception e){
            Log.d(TAG_LIST_CATEGORIES, "Error while trying to add category to database");
        }finally {
            if (cursorProducts != null && !cursorProducts.isClosed()) {
                cursorProducts.close();
            }
        }
        return listProducts;
    }

    @SuppressLint({"Range", "LongLogTag"})
    public List<Product> listProductsByBarcode(String barcode){
        SQLiteDatabase db = getReadableDatabase();
        List<Product> listProducts = new ArrayList<>();
        //String PRODUCT_NAME_CONTAINING_SELECT_QUERY =  "SELECT * FROM PRODUCTS where nameProduct Like \"%"+nameContaining+"%\";";
        String PRODUCT_NAME_CONTAINING_SELECT_QUERY =  "SELECT * FROM PRODUCTS where barcode = \""+barcode+"\"";
        Cursor cursorProducts = db.rawQuery(PRODUCT_NAME_CONTAINING_SELECT_QUERY, null);
        try{
            if(cursorProducts.moveToFirst()){
                do {
                    Product product = new Product();
                    product.setProductId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_productId)));
                    product.setFkBusinessId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_fkBusinessId)));
                    product.setFkCategoryId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_fkCategoryId)));
                    product.setFkSubcategoryId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_fkSubcategoryId)));
                    product.setFkBrandId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_fkBrandId)));
                    product.setFkSupplierId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_fkSupplierId)));

                    product.setBarcode(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_barcode)));
                    product.setNameProduct(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_nameProduct)));

                    product.setPresentation(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_presentation)));
                    product.setVolumeWeight(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_volumeWeight)));

                    if (cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_isDrink)) == 1) {
                        product.setDrink(true);
                    }
                    if (cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_isDrink)) == 0) {
                        product.setDrink(false);
                    }
                    product.setQuantityPerPackage(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_quantityPerPackage)));

                    product.setUnitPurchasePriceXmayor(cursorProducts.getFloat(cursorProducts.getColumnIndex(KEY_PRODUCT_unitPurchasePriceXmayor)));
                    product.setUnitSalePriceXmayor(cursorProducts.getFloat(cursorProducts.getColumnIndex(KEY_PRODUCT_unitSalePriceXmayor)));
                    product.setUnitSalePriceXminor(cursorProducts.getFloat(cursorProducts.getColumnIndex(KEY_PRODUCT_unitSalePriceXminor)));
                    product.setImage(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_image)));
                    product.setDescription(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_description)));

                    if (cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_active)) == 1) {
                        product.setActive(true);
                    }
                    if (cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_active)) == 0) {
                        product.setActive(false);
                    }

                    listProducts.add(product);
                }while (cursorProducts.moveToNext());
            }
        }catch (Exception e){
            Log.d(TAG_LIST_CATEGORIES, "Error while trying to add category to database");
        }finally {
            if (cursorProducts != null && !cursorProducts.isClosed()) {
                cursorProducts.close();
            }
        }
        return listProducts;
    }

    @SuppressLint({"Range", "LongLogTag"})
    public List<Product> listProductsByFrequency(String frequency){
        SQLiteDatabase db = getReadableDatabase();
        List<Product> listProducts = new ArrayList<>();
        String PRODUCT_NAME_CONTAINING_SELECT_QUERY =  "select * from PRODUCTS where productId in "+frequency;
        Cursor cursorProducts = db.rawQuery(PRODUCT_NAME_CONTAINING_SELECT_QUERY, null);
        try{
            if(cursorProducts.moveToFirst()){
                do {
                    Product product = new Product();
                    product.setProductId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_productId)));
                    product.setFkBusinessId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_fkBusinessId)));
                    product.setFkCategoryId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_fkCategoryId)));
                    product.setFkSubcategoryId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_fkSubcategoryId)));
                    product.setFkBrandId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_fkBrandId)));
                    product.setFkSupplierId(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_fkSupplierId)));

                    product.setBarcode(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_barcode)));
                    product.setNameProduct(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_nameProduct)));

                    product.setPresentation(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_presentation)));
                    product.setVolumeWeight(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_volumeWeight)));

                    if (cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_isDrink)) == 1) {
                        product.setDrink(true);
                    }
                    if (cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_isDrink)) == 0) {
                        product.setDrink(false);
                    }

                    product.setQuantityPerPackage(cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_quantityPerPackage)));

                    product.setUnitPurchasePriceXmayor(cursorProducts.getFloat(cursorProducts.getColumnIndex(KEY_PRODUCT_unitPurchasePriceXmayor)));
                    product.setUnitSalePriceXmayor(cursorProducts.getFloat(cursorProducts.getColumnIndex(KEY_PRODUCT_unitSalePriceXmayor)));
                    product.setUnitSalePriceXminor(cursorProducts.getFloat(cursorProducts.getColumnIndex(KEY_PRODUCT_unitSalePriceXminor)));
                    product.setImage(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_image)));
                    product.setDescription(cursorProducts.getString(cursorProducts.getColumnIndex(KEY_PRODUCT_description)));

                    if (cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_active)) == 1) {
                        product.setActive(true);
                    }
                    if (cursorProducts.getInt(cursorProducts.getColumnIndex(KEY_PRODUCT_active)) == 0) {
                        product.setActive(false);
                    }

                    listProducts.add(product);
                }while (cursorProducts.moveToNext());
            }
        }catch (Exception e){
            Log.d(TAG_LIST_CATEGORIES, "Error while trying to add category to database");
        }finally {
            if (cursorProducts != null && !cursorProducts.isClosed()) {
                cursorProducts.close();
            }
        }
        return listProducts;
    }






    // -----------------------  methods for inventory ------------------------------------
    public void addInventory(Inventory inventory){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues ValuesProduct = new ContentValues();
            ValuesProduct.put(KEY_INVENTORY_inventoryId, inventory.getInventoryId());
            ValuesProduct.put(KEY_INVENTORY_fkProductId, inventory.getFkProductId());
            ValuesProduct.put(KEY_INVENTORY_fkBusinessId, inventory.getFkBusinessId());
            ValuesProduct.put(KEY_INVENTORY_nameProduc, inventory.getNameProduc());
            ValuesProduct.put(KEY_INVENTORY_purchasePriceUnit, inventory.getPurchasePriceUnit());
            ValuesProduct.put(KEY_INVENTORY_quantityUnits, inventory.getQuantityUnits());
            ValuesProduct.put(KEY_INVENTORY_quantityRemainning, inventory.getQuantityRemainning());
            ValuesProduct.put(KEY_INVENTORY_active, inventory.getActive());

            db.insertOrThrow(TABLE_INVENTORY, null, ValuesProduct);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG_ADD_PRODUCT, "Error while trying to add product to database");
        } finally {
            db.endTransaction();
        }
    }




    // -----------------------  methods for sales ------------------------------------
    public void addSale(Sale sale) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues ValuesSale= new ContentValues();
            ValuesSale.put(KEY_SALES_saleId, sale.getSaleId());
            ValuesSale.put(KEY_SALES_fkBusinessId, sale.getFkBusinessId());
            ValuesSale.put(KEY_SALES_fkEmployeeId, sale.getFkEmployeeId());
            ValuesSale.put(KEY_SALES_fkClientId, sale.getFkClientId());

            ValuesSale.put(KEY_SALES_saleDate, sale.getSaleDate());
            ValuesSale.put(KEY_SALES_voucherType, sale.getVoucherType());
            ValuesSale.put(KEY_SALES_voucherNumber, sale.getVoucherNumber());
            ValuesSale.put(KEY_SALES_payType, sale.getPayType());

            ValuesSale.put(KEY_SALES_total, sale.getTotal());
            ValuesSale.put(KEY_SALES_igv, sale.getIgv());
            ValuesSale.put(KEY_SALES_descripcion, sale.getDescripcion());
            ValuesSale.put(KEY_SALES_estado, sale.isEstado());

            db.insertOrThrow(TABLE_SALES, null, ValuesSale);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG_ADD_SALE, "Error while trying to add category to database");
        } finally {
            db.endTransaction();
        }
    }

    public  void addSales(List<Sale> listSales){
        try{
            for (Sale sale : listSales) {
                addSale(sale);
            }
        }catch (Exception e){
            Log.d(TAG_ADD_SALES, "Error while trying to add categories to database");
        }
    }



    // -----------------------  methods for Consumptions ------------------------------------
    public void addConsumption(Consumption consumption) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues valuesConsumption= new ContentValues();
            valuesConsumption.put(KEY_CONSUMPTION_consumptionId, consumption.getConsumptionId());
            valuesConsumption.put(KEY_CONSUMPTION_fkBusinessId, consumption.getFkBusinessId());
            valuesConsumption.put(KEY_CONSUMPTION_fkSaleId, consumption.getFkSaleId());
            valuesConsumption.put(KEY_CONSUMPTION_fkCategoryId, consumption.getFkCategoryId());
            valuesConsumption.put(KEY_CONSUMPTION_fkProductId, consumption.getFkProductId());


            valuesConsumption.put(KEY_CONSUMPTION_nameProduct, consumption.getNameProduct());
            valuesConsumption.put(KEY_CONSUMPTION_quantityPerPackage, consumption.getQuantityPerPackage());

            valuesConsumption.put(KEY_CONSUMPTION_pricePurchaseUnitXmayor, consumption.getPricePurchaseUnitXmayor());
            valuesConsumption.put(KEY_CONSUMPTION_priceSaleUnitXmayor, consumption.getPriceSaleUnitXmayor());
            valuesConsumption.put(KEY_CONSUMPTION_priceSaleUnitXminor, consumption.getPriceSaleUnitXminor());

            valuesConsumption.put(KEY_CONSUMPTION_typeSale, consumption.getTypeSale());
            valuesConsumption.put(KEY_CONSUMPTION_salePrice, consumption.getSalePrice());
            valuesConsumption.put(KEY_CONSUMPTION_quantity, consumption.getQuantity());
            valuesConsumption.put(KEY_CONSUMPTION_totalSale, consumption.getTotalSale());

            valuesConsumption.put(KEY_CONSUMPTION_icedDrink, consumption.isIcedDrink());
            valuesConsumption.put(KEY_CONSUMPTION_quantityIIDD, consumption.getQuantityIIDD());
            valuesConsumption.put(KEY_CONSUMPTION_priceIIDD, consumption.getPriceIIDD());
            valuesConsumption.put(KEY_CONSUMPTION_totalIIDD, consumption.getTotalIIDD());


            valuesConsumption.put(KEY_CONSUMPTION_totalConsumption, consumption.getTotalConsumption());
            valuesConsumption.put(KEY_CONSUMPTION_consumptionDate, consumption.getConsumptionDate().toString());
            valuesConsumption.put(KEY_CONSUMPTION_active, consumption.getActive());

            db.insertOrThrow(TABLE_CONSUMPTION, null, valuesConsumption);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG_ADD_CONSUMPTION, "Error while trying to add category to database");
        } finally {
            db.endTransaction();
        }
    }

    @SuppressLint("LongLogTag")
    public  void addConsumptions(List<Consumption> listConsumptions){
        try{
            for (Consumption consumption : listConsumptions) {
                addConsumption(consumption);
            }
        }catch (Exception e){
            Log.d(TAG_ADD_LIST_CONSUMPTIOS, "Error while trying to add categories to database");
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    @SuppressLint({"Range", "LongLogTag"})
    public List<Consumption>  listConsumptios(){
        SQLiteDatabase db = getReadableDatabase();
        List<Consumption> listConsumption = new ArrayList<>();

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.parse("00:00:00.000",DateTimeFormatter.ISO_TIME);
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        String CONSUMPTIOS_SELECT_QUERY = "SELECT * FROM "+TABLE_CONSUMPTION +" WHERE datetime(consumptionDate) > datetime(\""+dateTime+"\")";

        Cursor cursorConsumptions = db.rawQuery(CONSUMPTIOS_SELECT_QUERY, null);
        try{
            if (cursorConsumptions.moveToFirst()){
                do {
                    Consumption consumption = new Consumption();
                    consumption.setConsumptionId(cursorConsumptions.getInt(cursorConsumptions.getColumnIndex(KEY_CONSUMPTION_consumptionId)));
                    consumption.setFkBusinessId(cursorConsumptions.getInt(cursorConsumptions.getColumnIndex(KEY_CONSUMPTION_fkBusinessId)));
                    consumption.setFkSaleId(cursorConsumptions.getInt(cursorConsumptions.getColumnIndex(KEY_CONSUMPTION_fkSaleId)));
                    consumption.setFkCategoryId(cursorConsumptions.getInt(cursorConsumptions.getColumnIndex(KEY_CONSUMPTION_fkCategoryId)));
                    consumption.setFkProductId(cursorConsumptions.getInt(cursorConsumptions.getColumnIndex(KEY_CONSUMPTION_fkProductId)));

                    consumption.setNameProduct(cursorConsumptions.getString(cursorConsumptions.getColumnIndex(KEY_CONSUMPTION_nameProduct)));
                    consumption.setQuantityPerPackage(cursorConsumptions.getInt(cursorConsumptions.getColumnIndex(KEY_CONSUMPTION_quantityPerPackage)));

                    consumption.setPricePurchaseUnitXmayor(cursorConsumptions.getFloat(cursorConsumptions.getColumnIndex(KEY_CONSUMPTION_pricePurchaseUnitXmayor)));
                    consumption.setPriceSaleUnitXmayor(cursorConsumptions.getFloat(cursorConsumptions.getColumnIndex(KEY_CONSUMPTION_priceSaleUnitXmayor)));
                    consumption.setPriceSaleUnitXminor(cursorConsumptions.getFloat(cursorConsumptions.getColumnIndex(KEY_CONSUMPTION_priceSaleUnitXminor)));

                    consumption.setTypeSale(cursorConsumptions.getString(cursorConsumptions.getColumnIndex(KEY_CONSUMPTION_typeSale)));
                    consumption.setSalePrice(cursorConsumptions.getFloat(cursorConsumptions.getColumnIndex(KEY_CONSUMPTION_salePrice)));
                    consumption.setQuantity(cursorConsumptions.getInt(cursorConsumptions.getColumnIndex(KEY_CONSUMPTION_quantity)));
                    consumption.setTotalSale(cursorConsumptions.getFloat(cursorConsumptions.getColumnIndex(KEY_CONSUMPTION_totalSale)));

                    if (cursorConsumptions.getInt(cursorConsumptions.getColumnIndex(KEY_CONSUMPTION_icedDrink)) == 0){
                        consumption.setIcedDrink(false);
                    }
                    if (cursorConsumptions.getInt(cursorConsumptions.getColumnIndex(KEY_CONSUMPTION_icedDrink)) == 1){
                        consumption.setIcedDrink(true);
                    }
                    consumption.setQuantityIIDD(cursorConsumptions.getInt(cursorConsumptions.getColumnIndex(KEY_CONSUMPTION_quantityIIDD)));
                    consumption.setPriceIIDD(cursorConsumptions.getFloat(cursorConsumptions.getColumnIndex(KEY_CONSUMPTION_priceIIDD)));
                    consumption.setTotalIIDD(cursorConsumptions.getFloat(cursorConsumptions.getColumnIndex(KEY_CONSUMPTION_totalIIDD)));



                    consumption.setTotalConsumption(cursorConsumptions.getFloat(cursorConsumptions.getColumnIndex(KEY_CONSUMPTION_totalConsumption)));
                    //String txt = cursorConsumptions.getString(cursorConsumptions.getColumnIndex(KEY_CONSUMPTION_consumptionDate));
                    //consumption.setConsumptionDate(LocalDateTime.parse(txt, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                    //consumption.setConsumptionDate();
                    consumption.setConsumptionDate(cursorConsumptions.getString(cursorConsumptions.getColumnIndex(KEY_CONSUMPTION_consumptionDate)));
                    if (cursorConsumptions.getInt(cursorConsumptions.getColumnIndex(KEY_CONSUMPTION_active)) == 1) {
                        consumption.setActive(true);
                    }
                    if (cursorConsumptions.getInt(cursorConsumptions.getColumnIndex(KEY_CONSUMPTION_active)) == 0) {
                        consumption.setActive(false);
                    }

                    listConsumption.add(consumption);
                }while (cursorConsumptions.moveToNext());
            }
        }catch (Exception e){
            Log.d(TAG_LIST_COMSUMPTIOS, "Error while trying to TAG_LIST_COMSUMPTIOS to database");
        }finally {
            if (cursorConsumptions != null && !cursorConsumptions.isClosed()) {
                cursorConsumptions.close();
            }
        }
        return listConsumption;
    }


    @TargetApi(Build.VERSION_CODES.O)
    @SuppressLint("Range")
    public ResumeGeneralVentas resumenGeneralUND(String typeSale){
        SQLiteDatabase db = getReadableDatabase();
        ResumeGeneralVentas resumeGeneralVentas = new ResumeGeneralVentas();
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.parse("00:00:00.000",DateTimeFormatter.ISO_TIME);
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        String RESUME_GENERAL_SELECT_QUERY = "select round(sum(unitPurchasePrice*quantity), 2) as CAPITAL, round(sum(total-unitPurchasePrice*quantity+priceExtraIIDD),2) as GANANCIA,\n" +
                "round(sum(priceExtraIIDD),2) AS EXTRAS, round(sum(total),2) AS TOTAL FROM CONSUMPTIONS where typeProductSale = \""+typeSale+"\" and "+ "datetime(consumptionDate) > datetime(\""+dateTime+"\")" ;
        Cursor cursorResumeGeneral = db.rawQuery(RESUME_GENERAL_SELECT_QUERY, null);

        try{
            if (cursorResumeGeneral.moveToFirst()){
                resumeGeneralVentas.setCapital(cursorResumeGeneral.getFloat(cursorResumeGeneral.getColumnIndex("CAPITAL")));
                resumeGeneralVentas.setGanancia(cursorResumeGeneral.getFloat(cursorResumeGeneral.getColumnIndex("GANANCIA")));
                resumeGeneralVentas.setAdditionalCost(cursorResumeGeneral.getFloat(cursorResumeGeneral.getColumnIndex("EXTRAS")));
                resumeGeneralVentas.setTotal(cursorResumeGeneral.getFloat(cursorResumeGeneral.getColumnIndex("TOTAL")));
            }
        }catch (Exception e){
            Log.d(TAG_RESUME_GENERAL_COMSUMPTIOS, "Error while trying to TAG_LIST_COMSUMPTIOS to database");
        }finally {
            if (cursorResumeGeneral != null && !cursorResumeGeneral.isClosed()) {
                cursorResumeGeneral.close();
            }
        }
        return resumeGeneralVentas;
    }

    @TargetApi(Build.VERSION_CODES.O)
    @SuppressLint("Range")
    public ResumeGeneralVentas resumenGeneralPQT(String typeSale){
        SQLiteDatabase db = getReadableDatabase();
        ResumeGeneralVentas resumeGeneralVentas = new ResumeGeneralVentas();
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.parse("00:00:00.000",DateTimeFormatter.ISO_TIME);
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        String RESUME_GENERAL_SELECT_QUERY = "select round(sum(packagePurchasePrice*quantity), 2) as CAPITAL, round(sum(total-packagePurchasePrice*quantity+priceExtraIIDD),2) as GANANCIA,\n" +
                "round(sum(priceExtraIIDD),2) AS EXTRAS, round(sum(total),2) AS TOTAL FROM CONSUMPTIONS where typeProductSale = \""+typeSale+"\" and "+ "datetime(consumptionDate) > datetime(\""+dateTime+"\")" ;
        Cursor cursorResumeGeneral = db.rawQuery(RESUME_GENERAL_SELECT_QUERY, null);

        try{
            if (cursorResumeGeneral.moveToFirst()){
                resumeGeneralVentas.setCapital(cursorResumeGeneral.getFloat(cursorResumeGeneral.getColumnIndex("CAPITAL")));
                resumeGeneralVentas.setGanancia(cursorResumeGeneral.getFloat(cursorResumeGeneral.getColumnIndex("GANANCIA")));
                resumeGeneralVentas.setAdditionalCost(cursorResumeGeneral.getFloat(cursorResumeGeneral.getColumnIndex("EXTRAS")));
                resumeGeneralVentas.setTotal(cursorResumeGeneral.getFloat(cursorResumeGeneral.getColumnIndex("TOTAL")));
            }
        }catch (Exception e){
            Log.d(TAG_RESUME_GENERAL_COMSUMPTIOS, "Error while trying to TAG_LIST_COMSUMPTIOS to database");
        }finally {
            if (cursorResumeGeneral != null && !cursorResumeGeneral.isClosed()) {
                cursorResumeGeneral.close();
            }
        }
        return resumeGeneralVentas;
    }

    @TargetApi(Build.VERSION_CODES.O)
    @SuppressLint("Range")
    public List<QuantityPerCategory> categoryQuantityGeneral(){
        SQLiteDatabase db = getReadableDatabase();
        List<QuantityPerCategory> listQuantityCategories = new ArrayList<>() ;
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.parse("00:00:00.000",DateTimeFormatter.ISO_TIME);
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        String CATEGORY_QUANTITY_GENERAL_SELECT_QUERY = "select (select fkCategoryId from CATEGORIES where categoryId = fkCategoryId) as category, count(*) as contar from CONSUMPTIONS"+
                " WHERE datetime(consumptionDate) > datetime(\""+dateTime +"\")  group by fkCategoryId order by contar desc;";
        Cursor cursorQuantityCategory = db.rawQuery(CATEGORY_QUANTITY_GENERAL_SELECT_QUERY, null);

        try{
            if (cursorQuantityCategory.moveToFirst()){

                do{
                    QuantityPerCategory quantityPerCategory = new QuantityPerCategory();
                    quantityPerCategory.setCategory(cursorQuantityCategory.getInt(cursorQuantityCategory.getColumnIndex("category")));
                    quantityPerCategory.setQuantity(cursorQuantityCategory.getInt(cursorQuantityCategory.getColumnIndex("contar")));
                    listQuantityCategories.add(quantityPerCategory);
                }while (cursorQuantityCategory.moveToNext());
            }
        }catch (Exception e){
            //Log.d(TAG_RESUME_GENERAL_COMSUMPTIOS, "Error while trying to TAG_LIST_COMSUMPTIOS to database");
        }finally {
            if (cursorQuantityCategory != null && !cursorQuantityCategory.isClosed()) {
                cursorQuantityCategory.close();
            }
        }
        return listQuantityCategories;
    }

    @TargetApi(Build.VERSION_CODES.O)
    @SuppressLint({"Range", "LongLogTag"})
    public List<Integer> listProductsConsumptionsFrequent(){
        SQLiteDatabase db = getReadableDatabase();
        List<Integer> listProductId = new ArrayList<>();
        String CONSUMPTIOS_SELECT_QUERY = "select fkProductId from CONSUMPTIONS GROUP by fkProductId ORDER by fkCategoryId and fkProductId ASC LIMIT 30;";
        Cursor cursorListProductId = db.rawQuery(CONSUMPTIOS_SELECT_QUERY, null);
        try{
            if (cursorListProductId.moveToFirst()){
                do {
                    Integer productId = 0;
                    productId = cursorListProductId.getInt(cursorListProductId.getColumnIndex("fkProductId"));
                    listProductId.add(productId);
                }while (cursorListProductId.moveToNext());
            }
        }catch (Exception e){
            Log.d(TAG_LIST_COMSUMPTIOS, "Error while trying to TAG_LIST_COMSUMPTIOS to database");
        }finally {
            if (cursorListProductId != null && !cursorListProductId.isClosed()) {
                cursorListProductId.close();
            }
        }
        return listProductId;
    }


    // ----------------- methods for sqlite_sequence -----------
    @SuppressLint("Range")
    public String getSquenceSales(){
        SQLiteDatabase db = getReadableDatabase();
        String seq = new String();
        String SEQUENCE_NAME_CONTAINING_SELECT_QUERY =  "select seq from sqlite_sequence where name = \"SALES\"";
        Cursor cursorSquence = db.rawQuery(SEQUENCE_NAME_CONTAINING_SELECT_QUERY, null);
        try{
            if(cursorSquence.moveToFirst()){
                do {
                    seq = cursorSquence.getString(cursorSquence.getColumnIndex("seq"));

                }while (cursorSquence.moveToNext());
            }
        }catch (Exception e){
            //Log.d(TAG_LIST_CATEGORIES, "Error while trying to add category to database");
        }finally {
            if (cursorSquence != null && !cursorSquence.isClosed()) {
                cursorSquence.close();
            }
        }
        return seq;
    }

}
