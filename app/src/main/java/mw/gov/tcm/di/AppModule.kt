package mw.gov.tcm.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mw.gov.tcm.data.auth.AuthRepository
import mw.gov.tcm.data.auth.AuthRepositoryImpl
import mw.gov.tcm.data.repository.ApplicationStatusRepository
import mw.gov.tcm.data.repository.ApplicationStatusRepositoryImpl
import mw.gov.tcm.data.teacher.TeacherRepository
import mw.gov.tcm.data.teacher.TeacherRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideTeacherRepository(firestore: FirebaseFirestore): TeacherRepository {
        return TeacherRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideApplicationStatusRepository(
        firestore: FirebaseFirestore,
        auth: FirebaseAuth
    ): ApplicationStatusRepository {
        return ApplicationStatusRepositoryImpl(firestore, auth)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }
}