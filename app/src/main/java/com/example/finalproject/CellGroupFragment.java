/**
 * This fragment represents a group of 3x3 cells on the board.
 */
package com.example.finalproject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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

    /**
     * Default empty constructor
     */
    public CellGroupFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    /**
     * Displays the fragment on create.
     * @param inflater Inflates the layout
     * @param container Container to inflate the layout
     * @param savedInstanceState The saved previous state
     * @return Returns the view of the fragment
     */
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

    /**
     * Sets the group ID for each fragment that will be used to get the x and y position for a cell.
     * @param groupId
     */
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    /**
     * Sets the values for a matrix group. It will only set the text if the value is not 0 because a 0 value
     * represents an empty space for the user to solve.
     * @param position
     * @param value
     */
    public void setValue(int position, int value) {
        int cells[] = new int[]{R.id.cell0, R.id.cell1, R.id.cell2, R.id.cell3,
                R.id.cell4, R.id.cell5, R.id.cell6, R.id.cell7, R.id.cell8};
        TextView currentView = view.findViewById(cells[position]);

        if(value!=0){
            currentView.setText(String.valueOf(value));
            currentView.setTextColor(Color.BLACK);
            currentView.setTypeface(null, Typeface.BOLD);
            currentView.setBackgroundColor(Color.WHITE);
        }
        else{
            currentView.setText("");
            currentView.setTextColor(Color.BLACK);
            currentView.setTypeface(null, Typeface.BOLD);
            currentView.setBackgroundColor(Color.WHITE);
        }
    }

    /**
     * Shows the result of the puzzle creation.
     * @param position Position of the cell
     * @param value The value in the cell
     * @param vs Value in the start board
     * @param vc Value in the current board
     */
    public void showResult(int position, int value, int vs, int vc) {
        int cells[] = new int[]{R.id.cell0, R.id.cell1, R.id.cell2, R.id.cell3,
                R.id.cell4, R.id.cell5, R.id.cell6, R.id.cell7, R.id.cell8};
        TextView currentView = view.findViewById(cells[position]);

        if(vs!=vc){
            currentView.setText(String.valueOf(value));
            currentView.setTextColor(Color.BLACK);
            currentView.setTypeface(null, Typeface.BOLD);
            currentView.setBackgroundColor(Color.RED);
        }
        else{
            currentView.setText(String.valueOf(value));
            currentView.setTextColor(Color.BLACK);
            currentView.setTypeface(null, Typeface.BOLD);
            currentView.setBackgroundColor(Color.WHITE);
        }
    }

    /**
     * Sets the listener for the fragment
     * @param context Context of the application it's in
     */
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

    /**
     * Closes the listener when removed.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        Listener = null;
    }

    /**
     * Sets the interface of the fragment so the listener can be called from game activity.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int groupId, int cellId, View view);
    }
}
