package com.hwmlygr.ground.schoolbbs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.hwmlygr.ground.schoolbbs.R;

/**
 * Created by MA on 2018/6/14.
 */

public class GuideFragment extends Fragment {

    private ImageView iv_guide;
    private Button btn_guide;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide, container, false);
        btn_guide = view.findViewById(R.id.btn_guide);
        iv_guide = view.findViewById(R.id.iv_guide);

        Bundle bundle = getArguments();
        int imageResoure = bundle.getInt("imageResource");
        btn_guide.setVisibility(bundle.getBoolean("isLast")?View.VISIBLE:View.GONE);
        iv_guide.setImageResource(imageResoure);
        btn_guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity activity = getActivity();
                startActivity(new Intent(activity,LoginActivity.class));
                activity.finish();
            }
        });
        return view;
    }
}
