package com.vaibhav.alakh;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Step2 extends AppCompatActivity {
    CircularImageView imageView;
    private String filePath = null;
    EditText nameET, addressET, cityET, wardET, assemblyET, occupationET;
    Button nameFAB, addressFAB, wardFAB, occupationFAB, sadasyataFAB, pgpFAB, asangathitFAB, asangathitSkipFAB, entrepreneurshipFAB, entrepreneurshipSkipFAB, submitFAB, secondLastFAB, secondLastSkipFAB, lastFAB;
    CardView nameCV, addressCV, wardCV, occupationCV, sadasyataCV, pgpCV, asangathitConditionCV, asangathitCV, entrepreneurshipCV, secondLastCV, lastCV;
    int errorColor;
    String name, mobile, address, city, ward, assembly, occupation, referred_by, pgp, final_pgp, asangathit_temp, asangathit_op, entrepreneurship, final_entrepreneurship, secondlast_temp, secondlast, last_temp, last;
    TextView mobiletv, referredTV;
    long totalSize = 0;
    ImageView backButton;
    private ProgressBar progressBar;
    private TextView txtPercentage;
    // LogCat tag
    private static final String TAG = Step2.class.getSimpleName();

    RadioGroup selectGroupOne, selectGrouptwo, selectGroupfour, selectGroupfive, selectGroupsix, selectGroupseven;
    RadioButton selectOne, selectTwo, selectFour, selectFive, selectSix, selectSeven;

    CheckBox asangathitCheckBox, entrepreneurshipCheckBox;
    CheckBox pgp_op1,pgp_op2,pgp_op3,pgp_op4,pgp_op5,pgp_op6,pgp_op7;

    String pgp1,pgp2,pgp3,pgp4,pgp5,pgp6,pgp7;
    Button occ_op1, occ_op2, occ_op3, occ_op4, occ_op5, occ_op6, occ_op7, occ_op8, occ_op9, occ_op10, occ_op11, occ_op12, asang_cond1, asang_cond2;

    Spinner sp1, sp2;

    Button addressBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step2);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        //Code for Error
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {

            errorColor = ContextCompat.getColor(getApplicationContext(), R.color.errorColor);
        } else {
            errorColor = getResources().getColor(R.color.errorColor);
        }

        // Receiving the data from previous activity
        Intent i = getIntent();

        imageView = findViewById(R.id.step2_member_image_view);

        // image or video path that is captured in previous activity
        filePath = i.getStringExtra("filePath");


        // boolean flag to identify the media type, image or video
        boolean isImage = i.getBooleanExtra("isImage", true);

        if (filePath != null) {
            // Displaying the image or video on the screen
            previewMedia(isImage);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Sorry, file path is missing!", Toast.LENGTH_LONG).show();
        }
        
        



        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        mobile = sharedPreferences.getString(Config.MOBILE_REG_SP, "");

        referred_by = sharedPreferences.getString(Config.MID_SHARED_PREF, "");


        mobiletv = findViewById(R.id.step2_mobile_tv);
        mobiletv.setText(mobile);

        

        nameET = findViewById(R.id.step2_name_et);
        addressET = findViewById(R.id.step2_address_et);
        cityET = findViewById(R.id.step2_city_et);
        //wardET = findViewById(R.id.step2_ward_et);
        //assemblyET = findViewById(R.id.step2_assembly_et);
        occupationET = findViewById(R.id.step2_occupation_et);

        nameFAB = findViewById(R.id.fab_step2_name);
        addressFAB = findViewById(R.id.fab_step2_address);
        wardFAB = findViewById(R.id.fab_step2_ward);
        occupationFAB = findViewById(R.id.fab_step2_occupation);
//        pgpFAB = findViewById(R.id.fab_step2_pgp);
//        asangathitFAB = findViewById(R.id.fab_step2_asangathit);
//        //asangathitSkipFAB = findViewById(R.id.fab_step2_asangathit_skip);
//        entrepreneurshipFAB = findViewById(R.id.fab_step2_entrepreneurship);
//        entrepreneurshipSkipFAB = findViewById(R.id.fab_step2_entrepreneurship_skip);
//        secondLastFAB = findViewById(R.id.fab_step2_secondlast);
//        secondLastSkipFAB = findViewById(R.id.fab_step2_secondlast_skip);
//        lastFAB = findViewById(R.id.fab_step2_last);
        submitFAB = findViewById(R.id.fab_step2_final_submit);

        addressBackButton = findViewById(R.id.fab_step2_address_back);


        nameCV = findViewById(R.id.step2_name_card_view);
        addressCV = findViewById(R.id.step2_address_card_view);
        wardCV = findViewById(R.id.step2_assembly_card_view);
        occupationCV = findViewById(R.id.step2_occuption_card_view);
