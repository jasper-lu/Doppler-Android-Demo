package com.jasperlu.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jasperlu.doppler.Doppler;

import org.w3c.dom.Text;

public class DemoFragment extends Fragment {
    public boolean gesturesOn = true;
    public boolean isFocused = false;

    private View root;
    private ViewPager vp;
    private ScrollView scrollView;
    private TextView gestureLight;
    private TextView scrollLight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_demo, container, false);
        vp = ((ViewPager) getActivity().findViewById(R.id.pager));
        scrollView = (ScrollView) root.findViewById(R.id.scroll);

        scrollLight = (TextView) root.findViewById(R.id.scroll_light);
        gestureLight = (TextView) root.findViewById(R.id.gesture_light);
        return root;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            startScrolling();
        }
    }

    public void startScrolling() {
        TheDoppler.getDoppler().removeReadCallback();
        TheDoppler.getDoppler().setOnGestureListener(new Doppler.OnGestureListener() {
            @Override
            public void onPush() {
                root.findViewById(R.id.colorBox).setBackgroundColor(root.getResources().getColor(R.color.red));
                if (gesturesOn) {
                    if (isFocused) {
                        //scroll
                        scrollView.smoothScrollTo(0, scrollView.getScrollY() + 200);
                    } else {
                        vp.setCurrentItem(1);
                    }
                }
            }

            @Override
            public void onPull() {
                root.findViewById(R.id.colorBox).setBackgroundColor(root.getResources().getColor(R.color.blue));
                if (gesturesOn) {
                    if (isFocused) {
                        //scroll
                        scrollView.smoothScrollTo(0, scrollView.getScrollY() - 200);
                    } else {
                        vp.setCurrentItem(3);
                    }
                }
            }

            @Override
            public void onTap() {
                root.findViewById(R.id.colorBox).setBackgroundColor(root.getResources().getColor(R.color.purple));
                if (gesturesOn) {
                    if (isFocused) {
                        isFocused = false;
                        scrollLight.setTextColor(getResources().getColor(R.color.grey));
                        scrollLight.setText("Scroll Off");
                    } else {
                        isFocused = true;
                        scrollLight.setTextColor(getResources().getColor(R.color.green));
                        scrollLight.setText("Scroll On");
                    }
                }
            }

            @Override
            public void onDoubleTap() {
                root.findViewById(R.id.colorBox).setBackgroundColor(root.getResources().getColor(R.color.black));
                if (gesturesOn) {
                    gesturesOn = false;
                    gestureLight.setTextColor(getResources().getColor(R.color.grey));
                    gestureLight.setText("Gestures Off");
                } else {
                    gesturesOn = true;
                    gestureLight.setTextColor(getResources().getColor(R.color.green));
                    gestureLight.setText("Gestures On");
                }
            }

            @Override
            public void onNothing() {
                root.findViewById(R.id.colorBox).setBackgroundColor(root.getResources().getColor(R.color.neutral));
            }
        });
    }
}
