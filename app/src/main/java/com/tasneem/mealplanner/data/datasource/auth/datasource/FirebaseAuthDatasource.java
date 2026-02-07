package com.tasneem.mealplanner.data.datasource.auth.datasource;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.tasneem.mealplanner.data.datasource.auth.model.User;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FirebaseAuthDatasource implements AuthenticationDatasource {
    private final FirebaseAuth firebaseAuth;

    public FirebaseAuthDatasource() {
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public Single<User> signUpWithEmailAndPassword(String email, String password, String name) {
        return Single.<FirebaseUser>create(emitter ->
                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                        if (firebaseUser != null) {
                                            emitter.onSuccess(firebaseUser);
                                        } else {
                                            emitter.onError(new Exception("Failed to get user after sign up"));
                                        }
                                    } else {
                                        emitter.onError(task.getException() != null
                                                ? task.getException()
                                                : new Exception("Sign up failed"));
                                    }
                                })
                )
                .flatMap(firebaseUser -> updateUserDisplayName(firebaseUser, name));
    }

    @Override
    public Single<User> signInWithGoogle(String idToken) {
        return Single.create(emitter -> {
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

            firebaseAuth.signInWithCredential(credential)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            if (firebaseUser != null) {
                                User user = mapFirebaseUserToUser(firebaseUser);
                                emitter.onSuccess(user);
                            } else {
                                emitter.onError(new Exception("Failed to get user after Google sign in"));
                            }
                        } else {
                            Exception exception = task.getException();
                            emitter.onError(exception != null ? exception :
                                    new Exception("Google sign in failed"));
                        }
                    });
        });
    }

    public Single<User> updateUserDisplayName(FirebaseUser firebaseUser, String name) {
        return Single.create(emitter ->
                firebaseUser.updateProfile(new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                emitter.onSuccess(mapFirebaseUserToUser(firebaseUser));
                            } else {
                                Exception exception = task.getException();
                                emitter.onError(exception != null ? exception : new Exception("Failed to update profile"));
                            }
                        })
        );
    }

    @Override
    public Single<User> signInWithEmailAndPassword(String email, String password) {
        return Single.create(emitter -> firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            User user = mapFirebaseUserToUser(firebaseUser);
                            emitter.onSuccess(user);
                        } else {
                            emitter.onError(new Exception("Failed to get user after sign in"));
                        }
                    } else {
                        Exception exception = task.getException();
                        emitter.onError(exception != null ? exception :
                                new Exception("Sign in failed"));
                    }
                }));
    }

    @Override
    public Completable signOut() {
        return Completable.create(emitter -> {
            try {
                firebaseAuth.signOut();
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    @Override
    public Single<User> getCurrentUser() {
        return Single.create(emitter -> {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                User user = mapFirebaseUserToUser(firebaseUser);
                emitter.onSuccess(user);
            } else {
                emitter.onError(new Exception("No user is currently signed in"));
            }
        });
    }

    @Override
    public Single<Boolean> isUserSignedIn() {
        return Single.create(emitter -> {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            emitter.onSuccess(firebaseUser != null);
        });
    }

    private User mapFirebaseUserToUser(FirebaseUser firebaseUser) {
        return new User(
                firebaseUser.getUid(),
                firebaseUser.getEmail(),
                firebaseUser.getDisplayName()
        );
    }
}
