package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        // Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants whipped cream topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        // Calculate the price
        int price = calculatePrice(hasWhippedCream, hasChocolate);

        TextView pricetextview = (TextView) findViewById(R.id.price_text_view);
        pricetextview.setText(" " + createOrderSummary(price,hasWhippedCream,hasChocolate,name));

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(price, hasWhippedCream, hasChocolate, name));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Creater summery of the order
     *
     * @param name            the customer
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param price           of the order
     * @param addChocolate    is whether or not the user wants chocolate
     * @return text summary
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name) {
        String priceMessage = "Name: " + name;
        priceMessage += "\n" + getString(R.string.addwhippedcream) + addWhippedCream;
        priceMessage += "\n" + getString(R.string.aadchocolate) + addChocolate;
        priceMessage += "\n" + getString(R.string.quantity2) + quantity;
        priceMessage += "\n" + getString(R.string.total) + price;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;

    }

    /**
     * Calculates the price of the order.
     * *@param addWhippedCream is whether or not the user wants whipped cream topping
     * *@param addChocolate is whether or not the user wants chocolate
     * return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        // Price of 1 cup of coffeee
        int baseprice = 5;
        // Add 2 dollars if the user wants whipped cream
        if (addWhippedCream) {
            baseprice = baseprice + 2;
        }
        // Add 1 dollars if the user wants chocolate
        if (addChocolate) {
            baseprice = baseprice + 1;
        }
        // Calculate the total order price by  multiplying
        return quantity * baseprice;
    }


    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}
