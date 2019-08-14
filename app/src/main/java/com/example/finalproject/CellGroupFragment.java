package com.example.finalproject;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

//Fragment for the game board, this fragment is mainly about the cell group, each group has 3 x 3 cells
//each board has 3 x 3 cell groups

public class CellGroupFragment extends Fragment {

    //attributes

    private int groupId;
    private OnFragmentInteractionListener Listener;//Listener: very important, can respond user's click on cell group
    private View view;

    public CellGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }


    //oncreate function

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.cell_group_fragment, container, false);

        //Set  click listeners for all cells in this group
        int cells[] = new int[]{R.id.cell0, R.id.cell1, R.id.cell2, R.id.cell3,
                R.id.cell4, R.id.cell5, R.id.cell6, R.id.cell7, R.id.cell8};
        for (int cell : cells) {
            TextView txt_cell = view.findViewById(cell);
            txt_cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Listener.onFragmentInteraction(groupId, Integer.parseInt(view.getTag().toString()), view);
                }
            });
        }
        return view;
    }

    //for each fragment, there is a group id, will be used to get x and y position for a single cell

    public void setGroupId(int groupId) {

        this.groupId = groupId;

    }

    //put values for this group, will only set Text if the cell value is not o, because cell with 0 value means it is a space for user to fill

    public void setValue(int position, int value) {
        int cells[] = new int[]{R.id.cell0, R.id.cell1, R.id.cell2, R.id.cell3,
                R.id.cell4, R.id.cell5, R.id.cell6, R.id.cell7, R.id.cell8};
        TextView currentView = view.findViewById(cells[position]);

        if(value!=0){
            currentView.setText(String.valueOf(value));
            currentView.setTextColor(Color.BLACK);
            currentView.setTypeface(null, Typeface.BOLD);
        }
        else{
            currentView.setText("");
        }
    }



//  When called, setup listener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            Listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    //when removed, close listener

    @Override
    public void onDetach() {
        super.onDetach();
        Listener = null;
    }

    //Set the interface so, the listener can be called from the game activity

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int groupId, int cellId, View view);
    }

}
