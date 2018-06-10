package com.tga.Activity;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.tga.Controller.ProgramController;
import com.tga.Controller.SimpleCallback;
import com.tga.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditDiscount extends AppCompatActivity {

    private int mYear,mMonth,mDay;
    private EditText title, desc, percentage, endDate;
    private Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_discount);

        title = (EditText) findViewById(R.id.etxtDiscountTitle);
        desc = (EditText) findViewById(R.id.etxtDiscountDesc);
        percentage = (EditText) findViewById(R.id.etxtPercentage);
        endDate = (EditText) findViewById(R.id.imgDiscountEnd);
        update = (Button) findViewById(R.id.btnUpdateDiscount);

        String progID = getIntent().getStringExtra("PROG_ID");
        ProgramController.getByID(new SimpleCallback<ProgramController>() {
            @Override
            public void callback(final ProgramController pc) {
                title.setText(pc.getTitle());
                title.setEnabled(false);
                desc.setText(pc.getDescription());
                desc.setEnabled(false);
                percentage.setText(String.valueOf(pc.getDiscountPercentage() * 100));
                endDate.setText(pc.getDiscountEndDate());

                endDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar mcurrentDate = Calendar.getInstance();
                        mYear = mcurrentDate.get(Calendar.YEAR);
                        mMonth = mcurrentDate.get(Calendar.MONTH);
                        mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog mDatePicker = new DatePickerDialog(EditDiscount.this, new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                                Calendar myCalendar = Calendar.getInstance();
                                myCalendar.set(Calendar.YEAR, selectedyear);
                                myCalendar.set(Calendar.MONTH, selectedmonth);
                                myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                                String myFormat = "dd/MM/yy"; //Change as you need
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                                endDate.setText(sdf.format(myCalendar.getTime()));

                                mDay = selectedday;
                                mMonth = selectedmonth;
                                mYear = selectedyear;
                            }
                        }, mYear, mMonth, mDay);
                        //mDatePicker.setTitle("Select date");
                        mDatePicker.show();
                    }
                });

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (checkText() && Integer.parseInt(percentage.getText().toString()) >= 1
                                && Integer.parseInt(percentage.getText().toString()) < 100) {
                            pc.updateDiscount(endDate.getText().toString(),
                                    Double.parseDouble(percentage.getText().toString()) / 100);
                            pc.updateToDB();
                            Toast.makeText(getApplicationContext(), "Discount updated", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }, progID);
    }

    private boolean checkText(){
        return endDate.getText().toString().isEmpty() || percentage.getText().toString().isEmpty();
    }
}
