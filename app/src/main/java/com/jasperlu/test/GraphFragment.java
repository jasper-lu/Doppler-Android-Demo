package com.jasperlu.test;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jasperlu.doppler.Doppler;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;



public class GraphFragment extends Fragment {
    Doppler doppler;
    private XYSeries mSeries;
    private XYSeries div10;
    private GraphicalView mChart;
    private final XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
    private XYSeriesRenderer mRenderer;
    private XYSeriesRenderer div10Renderer;

    private final XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

    private View root;

    private boolean gesturesOn = false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Main", "On create");
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_main, container, false);
        root = rootView;

        String gesturesString = " <font color= '#ff0000'>Push (move towards the mic) </font>, " +
                "<font color='#0000ff'>Pull (move away from the mic) </font>, " +
                "<font color='#ff00ff'>Tap (push then pull) </font>, and " +
                "<font color='#000000'>Double Tap</font>.";

        ((TextView)rootView.findViewById(R.id.graph_text_3)).append(Html.fromHtml(gesturesString));

        doppler = TheDoppler.getDoppler();
        renderGraph();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            startGraph();
        }
    }

    public void startGraph() {
        doppler.setOnReadCallback(new Doppler.OnReadCallback() {
            @Override
            public void onBandwidthRead(int leftBandwidth, int rightBandwidth) {

            }
            @Override
            public void onBinsRead(double[] bins) {
                mSeries.clear();
                div10.clear();
                double frac = bins[929] * doppler.maxVolRatio;
                //Log.d("PRIMARY VOL", bins[929] + "");
                for (int i = 920; i < 940; ++i) {
                    mSeries.add(i, bins[i]);
                    div10.add(i, frac);
                }
                mChart.repaint();
            }
        });

        doppler.setOnGestureListener(new Doppler.OnGestureListener() {
            @Override
            public void onPush() {
                root.findViewById(R.id.colorBar).setBackgroundColor(root.getResources().getColor(R.color.red));
            }

            @Override
            public void onPull() {
                root.findViewById(R.id.colorBar).setBackgroundColor(root.getResources().getColor(R.color.blue));

                if (gesturesOn) {
                    ((ViewPager) getActivity().findViewById(R.id.pager)).setCurrentItem(2);
                }
            }

            @Override
            public void onTap() {
                root.findViewById(R.id.colorBar).setBackgroundColor(root.getResources().getColor(R.color.purple));
            }

            @Override
            public void onDoubleTap() {
                root.findViewById(R.id.colorBar).setBackgroundColor(root.getResources().getColor(R.color.black));

                if (gesturesOn) {
                    gesturesOn = false;
                } else {
                    gesturesOn = true;
                }
            }

            @Override
            public void onNothing() {
                root.findViewById(R.id.colorBar).setBackgroundColor(root.getResources().getColor(R.color.neutral));
            }
        });
    }

    private void renderGraph() {
        mSeries = new XYSeries("Vols/FreqBin");
        div10 = new XYSeries("div10");
        mSeries.add(3,4);
        mRenderer = new XYSeriesRenderer();
        div10Renderer = new XYSeriesRenderer();
        div10Renderer.setColor(root.getResources().getColor(R.color.red));
        dataset.addSeries(mSeries);
        dataset.addSeries(div10);
        renderer.addSeriesRenderer(mRenderer);
        renderer.addSeriesRenderer(div10Renderer);
        renderer.setPanEnabled(false);
        renderer.setZoomEnabled(false);
        mChart = ChartFactory.getLineChartView(getActivity(), dataset, renderer);

        ((LinearLayout)root.findViewById(R.id.chart)).addView(mChart);
    }
}