//        pgpCV = findViewById(R.id.step2_pgp_options_card);
//        asangathitConditionCV = findViewById(R.id.step2_asangathit_condition_card_view);
//        asangathitCV = findViewById(R.id.step2_asangathit_options_card);
//        entrepreneurshipCV = findViewById(R.id.step2_entrepreneurship_options_card);
//        secondLastCV = findViewById(R.id.step2_second_last_options_card);
//        lastCV = findViewById(R.id.step2_last_options_card);

        progressBar = findViewById(R.id.step2_progress_bar);
        txtPercentage = findViewById(R.id.step2_text_percentage);

        sp1 = findViewById(R.id.spinner1);
        sp2 = findViewById(R.id.spinner2);


        occ_op1 = findViewById(R.id.occupation_op1);
        occ_op2 = findViewById(R.id.occupation_op2);
        occ_op3 = findViewById(R.id.occupation_op3);
        occ_op4 = findViewById(R.id.occupation_op4);
        occ_op5 = findViewById(R.id.occupation_op5);
        occ_op6 = findViewById(R.id.occupation_op6);
        occ_op7 = findViewById(R.id.occupation_op7);
        occ_op8 = findViewById(R.id.occupation_op8);
        occ_op9 = findViewById(R.id.occupation_op9);
        occ_op10 = findViewById(R.id.occupation_op10);
        occ_op11 = findViewById(R.id.occupation_op11);
        occ_op12 = findViewById(R.id.occupation_op12);

//        pgp_op1 = findViewById(R.id.step2_pgp_op1);
//        pgp_op2 = findViewById(R.id.step2_pgp_op2);
//        pgp_op3 = findViewById(R.id.step2_pgp_op3);
//        pgp_op4 = findViewById(R.id.step2_pgp_op4);
//        pgp_op5 = findViewById(R.id.step2_pgp_op5);
//        pgp_op6 = findViewById(R.id.step2_pgp_op6);
//        pgp_op7 = findViewById(R.id.step2_pgp_op7);
//
//
//        asang_cond1 = findViewById(R.id.asangathit_condition_op1);
//        asang_cond2 = findViewById(R.id.asangathit_condition_op2);
        //Radio Group for PGP Options
        //selectGrouptwo = findViewById(R.id.step2_radio_group_two);

//        selectGroupfour = findViewById(R.id.step2_radio_group_four);
//
//        //Asangathit Union
//        selectGroupfive = findViewById(R.id.step2_radio_group_five);
//
//        //Second Last
//        selectGroupsix = findViewById(R.id.step2_radio_group_six);
//
//        //Last
//        selectGroupseven = findViewById(R.id.step2_radio_group_seven);

//        asangathitCheckBox = findViewById(R.id.step2_asangathit_checkbox);
//        entrepreneurshipCheckBox = findViewById(R.id.step2_entrepreneurship_checkbox);

        backButton = findViewById(R.id.step2_back_button_image);

