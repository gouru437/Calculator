package calculator.com.mycalculator;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Collections;
import java.util.HashSet;

public class History_Activity extends AppCompatActivity {

    private static History_Activity history_activity;

    private Button delButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_layout);

        delButton = (Button)findViewById(R.id.deleteButton);
        history_activity = this;

        Collections.sort(MainActivity.hisList);

        final RecyclerViewAdapter aAdapter = new RecyclerViewAdapter(MainActivity.hisList);
        RecyclerView myView =  (RecyclerView)findViewById(R.id.recyclerview);
        myView.setHasFixedSize(true);
        myView.setAdapter(aAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        myView.setLayoutManager(llm);

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new android.app.AlertDialog.Builder(getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Do You Want to Remove !")
                        .setMessage("Press Yes to Remove")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {

                                MainActivity.hisList.clear();
                                MainActivity.historyList.clear();
                                aAdapter.notifyDataSetChanged();


                                SharedPreferences sharedPreferences =
                                        MainActivity.getContext().getSharedPreferences("calculator.com.myCalc",MODE_PRIVATE);

                                HashSet<String> set = new HashSet<String>(MainActivity.historyList);
                                sharedPreferences.edit().putStringSet("notes",set).apply();
                                sharedPreferences.edit().commit();

                                Toast.makeText(getContext(),"History Cleared Successfully... ", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
            }
        });

    }

    static History_Activity getContext()
    {
        return history_activity;
    }


}
