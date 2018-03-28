package com.tga.fragment.needToKnow;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tga.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Shopping extends Fragment {

    private TextView discShoppingDist,discMarkets,discMalls;
    private boolean isTextViewClicked = false,isTextViewClicked2 = false,isTextViewClicked3 = false;

    public Shopping() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shopping, container, false);

        discShoppingDist = (TextView)v.findViewById(R.id.discShoppingDist);
        discMarkets = (TextView)v.findViewById(R.id.discMarkets);
        discMalls = (TextView)v.findViewById(R.id.discMalls);
        discShoppingDist.setText("Shopping districts"+" \n\n" + "The districts of Maadi (along road 9 in particular) and zamalek are known for their fashionable boutiques high-end jewelers and trendy home decor shops. Abbas El-Akkad street in Nasr City is lined with chain stores selling shoes,clothes and accessories.");
        discShoppingDist.setMaxLines(1);
        discShoppingDist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTextViewClicked) {
                    //This will shrink textview to 2 lines if it is expanded.
                    discShoppingDist.setMaxLines(1);
                    isTextViewClicked = false;
                } else {
                    //This will expand the textview if it is of 2 lines
                    discShoppingDist.setMaxLines(Integer.MAX_VALUE);
                    isTextViewClicked = true;
                    discMarkets.setMaxLines(1);
                    isTextViewClicked2 = false;
                    discMalls.setMaxLines(1);
                    isTextViewClicked3 = false;

                }
            }
        });
        discMarkets.setText("Markets"+" \n\n" + "Khan El-khalil is a famous souk filed with eclectic goods and souvenirs, including metalwork,leather good and scarves. \nThere are also section devoted to gold,herbs,food and other ware.");
        discMarkets.setMaxLines(1);
        discMarkets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTextViewClicked2) {
                    //This will shrink textview to 2 lines if it is expanded.
                    discMarkets.setMaxLines(1);
                    isTextViewClicked2 = false;
                } else {
                    //This will expand the textview if it is of 2 lines
                    discMarkets.setMaxLines(Integer.MAX_VALUE);
                    isTextViewClicked2 = true;
                    discMalls.setMaxLines(1);
                    isTextViewClicked3 = false;
                    discShoppingDist.setMaxLines(1);
                    isTextViewClicked = false;
                }
            }
        });
        discMalls.setText("Malls"+" \n\n" + "- City stars in Nasr City features international fashion brands,souvenir stores and jewelers,plus cinemas and bowling alley.\n- Known for its indoor ski park, the upscale Mall of Egypt aslo features Middle Eastern fashion boutiques.");
        discMalls.setMaxLines(1);
        discMalls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTextViewClicked3) {
                    //This will shrink textview to 2 lines if it is expanded.
                    discMalls.setMaxLines(1);
                    isTextViewClicked3 = false;
                } else {
                    //This will expand the textview if it is of 2 lines
                    discMalls.setMaxLines(Integer.MAX_VALUE);
                    isTextViewClicked3 = true;
                    discShoppingDist.setMaxLines(1);
                    isTextViewClicked = false;
                    discMarkets.setMaxLines(1);
                    isTextViewClicked2 = false;
                }
            }
        });

        return v;}

}
