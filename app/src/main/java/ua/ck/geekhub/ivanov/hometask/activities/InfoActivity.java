package ua.ck.geekhub.ivanov.hometask.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import ua.ck.geekhub.ivanov.hometask.R;
import ua.ck.geekhub.ivanov.hometask.fragments.MinecraftInfoFragment;
import ua.ck.geekhub.ivanov.hometask.models.Tool;

public class InfoActivity extends Activity {

    private Tool tool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        //check landscape mode
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            finish();
            return;
        }

        if (savedInstanceState == null){
            tool = (Tool) getIntent().getSerializableExtra(MinecraftInfoFragment.EXTRA_TOOL);

            Fragment fragment = MinecraftInfoFragment.newInstance(tool);
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().add(R.id.container_info, fragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