//        //Getting the instance of Spinner and applying OnItemSelectedListener on it
//        Spinner spin = (Spinner) findViewById(R.id.ward_spinner);
//
//        //Creating the ArrayAdapter instance having the country list
//        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,ward_string);
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        //Setting the ArrayAdapter data on the Spinner
//        spin.setAdapter(aa);


        //The Top Left Back Button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //Name Next Button
        nameFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validateName()) {
                    return;
                }

                nameCV.setVisibility(View.GONE);
                addressCV.setVisibility(View.VISIBLE);
                nameFAB.setVisibility(View.GONE);
                addressFAB.setVisibility(View.VISIBLE);
                addressBackButton.setVisibility(View.VISIBLE);
            }
        });

        //Address Next Button
        addressFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validateAddress()) {
                    return;
                }
                if (!validateCity()) {
                    return;
                }

                addressCV.setVisibility(View.GONE);
                wardCV.setVisibility(View.VISIBLE);
                addressFAB.setVisibility(View.GONE);
                addressBackButton.setVisibility(View.GONE);
                wardFAB.setVisibility(View.VISIBLE);
            }
        });

        //Address Back Button
        addressBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addressCV.setVisibility(View.GONE);
                addressFAB.setVisibility(View.GONE);
                addressBackButton.setVisibility(View.GONE);
                nameCV.setVisibility(View.VISIBLE);
                nameFAB.setVisibility(View.VISIBLE);
            }
        });

        //Item Selected for Lok-Sabha Spinner (sp1)
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String s1 = String.valueOf(sp1.getSelectedItem());

                if (s1.contentEquals("Ajmer")) {
                    Toast.makeText(Step2.this, s1, Toast.LENGTH_SHORT).show();
                    assembly = s1;

                    List<String> list = new ArrayList<String>();
                    list.add("Ajmer North");
                    list.add("Ajmer South");
                    list.add("Dudu");
                    list.add("Kishangarh");
                    list.add("Kekri");
                    list.add("Masuda");
                    list.add("Nasirabad");
                    list.add("Pushkar");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Step2.this,
                            R.layout.spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter.notifyDataSetChanged();
                    sp2.setAdapter(dataAdapter);
                }

                if (s1.contentEquals("Alwar")) {
                    Toast.makeText(Step2.this, s1, Toast.LENGTH_SHORT).show();
                    assembly = s1;
                    List<String> list = new ArrayList<String>();
                    list.add("Alwar Rural");
                    list.add("Alwar Urban");
                    list.add("Bansure");
                    list.add("Behror");
                    list.add("Kathumar");
                    list.add("Kishangarh Bas");
                    list.add("Mundawar");
                    list.add("Rajgarh Laxmangarh");
                    list.add("Ramgarh");
                    list.add("Thanagazi");
                    list.add("Tijara");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Step2.this,
                            R.layout.spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter.notifyDataSetChanged();
                    sp2.setAdapter(dataAdapter);
                }

                if (s1.contentEquals("Banswara")) {
                    Toast.makeText(Step2.this, s1, Toast.LENGTH_SHORT).show();
                    assembly = s1;
                    List<String> list = new ArrayList<String>();
                    list.add("Bagidora");
                    list.add("Banswara");
                    list.add("Chorasi");
                    list.add("Dungarpur");
                    list.add("Ghari");
                    list.add("Ghatol");
                    list.add("Kushalgarh");
                    list.add("Sagwara");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Step2.this,
                            R.layout.spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter.notifyDataSetChanged();
                    sp2.setAdapter(dataAdapter);
                }

                if (s1.contentEquals("Barmer")) {
                    Toast.makeText(Step2.this, s1, Toast.LENGTH_SHORT).show();
                    assembly = s1;
                    List<String> list = new ArrayList<String>();
                    list.add("Barmer");
                    list.add("Baytoo");
                    list.add("Chohtan");
                    list.add("Gudha Malani");
                    list.add("Jaisalmer");
                    list.add("Pachpadra");
                    list.add("Sheo");
                    list.add("Siwana");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Step2.this,
                            R.layout.spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter.notifyDataSetChanged();
                    sp2.setAdapter(dataAdapter);
                }

                if (s1.contentEquals("Bharatpur")) {
                    Toast.makeText(Step2.this, s1, Toast.LENGTH_SHORT).show();
                    assembly = s1;
                    List<String> list = new ArrayList<String>();
                    list.add("Bayana Rupwas");
                    list.add("Bharatpur");
                    list.add("Deeg-Kumher");
                    list.add("Kaman");
                    list.add("Nadbai");
                    list.add("Nagar");
                    list.add("Weir");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Step2.this,
                            R.layout.spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter.notifyDataSetChanged();
                    sp2.setAdapter(dataAdapter);
                }

                if (s1.contentEquals("Bhilwara")) {
                    Toast.makeText(Step2.this, s1, Toast.LENGTH_SHORT).show();
                    assembly = s1;
                    List<String> list = new ArrayList<String>();
                    list.add("Asind");
                    list.add("Bhilwara");
                    list.add("Hindoli");
                    list.add("Jahazpur");
                    list.add("Mandal");
                    list.add("Mandalgarh");
                    list.add("Sahara");
                    list.add("Shahpura");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Step2.this,
                            R.layout.spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter.notifyDataSetChanged();
                    sp2.setAdapter(dataAdapter);
                }

                if (s1.contentEquals("Bikaner")) {
                    Toast.makeText(Step2.this, s1, Toast.LENGTH_SHORT).show();
                    assembly = s1;
                    List<String> list = new ArrayList<String>();
                    list.add("Anupgarh");
                    list.add("Bikaner East");
                    list.add("Bikaner West");
                    list.add("Dungargarh");
                    list.add("Khajuwala");
                    list.add("Kolayat");
                    list.add("Lunkaransar");
                    list.add("Nokha");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Step2.this,
                            R.layout.spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter.notifyDataSetChanged();
                    sp2.setAdapter(dataAdapter);
                }

                if (s1.contentEquals("Chittorgarh")) {
                    Toast.makeText(Step2.this, s1, Toast.LENGTH_SHORT).show();
                    assembly = s1;
                    List<String> list = new ArrayList<String>();
                    list.add("Bari Sadri");
                    list.add("Begun");
                    list.add("Chittorgarh");
                    list.add("Kapasan");
                    list.add("Mavli");
                    list.add("Nimbhahera");
                    list.add("Pratapgarh");
                    list.add("Vallabhnagar");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Step2.this,
                            R.layout.spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter.notifyDataSetChanged();
                    sp2.setAdapter(dataAdapter);
                }

                if (s1.contentEquals("Churu")) {
                    Toast.makeText(Step2.this, s1, Toast.LENGTH_SHORT).show();
                    assembly = s1;
                    List<String> list = new ArrayList<String>();
                    list.add("Bhadra");
                    list.add("Churu");
                    list.add("Nohar");
                    list.add("Ratangarh");
                    list.add("Sadulpur");
                    list.add("Sardarshahar");
                    list.add("Sujangarh");
                    list.add("Taranagar");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Step2.this,
                            R.layout.spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter.notifyDataSetChanged();
                    sp2.setAdapter(dataAdapter);
                }

                if (s1.contentEquals("Dausa")) {
                    Toast.makeText(Step2.this, s1, Toast.LENGTH_SHORT).show();
                    assembly = s1;
                    List<String> list = new ArrayList<String>();
                    list.add("Bandikui");
                    list.add("Bassi");
                    list.add("Chaksu");
                    list.add("Dausa");
                    list.add("Lalsot");
                    list.add("Mahuwa");
                    list.add("Sikrai");
                    list.add("Thanagazi");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Step2.this,
                            R.layout.spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter.notifyDataSetChanged();
                    sp2.setAdapter(dataAdapter);
                }

                if (s1.contentEquals("Ganganagar")) {
                    Toast.makeText(Step2.this, s1, Toast.LENGTH_SHORT).show();
                    assembly = s1;
                    List<String> list = new ArrayList<String>();
                    list.add("Hanumangarh");
                    list.add("Karanpur");
                    list.add("Pilibanga");
                    list.add("Raisinghnagar");
                    list.add("Sadulshahar");
                    list.add("Sangaria");
                    list.add("Sri Ganganagar");
                    list.add("Suratgarh");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Step2.this,
                            R.layout.spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter.notifyDataSetChanged();
                    sp2.setAdapter(dataAdapter);
                }

                if (s1.contentEquals("Jaipur")) {
                    Toast.makeText(Step2.this, s1, Toast.LENGTH_SHORT).show();
                    assembly = s1;
                    List<String> list = new ArrayList<String>();
                    list.add("Adarsh Nagar");
                    list.add("Bagru");
                    list.add("Civil Lines");
                    list.add("Hawa Mahal");
                    list.add("Jhotwara");
                    list.add("Kishanpole");
                    list.add("Malviya Nagar");
                    list.add("Sanganer");
                    list.add("Vidhyadhar Nagar");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Step2.this,
                            R.layout.spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter.notifyDataSetChanged();
                    sp2.setAdapter(dataAdapter);
                }

                if (s1.contentEquals("Jaipur Rural")) {
                    Toast.makeText(Step2.this, s1, Toast.LENGTH_SHORT).show();
                    assembly = s1;
                    List<String> list = new ArrayList<String>();
                    list.add("Amber");
                    list.add("Bansur");
                    list.add("Jamwa Ramgarh");
                    list.add("Jhotwara");
                    list.add("Kotputli");
                    list.add("Phulera");
                    list.add("Shahpura");
                    list.add("Viratnagar");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Step2.this,
                            R.layout.spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter.notifyDataSetChanged();
                    sp2.setAdapter(dataAdapter);
                }

                if (s1.contentEquals("Jalore")) {
                    Toast.makeText(Step2.this, s1, Toast.LENGTH_SHORT).show();
                    assembly = s1;
                    List<String> list = new ArrayList<String>();
                    list.add("Ahore");
                    list.add("Bhinman");
                    list.add("Jalore");
                    list.add("Pindwara Abu");
                    list.add("Raniwara");
                    list.add("Reodar");
                    list.add("Sanchore");
                    list.add("Sirohi");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Step2.this,
                            R.layout.spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter.notifyDataSetChanged();
                    sp2.setAdapter(dataAdapter);
                }

                if (s1.contentEquals("Jhalawar")) {
                    Toast.makeText(Step2.this, s1, Toast.LENGTH_SHORT).show();
                    assembly = s1;
                    List<String> list = new ArrayList<String>();
                    list.add("Atru");
                    list.add("Chhabra");
                    list.add("Dag");
                    list.add("Jhalrapatan");
                    list.add("Khanpur");
                    list.add("Kishanganj");
                    list.add("Manohar Thana");
                    list.add("Pirawa");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Step2.this,
                            R.layout.spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter.notifyDataSetChanged();
                    sp2.setAdapter(dataAdapter);
                }

                if (s1.contentEquals("Jhunjhunu")) {
                    Toast.makeText(Step2.this, s1, Toast.LENGTH_SHORT).show();
                    assembly = s1;
                    List<String> list = new ArrayList<String>();
                    list.add("Jhunjhunu");
                    list.add("Khetri");
                    list.add("Mandawa");
                    list.add("Nawalgarh");
                    list.add("Pilani");
                    list.add("Surajgarh");
                    list.add("Udaipurwati");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Step2.this,
                            R.layout.spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter.notifyDataSetChanged();
                    sp2.setAdapter(dataAdapter);
                }

                if (s1.contentEquals("Jodhpur")) {
                    Toast.makeText(Step2.this, s1, Toast.LENGTH_SHORT).show();
                    assembly = s1;
                    List<String> list = new ArrayList<String>();
                    list.add("Jodhpur City");
                    list.add("Lohawat");
                    list.add("Luni");
                    list.add("Phalodi");
                    list.add("Pokaran");
                    list.add("Surdarpura");
                    list.add("Shergarh");
                    list.add("Soorsagar");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Step2.this,
                            R.layout.spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter.notifyDataSetChanged();
                    sp2.setAdapter(dataAdapter);
                }

                if (s1.contentEquals("Karauli Dholpur")) {
                    Toast.makeText(Step2.this, s1, Toast.LENGTH_SHORT).show();
                    assembly = s1;
                    List<String> list = new ArrayList<String>();
                    list.add("Bari");
                    list.add("Baseri");
                    list.add("Dholpur");
                    list.add("Hindaun");
                    list.add("Karauli");
                    list.add("Rajakhera");
                    list.add("Sapotra");
                    list.add("Todabhim");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Step2.this,
                            R.layout.spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter.notifyDataSetChanged();
                    sp2.setAdapter(dataAdapter);
                }

                if (s1.contentEquals("Kota")) {
                    Toast.makeText(Step2.this, s1, Toast.LENGTH_SHORT).show();
                    assembly = s1;
                    List<String> list = new ArrayList<String>();
                    list.add("Bundi");
                    list.add("Keshoraipatan");
                    list.add("Kota North");
                    list.add("Kota South");
                    list.add("Ladpura");
                    list.add("Pipalda");
                    list.add("Ramganj Mandi");
                    list.add("Sangod");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Step2.this,
                            R.layout.spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter.notifyDataSetChanged();
                    sp2.setAdapter(dataAdapter);
                }

                if (s1.contentEquals("Nagaur")) {
                    Toast.makeText(Step2.this, s1, Toast.LENGTH_SHORT).show();
                    assembly = s1;
                    List<String> list = new ArrayList<String>();
                    list.add("Deedwana");
                    list.add("Jayal");
                    list.add("Khinvsar");
                    list.add("Ladnun");
                    list.add("Makrana");
                    list.add("Nagaur");
                    list.add("Nawan");
                    list.add("Parbatsar");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Step2.this,
                            R.layout.spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter.notifyDataSetChanged();
                    sp2.setAdapter(dataAdapter);
                }

                if (s1.contentEquals("Pali")) {
                    Toast.makeText(Step2.this, s1, Toast.LENGTH_SHORT).show();
                    assembly = s1;
                    List<String> list = new ArrayList<String>();
                    list.add("Bali");
                    list.add("Bhopalgarh");
                    list.add("Bilara");
                    list.add("Marwar Junction");
                    list.add("Osian");
                    list.add("Pali");
                    list.add("Sojat");
                    list.add("Sumerpur");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Step2.this,
                            R.layout.spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter.notifyDataSetChanged();
                    sp2.setAdapter(dataAdapter);
                }

                if (s1.contentEquals("Rajsamand")) {
                    Toast.makeText(Step2.this, s1, Toast.LENGTH_SHORT).show();
                    assembly = s1;
                    List<String> list = new ArrayList<String>();
                    list.add("Beawar");
                    list.add("Bhim");
                    list.add("Degana");
                    list.add("Jaitaran");
                    list.add("Kumbhalgarh");
                    list.add("Merta");
                    list.add("Nathdwara");
                    list.add("Rajsamand");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Step2.this,
                            R.layout.spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter.notifyDataSetChanged();
                    sp2.setAdapter(dataAdapter);
                }

                if (s1.contentEquals("Sikar")) {
                    Toast.makeText(Step2.this, s1, Toast.LENGTH_SHORT).show();
                    assembly = s1;
                    List<String> list = new ArrayList<String>();
                    list.add("Chomu");
                    list.add("Danta Ramgarh");
                    list.add("Dhod");
                    list.add("Khandela");
                    list.add("Lacchmangarh");
                    list.add("Neem ka Thana");
                    list.add("Sikar");
                    list.add("Sri Madhopur");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Step2.this,
                            R.layout.spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter.notifyDataSetChanged();
                    sp2.setAdapter(dataAdapter);
                }

                if (s1.contentEquals("Tonk Sawai Madhopur")) {
                    Toast.makeText(Step2.this, s1, Toast.LENGTH_SHORT).show();
                    assembly = s1;
                    List<String> list = new ArrayList<String>();
                    list.add("Bamanwas");
                    list.add("Deoli Uniara");
                    list.add("Gangapur City");
                    list.add("Khandar");
                    list.add("Malpura");
                    list.add("Niwai");
                    list.add("Sawai Madhopur");
                    list.add("Tonk");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Step2.this,
                            R.layout.spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter.notifyDataSetChanged();
                    sp2.setAdapter(dataAdapter);
                }

                if (s1.contentEquals("Udaipur")) {
                    Toast.makeText(Step2.this, s1, Toast.LENGTH_SHORT).show();
                    assembly = s1;
                    List<String> list = new ArrayList<String>();
                    list.add("Aspur");
                    list.add("Dhariawad");
                    list.add("Gogunda");
                    list.add("Jhadol");
                    list.add("Kherwara");
                    list.add("Udaipur");
                    list.add("Udaipur Rural");
                    list.add("Vallabhnagar");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Step2.this,
                            R.layout.spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter.notifyDataSetChanged();
                    sp2.setAdapter(dataAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });


        //Item Selected for Vidhan-Sabha Spinner (sp2)
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String s2 = String.valueOf(sp2.getSelectedItem());
                ward = s2;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });


        //Ward Next Button
        wardFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                wardCV.setVisibility(View.GONE);
                wardFAB.setVisibility(View.GONE);
                occupationCV.setVisibility(View.VISIBLE);
                //occupationFAB.setVisibility(View.VISIBLE);
            }
        });

        //Occupation Buttons 1-10
        occ_op1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                occupation = "Kisaan";
                occupationCV.setVisibility(View.GONE);
                occupationFAB.setVisibility(View.GONE);

                submitFAB.setVisibility(View.VISIBLE);

            }
        });

        occ_op2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                occupation = "Udyog";
                occupationCV.setVisibility(View.GONE);
                occupationFAB.setVisibility(View.GONE);

                submitFAB.setVisibility(View.VISIBLE);

            }
        });

        occ_op3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                occupation = "Dukandaar";
                occupationCV.setVisibility(View.GONE);
                occupationFAB.setVisibility(View.GONE);

                submitFAB.setVisibility(View.VISIBLE);

            }
        });

        occ_op4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                occupation = "Haat or Thadi";
                occupationCV.setVisibility(View.GONE);
                occupationFAB.setVisibility(View.GONE);

                submitFAB.setVisibility(View.VISIBLE);
            }
        });

        occ_op5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                occupation = "Naukri";
                occupationCV.setVisibility(View.GONE);
                occupationFAB.setVisibility(View.GONE);

                submitFAB.setVisibility(View.VISIBLE);

            }
        });

        occ_op6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                occupation = "Berozgaar or Grahini";
                occupationCV.setVisibility(View.GONE);
                occupationFAB.setVisibility(View.GONE);

                submitFAB.setVisibility(View.VISIBLE);
            }
        });

        occ_op7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                occupation = "Shramik";
                occupationCV.setVisibility(View.GONE);
                occupationFAB.setVisibility(View.GONE);

                submitFAB.setVisibility(View.VISIBLE);

            }
        });

        occ_op8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                occupation = "Dastkar or Shilpkar";
                occupationCV.setVisibility(View.GONE);
                occupationFAB.setVisibility(View.GONE);

                submitFAB.setVisibility(View.VISIBLE);

            }
        });

        occ_op9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                occupation = "Driver";
                occupationCV.setVisibility(View.GONE);
                occupationFAB.setVisibility(View.GONE);

                submitFAB.setVisibility(View.VISIBLE);
            }
        });

        occ_op10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                occupationET.setVisibility(View.VISIBLE);
                occupationFAB.setVisibility(View.VISIBLE);

            }
        });

        occ_op11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                occupation = "Peshewar";
                occupationCV.setVisibility(View.GONE);
                occupationFAB.setVisibility(View.GONE);

                submitFAB.setVisibility(View.VISIBLE);
            }
        });
        occ_op12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                occupation = "Politician or Samaj Sevi";
                occupationCV.setVisibility(View.GONE);
                occupationFAB.setVisibility(View.GONE);
