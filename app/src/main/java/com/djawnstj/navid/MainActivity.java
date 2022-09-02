package com.djawnstj.navid;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.djawnstj.navid.databinding.ActivityMainBinding;
import com.navercorp.nid.NaverIdLoginSDK;
import com.navercorp.nid.oauth.NidOAuthLogin;
import com.navercorp.nid.oauth.OAuthLoginCallback;
import com.navercorp.nid.profile.NidProfileCallback;
import com.navercorp.nid.profile.data.NidProfileResponse;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;

    private MainActivity instance;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        instance = this;

        NaverIdLoginSDK.INSTANCE.initialize(getBaseContext(), "HFNZUVgUrfA9XVlwleAx", "2ogO3rXGAi", getString(R.string.app_name));

        binding.button.setOnClickListener(view -> {
            NaverIdLoginSDK.INSTANCE.authenticate(getBaseContext(), new OAuthLoginCallback() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "onSuccess: " + NaverIdLoginSDK.INSTANCE.getAccessToken());
                    Log.d(TAG, "onSuccess: " + NaverIdLoginSDK.INSTANCE.getRefreshToken());
                    Log.d(TAG, "onSuccess: " + NaverIdLoginSDK.INSTANCE.getExpiresAt());
                    Log.d(TAG, "onSuccess: " + NaverIdLoginSDK.INSTANCE.getTokenType());
                    Log.d(TAG, "onSuccess: " + NaverIdLoginSDK.INSTANCE.getState());


                    // 토큰정보로 유저정보를 가져올 수 있음
                    NidOAuthLogin nidOAuthLogin = new NidOAuthLogin();
                    nidOAuthLogin.callProfileApi(new NidProfileCallback<NidProfileResponse>() {
                        @Override
                        public void onSuccess(NidProfileResponse response) {
                            Log.e(TAG, "onSuccess: " + Objects.requireNonNull(response.getProfile()).getId());
                        }

                        @Override
                        public void onFailure(int i, @NotNull String s) {

                        }

                        @Override
                        public void onError(int i, @NotNull String s) {

                        }
                    });
                }

                @Override
                public void onFailure(int i, @NotNull String s) {
                    Log.e(TAG, "onFailure: " + NaverIdLoginSDK.INSTANCE.getLastErrorCode().getCode());
                }

                @Override
                public void onError(int i, @NotNull String s) {
                    onFailure(i, s);
                }
            });
        });

        binding.logoutBtn.setOnClickListener(view -> {
            NaverIdLoginSDK.INSTANCE.logout();
        });


    }

}
