package com.tasneem.mealplanner.data.datasource.auth.repository;

import com.tasneem.mealplanner.data.datasource.auth.datasource.AuthenticationDatasource;
import com.tasneem.mealplanner.data.datasource.auth.datasource.FirebaseAuthDatasource;
import com.tasneem.mealplanner.data.datasource.auth.model.AuthResult;
import com.tasneem.mealplanner.data.datasource.auth.model.User;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class AuthenticationRepositoryImpl implements AuthenticationRepository {
    private final AuthenticationDatasource datasource;

    public AuthenticationRepositoryImpl() {
        this.datasource = new FirebaseAuthDatasource();
    }

    @Override
    public Single<AuthResult> signUp(String email, String password, String name) {
        return datasource.signUpWithEmailAndPassword(email, password, name)
                .map(user -> new AuthResult(true, user, null))
                .onErrorReturn(throwable -> new AuthResult(false, null, getErrorMessage(throwable)));
    }

    @Override
    public Single<AuthResult> signIn(String email, String password) {
        return datasource.signInWithEmailAndPassword(email, password)
                .map(user -> new AuthResult(true, user, null))
                .onErrorReturn(throwable -> new AuthResult(false, null, getErrorMessage(throwable)));
    }

    @Override
    public Single<AuthResult> signInWithGoogle(String idToken) {
        if (idToken == null || idToken.trim().isEmpty()) {
            return Single.just(
                    new AuthResult(false, null, "Invalid Google ID token")
            );
        }

        return datasource.signInWithGoogle(idToken)
                .map(user -> new AuthResult(true, user, null))
                .onErrorReturn(throwable ->
                        new AuthResult(false, null, getErrorMessage(throwable)));
    }


    @Override
    public Completable signOut() {
        return datasource.signOut();
    }

    @Override
    public Single<User> getCurrentUser() {
        return datasource.getCurrentUser();
    }

    @Override
    public Single<Boolean> isUserSignedIn() {
        return datasource.isUserSignedIn();
    }

    private String getErrorMessage(Throwable throwable) {
        String message = throwable.getMessage();

        if (message == null) {
            return "An unknown error occurred";
        }

        if (message.contains("email address is already in use")) {
            return "This email is already registered";
        } else if (message.contains("password is invalid")) {
            return "Invalid password";
        } else if (message.contains("no user record")) {
            return "No account found with this email";
        } else if (message.contains("network error")) {
            return "Network error. Please check your connection";
        } else if (message.contains("too many requests")) {
            return "Too many attempts. Please try again later";
        } else if (message.contains("email address is badly formatted")) {
            return "Invalid email format";
        } else if (message.contains("weak password")) {
            return "Password is too weak";
        }

        return message;
    }
}
