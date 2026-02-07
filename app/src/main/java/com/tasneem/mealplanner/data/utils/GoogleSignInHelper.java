package com.tasneem.mealplanner.data.utils;

import android.content.Context;
import android.os.CancellationSignal;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.credentials.ClearCredentialStateRequest;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.ClearCredentialException;
import androidx.credentials.exceptions.GetCredentialException;

import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GoogleSignInHelper {

    private static final String TAG = "GoogleSignInHelper";

    private final Context context;
    private final CredentialManager credentialManager;
    private final String serverClientId;

    public GoogleSignInHelper(Context context, String serverClientId) {
        this.context = context;
        this.serverClientId = serverClientId;
        this.credentialManager = CredentialManager.create(context);
    }


    public void signIn(GoogleSignInCallback callback) {
        signIn(false, callback);
    }

    public void signIn(boolean filterByAuthorizedAccounts, GoogleSignInCallback callback) {
        GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
                .setServerClientId(serverClientId)
                .setFilterByAuthorizedAccounts(filterByAuthorizedAccounts)
                .build();

        GetCredentialRequest request = new GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build();

        CancellationSignal cancellationSignal = new CancellationSignal();
        Executor executor = Executors.newSingleThreadExecutor();

        credentialManager.getCredentialAsync(
                context,
                request,
                cancellationSignal,
                executor,
                new CredentialManagerCallback<>() {
                    @Override
                    public void onResult(GetCredentialResponse result) {
                        handleSignInResult(result, callback);
                    }

                    @Override
                    public void onError(@NonNull GetCredentialException e) {
                        Log.e(TAG, "Error getting credential", e);

                        if (filterByAuthorizedAccounts &&
                                e.getMessage() != null &&
                                e.getMessage().contains("No credentials available")) {
                            signIn(false, callback);
                        } else {
                            callback.onFailure(e.getMessage() != null ?
                                    e.getMessage() : "Failed to get Google credentials");
                        }
                    }
                }
        );
    }

    private void handleSignInResult(GetCredentialResponse result, GoogleSignInCallback callback) {
        Credential credential = result.getCredential();

        if (credential instanceof CustomCredential) {
            CustomCredential customCredential = (CustomCredential) credential;

            if (GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL.equals(customCredential.getType())) {
                try {
                    GoogleIdTokenCredential googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(customCredential.getData());

                    String idToken = googleIdTokenCredential.getIdToken();
                    callback.onSuccess(idToken);

                } catch (Exception e) {
                    Log.e(TAG, "Invalid Google ID token credential", e);
                    callback.onFailure("Invalid Google ID token credential");
                }
            } else {
                Log.e(TAG, "Unexpected credential type: " + customCredential.getType());
                callback.onFailure("Unexpected credential type");
            }
        } else {
            Log.e(TAG, "Unexpected credential class: " + credential.getClass().getName());
            callback.onFailure("Unexpected credential");
        }
    }

    public void clearCredentialState(ClearCredentialCallback callback) {
        ClearCredentialStateRequest request = new ClearCredentialStateRequest();
        CancellationSignal cancellationSignal = new CancellationSignal();
        Executor executor = Executors.newSingleThreadExecutor();

        credentialManager.clearCredentialStateAsync(
                request,
                cancellationSignal,
                executor,
                new CredentialManagerCallback<>() {
                    @Override
                    public void onResult(Void result) {
                        callback.onSuccess();
                    }

                    @Override
                    public void onError(@NonNull ClearCredentialException e) {
                        Log.e(TAG, "Error clearing credential state", e);
                        callback.onFailure(e.getMessage() != null ?
                                e.getMessage() : "Failed to clear credentials");
                    }
                }
        );
    }

    public interface GoogleSignInCallback {
        void onSuccess(String idToken);
        void onFailure(String errorMessage);
    }

    public interface ClearCredentialCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }
}
