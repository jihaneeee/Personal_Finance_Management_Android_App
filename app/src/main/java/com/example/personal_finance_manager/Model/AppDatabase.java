package com.example.personal_finance_manager.Model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {
                UserEntity.class,
                CategoryEntity.class,
                ExpenseEntity.class,
                IncomeEntity.class,
                UserCategorySetting.class
        },
        version = 2
)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;


    public abstract UserDao userDao();
    public abstract CategoryDao categoryDao();
    public abstract ExpenseDao expenseDao();
    public abstract UserCategorySettingDao userCategorySettingDao();

    public abstract IncomeDao incomeDao();

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "pfm_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
