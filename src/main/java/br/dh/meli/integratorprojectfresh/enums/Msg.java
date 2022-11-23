package br.dh.meli.integratorprojectfresh.enums;

/**
 * The enum Msg lists all the messages returned to the user.
 */
public enum Msg {
    /**
     * __ msg __ msg.
     */
    __MSG__;

    /**
     * The constant ANNOUNCEMENT_ID_NOT_NULL.
     */
    public static final String ANNOUNCEMENT_ID_NOT_NULL = "Announcement ID cannot be null.";
    /**
     * The constant ANNOUNCEMENT_ID_NOT_VALID.
     */
    public static final String ANNOUNCEMENT_ID_NOT_VALID = "Announcement ID is not valid.";
    /**
     * The constant ANNOUNCEMENT_ID_REQUIRED.
     */
    public static final String ANNOUNCEMENT_ID_REQUIRED = "Announcement id is required.";
    /**
     * The constant ANNOUNCEMENT_IS_EMPTY.
     */
    public static final String ANNOUNCEMENT_IS_EMPTY = "Announcement is empty.";
    /**
     * The constant ANNOUNCEMENT_NOT_FOUND.
     */
    public static final String ANNOUNCEMENT_NOT_FOUND = "Announcement not found.";
    /**
     * The constant BATCH_NOT_FOUND.
     */
    public static final String BATCH_NOT_FOUND = "Batch not found.";
    /**
     * The constant BATCH_STOCK_INSUFFICIENT.
     */
    public static final String BATCH_STOCK_INSUFFICIENT = "Batch stock is insufficient.";
    /**
     * The constant BATCHSTOCK_NOT_EMPTY.
     */
    public static final String BATCHSTOCK_NOT_EMPTY = "Batch stock is empty.";
    /**
     * The constant BUYER_ID_NOT_FOUND.
     */
    public static final String BUYER_ID_NOT_FOUND = "Buyer ID was not found.";
    /**
     * The constant BUYER_ID_NOT_NULL.
     */
    public static final String BUYER_ID_NOT_NULL = "Buyer ID cannot be null.";
    /**
     * The constant BUYER_ID_NOT_VALID.
     */
    public static final String BUYER_ID_NOT_VALID = "Buyer ID is not valid.";
    /**
     * The constant DATE_FUTURE.
     */
    public static final String DATE_FUTURE = "Date is in the future.";
    /**
     * The constant DATE_PAST_OR_PRESENT.
     */
    public static final String DATE_PAST_OR_PRESENT = "One or more fields are not past or present.";
    /**
     * The constant DATE_REQUIRED.
     */
    public static final String DATE_REQUIRED = "Date is required.";

    /**
     * The constant EMAIL_REQUIRED.
     */
    public static final String EMAIL_REQUIRED = "Email is required.";

    /**
     * The constant EMAIL_INCORRET.
     */
    public static final String EMAIL_INCORRET = "The email provided is in the wrong format.";
    /**
     * The constant FIELD_MIN_VALUE.
     */
    public static final String FIELD_MIN_VALUE = "One or more fields are less than the minimum value.";
    /**
     * The constant FIELD_NOT_FOUND.
     */
    public static final String FIELD_NOT_FOUND = "One or more fields are invalid.";
    /**
     * The constant FILTER_NOT_FOUND.
     */
    public static final String FILTER_NOT_FOUND = "Filter not found.";
    /**
     * The constant INBOUND_ORDER_NOT_FOUND.
     */
    public static final String INBOUND_ORDER_NOT_FOUND = "Inbound order not found.";
    /**
     * The constant INSERT_BATCH_SECTION_INCORRET.
     */
    public static final String INSERT_BATCH_SECTION_INCORRET = "Insert batch in wrong section";

