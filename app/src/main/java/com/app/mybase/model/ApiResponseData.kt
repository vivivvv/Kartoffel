package com.app.mybase.model

data class ApiResponseData(
    val `data`: List<Data>,
    val data_count: Int,
    val message: String,
    val status: Int
)

data class Data(
    val brand_id: Int,
    val category_id: Int,
    val cod_charge: Int,
    val created_by: Int,
    val created_ts: String,
    val dealer_id: Int,
    val delivery_charge: Int,
    val exchangeable_qty_amt: Int,
    val is_active: Int,
    val is_exchangeable: Int,
    val last_active_date: String,
    val prod_image: String,
    val product_description: String,
    val product_discount: Int,
    val product_id: Int,
    val product_image: String,
    val product_name: String,
    val product_price: Int,
    val product_price_id: Int,
    val product_selling_price: Int,
    val product_stock_quantity: Int,
    val sub_category_id: Int,
    val updated_by: Int,
    val updated_ts: String
)