package com.example.project_2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class GamesDatabase(context: Context, factory: SQLiteDatabase.CursorFactory?, version: Int):
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
        companion object {
            private val DATABASE_VERSION=1
            private val DATABASE_NAME="gamesDB"
            val TABLE_GAMES="games"
            val COLUMN_ID="id"
            val COLUMN_NAME="gamename"
            val COLUMN_YEAR="gameyear"
            val COLUMN_IMAGE="gameimage"
            val COLUMN_POSITION="gameposition"
        }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_GAMES_TABLE=("CREATE TABLE "+
                TABLE_GAMES+" ("+
                COLUMN_ID+" INTEGER PRIMARY KEY, "+
                COLUMN_NAME + " TEXT," +
                COLUMN_YEAR + " INTEGER," +
                COLUMN_IMAGE + " TEXT," +
                COLUMN_POSITION + " TEXT" + ")")

//        val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${TABLE_GAMES}"

        db.execSQL(CREATE_GAMES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_GAMES)
        onCreate(db)
    }

    fun addGame(game: Game){
        val db = this.writableDatabase
        val values = ContentValues()
        val name=game.name?.replace("\'", "")

        if(game!=null) {
                values.put(COLUMN_NAME, name)
                values.put(COLUMN_YEAR, game.year)
                values.put(COLUMN_IMAGE, game.image)
                values.put(COLUMN_POSITION, game.position)
//            values.put(COLUMN_NAME, game.name)
//            values.put(COLUMN_YEAR, game.year)
//            values.put(COLUMN_IMAGE, game.image)
//            values.put(COLUMN_POSITION, game.position)

            db.insert(TABLE_GAMES, null, values)
            db.close()
        }
    }

    fun findGame(gameName: String): Game?{
        val name=gameName.replace("\'", "")

        val query="SELECT * FROM $TABLE_GAMES WHERE $COLUMN_NAME LIKE \'$name\'"
        val db=this.readableDatabase
        val cursor=db.rawQuery(query, null)
        var game: Game?=null

        if(cursor.moveToFirst()){
            val id=Integer.parseInt(cursor.getString(0))
            val name=cursor.getString(1)
            val year=cursor.getInt(2)
            val image=cursor.getString(3)
            val position=cursor.getString(4)

            game=Game(id, name, year, image, position)
            cursor.close()
        }

        db.close()
        return game
    }

    fun clearDB(){
        val db = this.writableDatabase
        db.execSQL("delete * from " + TABLE_GAMES);
    }

    fun deleteGame(gameName: String):Boolean{
        val name=gameName.replace("\'", "")

        var result=false
        val query="SELECT * FROM $TABLE_GAMES WHERE $COLUMN_NAME LIKE \'$name\'"
        val db=this.writableDatabase
        val cursor=db.rawQuery(query, null)

        if(cursor.moveToFirst()){
            val id=cursor.getInt(0)
            db.delete(TABLE_GAMES, COLUMN_ID+" = ?", arrayOf(id.toString()))
            cursor.close()
            result=true
        }

        db.close()
        return result
    }
}













