package com.susheel.pocketparliament.ui.pages.mp_list;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.model.MemberParliament;
import com.susheel.pocketparliament.ui.tasks.AsyncResponseListener;
import com.susheel.pocketparliament.ui.tasks.GetMemberParliamentTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllMpTabFragment extends Fragment {



    public AllMpTabFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_mp_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getData();
    }

    private void getData() {
        GetMemberParliamentTask task = new GetMemberParliamentTask();
        task.setAsyncResponseListener(new AsyncResponseListener<MemberParliament[]>() {
            @Override
            public void onTaskSuccess(Class source, MemberParliament[] data) {

            }

            @Override
            public void onTaskError(Class source, MemberParliament[] data) {

            }
        });

        task.execute();
    }

}