    /**
     * The constant LETTER_NOT_VALID.
     */
    public static final String LETTER_NOT_VALID = "The letter must to be L, V or Q";
    /**
     * The constant LIMIT_CAPACITY_SECTION.
     */
    public static final String LIMIT_CAPACITY_SECTION = "Total sector capacity limit exceeded.";
    /**
     * The constant MANAGER_NOT_VALID.
     */
    public static final String MANAGER_NOT_VALID = "Warehouse does not have a valid manager.";
    /**
     * The constant PRICE_REQUIRED.
     */
    public static final String PRICE_REQUIRED = "Price is required.";
    /**
     * The constant PRODUCT_PRICE_NOT_NULL.
     */
    public static final String PRODUCT_PRICE_NOT_NULL = "Product Price cannot be null.";
    /**
     * The constant PRODUCT_PRICE_NOT_VALID.
     */
    public static final String PRODUCT_PRICE_NOT_VALID = "Product Price is not valid.";
    /**
     * The constant PRODUCT_QUANTITY_NOT_NULL.
     */
    public static final String PRODUCT_QUANTITY_NOT_NULL = "Product Quantity cannot be null.";
    /**
     * The constant PRODUCT_QUANTITY_NOT_VALID.
     */
    public static final String PRODUCT_QUANTITY_NOT_VALID = "Product Quantity is not valid.";
    /**
     * The constant PURCHASE_ORDER_ALREADY_APPROVED.
     */
    public static final String PURCHASE_ORDER_ALREADY_APPROVED = "Purchase Order already approved.";
    /**
     * The constant PURCHASE_ORDER_DATE_NOT_NULL.
     */
    public static final String PURCHASE_ORDER_DATE_NOT_NULL = "Purchase Order Date cannot be null.";
    /**
     * The constant PURCHASE_ORDER_DATE_NOT_VALID.
     */
    public static final String PURCHASE_ORDER_DATE_NOT_VALID = "Purchase Order Date is not valid.";
    /**
     * The constant PURCHASE_ORDER_ID_NOT_NULL.
     */
    public static final String PURCHASE_ORDER_ID_NOT_NULL = "Purchase Order ID cannot be null.";
    /**
     * The constant PURCHASE_ORDER_ID_NOT_VALID.
     */
    public static final String PURCHASE_ORDER_ID_NOT_VALID = "Purchase Order ID is not valid.";
    /**
     * The constant PURCHASE_ORDER_ITEMS_NOT_EMPTY.
     */
    public static final String PURCHASE_ORDER_ITEMS_NOT_EMPTY = "Purchase Order items list cannot be empty.";
    /**
     * The constant PURCHASE_ORDER_ITEMS_NOT_FOUND.
     */
    public static final String PURCHASE_ORDER_ITEMS_NOT_FOUND = "Purchase Order items not found.";
    /**
     * The constant PURCHASE_ORDER_STATUS_NOT_NULL.
     */
    public static final String PURCHASE_ORDER_STATUS_NOT_NULL = "Purchase Order status cannot be null.";
    /**
     * The constant PURCHASE_ORDER_STATUS_NOT_VALID.
     */
    public static final String PURCHASE_ORDER_STATUS_NOT_VALID = "Purchase Order status is not valid.";
    /**
     * The constant QUANTITY_MIN_VALUE.
     */
    public static final String QUANTITY_MIN_VALUE = "Quantity is less than the minimum value.";
    /**
     * The constant QUANTITY_REQUIRED.
     */
    public static final String QUANTITY_REQUIRED = "Quantity is required.";
    /**
     * The constant SECTION_CODE_NOT_EMPTY.
     */
    public static final String SECTION_CODE_NOT_EMPTY = "Section code is empty.";

    /**
     * The constant ROLE_REQUIRED.
     */
    public static final String ROLE_REQUIRED = "Role is required.";

    /**
     * The constant ROLE_IS_NOT_EXIST.
     */
    public static final String ROLE_IS_NOT_EXIST = "Role is not exist.";
    /**
     * The constant SECTION_NOT_VALID.
     */
    public static final String SECTION_NOT_VALID = "Section is not valid.";

    /**
     * The constant STATUS_NOT_VALID.
     */
    public static final String  STATUS_NOT_VALID = "Status is not valid.";
    /**
     * The constant SECTION_CODE_POSITIVE.
     */
    public static final String SECTION_CODE_POSITIVE = "Section code is not positive.";
    /**
     * The constant SECTION_CODE_REQUIRED.
     */
    public static final String SECTION_CODE_REQUIRED = "Section code is required.";
    /**
     * The constant SECTION_NOT_FOUND.
     */
    public static final String SECTION_NOT_FOUND = "Section not found.";
    /**
     * The constant TEMPERATURE_REQUIRED.
     */
    public static final String TEMPERATURE_REQUIRED = "Temperature is required.";
    /**
     * The constant TIME_REQUIRED.
     */
    public static final String TIME_REQUIRED = "Time is required.";
    /**
     * The constant TIME_PAST_OR_PRESENT.
     */
    public static final String TIME_PAST_OR_PRESENT = "Time is not past or present.";
    /**
     * The constant USER_NAME_REQUIRED.
     */
    public static final String USER_NAME_REQUIRED = "User name is required.";

    /**
     * The constant USER_NOT_FOUND.
     */
    public static final String USER_NOT_FOUND = "User not found.";

    /**
     * The constant USER_ID_NOT_FOUND.
     */
    public static final String  USER_ID_NOT_FOUND = "User id not found.";

    /**
     * The constant USER_NAME_OR_EMAIL_ALREADY_REGISTERED.
     */
    public static final String USER_NAME_OR_EMAIL_ALREADY_REGISTERED = "USERNAME or EMAIL already registered";
    /**
     * The constant VOLUME_MIN_VALUE.
     */
    public static final String VOLUME_MIN_VALUE = "Volume is less than the minimum value.";
    /**
     * The constant VOLUME_REQUIRED.
     */
    public static final String VOLUME_REQUIRED = "Volume is required.";
    /**
     * The constant WAREHOUSE_CODE_REQUIRED.
     */
    public static final String WAREHOUSE_CODE_REQUIRED = "Warehouse code is required.";
    /**
     * The constant WAREHOUSE_NOT_FOUND.
     */
    public static final String WAREHOUSE_NOT_FOUND =  "Warehouse not found.";
    /**
     * The constant SECTION_NOT_BELONG_WAREHOUSE.
     */
    public static final String SECTION_NOT_BELONG_WAREHOUSE =  "Section doesn't belong warehouse.";
    /**
     * The constant BATCH_EXPIRED.
     */
    public static final String BATCH_EXPIRED =  "Batch contains product expired or shelf life of less than 3 weeks.";

    /**
     * The constant PASSWORD_REQUIRED.
     */
    public static final String PASSWORD_REQUIRED = "Password is required.";
    /**
     * The constant PRODUCT_BATCH_INCORRECT.
     */
    public static final String PRODUCT_BATCH_INCORRECT =  "Product is in an incorrect batch.";
    /**
     * The constant CATEGORY_NOT_FOUND.
     */
    public static final String CATEGORY_NOT_FOUND = "Category must be FS, RF or FF.";
}
