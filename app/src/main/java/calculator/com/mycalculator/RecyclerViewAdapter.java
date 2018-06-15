package calculator.com.mycalculator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;

import static android.content.Context.MODE_PRIVATE;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>  {
    public ArrayList<MyData> myValues;
    public RecyclerViewAdapter (ArrayList<MyData> myValues){
        this.myValues= myValues;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.myTextView.setText(myValues.get(position).getInput());
        holder.secTextView.setText(myValues.get(position).getResult());
        holder.dateT.setText(myValues.get(position).getD());

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(History_Activity.getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Do You Want to Remove !")
                        .setMessage("Press Yes to Remove")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {

                                myValues.remove(position);
                                notifyDataSetChanged();

                                SharedPreferences sharedPreferences =
                                        MainActivity.getContext().getSharedPreferences("calculator.com.myCalc",MODE_PRIVATE);
                                Gson gson = new Gson();
                                String json = "";
                                MainActivity.historyList.clear();
                                for (int i=0;i < myValues.size(); i++)
                                {
                                    json = gson.toJson(myValues.get(i));
                                    MainActivity.historyList.add(json);
                                }
                                HashSet<String> set = new HashSet<String>(MainActivity.historyList);
                                sharedPreferences.edit().putStringSet("notes",set).apply();
                                sharedPreferences.edit().commit();
                                Toast.makeText(History_Activity.getContext(),"Deleted... ", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("No",null)
                        .show();



            }
        });

    }


    @Override
    public int getItemCount() {
        return myValues.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        private TextView myTextView;
        private TextView secTextView;
        private TextView dateT;
        private Button del;
        public MyViewHolder(View itemView) {
            super(itemView);
            del = (Button)itemView.findViewById(R.id.bn_del);
            myTextView = (TextView)itemView.findViewById(R.id.text_cardview);
            secTextView = (TextView)itemView.findViewById(R.id.text_cardview1);
            dateT = (TextView)itemView.findViewById(R.id.text_cardview2);

        }
    }

}

