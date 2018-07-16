package com.susheel.pocketparliament.ui.fragments.bills;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.model.legislation.Bill;
import com.susheel.pocketparliament.ui.BillActivity;
import com.susheel.pocketparliament.ui.adapters.BillsListAdapter;
import com.susheel.pocketparliament.ui.adapters.RecyclerViewListener;
import com.susheel.pocketparliament.ui.tasks.AsyncResponseListener;
import com.susheel.pocketparliament.ui.tasks.GetBillsTask;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.

 */
public class BillsListFragment extends Fragment {

    // Data
    private List<Bill> bills;
    private GetBillsTask task;

    // Views
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    // Adapters
    private RecyclerView.LayoutManager manager;
    private BillsListAdapter adapter;


    public BillsListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bills_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews(view);
        setUpRecyclerView();
        getData(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        View view = getView();
        getData(view);
//        view.setFocusableInTouchMode(true);
//        view.requestFocus();

    }



    @Override
    public void onPause() {
        super.onPause();
        task.cancel(true);
    }

    private void bindViews(View parent) {
        swipeRefreshLayout = (SwipeRefreshLayout) parent.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.senate);
        recyclerView = (RecyclerView) parent.findViewById(R.id.recycler_view);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                parent.setVisibility(View.INVISIBLE);
                getData(parent);
                parent.setVisibility(View.VISIBLE);
            }
        });
    }

    public void setUpRecyclerView(){
        adapter = new BillsListAdapter((AppCompatActivity)getActivity());
        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        adapter.addRecyclerViewListener(new RecyclerViewListener() {
            @Override
            public void onItemClick(Object object) {
                goToActivity((Bill)(object));
            }
        });
        recyclerView.setAdapter(adapter);

    }

    private void getData(View view) {
        swipeRefreshLayout.setRefreshing(true);
        task = new GetBillsTask();
        task.setAsyncResponseListener(new AsyncResponseListener<List<Bill>>() {
            @Override
            public void onTaskSuccess(Class source, List<Bill> data) {
                Log.i("getBills", data.size()+"");
                bills = data;
                adapter.update(data);
                swipeRefreshLayout.setRefreshing(false);

                if(data.size() == 0){
                    Log.i("empty", "empty");
//                    Toast.makeText(getContext(), "No bills", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onTaskError(Class source, String message) {
                Log.e("getBills", message+"");
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        task.execute(getArguments().getString("params"));
    }

    private void goToActivity(Bill bill){
        Log.i("GOTO", "bill");
        Intent intent = new Intent(getActivity(), BillActivity.class);
        Bundle args = new Bundle();
        args.putString(BillActivity.NUMBER, bill.getNumber());
        args.putString(BillActivity.TITLE, bill.getTitle());
        args.putInt(BillActivity.ID, bill.getId());
        intent.putExtras(args);
        startActivity(intent);
    }

    /** Returns list of recent bills
     *
     * @return
     */
    public static Fragment forRecent() {
        Bundle bundle = new Bundle();
        bundle.putString("params", "?include=number,title,lastMajorEvent,dateLastUpdated&size=900");
        Fragment fragment = new BillsListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    /** Returns list of bills of a certain sponsor
     *
     * @param name Full name of Sponsor
     * @return
     */
    public static Fragment forSponsor(String name) {
        Bundle bundle = new Bundle();
        name = name.replace(" ", "_");
        bundle.putString("params", "?include=number,title,lastMajorEvent,sponsor,dateLastUpdated&size=50&sponsor_name="+name);

        Fragment fragment = new BillsListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    /** Returns list of bills based on whether they have passed
     *
     * @param law Whether a bill is law yet or not
     * @return
     */
    public static Fragment forLaw(boolean law) {
        Bundle bundle = new Bundle();
        bundle.putString("params", "?include=number,title,lastMajorEvent,law,dateLastUpdated&size=500&law="+law);

        Fragment fragment = new BillsListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    /** Returns bills of a certain status
     *
     * @param status
     * @return
     */
    public static Fragment forStatus(String status) {
        Bundle bundle = new Bundle();
        status = status.replace(" ", "_");
        bundle.putString("params", "?include=number,title,lastMajorEvent,dateLastUpdated&size=50&bill_state="+status);

        Fragment fragment = new BillsListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    /** Returns new bills
     *
     * @param bnew
     * @return
     */
    public static Fragment forNew(boolean bnew) {
        Bundle bundle = new Bundle();
        bundle.putString("params", "?include=number,title,lastMajorEvent,law,dateLastUpdated&size=50&new="+bnew);

        Fragment fragment = new BillsListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    /** Returns Bills of a certain search query
     *
     * @param query
     * @return
     */
    public static Fragment forSearch(String query){
        Bundle bundle = new Bundle();
        bundle.putString("params", "?include=number,title,lastMajorEvent,law,dateLastUpdated&size=50&query="+query);

        Fragment fragment = new BillsListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


}
