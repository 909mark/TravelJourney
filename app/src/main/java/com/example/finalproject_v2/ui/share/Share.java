package com.example.finalproject_v2.ui.share;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.finalproject_v2.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

public class Share extends Fragment {
    private Button shareLink;
    private Button shareFacebook;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(requireContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_share, container, false);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(getActivity());
        shareLink = v.findViewById(R.id.share_by_link);
        shareLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String link = "https://github.com/909mark/FinalProject_v2";
                myIntent.putExtra(Intent.EXTRA_TEXT, link);
                startActivity(Intent.createChooser(myIntent, "Share Using..."));
            }
        });

        shareFacebook = v.findViewById(R.id.share_by_fb);
        shareFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setQuote("Check this app out!")
                        .setContentUrl(Uri.parse("https://github.com/909mark/FinalProject_v2"))
                        .build();
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    shareDialog.show(content);
                }
            }
        });
        return v;
    }
}
