package com.susheel.pocketparliament.android.fragments.bills;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.model.legislation.Bill;
import com.susheel.pocketparliament.android.activities.BillActivity;
import com.susheel.pocketparliament.android.fragments.mps.MpListFragment;
import com.susheel.pocketparliament.android.tasks.AsyncResponseListener;
import com.susheel.pocketparliament.android.tasks.GetBillTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillOverviewFragment extends Fragment {

    private GetBillTask task;

    private TextView title;
    private ProgressBar progressBar;
    private View content;
    private View senatorLayout;
    private TextView senatorName;
    private Button legisinfoButton;


    public BillOverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bill_overview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews(view);
        getData();

    }

    private void bindViews(View p){
        title = (TextView) p.findViewById(R.id.bill_title);
        progressBar = (ProgressBar) p.findViewById(R.id.progress_bar);
        content = p.findViewById(R.id.content);
        content.setVisibility(View.GONE);
        senatorLayout = p.findViewById(R.id.senator_sponsor_layout);
        senatorName = (TextView) p.findViewById(R.id.senator_name);
        legisinfoButton = (Button) p.findViewById(R.id.legisinfo_button);


    }

    private void getData(){
        progressBar.setVisibility(View.VISIBLE);
        task = new GetBillTask();
        task.setAsyncResponseListener(new AsyncResponseListener<Bill>() {
            @Override
            public void onTaskSuccess(Class source, Bill data) {
                title.setText(data.getTitle());
                AppCompatActivity activity = ((AppCompatActivity)getActivity());
                activity.getSupportActionBar().
                        setTitle(data.getBillType()+" "+data.getNumber());

                progressBar.setVisibility(View.GONE);
                content.setVisibility(View.VISIBLE);

                FragmentManager manager = activity.getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                if(!data.getBillType().matches("Senate Public Bill")){
                    transaction.replace(R.id.sponsor_container,
                            MpListFragment.forOne(data.getSponsor().getName()));
                    transaction.commit();
                } else {
                    senatorLayout.setVisibility(View.VISIBLE);
                    senatorName.setText("Senator "+data.getSponsor().getName());
                }

                legisinfoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://www.parl.ca/LegisInfo/BillDetails.aspx?Language=E&billId="
                                        +data.getId())));
                    }
                });
            }

            @Override
            public void onTaskError(Class source, String message) {

            }
        });
        task.execute(getArguments().getInt(BillActivity.ID));
    }

    public static Fragment forBill(int id) {
        Fragment fragment = new BillOverviewFragment();
        Bundle args = new Bundle();
        args.putInt(BillActivity.ID, id);
        fragment.setArguments(args);
        return fragment;
    }

}
