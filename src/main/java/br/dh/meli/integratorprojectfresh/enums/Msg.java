package br.dh.meli.integratorprojectfresh.enums;

/**
 * The enum Msg lists all the messages returned to the user.
 */
public enum Msg {
  __MSG__;

  public static final String ANNOUNCEMENT_ID_NOT_NULL = "Announcement ID cannot be null.";
  public static final String ANNOUNCEMENT_ID_NOT_VALID = "Announcement ID is not valid.";
  public static final String ANNOUNCEMENT_ID_REQUIRED = "Announcement id is required.";
  public static final String ANNOUNCEMENT_IS_EMPTY = "Announcement is empty.";
  public static final String ANNOUNCEMENT_NOT_FOUND = "Announcement not found.";
  public static final String BATCH_ID_NOT_NULL = "Batch ID cannot be null.";
  public static final String BATCH_ID_NOT_VALID = "Batch ID is not valid.";
  public static final String BATCH_EXPIRED = "Batch contains expired product or shelf life of less than 3 weeks.";
  public static final String BATCH_NOT_EXPIRED = "Batch is not expired.";
  public static final String BATCH_NOT_FOUND = "Batch not found.";
  public static final String BATCH_STOCK_INSUFFICIENT = "Batch stock is insufficient.";
  public static final String BATCHSTOCK_NOT_EMPTY = "Batch stock is empty.";
  public static final String BUYER_ID_NOT_FOUND = "Buyer ID was not found.";
  public static final String BUYER_ID_NOT_NULL = "Buyer ID cannot be null.";
  public static final String BUYER_ID_NOT_VALID = "Buyer ID is not valid.";
  public static final String DATE_FUTURE = "Date is in the future.";
  public static final String DATE_PAST_OR_PRESENT = "One or more fields are not past or present.";
  public static final String DATE_REQUIRED = "Date is required.";
  public static final String FIELD_MIN_VALUE = "One or more fields are less than the minimum value.";
  public static final String FIELD_NOT_FOUND = "One or more fields are invalid.";
  public static final String FILTER_NOT_FOUND = "Filter not found.";
  public static final String INBOUND_ORDER_NOT_FOUND = "Inbound order not found.";
  public static final String INSERT_BATCH_SECTION_INCORRET = "Insert batch in wrong section";

  public static final String LETTER_NOT_VALID = "The letter must to be L, V or Q";
  public static final String LIMIT_CAPACITY_SECTION = "Total sector capacity limit exceeded.";
  public static final String MANAGER_NOT_VALID = "Warehouse does not have a valid manager.";
  public static final String ORDER_BY_NOT_VALID = "Order parameter is not valid.";
  public static final String OUTBOUND_ORDER_BATCH_NOT_FOUND = "Outbound Order batch not found.";
  public static final String OUTBOUND_ORDER_NOT_FOUND = "Outbound Order not found.";
  public static final String PRICE_REQUIRED = "Price is required.";
  public static final String PRODUCT_PRICE_NOT_NULL = "Product Price cannot be null.";
  public static final String PRODUCT_PRICE_NOT_VALID = "Product Price is not valid.";
  public static final String PRODUCT_QUANTITY_NOT_NULL = "Product Quantity cannot be null.";
  public static final String PRODUCT_QUANTITY_NOT_VALID = "Product Quantity is not valid.";
  public static final String PURCHASE_ORDER_ALREADY_APPROVED = "Purchase Order already approved.";
  public static final String PURCHASE_ORDER_DATE_NOT_NULL = "Purchase Order Date cannot be null.";
  public static final String PURCHASE_ORDER_DATE_NOT_VALID = "Purchase Order Date is not valid.";
  public static final String PURCHASE_ORDER_ID_NOT_NULL = "Purchase Order ID cannot be null.";
  public static final String PURCHASE_ORDER_ID_NOT_VALID = "Purchase Order ID is not valid.";
  public static final String PURCHASE_ORDER_ITEMS_NOT_EMPTY = "Purchase Order items list cannot be empty.";
  public static final String PURCHASE_ORDER_ITEMS_NOT_FOUND = "Purchase Order items not found.";
  public static final String PURCHASE_ORDER_STATUS_NOT_NULL = "Purchase Order status cannot be null.";
  public static final String PURCHASE_ORDER_STATUS_NOT_VALID = "Purchase Order status is not valid.";
  public static final String QUANTITY_MIN_VALUE = "Quantity is less than the minimum value.";
  public static final String QUANTITY_REQUIRED = "Quantity is required.";
  public static final String SECTION_CODE_NOT_EMPTY = "Section code is empty.";
  public static final String SECTION_NOT_VALID = "Section is not valid.";
  public static final String SECTION_CODE_POSITIVE = "Section code is not positive.";
  public static final String SECTION_CODE_REQUIRED = "Section code is required.";
  public static final String SECTION_NOT_FOUND = "Section not found.";
  public static final String TEMPERATURE_REQUIRED = "Temperature is required.";
  public static final String TIME_REQUIRED = "Time is required.";
  public static final String TIME_PAST_OR_PRESENT = "Time is not past or present.";
  public static final String VOLUME_MIN_VALUE = "Volume is less than the minimum value.";
  public static final String VOLUME_REQUIRED = "Volume is required.";
  public static final String WAREHOUSE_CODE_REQUIRED = "Warehouse code is required.";
  public static final String WAREHOUSE_NOT_FOUND =  "Warehouse not found.";
  public static final String SECTION_NOT_BELONG_WAREHOUSE =  "Section doesn't belong warehouse.";
  public static final String PRODUCT_BATCH_INCORRECT =  "Product is in an incorrect batch.";
  public static final String CATEGORY_NOT_FOUND = "Category must be FS, RF or FF.";
}
