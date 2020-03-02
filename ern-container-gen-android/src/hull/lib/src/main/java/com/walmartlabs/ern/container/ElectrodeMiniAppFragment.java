package com.walmartlabs.ern.container;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.ReactRootView;
import com.walmartlabs.ern.container.ElectrodeReactActivityDelegate;

public class ElectrodeMiniAppFragment extends Fragment { //implements ElectrodeReactActivityDelegate.BackKeyHandler, PermissionAwareActivity {

    private static final String INITIAL_PROPS = "props";
    private ElectrodeReactActivityDelegate mReactActivityDelegate;
    private ReactRootView view;

    public void addInitialProps(@NonNull Bundle bundle) {
        Bundle args = new Bundle();
        args.putBundle(INITIAL_PROPS, bundle);
        setArguments(args);
    }

    public static ElectrodeMiniAppFragment newInstance(Bundle initialProps) {
        ElectrodeMiniAppFragment fragment = new ElectrodeMiniAppFragment();

        return fragment;
    }

    protected String getMiniAppName() {
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle initialProps = getArguments().getBundle(INITIAL_PROPS);
        view = mReactActivityDelegate.createReactRootView(getMiniAppName(), initialProps);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        view.unmountReactApplication();
        view = null;
        mReactActivityDelegate.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mReactActivityDelegate = new ElectrodeReactActivityDelegate(getActivity());
    }
}
