package com.example.vishnusports.ui.Profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.vishnusports.LoginActivity;
import com.example.vishnusports.R;
import com.example.vishnusports.RegisterActivity2;
import com.example.vishnusports.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;
    TextView email,username, viewGames;
    Button btnLogout,btnSave,btnCancel;
    String email2,username1,game1,game2,game3,temp,rollno;
    String id;
    int count=0;
    DocumentReference myReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DocumentReference documentReference;
    FirebaseUser user;
    FirebaseFirestore fStore;
    DatabaseReference databaseReference;
    int j=0;
    int max=3;
    int count1=0;
    boolean boolUsername,boolGames=false;
    char[] charArray;
    char ch;
    //public static String username="",gender,game1,game2,game3;
    public static String[] interest=new String[12];


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onStart() {
        super.onStart();


        user = FirebaseAuth.getInstance().getCurrentUser();
        user.reload();
        if (!(user.isEmailVerified())) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getActivity(), "Please complete Verification through mail", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        } else {

            email = getActivity().findViewById(R.id.displayEmail);
            username = getActivity().findViewById(R.id.displayUsername);
            viewGames = getActivity().findViewById(R.id.gamesPlayedDisplay1);
            firebaseDatabase = FirebaseDatabase.getInstance("https://vishnu-sports-default-rtdb.asia-southeast1.firebasedatabase.app/");
            databaseReference = firebaseDatabase.getReference("Users");

            user = FirebaseAuth.getInstance().getCurrentUser();
            id = user.getUid();
            fStore = FirebaseFirestore.getInstance();
            documentReference = fStore.collection("users").document(id);

            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    if (documentSnapshot.exists()) {
                        username1 = documentSnapshot.getString("username");
                        username.setText(username1);
                        email2 = documentSnapshot.getString("email");
                        email.setText(email2);
                        rollno = email2.substring(0, email.length() - 12);
                        game1 = documentSnapshot.getString("game1");
                        game2 = documentSnapshot.getString("game2");
                        game3 = documentSnapshot.getString("game3");
                        if (game2 == null) {
                            interest[0] = game1;
                            count = 1;
                            temp = "Your Interested games(Click to change)\n   " + game1;
                        } else if (game3 == null) {
                            count = 2;
                            interest[0] = game1;
                            interest[1] = game2;
                            temp = "Your Interested games(Click to change)\n   " + game1 + "\n   " + game2;
                        } else {
                            count = 3;
                            interest[0] = game1;
                            interest[1] = game2;
                            interest[2] = game3;
                            temp = "Your Interested games(Click to change)\n   " + game1 + "\n   " + game2 + "\n   " + game3;
                        }
                        viewGames.setText(temp);
                    } else {
                        Toast.makeText(getActivity(), "Failed to fetch", Toast.LENGTH_SHORT).show();
                    }
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Failed to fetch", Toast.LENGTH_SHORT).show();
                }
            });

            btnLogout = getActivity().findViewById(R.id.btnLogout);
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Toast.makeText(getActivity(), "Logout successful", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();

                    startActivity(new Intent(getActivity(), LoginActivity.class));

                }
            });

            btnCancel = getActivity().findViewById(R.id.btnCancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Changes Discarded", Toast.LENGTH_SHORT).show();
                }
            });

            btnSave = getActivity().findViewById(R.id.btnSave);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    username1 = username.getText().toString();
                    firebaseDatabase = FirebaseDatabase.getInstance("https://vishnu-sports-default-rtdb.asia-southeast1.firebasedatabase.app/");
                    databaseReference = firebaseDatabase.getReference("Users");

                    boolean temp = true;
                    username1 = username.getText().toString();
                    charArray = username1.toCharArray();
                    for (char c : charArray) {
                        ch = c;
                        if (!((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == ' ')) {
                            temp = false;
                        }
                    }

                    if (username1.length() < 1 || !temp)
                        username.setError("Username must contain alphabets only and cannot be empty");
                    else if (username.length() >= 20)
                        username.setError("Username too long !");
                    else if (count < 1) {
                        //viewGames.setTextColor(Color.RED);
                        viewGames.setText("Please select atleast one game\n");
                        viewGames.setTextColor(Color.WHITE);
                    } else if (count > 3) {
                        viewGames.setText("Please select atmost 3 games");
                    } else {
                        Map<String, Object> hashMap = new HashMap();
                        hashMap.put("username", username1);
                        documentReference.update(hashMap).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Failed to Change", Toast.LENGTH_SHORT).show();
                            }
                        });
                        databaseReference.child(rollno).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Toast.makeText(getActivity(), "Changes saved", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Failed to change !", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Map<String, Object> hashMap1 = new HashMap();
                        hashMap1.put("game1", interest[0]);
                        documentReference.update(hashMap1).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Failed to Change", Toast.LENGTH_SHORT).show();
                            }
                        });
                        databaseReference.child(rollno).updateChildren(hashMap1).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Toast.makeText(getActivity(), "Changes saved", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Failed to change !", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Map<String, Object> hashMap2 = new HashMap();
                        hashMap2.put("game2", interest[1]);
                        documentReference.update(hashMap2).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Failed to Change", Toast.LENGTH_SHORT).show();
                            }
                        });
                        databaseReference.child(rollno).updateChildren(hashMap2).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Toast.makeText(getActivity(), "Changes saved", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Failed to change !", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Map<String, Object> hashMap3 = new HashMap();
                        hashMap3.put("game3", interest[2]);
                        documentReference.update(hashMap3).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Failed to Change", Toast.LENGTH_SHORT).show();
                            }
                        });
                        databaseReference.child(rollno).updateChildren(hashMap3).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Toast.makeText(getActivity(), "Changes saved", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Failed to change !", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                    // Toast.makeText(getActivity(),"Changes saved",Toast.LENGTH_SHORT).show();
                }
            });


            viewGames.setOnClickListener(v -> {
                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                // String array for alert dialog multi choice items
                String[] games = new String[]{
                        "Badminton", "Basketball", "Carroms", "Chess", "Cricket", "Football",
                        "Kabaddi", "Kho-Kho", "Table Tennis", "Tennis", "Throwball", "Volleyball"
                };

                // Boolean array for initial selected items
                final boolean[] checkedGames = new boolean[]{
                        false, false, false, false, false, false,
                        false, false, false, false, false, false
                };

                // Convert the color array to list
                final List<String> gamesList = Arrays.asList(games);

                builder.setMultiChoiceItems(games, checkedGames, (dialog, which, isChecked) -> {

                    // Update the current focused item's checked status
                    checkedGames[which] = isChecked;

                    // Get the current focused item
                    String currentItem = gamesList.get(which);

                    // Notify the current action
                    Toast.makeText(getContext(),
                            currentItem + " " + (isChecked ? " added" : " removed"), Toast.LENGTH_SHORT).show();
                });

                // Specify the dialog is not cancelable
                builder.setCancelable(false);

                // Set a title for alert dialog
                //builder.setTitle("Your preferred games?");

                // Set the positive/yes button click listener
                builder.setPositiveButton("Done", (dialog, which) -> {
                    // Do something when click positive button
                    viewGames.setText("");
                    viewGames.setTextColor(Color.WHITE);
                    viewGames.setText("Your Interested games..... \n   ");
                    if (count == 0) {
                        //boolGames=false;
                    }
                    j = 0;
                    count = 0;
                    for (int i = 0; i < checkedGames.length; i++) {
                        viewGames.setTextColor(Color.WHITE);
                        boolGames = true;
                        boolean checked = checkedGames[i];
                        if (checked) {
                            interest[j++] = gamesList.get(i);
                            count++;
                            if (count <= max) {
                                viewGames.setTextColor(Color.WHITE);
                                viewGames.setText(viewGames.getText() + gamesList.get(i) + "\n   ");
                            } else {
                                if (viewGames.getCurrentTextColor() == Color.WHITE)
                                    //viewGames.setTextColor(Color.RED);
                                    viewGames.setText("Please select atmost 3 games");

                            }
                        }
                    }
                });

                // Set the negative/no button click listener
                builder.setNegativeButton("Back", (dialog, which) -> {
                    // Do something when click the negative button
                });

                // Set the neutral/cancel button click listener
                builder.setNeutralButton("Cancel", (dialog, which) -> {
                    // Do something when click the neutral button
                });

                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();
            });

        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}