package calculator.com.mycalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    private static MainActivity mContext;
    public TextView lastCompiled;
    public TextView resultView;

    public TextView tv_x_power_y;

    String outStr = "";
    boolean justEqualPresses = false;


    String lastCompileStr = "";
    String calculateStr = "";
    String value = "";
    String dummy = "";


    int Length = 0;
    boolean pointTaken = false;

    static ArrayList<String> historyList = new ArrayList<String>();
   //static ArrayAdapter<String> arrayAdapter;

    static ArrayList<MyData> hisList = new ArrayList<MyData>();
    static ArrayAdapter<MyData> aAdapter;


    public static MainActivity getContext() {
        return mContext;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.menu_history)
        {
            Intent intent = new Intent(MainActivity.this,History_Activity.class );
            startActivity(intent);
        }


        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        resultView = (TextView) findViewById(R.id.tv_userStr);
        lastCompiled = (TextView) findViewById(R.id.tv_outPut);

        //Toast.makeText(getContext(),"OnCreate Called... ", Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences =
                getApplicationContext().getSharedPreferences("calculator.com.myCalc", MODE_PRIVATE);

        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);

        if (set == null) {

        } else {
            historyList = new ArrayList(set);
        }

        Gson gson = new Gson();


        MainActivity.hisList.clear();

        for (int i = 0; i < historyList.size(); i++)
        {
            hisList.add(gson.fromJson(historyList.get(i),MyData.class));
        }

        //arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyList);

    }

    @Override
    protected void onStart() {

        super.onStart();

        //Toast.makeText(getContext(),"Start Called... ", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        //Toast.makeText(getContext(),"Resume Called... ", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected  void onStop()
    {
        super.onStop();

        //Toast.makeText(getContext(),"Stop Called... ", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onRestart()
    {
        super.onRestart();

        //Toast.makeText(getContext(),"Restart Called... ", Toast.LENGTH_SHORT).show();
    }


    public void numberCallback(View view) {

        switch (view.getId())
        {
            case R.id.bn_zero:
                numberButtonUpdate("0");
                break;
            case R.id.bn_one:
                numberButtonUpdate("1");
                break;
            case R.id.bn_two:
                numberButtonUpdate("2");
                break;
            case R.id.bn_three:
                numberButtonUpdate("3");
                break;
            case R.id.bn_four:
                numberButtonUpdate("4");
                break;
            case R.id.bn_five:
                numberButtonUpdate("5");
                break;
            case R.id.bn_six:
                numberButtonUpdate("6");
                break;
            case R.id.bn_seven:
                numberButtonUpdate("7");
                break;
            case R.id.bn_eight:
                numberButtonUpdate("8");
                break;
            case R.id.bn_nine:
                numberButtonUpdate("9");
                break;
        }

    }

    public void numberButtonUpdate ( String str)
    {
        goToBack();

        if(lastCompileStr.endsWith("%"))
        {
            return;
        }

        justEqualPresses = false;

        lastCompileStr = lastCompileStr.concat(str);
        lastCompiled.setText(lastCompileStr);
        calculateStr = calculateStr.concat(str);
        value = value.concat(str);


    }

    public void operatorCallback(View view) {

        goToBack();

        switch (view.getId())
        {
            case R.id.bn_plus:
                operatorButtonUpdate("+");
                break;
            case R.id.bn_minus:
                operatorButtonUpdate("-");
                break;
            case R.id.bn_mul:
                operatorButtonUpdate("*");
                break;
            case R.id.bn_div:
                operatorButtonUpdate("/");
                break;
        }
    }

    public void operatorButtonUpdate(String str)
    {
        if(     !lastCompileStr.endsWith("*") &&
                !lastCompileStr.endsWith("+") &&
                !lastCompileStr.endsWith("-") &&
                !lastCompileStr.endsWith("/") &&
                !lastCompileStr.endsWith(".")
          )
        {
            if (justEqualPresses)
            {
                lastCompileStr = outStr;
                calculateStr = outStr;
                justEqualPresses = false;
            }

            if (lastCompileStr.equals(""))
            {
                lastCompileStr = "0";
            }

            if(calculateStr.equals(""))
            {
                calculateStr = "0";
            }

            lastCompileStr = lastCompileStr.concat(str);
            calculateStr = calculateStr.concat(str);
            lastCompiled.setText(lastCompileStr);
            dummy = value;
            value = "";
            pointTaken = false;
        }

    }

    public void buttonPercentage(View view) {

        double val = 0.0;
        int i = 0;

        justEqualPresses = false;

        if(     !lastCompileStr.endsWith("*") &&
                !lastCompileStr.endsWith("+") &&
                !lastCompileStr.endsWith("-") &&
                !lastCompileStr.endsWith("/") &&
                lastCompileStr.length()>0 )
        {

            pointTaken = false;

            if(!value.equals(""))
                dummy = value;
            else
                value = dummy;

            if (value.endsWith("."))
            {
                value = value.concat("0");
            }

            val = Double.parseDouble(value);

            val = val / 100.0;

            for (i= calculateStr.length()-1; i>=0; i--)
            {
                if (    calculateStr.charAt(i) == '+' ||
                        calculateStr.charAt(i) == '-' ||
                        calculateStr.charAt(i) == '*' ||
                        calculateStr.charAt(i) == '/' )
                {
                    break;
                }
            }

            NumberFormat formatter = new DecimalFormat("#0.000000000000000000000000000000");
            String dm = formatter.format(val);
            calculateStr = calculateStr.substring(0,i+1) + dm;

            Length = dm.length();

            lastCompileStr = lastCompileStr.concat("%");

            lastCompiled.setText(lastCompileStr);

            value = dm;

        }

    }

    public void buttonDot(View view) {

        goToBack();
        String str = ".";
        justEqualPresses = false;
        if (!pointTaken && !lastCompileStr.endsWith("%")) {

            justEqualPresses = false;

            if(
                    lastCompileStr.equals("")  ||
                    lastCompileStr.endsWith("+") ||
                    lastCompileStr.endsWith("-") ||
                    lastCompileStr.endsWith("*") ||
                    lastCompileStr.endsWith("/") ||
                    lastCompileStr.endsWith("%") )
            {
                str = "0.";
            }

            lastCompileStr = lastCompileStr.concat(str);
            lastCompiled.setText(lastCompileStr);
            calculateStr = calculateStr.concat(str);
            value = value.concat(str);

            pointTaken = true;
        }

    }

    public void buttonEqual(View view) {

        String input = calculateStr;

        if (    input.endsWith("+") ||
                input.endsWith("-") ||
                input.endsWith("/") ||
                input.endsWith("*") ||
				input.equals("") 
                )
        {
            //answerView.setText("Syntax Error");
            return;
        }

        if(     input.startsWith("*") ||
                input.startsWith("/") ||
                input.startsWith("-") ||
                input.startsWith("+")
          )
        {
            input = "0" + input;
        }

        String Result = ""+EvaluateExp.evaluateMathExp(input);

        outStr = Result;

        String[] res = outStr.split(Pattern.quote("."));

        int deci = 0;

        try
        {
            deci = Integer.parseInt(res[1]);
        }
        catch (Exception e)
        {
            deci = 1;
        }
        if (deci == 0) {
            outStr = res[0];
        }


        //answerView.setText(outStr);

/*
      LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                3.0f
        );

        lastCompiled.setLayoutParams(param1);

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f
        );

        resultView.setLayoutParams(param);
        lastCompiled.setLayoutParams(param1);
        resultView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.resultSize));
        lastCompiled.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.resultSize));
*/

        resultView.setText(lastCompiled.getText().toString());
        lastCompiled.setText(outStr);
        lastCompiled.setTextColor(this.getResources().getColor(R.color.answerclr));

        Calendar cal = Calendar.getInstance();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        SimpleDateFormat sf = new SimpleDateFormat(" dd MMMM yyyy HH:mm:ss");

        hisList.add(new MyData(lastCompileStr+"=",outStr,sf.format(cal.getTime()),sdf.format(cal.getTime())));
        //aAdapter.notifyDataSetChanged();

        //historyList.add(lastCompileStr + "  = " + outStr);
        //arrayAdapter.notifyDataSetChanged();

        SharedPreferences sharedPreferences =
                getApplicationContext().getSharedPreferences("calculator.com.myCalc",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = "";
        historyList.clear();
        for (int i=0;i < hisList.size(); i++)
        {
            json = gson.toJson(hisList.get(i));
            historyList.add(json);
        }
        HashSet<String> set = new HashSet<String>(historyList);
        sharedPreferences.edit().putStringSet("notes",set).apply();
        sharedPreferences.edit().commit();

        //arrayAdapter.insert(lastCompileStr + " = " + outStr, 0);

        lastCompileStr = "";
        calculateStr = "";
        value = "";
        pointTaken = false;
        justEqualPresses = true;

    }

    public void buttonDelete(View view) {

        justEqualPresses = false;

        if (lastCompileStr.endsWith("."))
        {
            pointTaken = false;
        }

        if ( lastCompileStr.endsWith("%"))
        {
            calculateStr = calculateStr.substring(0,calculateStr.length() - Length);
            calculateStr = calculateStr.concat(dummy);
            //dummy = "";
        }
        else
        {
            if ( (calculateStr != null) && (calculateStr.length()>0) )
            {
                calculateStr = calculateStr.substring(0, calculateStr.length()-1);
            }
        }

        if ( (lastCompileStr != null) && (lastCompileStr.length()>0) )
        {
            lastCompileStr = lastCompileStr.substring(0, lastCompileStr.length()-1);

            if(lastCompileStr.endsWith("%"))
            {
                double d = Double.parseDouble(dummy)*100.0;
                NumberFormat formatter = new DecimalFormat("#0.000000000000000000000000000000");
                dummy = formatter.format(d);

            }
            else
            {
                dummy = "";
            }

            lastCompiled.setText(lastCompileStr);

        }
    }

    public void goToBack()
    {
        resultView.setText("");
        //lastCompiled.setText("");
        lastCompiled.setTextColor(this.getResources().getColor(R.color.black));

    }

    public void allClear(View view) {

        if(justEqualPresses == false)
        {
            lastCompileStr = "";
            calculateStr = "";
        }
        resultView.setText("");
        lastCompiled.setText("");
        pointTaken = false;
        value = "";

    }


    private void findPower(int i) {
        goToBack();

        if(lastCompileStr.endsWith("%"))
        {
            return;
        }

        justEqualPresses = false;

        //lastCompileStr = lastCompileStr.concat("("+i);
        //lastCompiled.setText(lastCompileStr);
        //calculateStr = calculateStr.concat(str);
        //value = value.concat(str);


    }
}
