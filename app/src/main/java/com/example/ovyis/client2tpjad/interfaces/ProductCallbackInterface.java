package com.example.ovyis.client2tpjad.interfaces;

/**
 * Created by OvyIs on 1/23/2016.
 */

import com.example.ovyis.client2tpjad.models.Product;
import java.util.List;


public interface ProductCallbackInterface {
    void onLoadComplete(List<Product> products);
}
