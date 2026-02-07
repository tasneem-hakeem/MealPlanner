package com.tasneem.mealplanner.data.datasource.auth.repository;

import com.tasneem.mealplanner.data.datasource.auth.model.AuthResult;
import com.tasneem.mealplanner.data.datasource.auth.model.User;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface AuthenticationRepository {
    Single<AuthResult> signUp(String email, String password, String name);
    Single<AuthResult> signIn(String email, String password);
    Single<AuthResult> signInWithGoogle(String idToken);
    Completable signOut();
    Single<User> getCurrentUser();
    Single<Boolean> isUserSignedIn();
}