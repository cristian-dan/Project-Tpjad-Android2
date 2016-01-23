package com.example.ovyis.client2tpjad.utils;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ovyis.client2tpjad.constants.RequestsConstants;
import com.example.ovyis.client2tpjad.interfaces.BuyProductCallbackInterface;
import com.example.ovyis.client2tpjad.interfaces.ProductCallbackInterface;
import com.example.ovyis.client2tpjad.interfaces.RequestCallbackInterface;
import com.example.ovyis.client2tpjad.mappers.ProductMapper;
import com.example.ovyis.client2tpjad.models.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by OvyIs on 1/23/2016.
 */
public class ProductHelper {

    private static ProductHelper mInstance;
    private List<Product> mProductList;
    public List<Product> getProductList() {
        return mProductList;
    }
    private ProductHelper() {

    }

    public static ProductHelper getInstance() {
        if (mInstance == null) {
            mInstance = new ProductHelper();
        }

        return mInstance;
    }

    public void loadProducts(final Context context, final ProductCallbackInterface callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                RequestsConstants.PRODUCTS_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mProductList = new ArrayList<>(ProductMapper.convertToModels(response));
                        callback.onLoadComplete(mProductList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {

        };

        queue.add(request);
    }

    public void buyProduct(final Context context, final BuyProductCallbackInterface callback, String productId) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                RequestsConstants.PRODUCT_BUY + productId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onLoadComplete();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Out of stock!", Toast.LENGTH_LONG).show();
                    }
                }
        ) {

        };

        queue.add(request);
    }

    public String getProductListAtIndex(int position) {
        if (mProductList == null || mProductList.size() == 0) {
            return null;
        }
        if (mProductList.size() <= position) {
            return  null;
        }
        return mProductList.get(position).getId() + "";
    }
}
