package com.jasperlu.test;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jasperlu.doppler.Doppler;

public class EndFragment extends android.support.v4.app.Fragment{
    Doppler doppler = TheDoppler.getDoppler();
    boolean gesturesOn = true;
    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_end, container, false);


        TextView textView = (TextView) root.findViewById(R.id.end_text);
        Linkify.addLinks(textView, Linkify.ALL);

        textView.setMovementMethod(LinkMovementMethod.getInstance());

        return root;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            doppler.setOnGestureListener(new Doppler.OnGestureListener() {
                @Override
                public void onPush() {
                    if (gesturesOn) {
                        ((ViewPager) getActivity().findViewById(R.id.pager)).setCurrentItem(2);
                    }
                }

                @Override
                public void onPull() {
                }

                @Override
                public void onTap() {
                }

                @Override
                public void onDoubleTap() {
                    gesturesOn = false;
                }

                @Override
                public void onNothing() {
                }
            });

        }
    }
}
