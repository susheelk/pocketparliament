package tech.susheelkona.pocketparliament.android.fragments.bills;

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

import com.annimon.stream.Collectors;

import tech.susheelkona.pocketparliament.android.activities.BillActivity;
import tech.susheelkona.pocketparliament.R;
import tech.susheelkona.pocketparliament.model.legislation.Bill;
import tech.susheelkona.pocketparliament.android.adapters.BillsListAdapter;
import tech.susheelkona.pocketparliament.android.adapters.RecyclerViewListener;
import tech.susheelkona.pocketparliament.android.tasks.AsyncResponseListener;
import tech.susheelkona.pocketparliament.android.tasks.GetBillsTask;
import tech.susheelkona.pocketparliament.android.tasks.SharedPreferenceHelper;

import java.util.List;
import com.annimon.stream.Stream;

/**
 * A simple {@link Fragment} subclass.

 */
public class BillsListFragment extends Fragment{

    // Data
    private List<Bill> bills;
    private GetBillsTask task;
    private int page = 1;

    // Views
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    // Adapters
    private RecyclerView.LayoutManager manager;
    private BillsListAdapter adapter;

    private final SharedPreferenceHelper preferences = SharedPreferenceHelper.getInstance();


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
//        getData(view);
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

            @Override
            public void onBottomReached() {
                addData();
            }
        });
        recyclerView.setAdapter(adapter);

    }

    private void getData(View view) {
        page = 1;
        swipeRefreshLayout.setRefreshing(true);
        task = new GetBillsTask();
        task.setAsyncResponseListener(new AsyncResponseListener<List<Bill>>() {
            @Override
            public void onTaskSuccess(Class source, List<Bill> data) {
                Log.i("getBills", data.size()+"");

                if (getArguments().getBoolean(SharedPreferenceHelper.FOLLOWED_ONLY)){
                    if (getContext() != null){
                        data = Stream.of(data).filter(bill -> preferences.isFollowed(bill, getContext())).collect(Collectors.toList());
                    }
                    swipeRefreshLayout.setEnabled(false);
                }
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
                displayError();
            }
        });
        task.execute(getArguments().getString("params"));
    }

    private void addData(){
        page = 1;
        swipeRefreshLayout.setRefreshing(true);
        task = new GetBillsTask();
        task.setAsyncResponseListener(new AsyncResponseListener<List<Bill>>() {
            @Override
            public void onTaskSuccess(Class source, List<Bill> data) {
                Log.i("addBills", data.size()+"");

                if (getArguments().getBoolean(SharedPreferenceHelper.FOLLOWED_ONLY)){
                    data = Stream.of(data).filter(bill -> preferences.isFollowed(bill, getContext())).collect(Collectors.toList());
                    swipeRefreshLayout.setEnabled(false);
                }
//                bills.addAll(data);
                adapter.add(data);
                swipeRefreshLayout.setRefreshing(false);

                if(data.size() == 0){
                    Log.i("empty", "empty");
//                    Toast.makeText(getContext(), "No bills", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onTaskError(Class source, String message) {
                Log.e("getBills", message+"");
                displayError();
            }
        });
        task.execute(getArguments().getString("params")+"&page="+(++page));
    }

    private synchronized void displayError(){
        swipeRefreshLayout.setRefreshing(false);
//        Toast.makeText(getContext(), "No Connection", Toast.LENGTH_SHORT).show();
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
        bundle.putString("params", "?include=number,title,lastMajorEvent,dateLastUpdated&size=20");
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
        bundle.putString("params", "?include=number,title,lastMajorEvent,sponsor,dateLastUpdated&size=20&sponsor_name="+name);

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
        bundle.putString("params", "?include=number,title,lastMajorEvent,law,dateLastUpdated&size=20&law="+law);

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
        bundle.putString("params", "?include=number,title,lastMajorEvent,dateLastUpdated&size=20&bill_state="+status);

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
        bundle.putString("params", "?include=number,title,lastMajorEvent,law,dateLastUpdated&size=20&new="+bnew);

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

    public static Fragment forFollowed(boolean followed){
        Fragment fragment = BillsListFragment.forRecent();
        fragment.getArguments().putBoolean(SharedPreferenceHelper.FOLLOWED_ONLY, followed);
        return fragment;
    }


}
