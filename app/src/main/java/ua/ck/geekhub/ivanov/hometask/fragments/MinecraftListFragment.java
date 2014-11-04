package ua.ck.geekhub.ivanov.hometask.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import ua.ck.geekhub.ivanov.hometask.R;
import ua.ck.geekhub.ivanov.hometask.activities.InfoActivity;
import ua.ck.geekhub.ivanov.hometask.adapters.ToolAdapter;
import ua.ck.geekhub.ivanov.hometask.models.Tool;

public class MinecraftListFragment extends Fragment {

    private ListView toolListView;
    ToolAdapter toolAdapter;

    private boolean land;

    List<Tool> toolList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View infoFrame = getActivity().findViewById(R.id.information);

        land = infoFrame != null && infoFrame.getVisibility() == View.VISIBLE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_minecraft_list, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] toolName = view.getResources().getStringArray(R.array.tool_name);
        String[] toolDescrioption = view.getResources().getStringArray(R.array.tool_description);
        int[] toolImage = {
                R.drawable.tl_diamond_pickaxe,
                R.drawable.tl_diamond_axe,
                R.drawable.tl_diamond_shovel,
                R.drawable.wpn_diamond_sword,
                R.drawable.wpn_bow,
                R.drawable.wpn_arrow,
                R.drawable.arm_diamond_helmet,
                R.drawable.arm_diamond_chestplate,
                R.drawable.arm_diamond_leggings,
                R.drawable.arm_diamond_boots,
        };
        toolListView = (ListView) view.findViewById(R.id.toolListView);
        toolList = createToolList(toolName, toolDescrioption, toolImage);
        toolAdapter = new ToolAdapter(getActivity(), toolList);
        toolListView.setAdapter(toolAdapter);
        toolListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });


        if (view.findViewById(R.id.container_info) != null) {
            if (savedInstanceState != null) {
                return;
            }

            MinecraftInfoFragment minecraftInfoFragment = new MinecraftInfoFragment();
            getFragmentManager().beginTransaction().add(R.id.container_info, minecraftInfoFragment).commit();

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.minecraft, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        //SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                toolAdapter.filter(newText);
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query) {
                // this is your adapter that will be filtered
                toolAdapter.filter(query);
                return true;
            }
        });
    }

    public void doMySearch(String query) {
        toolAdapter.filter(query);
    }


    private void selectItem(int position) {
        if (land) {
            MinecraftInfoFragment info = MinecraftInfoFragment.newInstance(toolList.get(position));
            getFragmentManager().beginTransaction().replace(R.id.information, info).commit();
        } else {
            Intent intent = new Intent(getActivity(), InfoActivity.class);
            intent.putExtra(MinecraftInfoFragment.EXTRA_TOOL, toolList.get(position));
            startActivity(intent);
        }
    }

    private List<Tool> createToolList(String[] name, String[] description, int[] image) {
        List<Tool> list = new ArrayList<Tool>();
        for (int i = 0; i < name.length; i++) {
            list.add(new Tool(name[i], description[i], image[i]));
        }
        return list;
    }
}