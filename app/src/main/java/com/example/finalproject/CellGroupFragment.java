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


/**
 * A simple {@link Fragment} subclass.
 */
public class CellGroupFragment extends Fragment {

    private int groupId;
    private OnFragmentInteractionListener Listener;
    private View view;

    public CellGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

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

    public void setGroupId(int groupId) {

        this.groupId = groupId;

    }

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


    public boolean checkGroupCorrect() {
        ArrayList<Integer> numbers = new ArrayList<>();
        //Set  click listeners for all cells in this group
        int cells[] = new int[]{R.id.cell0, R.id.cell1, R.id.cell2, R.id.cell3,
                R.id.cell4, R.id.cell5, R.id.cell6, R.id.cell7, R.id.cell8};

        for (int cell : cells) {
            TextView txt_cell = view.findViewById(cell);
            int number = Integer.parseInt(txt_cell.getText().toString());
            if (numbers.contains(number)) {
                return false;
            } else {
                numbers.add(number);
            }
        }
        return true;
    }

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

    @Override
    public void onDetach() {
        super.onDetach();
        Listener = null;
    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int groupId, int cellId, View view);
    }

}
