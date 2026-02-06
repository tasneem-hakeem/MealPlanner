package com.tasneem.mealplanner.data.datasource.auth.datasource;

import com.tasneem.mealplanner.data.datasource.auth.model.User;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface AuthenticationDatasource {
    Single<User> signInWithEmailAndPassword(String email, String password);
    Single<User> signUpWithEmailAndPassword(String email, String password, String name);
    Completable signOut();
    Single<User> getCurrentUser();
    Single<Boolean> isUserSignedIn();
}
