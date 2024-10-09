package main

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import com.google.inject.name.Named
import org.jetbrains.exposed.sql.Database
import services.DatabaseInitializationService

class AppModule : AbstractModule() {

    @Provides
    @Singleton
    @Named("clientDatabase")
    fun provideClientDatabase(databaseInitializationService: DatabaseInitializationService): Database {
        return databaseInitializationService.connectToDatabase("users")
    }

    @Provides
    @Singleton
    @Named("coachDatabase")
    fun provideCoachDatabase(databaseInitializationService: DatabaseInitializationService): Database {
        return databaseInitializationService.connectToDatabase("users")
    }

    @Provides
    @Singleton
    @Named("lessonsDatabase")
    fun provideLessonsDatabase(databaseInitializationService: DatabaseInitializationService): Database {
        return databaseInitializationService.connectToDatabase("users")
    }
}
