package com.example.vishnusports;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Arrays;
import java.util.List;

public class RegisterActivity2 extends AppCompatActivity {

    int j=0;
    int max=3;
    int count=0;
    boolean boolUsername,boolGames=false;
    char[] charArray;
    char ch;
    public static String username="",gender,game1,game2,game3;
    public static String[] interest=new String[12];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        Spinner mySpinner =findViewById(R.id.gender);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(RegisterActivity2.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Gender));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);


        gender=mySpinner.getSelectedItem().toString();


        EditText mEdit;
        mEdit   = findViewById(R.id.inputEmail);
        mEdit.setOnClickListener(v -> {
            username =mEdit.getText().toString();
            charArray = username.toCharArray();
            for (char c : charArray) {
                ch = c;
                if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == ' ') {
                    boolUsername = true;
                } else {
                    boolUsername = false;
                    break;
                }
            }
        });


        TextView btn =  findViewById(R.id.gamesPlayed);
        final TextView tv =  findViewById(R.id.gamesPlayedDisplay1);

        btn.setOnClickListener(v -> {
            // Build an AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity2.this);

            // String array for alert dialog multi choice items
            String[] games = new String[]{
                    "Badminton", "Basketball", "Carroms", "Chess", "Cricket", "Football",
                    "Kabaddi", "Kho-Kho", "Table tennis", "Tennis", "Throwball", "Volleyball"
            };

            // Boolean array for initial selected items
            final boolean[] checkedGames = new boolean[]{
                    false, false, false, false, false, false,
                    false, false, false, false, false, false
            };

            // Convert the color array to list
            final List<String> gamesList = Arrays.asList(games);

            // Set multiple choice items for alert dialog
            /*
                AlertDialog.Builder setMultiChoiceItems(CharSequence[] items, boolean[]
                checkedItems, DialogInterface.OnMultiChoiceClickListener listener)
                    Set a list of items to be displayed in the dialog as the content,
                    you will be notified of the selected item via the supplied listener.
             */
            /*
                DialogInterface.OnMultiChoiceClickListener
                public abstract void onClick (DialogInterface dialog, int which, boolean isChecked)

                    This method will be invoked when an item in the dialog is clicked.

                    Parameters
                    dialog The dialog where the selection was made.
                    which The position of the item in the list that was clicked.
                    isChecked True if the click checked the item, else false.
             */
            builder.setMultiChoiceItems(games, checkedGames, (dialog, which, isChecked) -> {

                // Update the current focused item's checked status
                checkedGames[which] = isChecked;

                // Get the current focused item
                String currentItem = gamesList.get(which);

                // Notify the current action
                Toast.makeText(getApplicationContext(),
                        currentItem + " " + (isChecked ? " added" :" removed"), Toast.LENGTH_SHORT).show();
            });

            // Specify the dialog is not cancelable
            builder.setCancelable(false);

            // Set a title for alert dialog
            //builder.setTitle("Your preferred games?");

            // Set the positive/yes button click listener
            builder.setPositiveButton("Done", (dialog, which) -> {
                // Do something when click positive button
                tv.setText("");
                tv.setTextColor(Color.BLACK);
                tv.setText("Your Interested games..... \n");
                if(count==0){
                    boolGames=false;
                }
                j=0;
                count=0;
                for (int i = 0; i<checkedGames.length; i++){
                    tv.setTextColor(Color.BLACK);
                    boolGames=true;
                    boolean checked = checkedGames[i];
                    if (checked) {
                        interest[j++]= gamesList.get(i);
                        count++;
                        if(count<=max) {
                            tv.setTextColor(Color.BLACK);
                            tv.setText(tv.getText() + gamesList.get(i) + "\n");
                        }
                        else{
                            if(tv.getCurrentTextColor()==Color.BLACK)
                                tv.setTextColor(Color.RED);
                            tv.setText("   Please select atmost 3 games");

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

        TextView btn1 = findViewById(R.id.Login);
        btn1.setOnClickListener(v -> startActivity(new Intent(RegisterActivity2.this,LoginActivity.class)));
        j=0;
        TextView btn2 = findViewById(R.id.otpRegister);
        btn2.setOnClickListener(v -> {

            gender=mySpinner.getSelectedItem().toString();

            boolean temp=true;
            username =mEdit.getText().toString();
            charArray = username.toCharArray();
            for (char c : charArray) {
                ch = c;
                if (!((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == ' ')) {
                    temp=false;
                }
            }
            if ( username.length()<1 || !temp){
                mEdit.setError("Username must contain alphabets only and cannot be empty");
            }
            else if(username.length()>=20)
                mEdit.setError("Username too long !");
            else if(gender.compareTo("Gender")==0){
                ((TextView)mySpinner.getSelectedView()).setTextColor(Color.RED);
                ((TextView)mySpinner.getSelectedView()).setError("Invalid Gender");
            }

            else if(count<1){
                tv.setTextColor(Color.RED);
                tv.setText("  Please select atleast one game\n");
            }
            else {
                Intent i=(new Intent(RegisterActivity2.this,RegisterActivity.class));
                /*i.putExtra("username",username);
                i.putExtra("gender",gender);*/
                /*if(count==1){
                    interest[1]="null";
                    interest[2]="null";
                }else
                    interest[2]="null";*/
                game1=interest[0];
                game1=interest[1];
                game2=interest[2];
               /* i.putExtra("game1",interest[0]);
                i.putExtra("game2",interest[1]);
                i.putExtra("game3",interest[2]);*/
                startActivity(i);
            }
        });
    }
}
