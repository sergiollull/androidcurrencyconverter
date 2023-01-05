package com.example.exchangeme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView newValue;
    private EditText enteredValue;
    private Spinner targetCurrency;
    private Spinner chosenCurrency;
    private TextView isconnected;
    private TextView resultV;
    private AppCompatButton appCompatButton;
    private ArrayList<String> arrayList;
    public boolean hasInt;
    private ArrayList<String> arrayList2;
    private HashMap<String, Float> hashMap;
    private boolean gbpToF = false, usdToF= false, tryToF= false,eurToF= false,chfToF= false;
    private boolean gbpFromF=false, usdFromF=false, tryFromF=false,eurFromF=false,chfFromF=false;
    Float gbpTo, usdTo, tryTo,eurTo,chfTo = 0.f;
    Float gbpFrom, usdFrom, tryFrom,eurFrom,chfFrom = 0.f;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newValue = findViewById(R.id.newValue);
        resultV = findViewById(R.id.result);
        isconnected = findViewById(R.id.isconnected);
        enteredValue = findViewById(R.id.enteredValue);
        appCompatButton= findViewById(R.id.appCompatButton);
        targetCurrency = findViewById(R.id.targetCurrency);
        chosenCurrency = findViewById(R.id.chosenCurrency);
        arrayList = new ArrayList<>();
        arrayList2 = new ArrayList<>();
        hashMap = new HashMap<>();
        String [] items = new String[]{"GBP", "USD","TRY","EUR","CHF"};
        String [] items1 = new String[]{"GBP", "USD","TRY","EUR","CHF"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        targetCurrency.setAdapter(adapter);
        chosenCurrency.setAdapter(adapter2);
        targetCurrency.setOnItemSelectedListener(this);
        //chosenCurrency.setOnItemSelectedListener(new Extraadapter(hashMap));

        if( checkNetworkConnection()){
            hasInt = true;
            new HTTPAsyncTask().execute("https://api.exchangerate.host/latest?format=xml");
        } else
            hasInt = false;

    }

    public boolean checkNetworkConnection() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        boolean isConnected = false;
        if (networkInfo != null && (isConnected = networkInfo.isConnected())) {
            isconnected.setText("Values Current !");
            isconnected.setBackgroundColor(0xFF7CCC26);

        } else {
            isconnected.setText("Values not Current !");
            isconnected.setBackgroundColor(0xFFFF0000);
        }
        return isConnected;
    }


    public void convert(View view) {
        if (hasInt) {
            String val = enteredValue.getText().toString();
            if (!Double.isNaN(Double.parseDouble(val)) && (!val.equals("") || !val.equals(";") || !val.equals(".") || !val.equals("!") || !val.equals(","))) {
                float value = Float.parseFloat(val);
                chosenCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                        switch (position) {
                            case 0:
                                Log.v("item", (String) adapterView.getItemAtPosition(position));
                                gbpFrom = hashMap.get("GBP");
                                gbpFromF = true;
                                usdFromF = false;
                                tryFromF = false;
                                eurFromF = false;
                                chfFromF = false;
                                break;

                            case 1:
                                usdFrom = hashMap.get("USD");
                                usdFromF = true;
                                gbpFromF = false;
                                tryFromF = false;
                                eurFromF = false;
                                chfFromF = false;
                                break;
                            case 2:
                                tryFrom = hashMap.get("TRY");
                                tryFromF = true;
                                usdFromF = false;
                                gbpFromF = false;
                                eurFromF = false;
                                chfFromF = false;
                                break;
                            case 3:
                                eurFrom = hashMap.get("EUR");
                                eurFromF = true;
                                usdFromF = false;
                                tryFromF = false;
                                gbpFromF = false;
                                chfFromF = false;
                                break;
                            case 4:
                                chfFrom = hashMap.get("CHF");
                                chfFromF = true;
                                usdFromF = false;
                                tryFromF = false;
                                eurFromF = false;
                                gbpFromF = false;
                                break;

                        }

                    }


                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                targetCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                gbpTo = hashMap.get("GBP");
                                gbpToF = true;
                                usdToF = false;
                                tryToF = false;
                                eurToF = false;
                                chfToF = false;
                                break;

                            case 1:
                                usdTo = hashMap.get("USD");
                                usdToF = true;
                                gbpToF = false;
                                tryToF = false;
                                eurToF = false;
                                chfToF = false;
                                break;
                            case 2:
                                tryTo = hashMap.get("TRY");
                                tryToF = true;
                                usdToF = false;
                                gbpToF = false;
                                eurToF = false;
                                chfToF = false;
                                break;
                            case 3:
                                eurTo = hashMap.get("EUR");
                                eurToF = true;
                                usdToF = false;
                                tryToF = false;
                                gbpToF = false;
                                chfToF = false;
                                break;
                            case 4:
                                chfTo = hashMap.get("CHF");
                                chfToF = true;
                                usdToF = false;
                                tryToF = false;
                                eurToF = false;
                                gbpToF = false;
                                break;

                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                if (chfToF && chfFromF) {
                    float lastResult=(value * chfTo) / chfFrom;
                    resultV.setText(String.valueOf(lastResult));
                } else if (chfToF && usdFromF) {
                    float lastResult=(value * chfTo) / usdFrom;
                    resultV.setText(String.valueOf(lastResult));
                } else if (chfToF && eurFromF) {
                    float lastResult=(value * chfTo) / eurFrom;
                    resultV.setText(String.valueOf(lastResult));
                } else if (chfToF && tryFromF) {
                    float lastResult=(value * chfTo) / tryFrom;
                    resultV.setText(String.valueOf(lastResult));
                } else if (chfToF && gbpFromF) {
                    float lastResult=(value * chfTo) / gbpFrom;
                    resultV.setText(String.valueOf(lastResult));
                } else if (usdToF && usdFromF) {
                    float lastResult=(value * usdTo) / usdFrom;
                    resultV.setText(String.valueOf(lastResult));
                } else if (usdToF && chfFromF) {
                    float lastResult=(value * usdTo) / chfFrom;
                    resultV.setText(String.valueOf(lastResult));
                } else if (usdToF && eurFromF) {
                    float lastResult=(value * usdTo) / eurFrom;
                    resultV.setText(String.valueOf(lastResult));
                } else if (usdToF && tryFromF) {
                    float lastResult=(value * usdTo) / tryFrom;
                    resultV.setText(String.valueOf(lastResult));
                } else if (usdToF && gbpFromF) {
                    float lastResult=(value * usdTo) / gbpFrom;
                    resultV.setText(String.valueOf(lastResult));
                } else if (eurToF && eurFromF) {
                    float lastResult=(value * eurTo) / eurFrom;
                    resultV.setText(String.valueOf(lastResult));
                } else if (eurToF && chfFromF) {
                    float lastResult=(value * eurTo) / chfFrom;
                    resultV.setText(String.valueOf(lastResult));
                } else if (eurToF && usdFromF) {
                    float lastResult=(value * eurTo) / usdFrom;
                    resultV.setText(String.valueOf(lastResult));
                } else if (eurToF && tryFromF) {
                    float lastResult=(value * eurTo) / tryFrom;
                    resultV.setText(String.valueOf(lastResult));
                } else if (eurToF && gbpFromF) {
                    float lastResult=(value * eurTo) / gbpFrom;
                    resultV.setText(String.valueOf(lastResult));
                } else if (tryToF && tryFromF) {
                    float lastResult=(value * tryTo) / tryFrom;
                    resultV.setText(String.valueOf(lastResult));
                } else if (tryToF && chfFromF) {
                    float lastResult=(value * tryTo) / chfFrom;
                    resultV.setText(String.valueOf(lastResult));
                } else if (tryToF && usdFromF) {
                    float lastResult=(value * tryTo) / usdFrom;
                    resultV.setText(String.valueOf(lastResult));
                } else if (tryToF && eurFromF) {
                    float lastResult=(value * tryTo) / eurFrom;
                    resultV.setText(String.valueOf(lastResult));
                } else if (tryToF && gbpFromF) {
                    float lastResult=(value * tryTo) / gbpFrom;
                    resultV.setText(String.valueOf(lastResult));
                } else if (gbpToF && gbpFromF) {
                    float lastResult=(value * gbpTo) / gbpFrom;
                    resultV.setText(String.valueOf(lastResult));
                } else if (gbpToF && chfFromF) {
                    float lastResult=(value * gbpTo) / chfFrom;
                    resultV.setText(String.valueOf(lastResult));
                } else if (gbpToF && usdFromF) {
                    float lastResult=(value * gbpTo) / usdFrom;
                    resultV.setText(String.valueOf(lastResult));
                } else if (gbpToF && tryFromF) {
                    float lastResult=(value * gbpTo) / tryFrom;
                    resultV.setText(String.valueOf(lastResult));
                } else if (gbpToF && eurFromF) {
                    float lastResult=(value * gbpTo) / eurFrom;
                    resultV.setText(String.valueOf(lastResult));
                }
            }
        }
    }
        private class HTTPAsyncTask extends AsyncTask<String,Void,String>{

            @Override
            protected String doInBackground(String... strings) {
                try {
                    return HttpGet(strings[0]);
                }catch (IOException exception){
                    return "Something went wrong. Please check the url and internet connection.";
                }
            }
        private String HttpGet(String string) throws IOException {
            InputStream inputStream = null;
            String result = "";
            URL url = new URL(string);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            inputStream = conn.getInputStream();
            if(inputStream != null) result = convertInputStreamToString(inputStream);
            else result = "Did not work";
            Log.d("HttpGet", result);
            System.out.println(result);
            newValue.setText(result);
            return result;

        }

        private String convertInputStreamToString(InputStream inputStream) throws IOException{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line  = "";
            String result = "";
            while((line = bufferedReader.readLine()) != null){
                result += line;
            }
            inputStream.close();
            return result;
        }


        public void XMLParser(String result) throws XmlPullParserException, IOException {
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(false);
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(new StringReader(result));
            int eventType = xmlPullParser.getEventType();
            String newResult = "";
            String tag = "";
            while(eventType != XmlPullParser.END_DOCUMENT){
                if(eventType == XmlPullParser.START_DOCUMENT){
                    newResult = "This IP Belongs to";
                } else if(eventType == XmlPullParser.END_DOCUMENT){

                } else if(eventType == XmlPullParser.START_TAG){
                    tag = xmlPullParser.getName();
                } else if(eventType == XmlPullParser.END_TAG){

                } else if(eventType==XmlPullParser.TEXT){
                    if(tag.equals("rate")){
                        arrayList.add(xmlPullParser.getText());
                        newResult = newResult + System.getProperty("line.separator") + tag + ": " + xmlPullParser.getText();
                        tag = "";
                    }
                    else if(tag.equals("code")){
                        arrayList2.add(xmlPullParser.getText());
                        tag = "";
                    }
                }
                eventType = xmlPullParser.next();
            }
           // newValue.setText("newResult");
            String temp= "";
            for(int i=0; i<arrayList.size(); i++){
                hashMap.put(arrayList2.get(i), Float.valueOf(arrayList.get(i)));
            }
            for(Map.Entry<String, Float> entry: hashMap.entrySet()){
                Log.d("HASHMAPPP", entry.getKey() + ": " + entry.getValue());
            }
            newValue.setText(temp);
        }
        protected void onPostExecute(String s){
            try {
                XMLParser(s);
            }catch (XmlPullParserException | IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}