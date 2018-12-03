package com.blogspot.blogsetyaaji.footballpedia.dblocal

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(
    ctx, "db_favorite",
    null, 1
) {

    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
            FavoriteMatch.TABLE_FAVORITE_MATCH, true,
            FavoriteMatch.ID_FAVORITE to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteMatch.EVENT_ID to TEXT,
            FavoriteMatch.TEAM_HOME to TEXT,
            FavoriteMatch.TEAM_HOME_SCORE to TEXT,
            FavoriteMatch.TEAM_AWAY to TEXT,
            FavoriteMatch.TEAM_AWAY_SCORE to TEXT,
            FavoriteMatch.EVENT_DATE to TEXT,
            FavoriteMatch.EVENT_TIME to TEXT
        )

        db?.createTable(
            FavoriteTeam.TABLE_FAVORITE_TEAM, true,
            FavoriteTeam.ID_FAVORITE_TEAM to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteTeam.TEAM_ID to TEXT,
            FavoriteTeam.TEAM_NAME to TEXT,
            FavoriteTeam.TEAM_IMAGE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // menghapus table
        db?.dropTable(FavoriteMatch.TABLE_FAVORITE_MATCH, true)
        db?.dropTable(FavoriteTeam.TABLE_FAVORITE_TEAM, true)
    }
}

// extension function
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)