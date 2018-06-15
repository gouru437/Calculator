package calculator.com.mycalculator;

import android.support.annotation.NonNull;

public class MyData implements Comparable{

    private String input;
    private String result;
    private String d;
    private String dat;

    MyData(String i, String r, String d, String d1)
    {
        input = i;
        result = r;
        this.d = d;
        dat = d1;
    }

    void setInput(String i)
    {
        input = i;
    }

    void setResult(String r)
    {
        result = r;
    }

    void setD(String dt)
    {
        d = dt;
    }

    String getInput()
    {
        return input;
    }

    String getResult()
    {
        return result;
    }

    String getD()
    {
        return d;
    }

    String getDat()
    {
        return dat;
    }


    @Override
    public int compareTo(@NonNull Object o) {
        String date = ((MyData)o).getDat();

        return date.compareTo(this.dat);
    }
}
