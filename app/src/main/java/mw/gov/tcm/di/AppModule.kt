package mw.gov.tcm.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mw.gov.tcm.data.auth.AuthRepository
import mw.gov.tcm.data.auth.AuthRepositoryImpl
import mw.gov.tcm.data.teacher.TeacherRepository
import mw.gov.tcm.data.teacher.TeacherRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideTeacherRepository(firebaseAuth: FirebaseAuth, firestore: FirebaseFirestore): TeacherRepository {
        return TeacherRepositoryImpl(firebaseAuth, firestore)
    }
}