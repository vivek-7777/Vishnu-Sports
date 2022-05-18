package com.example.vishnusports.ui.Search;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.collection.ArrayMap;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.vishnusports.R;
import com.example.vishnusports.RegisterActivity;
import com.example.vishnusports.RegisterActivity2;
import com.example.vishnusports.databinding.FragmentSearchBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SearchFragment extends Fragment {
    TextView viewSearch,next;
    ImageView btnSearch;
    String selectedItem,q;
    public int count=0;
    public int temp=0;
    RelativeLayout[] infoLayout =new RelativeLayout[11];
    ArrayList<String> infoEmail = new ArrayList<>();
    ArrayList<String> infoUsername = new ArrayList<>();

    TextView[] user=new TextView[10];
    TextView[] email=new TextView[10];

    private SearchViewModel searchViewModel;
    private FragmentSearchBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        next=getActivity().findViewById(R.id.next);

        //------------------visibility---------------------------
        viewSearch = getActivity().findViewById(R.id.search_bar);
        btnSearch = getActivity().findViewById(R.id.btnSearch);


        user[0] = getActivity().findViewById(R.id.outputUsername0);
        email[0] = getActivity().findViewById(R.id.outputEmail0);

        user[1] = getActivity().findViewById(R.id.outputUsername1);
        email[1] = getActivity().findViewById(R.id.outputEmail1);

        user[2] = getActivity().findViewById(R.id.outputUsername2);
        email[2] = getActivity().findViewById(R.id.outputEmail2);

        user[3] = getActivity().findViewById(R.id.outputUsername3);
        email[3] = getActivity().findViewById(R.id.outputEmail3);

        user[4] = getActivity().findViewById(R.id.outputUsername4);
        email[4] = getActivity().findViewById(R.id.outputEmail4);

        user[5] = getActivity().findViewById(R.id.outputUsername5);
        email[5] = getActivity().findViewById(R.id.outputEmail5);

        user[6] = getActivity().findViewById(R.id.outputUsername6);
        email[6] = getActivity().findViewById(R.id.outputEmail6);

        user[7] = getActivity().findViewById(R.id.outputUsername7);
        email[7] = getActivity().findViewById(R.id.outputEmail7);

        user[8] = getActivity().findViewById(R.id.outputUsername8);
        email[8] = getActivity().findViewById(R.id.outputEmail8);

        user[9] = getActivity().findViewById(R.id.outputUsername9);
        email[9] = getActivity().findViewById(R.id.outputEmail9);

        infoLayout[0] = (RelativeLayout) getActivity().findViewById(R.id.info0);
        infoLayout[1] = (RelativeLayout) getActivity().findViewById(R.id.info1);
        infoLayout[2] = (RelativeLayout) getActivity().findViewById(R.id.info2);
        infoLayout[3] = (RelativeLayout) getActivity().findViewById(R.id.info3);
        infoLayout[4] = (RelativeLayout) getActivity().findViewById(R.id.info4);
        infoLayout[5] = (RelativeLayout) getActivity().findViewById(R.id.info5);
        infoLayout[6] = (RelativeLayout) getActivity().findViewById(R.id.info6);
        infoLayout[7] = (RelativeLayout) getActivity().findViewById(R.id.info7);
        infoLayout[8] = (RelativeLayout) getActivity().findViewById(R.id.info8);
        infoLayout[9] = (RelativeLayout) getActivity().findViewById(R.id.info9);
        infoLayout[10] = (RelativeLayout) getActivity().findViewById(R.id.info10);


        infoLayout[0].setVisibility(View.INVISIBLE);
        infoLayout[1].setVisibility(View.INVISIBLE);
        infoLayout[2].setVisibility(View.INVISIBLE);
        infoLayout[3].setVisibility(View.INVISIBLE);
        infoLayout[4].setVisibility(View.INVISIBLE);
        infoLayout[5].setVisibility(View.INVISIBLE);
        infoLayout[6].setVisibility(View.INVISIBLE);
        infoLayout[7].setVisibility(View.INVISIBLE);
        infoLayout[8].setVisibility(View.INVISIBLE);
        infoLayout[9].setVisibility(View.INVISIBLE);
        infoLayout[10].setVisibility(View.INVISIBLE);
        next.setVisibility(View.INVISIBLE);

        //-------------------------------------------------------

        viewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSearch.setEnabled(true);
                selectedItem=null;
                infoEmail.clear();
                infoUsername.clear();
                temp=0;
                count=0;

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


                viewSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Initializing a new alert dialog
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                        // Set the alert dialog title
                        builder.setTitle("Choose a game");

                        // Initializing an array of flowers
                        final String[] games = new String[]{
                                "Badminton", "Basketball", "Carroms", "Chess", "Cricket", "Football",
                                "Kabaddi", "Kho-Kho", "Table Tennis", "Tennis", "Throwball", "Volleyball"
                        };

                        // Set a single choice items list for alert dialog
                        builder.setSingleChoiceItems(
                                games, // Items list
                                -1, // Index of checked item (-1 = no selection)
                                new DialogInterface.OnClickListener() // Item click listener
                                {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // Get the alert dialog selected item's text
                                        selectedItem = Arrays.asList(games).get(i);

                                    }
                                });

                        // Set the alert dialog positive button
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                viewSearch.setText(selectedItem);
                                // Just dismiss the alert dialog after selection
                                // Or do something now
                            }
                        });

                        // Create the alert dialog
                        AlertDialog dialog = builder.create();

                        // Finally, display the alert dialog
                        dialog.show();
                    }
                });

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                infoLayout[0].setVisibility(View.INVISIBLE);
                infoLayout[1].setVisibility(View.INVISIBLE);
                infoLayout[2].setVisibility(View.INVISIBLE);
                infoLayout[3].setVisibility(View.INVISIBLE);
                infoLayout[4].setVisibility(View.INVISIBLE);
                infoLayout[5].setVisibility(View.INVISIBLE);
                infoLayout[6].setVisibility(View.INVISIBLE);
                infoLayout[7].setVisibility(View.INVISIBLE);
                infoLayout[8].setVisibility(View.INVISIBLE);
                infoLayout[9].setVisibility(View.INVISIBLE);
                infoLayout[10].setVisibility(View.INVISIBLE);
                next.setVisibility(View.INVISIBLE);

                if (selectedItem != null) {

                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://vishnu-sports-default-rtdb.asia-southeast1.firebasedatabase.app/");
                    DatabaseReference myRef = database.getReference("Users");

                    if (count != 0) {
                        myRef.addValueEventListener(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                            if (ds.child("game2").getValue() == null) {
                                                if ((Objects.requireNonNull(ds.child("game1").getValue()).toString().compareTo(selectedItem) == 0)

                                                ) {
                                                    infoEmail.add(Objects.requireNonNull(ds.child("email").getValue()).toString());
                                                    infoUsername.add(Objects.requireNonNull(ds.child("username").getValue()).toString());

                                                }
                                            }
                                            else if (ds.child("game3").getValue() == null) {
                                                if ((Objects.requireNonNull(ds.child("game1").getValue()).toString().compareTo(selectedItem) == 0)

                                                        || (Objects.requireNonNull(ds.child("game2").getValue())).toString().compareTo(selectedItem) == 0
                                                ) {

                                                    infoEmail.add(Objects.requireNonNull(ds.child("email").getValue()).toString());
                                                    infoUsername.add(Objects.requireNonNull(ds.child("username").getValue()).toString());
                                                }
                                            }
                                            else {
                                                if ((Objects.requireNonNull(ds.child("game1").getValue()).toString().compareTo(selectedItem) == 0)
                                                        || (Objects.requireNonNull(ds.child("game2").getValue())).toString().compareTo(selectedItem) == 0
                                                        || (Objects.requireNonNull(ds.child("game3").getValue()).toString().compareTo(selectedItem) == 0)
                                                ) {

                                                    infoEmail.add(Objects.requireNonNull(ds.child("email").getValue()).toString());
                                                    infoUsername.add(Objects.requireNonNull(ds.child("username").getValue()).toString());

                                                }

                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        //handle databaseError
                                    }
                                });

                        if (infoUsername.size() == 0) {
                            btnSearch.setEnabled(true);
                            Toast.makeText(getActivity(), "Please click again!!", Toast.LENGTH_SHORT).show();
                        }

                        if (infoUsername.size() <= 10) {
                            for (int i = 0; i < infoUsername.size(); i++) {
                                infoLayout[i].setVisibility(View.VISIBLE);
                                user[i].setText(infoUsername.get(i));
                                email[i].setText(infoEmail.get(i));
                            }
                            next.setEnabled(false);
                            btnSearch.setEnabled(true);
                            infoUsername.clear();
                            infoEmail.clear();
                        }
                        else {
                            count = infoUsername.size();
                            for (int i = 0; i < 10; i++) {
                                infoLayout[i].setVisibility(View.VISIBLE);
                                user[i].setText(infoUsername.get(i));
                                email[i].setText(infoEmail.get(i));
                                temp=i+1;
                            }
                            infoLayout[10].setVisibility(View.VISIBLE);
                            next.setVisibility(View.VISIBLE);
                            next.setEnabled(true);
                            btnSearch.setEnabled(false);
                        }


                    }else{
                        Toast.makeText(getActivity(), "Please click again", Toast.LENGTH_SHORT).show();
                        count++;
                    }
                }

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();


                infoLayout[0].setVisibility(View.INVISIBLE);
                infoLayout[1].setVisibility(View.INVISIBLE);
                infoLayout[2].setVisibility(View.INVISIBLE);
                infoLayout[3].setVisibility(View.INVISIBLE);
                infoLayout[4].setVisibility(View.INVISIBLE);
                infoLayout[5].setVisibility(View.INVISIBLE);
                infoLayout[6].setVisibility(View.INVISIBLE);
                infoLayout[7].setVisibility(View.INVISIBLE);
                infoLayout[8].setVisibility(View.INVISIBLE);
                infoLayout[9].setVisibility(View.INVISIBLE);
                infoLayout[10].setVisibility(View.INVISIBLE);
                next.setVisibility(View.INVISIBLE);

                count=count-10;
                if(count<=10){
                    for(int i=0;i<count;i++){
                        infoLayout[i].setVisibility(View.VISIBLE);
                        user[i].setText(infoUsername.get(temp+i));
                        email[i].setText(infoEmail.get(temp+i));
                    }
                    infoUsername.clear();
                    infoEmail.clear();
                    btnSearch.setEnabled(true);
                    next.setEnabled(false);
                }else{
                    for(int i=0;i<10;i++){
                        infoLayout[i].setVisibility(View.VISIBLE);
                        user[i].setText(infoUsername.get(temp+i));
                        email[i].setText(infoEmail.get(temp+i));
                    }
                    infoLayout[10].setVisibility(View.VISIBLE);
                    next.setVisibility(View.VISIBLE);
                    next.setEnabled(true);
                    btnSearch.setEnabled(false);
                    temp=temp+10;
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}