//                pgpCV.setVisibility(View.VISIBLE);
//                pgpFAB.setVisibility(View.VISIBLE);

                submitFAB.setVisibility(View.VISIBLE);

            }
        });

        //Occupation Next Button
        occupationFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validateOccupation()) {
                    return;
                }

                occupation = occupationET.getText().toString();

                occupationCV.setVisibility(View.GONE);
                occupationFAB.setVisibility(View.GONE);
                occupationET.setVisibility(View.GONE);

                submitFAB.setVisibility(View.VISIBLE);

            }
        });

//        //PGP Next Button
//        pgpFAB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(pgp_op1.isChecked()){
//
//                    pgp1 = "Yes";
//                }
//                else {
//                    pgp1 = "No";
//                }
//
//                if(pgp_op2.isChecked()){
//
//                    pgp2 = "Yes";
//                }
//                else {
//                    pgp2 = "No";
//                }
//
//                if(pgp_op3.isChecked()){
//
//                    pgp3 = "Yes";
//                }
//                else {
//                    pgp3 = "No";
//                }
//
//                if(pgp_op4.isChecked()){
//
//                    pgp4 = "Yes";
//                }
//                else {
//                    pgp4 = "No";
//                }
//
//                if(pgp_op5.isChecked()){
//
//                    pgp5 = "Yes";
//                }
//                else {
//                    pgp5 = "No";
//                }
//
//                if(pgp_op6.isChecked()){
//
//                    pgp6 = "Yes";
//                }
//                else {
//                    pgp6 = "No";
//                }
//
//                if(pgp_op7.isChecked()){
//
//                    pgp7 = "Yes";
//                }
//                else {
//                    pgp7 = "No";
//                }
//
//                pgpCV.setVisibility(View.GONE);
//                pgpFAB.setVisibility(View.GONE);
//                asangathitConditionCV.setVisibility(View.VISIBLE);
//            }
//        });
//
//        asang_cond1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                asangathitConditionCV.setVisibility(View.GONE);
//
//                asangathitCV.setVisibility(View.VISIBLE);
//                asangathitFAB.setVisibility(View.VISIBLE);
//
//            }
//        });
//
//        asang_cond2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                asangathit_op = "No";
//                final_entrepreneurship = "No";
//                asangathitConditionCV.setVisibility(View.GONE);
//
//                secondLastCV.setVisibility(View.VISIBLE);
//                secondLastFAB.setVisibility(View.VISIBLE);
//                secondLastSkipFAB.setVisibility(View.VISIBLE);
//
//            }
//        });
//        //Asangathit Union Next Button
//        asangathitFAB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (selectGroupfive.getCheckedRadioButtonId() == -1) {
//                    Toast.makeText(Step2.this, "कृपया एक विकल्प चुनें", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    int selectedId = selectGroupfive.getCheckedRadioButtonId();
//                    // find the radiobutton by returned id
//                    selectFive = findViewById(selectedId);
//                    asangathit_temp = selectFive.getText().toString();
//
//                    switch (asangathit_temp) {
//
//                        case "राजस्थान वाहन चालक संगठन":
//                            asangathit_op = "Rajasthan Vaahan Chaalak Sangathan";
//                            final_entrepreneurship = "No";
//                            break;
//
//                        case "राजस्थान डिलीवरी लायंस":
//                            asangathit_op = "Rajasthan Delivery Lions";
//                            final_entrepreneurship = "No";
//                            break;
//
//                        case "इ-रिक्शा श्रमिक यूनियन":
//                            asangathit_op = "E Rickshaw Shramik Union";
//                            final_entrepreneurship = "No";
//                            break;
//
//                        case "हाट कारोबारी यूनियन":
//                            asangathit_op = "Haat Kaarobari Union";
//                            final_entrepreneurship = "No";
//                            break;
//
//                        default:
//                            asangathit_op = "No";
//                    }
//
//                    asangathitFAB.setVisibility(View.GONE);
//                    asangathitCV.setVisibility(View.GONE);
//
//                    pgpFAB.setVisibility(View.GONE);
//                    nameFAB.setVisibility(View.GONE);
//                    occupationFAB.setVisibility(View.GONE);
//                    wardFAB.setVisibility(View.GONE);
//                    addressFAB.setVisibility(View.GONE);
//
//                    secondLastCV.setVisibility(View.VISIBLE);
//                    secondLastFAB.setVisibility(View.VISIBLE);
//                    secondLastSkipFAB.setVisibility(View.VISIBLE);
//
//                }
//
//            }
//        });
//
////        //Asangathit Union Skip Button
////        asangathitSkipFAB.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////
////                asangathit_op = "No";
////                final_entrepreneurship = "No";
////
////                asangathitFAB.setVisibility(View.GONE);
////                asangathitSkipFAB.setVisibility(View.GONE);
////                asangathitCV.setVisibility(View.GONE);
////
////                pgpFAB.setVisibility(View.GONE);
////                nameFAB.setVisibility(View.GONE);
////                occupationFAB.setVisibility(View.GONE);
////                wardFAB.setVisibility(View.GONE);
////                addressFAB.setVisibility(View.GONE);
////
////
////                secondLastCV.setVisibility(View.VISIBLE);
////                secondLastFAB.setVisibility(View.VISIBLE);
////                secondLastSkipFAB.setVisibility(View.VISIBLE);
////
////            }
////        });
//
//
//        //Entrepreneurship Next Button
//        entrepreneurshipFAB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (selectGroupfour.getCheckedRadioButtonId() == -1) {
//                    Toast.makeText(Step2.this, "कृपया एक विकल्प चुनें", Toast.LENGTH_SHORT).show();
//                } else {
//                    int selectedId = selectGroupfour.getCheckedRadioButtonId();
//                    // find the radiobutton by returned id
//                    selectFour = findViewById(selectedId);
//                    entrepreneurship = selectFour.getText().toString();
//
//                    switch (entrepreneurship) {
//
//                        case "सस्ती दुकान":
//                            final_entrepreneurship = "Sasti Dukaan";
//                            asangathit_op = "No";
//                            break;
//
//                        case "माइक्रो फाइनेंस फॉर SHG":
//                            final_entrepreneurship = "Micro Finance for SHG";
//                            asangathit_op = "No";
//                            break;
//
//                        case "स्टार्ट अप्स":
//                            final_entrepreneurship = "Start Ups";
//                            asangathit_op = "No";
//                            break;
//
//                        default:
//                            final_entrepreneurship = "No";
//                            break;
//
//                    }
//
//                    entrepreneurshipFAB.setVisibility(View.GONE);
//                    entrepreneurshipCV.setVisibility(View.GONE);
//                    entrepreneurshipSkipFAB.setVisibility(View.GONE);
//                    asangathitSkipFAB.setVisibility(View.GONE);
//
//                    pgpFAB.setVisibility(View.GONE);
//                    nameFAB.setVisibility(View.GONE);
//                    occupationFAB.setVisibility(View.GONE);
//                    wardFAB.setVisibility(View.GONE);
//                    addressFAB.setVisibility(View.GONE);
//
//                    secondLastCV.setVisibility(View.VISIBLE);
//                    secondLastFAB.setVisibility(View.VISIBLE);
//                    secondLastSkipFAB.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });
//
//        secondLastFAB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (selectGroupsix.getCheckedRadioButtonId() == -1) {
//                    Toast.makeText(Step2.this, "कृपया एक विकल्प चुनें", Toast.LENGTH_SHORT).show();
//                } else {
//                    int selectedId = selectGroupsix.getCheckedRadioButtonId();
//                    // find the radiobutton by returned id
//                    selectSix = findViewById(selectedId);
//                    secondlast_temp = selectSix.getText().toString();
//
//                    switch (secondlast_temp) {
//
//                        case "मुझे खुद का नया व्यवसाय स्थापित करना है।":
//                            secondlast = "New Business";
//
//                            break;
//
//                        case "मेरे स्वयं के व्यवसाय को आगे बढ़ाना है।":
//                            secondlast = "Upgrade Business";
//
//                            break;
//
//                        case "मुझे दक्षता प्राप्त कर आत्म-निर्भर बनना है।":
//                            secondlast = "Self Dependent";
//
//                            break;
//
//                    }
//
//                    secondLastCV.setVisibility(View.GONE);
//                    secondLastFAB.setVisibility(View.GONE);
//                    secondLastSkipFAB.setVisibility(View.GONE);
//
//                    lastCV.setVisibility(View.VISIBLE);
//                    lastFAB.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });
//
//        secondLastSkipFAB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                secondlast = "No";
//                last = "No";
//
//                secondLastCV.setVisibility(View.GONE);
//                secondLastFAB.setVisibility(View.GONE);
//                secondLastSkipFAB.setVisibility(View.GONE);
//
////                lastCV.setVisibility(View.VISIBLE);
////                lastFAB.setVisibility(View.VISIBLE);
//
//                submitFAB.setVisibility(View.VISIBLE);
//
//            }
//        });
//
//        lastFAB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (selectGroupseven.getCheckedRadioButtonId() == -1) {
//                    Toast.makeText(Step2.this, "कृपया एक विकल्प चुनें", Toast.LENGTH_SHORT).show();
//                } else {
//                    int selectedId = selectGroupseven.getCheckedRadioButtonId();
//                    // find the radiobutton by returned id
//                    selectSeven = findViewById(selectedId);
//                    last_temp = selectSeven.getText().toString();
//
//                    switch (last_temp) {
//
//                        case "नयी खेती, पशुपालन से सम्बंधित":
//                            last = "Nayi Kheti and Pashupalan";
//
//                            break;
//
//                        case "हेंडीक्राफ्ट व उत्पादन से सम्बंधित":
//                            last = "Handicraft Related";
//
//                            break;
//
//                        case "दुकान, दक्षता व सर्विस आदि से सम्बंधित":
//                            last = "Dukan and Dakshta";
//
//                            break;
//
//                    }
//
//
//                    lastCV.setVisibility(View.GONE);
//                    lastFAB.setVisibility(View.GONE);
//
//                    submitFAB.setVisibility(View.VISIBLE);
//
//
//
//                }
//            }
//        });

        //The Last Final Submit Button
        submitFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new UploadFileToServer().execute();
            }
        });

    }

    /**
     * Displaying captured image/video on the screen
     */

    private void previewMedia(boolean isImage) {
        // Checking whether captured media is image or video
        if (isImage) {

            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // down sizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 10;

            final Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

            imageView.setImageBitmap(bitmap);

        }
    }


    //Password Validation
    private boolean validateName() {
        if (nameET.getText().toString().trim().isEmpty()) {
            String errorString = "वैध नाम दर्ज करें।";  // your error message
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(errorColor);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(errorString);
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString.length(), 0);
            nameET.setError(spannableStringBuilder);
            return (false);

        }
        return (true);
    }

    //Password Validation
    private boolean validateAddress() {
        if (addressET.getText().toString().trim().isEmpty()) {
            String errorString = "वैध पता दर्ज करें।";  // your error message
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(errorColor);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(errorString);
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString.length(), 0);
            addressET.setError(spannableStringBuilder);
            return (false);

        }
        return (true);
    }

    //Password Validation
    private boolean validateCity() {
        if (cityET.getText().toString().trim().isEmpty()) {
            String errorString = "वैध शहर दर्ज करें।";  // your error message
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(errorColor);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(errorString);
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString.length(), 0);
            cityET.setError(spannableStringBuilder);
            return (false);

        }
        return (true);
    }

    //Password Validation
    private boolean validateWard() {
        if (wardET.getText().toString().trim().isEmpty()) {
            String errorString = "वैध मुहल्ला दर्ज करें।";  // your error message
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(errorColor);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(errorString);
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString.length(), 0);
            wardET.setError(spannableStringBuilder);
            return (false);

        }
        return (true);
    }

    //Password Validation
    private boolean validateAssembly() {
        if (assemblyET.getText().toString().trim().isEmpty()) {
            String errorString = "वैध विधानसभा दर्ज करें।";  // your error message
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(errorColor);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(errorString);
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString.length(), 0);
            assemblyET.setError(spannableStringBuilder);
            return (false);

        }
        return (true);
    }

    //Password Validation
    private boolean validateOccupation() {
        if (occupationET.getText().toString().trim().isEmpty()) {
            String errorString = "वैध व्यवसाय दर्ज करें।";  // your error message
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(errorColor);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(errorString);
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString.length(), 0);
            occupationET.setError(spannableStringBuilder);
            return (false);

        }
        return (true);
    }


    /**
     * Uploading the file to server
     */
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            progressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            progressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
            progressBar.setProgress(progress[0]);

            txtPercentage.setVisibility(View.VISIBLE);
            // updating percentage value
            txtPercentage.setText("कृपया रजिस्टर करते समय प्रतीक्षा करें - " + String.valueOf(progress[0]) + "%");

            //Set Visibility of Upload Button
            submitFAB.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Config.FILE_UPLOAD_URL);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                File sourceFile = new File(filePath);

                // Adding file data to http body
                entity.addPart("image", new FileBody(sourceFile));

                // Extra parameters to pass to server

