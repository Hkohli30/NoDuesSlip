package com.example.hkohli.orthodox;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String USER_TYPE = "";
    private String ID ="";

    String[] ar = {"office","teacher","supervisor"};
    String INSERT_TYPE = ar[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.admin_menu);


        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");
        USER_TYPE = intent.getStringExtra("USER_TYPE");

        menuSelection();
    }

    private void menuSelection()
    {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.admin_menu_manage) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_framelayout,new UnderMaintanence()).commit();
        } else if (id == R.id.admin_menu_search1) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_framelayout, new SearchViaNameRoll()).commit();

        } else if (id == R.id.admin_menu_search2) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_framelayout,new SearchFragment()).commit();

        } else if (id == R.id.admin_menu_manager) {

            new AlertDialog.Builder(this).setTitle("Select User")
            .setSingleChoiceItems(ar, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    INSERT_TYPE = ar[which];
                }
            }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SotInsertManager sotInsertManager = new SotInsertManager();
                Bundle bundle = new Bundle();
                 bundle.putString("INSERT_TYPE", INSERT_TYPE);
                    bundle.putString("id", ID);
                    sotInsertManager.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_framelayout,sotInsertManager).commit();

                }
            }).create().show();

        } else if (id == R.id.admin_menu_manage_department) {
            AdminCourseManger adminCourseManger = new AdminCourseManger();
            Bundle bundle = new Bundle();
            bundle.putString("ID", ID);
            adminCourseManger.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                .add(R.id.main_framelayout, adminCourseManger).commit();

        } else if (id == R.id.admin_menu_message) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_framelayout,new UnderMaintanence()).commit();

        }
        else if (id == R.id.admin_menu_add_student)
        {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
