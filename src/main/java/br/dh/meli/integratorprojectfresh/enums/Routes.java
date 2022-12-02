package br.dh.meli.integratorprojectfresh.enums;

/**
 * The enum Routes lists the API base route and various endpoints.
 */
public class Routes {
  public static final String BASE_ROUTE = "/api/v1/fresh-products";
  public static final String DUE_DATE = "/due-date";
  public static final String DUE_DATE_LIST = DUE_DATE + "/list";
  public static final String INBOUND_ORDER = "/inbound-order";
  public static final String PRODUCT_LIST = "/list";
  public static final String PRODUCT_LIST_BATCH = PRODUCT_LIST + "/batch";
  public static final String PURCHASE_ORDER = "/orders";
  public static final String WAREHOUSE = "/warehouse";
  public static final String WAREHOUSE_QUERY_TYPE = WAREHOUSE + "/query_type";
}