//               SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
//                String email = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");

                name = nameET.getText().toString();
                //ward = wardET.getText().toString();
                //assembly = assemblyET.getText().toString();
                city = cityET.getText().toString();
                address = addressET.getText().toString();

                SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                referred_by = sharedPreferences.getString(Config.MID_SHARED_PREF, "");


                entity.addPart("name", new StringBody(name));
                entity.addPart("mobile", new StringBody(mobile));
                entity.addPart("city", new StringBody(city));
                entity.addPart("occupation", new StringBody(occupation));
                entity.addPart("address", new StringBody(address));
                entity.addPart("ward", new StringBody(ward));
                entity.addPart("assembly", new StringBody(assembly));
                entity.addPart("referred", new StringBody(referred_by));
//                entity.addPart("rajneeti", new StringBody(pgp1));
//                entity.addPart("jansankhya", new StringBody(pgp2));
//                entity.addPart("nayikheti", new StringBody(pgp3));
//                entity.addPart("entrepreneur", new StringBody(pgp4));
//                entity.addPart("mahilao", new StringBody(pgp5));
//                entity.addPart("beghar", new StringBody(pgp6));
//                entity.addPart("jalparyavaran", new StringBody(pgp7));
//                entity.addPart("asangathit", new StringBody(asangathit_op));
//                entity.addPart("entrepreneurship", new StringBody(final_entrepreneurship));
//                entity.addPart("businessidea", new StringBody(secondlast));
//                entity.addPart("interest", new StringBody(last));

                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e(TAG, "Response from server: " + result);

            // showing the server response in an alert dialog
            showAlert(result);

            super.onPostExecute(result);
        }

    }

    /**
     * Method to show alert dialog
     */
    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishActivity(0);
                        Intent intent = new Intent(Step2.this, Home.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
