package org.zayn.teamhub.core.di

import com.google.firebase.auth.FirebaseAuth

actual fun getPlatformFirebaseAuthProvider(): FirebaseAuthProvider {
    return FirebaseAuth.getInstance()
}