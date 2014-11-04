package ua.ck.geekhub.ivanov.hometask.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import ua.ck.geekhub.ivanov.hometask.R;
import ua.ck.geekhub.ivanov.hometask.fragments.MinecraftInfoFragment;
import ua.ck.geekhub.ivanov.hometask.models.Tool;

public class InfoActivity extends Activity {

    private Tool tool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        tool = (Tool) getIntent().getSerializableExtra(MinecraftInfoFragment.EXTRA_TOOL);

        Fragment fragment = MinecraftInfoFragment.newInstance(tool);
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().add(R.id.container_info, fragment).commit();
    }
}
