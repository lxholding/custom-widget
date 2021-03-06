package com.example.detailmodule.fragments;

import android.content.res.Configuration;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.detailmodule.utils.ParamsUtil;
import com.example.detailmodule.views.PanoramaViewPanel;


public class PanoramaFragment extends BaseFragment implements BaseFragment.ExitFragmentListener{
    private int mResId;
    private String mPanoramaUrl = null;
    private PanoramaViewPanel mPanoramaViewPanel = null;

    public PanoramaFragment() {
        super();
        mPanoramaUrl = ParamsUtil.DETAIL_PANORAMA_URL;
    }

    public PanoramaFragment(int resid) {
        super();
        mResId = resid;
    }

    @Override
    protected void preLoadingData() {

    }
    @Override
    public void removeFragment() {
        ViewGroup contentView = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        if (ismScreenPortrait()) {
            contentView.removeView(mPanoramaViewPanel.getPortraitView());
        } else {
            contentView.removeView(mPanoramaViewPanel.getLandscapeView());
        }

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment panorama = getActivity().getSupportFragmentManager().findFragmentByTag(BaseFragment.PANORAMA_TAG);
        if (null != panorama) {
            fragmentTransaction.hide(panorama);
            fragmentTransaction.remove(panorama);
        }

        DetailFragment detailFragment = new DetailFragment();
        fragmentTransaction.add(detailFragment,BaseFragment.DETAIL_TAG);
        fragmentTransaction.show(detailFragment);

        fragmentTransaction.commit();
    }

    @Override
    public void removeViews() {
        ViewGroup contentView = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        if (ismScreenPortrait()) {
            contentView.removeView(mPanoramaViewPanel.getPortraitView());
        } else {
            contentView.removeView(mPanoramaViewPanel.getLandscapeView());
        }
    }

    @Override
    public View initPortraitView(ViewGroup view) {
        if (null == mPanoramaViewPanel) {
            mPanoramaViewPanel = new PanoramaViewPanel(getContext());
        }
//        mPanoramaViewPanel.setInitData(mPanoramaUrl);
        mPanoramaViewPanel.setInitData(mResId);
        mPanoramaViewPanel = mPanoramaViewPanel.getPortraitView();
        mPanoramaViewPanel.setExitFragmentListener(this);
        mPanoramaViewPanel.setParentId(view.getId());
        view.removeView(mPanoramaViewPanel);
        view.addView(mPanoramaViewPanel);
        return mPanoramaViewPanel;
    }

    @Override
    public View initLandscapeView(ViewGroup view) {
        if (null == mPanoramaViewPanel) {
            mPanoramaViewPanel = new PanoramaViewPanel(getContext());
        }
//        mPanoramaViewPanel.setInitData(mPanoramaUrl);
        mPanoramaViewPanel.setInitData(mResId);
        mPanoramaViewPanel = mPanoramaViewPanel.getLandscapeView();
        mPanoramaViewPanel.setExitFragmentListener(this);
        mPanoramaViewPanel.setParentId(view.getId());
        view.removeView(mPanoramaViewPanel);
        view.addView(mPanoramaViewPanel);
        return mPanoramaViewPanel;
    }

    @Override
    public void dispatchOrientation(Configuration newConfig) {
        if (null == mPanoramaViewPanel) {
            mPanoramaViewPanel = new PanoramaViewPanel(getContext());
        }
        mPanoramaViewPanel.setExitFragmentListener(this);
        mPanoramaViewPanel.orientationChanged(newConfig);
        ViewGroup contentView = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            initPortraitView(contentView);
        } else {
            initLandscapeView(contentView);
        }
    }
}
