package ua.ck.geekhub.ivanov.hometask.fragments;



import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import ua.ck.geekhub.ivanov.hometask.R;

public class AnimationFragment extends Fragment {
    private Button
            rotateButton, vibrateButton,
            zoomButton, fadeButton;
    private Animation
            rotateAnimation, vibrateAnimation,
            zoomInAnimation, zoomOutAnimation,
            fadeInAnimation, fadeOutAnimation;

    private ImageView imageView;

    private boolean zoomOut, fadeOut;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_animation, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = (ImageView) view.findViewById(R.id.imageViewCat);

        rotateButton = (Button) view.findViewById(R.id.rotate_button);
        rotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.startAnimation(rotateAnimation);
            }
        });

        vibrateButton = (Button) view.findViewById(R.id.vibrate_button);
        vibrateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.startAnimation(vibrateAnimation);
            }
        });

        zoomButton = (Button) view.findViewById(R.id.zoom_button);
        zoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (zoomOut) {
                    imageView.startAnimation(zoomInAnimation);
                    zoomButton.setText(R.string.zoom_out_button);
                } else {
                    imageView.startAnimation(zoomOutAnimation);
                    zoomButton.setText(R.string.zoom_in_button);
                }
                zoomOut = !zoomOut;
            }
        });

        fadeButton = (Button) view.findViewById(R.id.fade_button);
        fadeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fadeOut) {
                    imageView.startAnimation(fadeInAnimation);
                    fadeButton.setText(R.string.fade_out_button);
                } else {
                    imageView.startAnimation(fadeOutAnimation);
                    fadeButton.setText(R.string.fade_in_button);
                }
                fadeOut = !fadeOut;
            }
        });

        rotateAnimation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate);
        vibrateAnimation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.vibrate);
        zoomInAnimation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.zoom_in);
        zoomOutAnimation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.zoom_out);
        fadeInAnimation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fade_in);
        fadeOutAnimation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fade_out);
    }
}