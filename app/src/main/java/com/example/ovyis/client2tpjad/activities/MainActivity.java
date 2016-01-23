package com.example.ovyis.client2tpjad.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ovyis.client2tpjad.R;
import com.example.ovyis.client2tpjad.adapters.ProductAdapter;
import com.example.ovyis.client2tpjad.interfaces.BuyProductCallbackInterface;
import com.example.ovyis.client2tpjad.interfaces.ProductCallbackInterface;
import com.example.ovyis.client2tpjad.models.Product;
import com.example.ovyis.client2tpjad.utils.ProductHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private ProgressDialog mProgressDialog;
    private ProductHelper mProductHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProductHelper = ProductHelper.getInstance();
        displayLoadingStatus();
        //refreshCategoriesSpinner();
        refreshProductsList();
        hideLoadingStatus();
        mListView = (ListView) findViewById(R.id.productsList);
    }

    public void refreshProductsList() {
        mProductHelper.loadProducts(
                this,
                new ProductCallbackInterface() {
                    @Override
                    public void onLoadComplete(List<Product> products) {
                        final ProductAdapter adapter = new ProductAdapter(MainActivity.this, products);
                        mListView.setAdapter(adapter);
                        registerForContextMenu(mListView);
                    }
                }
        );
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_context_menu, menu);
    }


    private void displayProductPopup(Product selectedProduct) {
    }

    private void displayLoadingStatus() {
        mProgressDialog = ProgressDialog.show(this, getString(R.string.loading_title), getString(R.string.loading_message), true);
    }

    private void hideLoadingStatus() {
        mProgressDialog.dismiss();
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        displayLoadingStatus();
        mProductHelper.buyProduct(this,
                new BuyProductCallbackInterface() {
                    @Override
                    public void onLoadComplete() {
                        refreshProductsList();
                    }
                },
                ProductHelper.getInstance().getProductListAtIndex(info.position));

        hideLoadingStatus();
        return  super.onContextItemSelected(item);
    }
}
