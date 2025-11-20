package mw.gov.tcm.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mw.gov.tcm.data.teacher.TeacherRepository
import mw.gov.tcm.data.teacher.TeacherRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTeacherRepository(impl: TeacherRepositoryImpl): TeacherRepository
}