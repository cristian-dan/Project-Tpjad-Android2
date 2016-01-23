package com.example.ovyis.client2tpjad.mappers;

import com.example.ovyis.client2tpjad.constants.ModelsConstants;
import com.example.ovyis.client2tpjad.models.Category;
import com.example.ovyis.client2tpjad.models.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OvyIs on 1/23/2016.
 */
public class ProductMapper {

    public static List<Product> convertToModels(String response) {
        List<Product> products = new ArrayList<>();

        try {
            JSONObject resp = new JSONObject(response);
            JSONArray jsonProducts = resp.getJSONArray(ModelsConstants.MODEL);

            for (int i = 0; i < jsonProducts.length(); i++) {
                JSONObject jsonProduct = jsonProducts.getJSONObject(i);
                JSONObject jsonCategory = jsonProduct.optJSONObject(ModelsConstants.PRODUCT_CATEGORY);
                Category category = new Category(jsonCategory.getInt(ModelsConstants.CATEGORY_ID), jsonCategory.getString(ModelsConstants.CATEGORY_NAME));
                Product product = new Product(
                        jsonProduct.getInt(ModelsConstants.PRODUCT_ID),
                        jsonProduct.getInt(ModelsConstants.PRODUCT_STOCK),
                        jsonProduct.getString(ModelsConstants.PRODUCT_NAME),
                        jsonProduct.getString(ModelsConstants.PRODUCT_DESCRIPTION),
                        category
                );
                products.add(product);
            }
        } catch (JSONException ex) {

        }

        return products;
    }
}
