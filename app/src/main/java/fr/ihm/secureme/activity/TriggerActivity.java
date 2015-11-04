package fr.ihm.secureme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import fr.ihm.secureme.R;

/**
 * Created by pierrebonhoure on 04/11/2015.
 */
public class TriggerActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            String s = intent.getStringExtra("EXTRA_mode");

            if (s.equals("mvt")) {
                setContentView(R.layout.activity_mvt);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            } else if (s.equals("dist")) {
                setContentView(R.layout.activity_dist);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            } else if (s.equals("cable")) {
                setContentView(R.layout.activity_cable);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            } else if (s.equals("sim")) {
                setContentView(R.layout.activity_sim);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

        }
        else{
            System.err.println("activity called from nowhere");
        }
        /*switch(tm){
            case Sim:
                setContentView(R.layout.activity_sim);
                break;
            case Cable:
                setContentView(R.layout.activity_cable);
                break;
            case Distance:
                setContentView(R.layout.activity_dist);
                break;
            case Mouvement:
                setContentView(R.layout.activity_mvt);
                break;
        }*/

    }
}


