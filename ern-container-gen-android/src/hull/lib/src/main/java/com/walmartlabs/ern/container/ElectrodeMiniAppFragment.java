package com.walmartlabs.ern.container;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.facebook.react.ReactRootView;

public abstract class ElectrodeMiniAppFragment extends Fragment {
    private static final String INITIAL_PROPS = "props";
    private ElectrodeReactActivityListener mElectrodeReactActivityListener;

    @Nullable
    private View mMiniAppView;

    public void addInitialProps(@NonNull Bundle bundle) {
        Bundle args = new Bundle();
        args.putBundle(INITIAL_PROPS, bundle);
        setArguments(args);
    }

    protected String getMiniAppName() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mMiniAppView == null) {
            Bundle initialProps = getArguments().getBundle(INITIAL_PROPS);
            if (this.getActivity() != null && !this.getActivity().isFinishing()) {
                mMiniAppView = mElectrodeReactActivityListener.getElectrodeDelegate()
                        .createReactRootView(getMiniAppName(), initialProps);
            }
        }
        return mMiniAppView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ElectrodeReactActivityListener) {
            mElectrodeReactActivityListener = (ElectrodeReactActivityListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ElectrodeReactActivityListener");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMiniAppView instanceof ReactRootView) {
            mElectrodeReactActivityListener.getElectrodeDelegate()
                    .removeMiniAppView(getMiniAppName(), (ReactRootView) mMiniAppView);
            mMiniAppView = null;
        }
    }
}
