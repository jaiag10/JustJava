package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.attr.name;
import static android.R.attr.text;
import static android.R.id.checkbox;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    int quantity =2;
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameedit = (EditText) findViewById(R.id.edittext);
        String value = nameedit.getText().toString();
        CheckBox whipcheck = (CheckBox) findViewById(R.id.checkbox);
        boolean haswhippedcream = whipcheck.isChecked();
        CheckBox choco = (CheckBox) findViewById(R.id.checkbox2);
        boolean haschoco = choco.isChecked();
        int price = calculatePrice(haswhippedcream, haschoco);
        String priceMessage = createOrderSummary(price, haswhippedcream, haschoco, value);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "jaijan59@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for " + value);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }


    public void increment(View view) {
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if(quantity==1)
        {
            Toast.makeText(this,"Seriously? no Coffee",Toast.LENGTH_SHORT).show();
            return;
        }
            quantity=quantity - 1;
        displayQuantity(quantity);

    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(boolean addwhip,boolean addchoco) {
        int basePrice = 5;
        if(addwhip){
            basePrice = basePrice +1;
        }
        if (addchoco){
            basePrice = basePrice + 2;
        }
        return quantity*basePrice;
    }


    private String createOrderSummary(int price , boolean addwhip , boolean addchoco, String namee) {
        String priceMessage = "Name : " + namee ;
        priceMessage = priceMessage + "\nQuantity: " + quantity;
        priceMessage = priceMessage + "\nAdd Whipped Cream? " + addwhip;
        priceMessage = priceMessage + "\nAdd Chocolate? " + addchoco;
        priceMessage = priceMessage + "\nTotal $" + price;
        priceMessage = priceMessage + "\nThank you!!";
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }

}