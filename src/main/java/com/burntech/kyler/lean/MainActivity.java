package com.burntech.kyler.lean;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.software.shell.fab.ActionButton;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private final String APP_NAME = "Lean";

    private DBHelper _db;
    private ArrayList<Weight> _weights = new ArrayList<Weight>();

    private ActionButton _fab = null;

    private RecyclerView _recyclerView;
    private WeightRecyclerAdapter _adapter;
    private RecyclerView.LayoutManager _layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weight_view);

        startup();
    }

    private void startup() {
        setTitle(APP_NAME);
        actionBarChanger();

        setupActionButton();
        setupWeightDB();
        setupRecyclerView();
    }

    private void setupActionButton() {
        // #4CAF50 == green500
        ActionButton _fab = (ActionButton) findViewById(R.id.action_button);

        _fab.setButtonColor(getResources().getColor(R.color.green500));
        _fab.setImageResource(R.drawable.fab_plus_icon); // adds the "+" symbol to the Action Button.

        _fab.setRippleEffectEnabled(true);
        _fab.setOnClickListener(_actionButtonClick);
    }

    private View.OnClickListener _actionButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Toast.makeText(getApplicationContext(), "You clicked the add button!", Toast.LENGTH_LONG).show();
            showAlertDialog();
        }
    };

    private void setupWeightDB() {
        _db = new DBHelper(this);
        _weights = _db.getWeights();
    }

    private void setupRecyclerView() {
        _recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        _recyclerView.setHasFixedSize(true);

        _layoutManager = new LinearLayoutManager(this);
        _recyclerView.setLayoutManager(_layoutManager);

        _recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        _adapter = new WeightRecyclerAdapter(_weights);
        _recyclerView.setAdapter(_adapter);
    }

    public void showAlertDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Add Weight");
        alert.setMessage("Please enter your new weight in pounds!");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_PHONE);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                // Do something with value!
                addWeight(value);
                //hideKeyboard();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                //hideKeyboard();
            }
        });

        alert.show();
        //showKeyboard(input);
    }

    private void showKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getWindow().getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(),
                    InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    private void addWeight(String value) {
        String curTime = Util.getCurrentTime();

        _db.insertWeight(value, curTime);
        _weights.add(new Weight(value, curTime));
        _adapter.notifyDataSetChanged();

        Toast.makeText(getApplicationContext(), "Successfully added: " + value + "lb", Toast.LENGTH_LONG).show();
    }

    private void actionBarChanger() {
        ActionBar actionBar = getActionBar();

        // Hides the Android icon in the top left in the actionbar.
        //actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        //actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);

        Drawable marker = getResources().getDrawable(R.color.green500);
        actionBar.setBackgroundDrawable(marker);

        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
