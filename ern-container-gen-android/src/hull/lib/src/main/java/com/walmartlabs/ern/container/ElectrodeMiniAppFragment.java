package com.walmartlabs.ern.container;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public abstract class ElectrodeMiniAppFragment extends Fragment {
    private static final String INITIAL_PROPS = "props";
    private ElectrodeReactActivityListener mElectrodeReactActivityListener;

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
        Bundle initialProps = getArguments().getBundle(INITIAL_PROPS);
        if (this.getActivity() != null && !this.getActivity().isFinishing()) {
            return mElectrodeReactActivityListener.getElectrodeDelegate().createReactRootView(getMiniAppName(), initialProps);
        } else {
            return null;
        }
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
} 