package com.tga.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.tga.R;

public class DialogChooser extends AppCompatDialogFragment {
    RadioButton R1,R2,R3,R4;
private dialoug_interface listerner;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout,null);
    builder.setView(view)
            .setTitle("Filter Type")
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setPositiveButton("submit", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            R1=view.findViewById(R.id.foodRadioBtn);
            R2= view.findViewById(R.id.hotelRadioBtn);
            R3=view.findViewById(R.id.cafeRadioBtn);
            R4= view.findViewById(R.id.barRadioBtn);

            if(R1.isSelected())
            {
                listerner.Apply("Restaurants");
            }
            else if(R2.isSelected())
            {
                listerner.Apply("Hotels");

            }

            else if(R3.isSelected())
            {
                listerner.Apply("Cafes");

            }
            else if(R4.isSelected())
            {
                listerner.Apply("Bars");


            }else {

            }

        }
    });
return  builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listerner = (dialoug_interface)context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString()+"must implement dialoug_interface");
        }
    }

    public interface  dialoug_interface{
        void Apply(String FilterType);
}

}

