package com.beginner.micromaster.flashcardsapp.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.beginner.micromaster.flashcardsapp.model.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by praxis on 12/04/17.
 */

public class DataBaseDAO {
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public DataBaseDAO(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }

    public void open() throws SQLiteException{
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
    }

    public void close(){
        dataBaseHelper.close();
    }

    public void addCard(Card card){
        ContentValues values = new ContentValues();
        values.put(DataContract.TodoEntry.COLUMN_QUESTION, card.getQuestion());
        values.put(DataContract.TodoEntry.COLUMN_ANSWER, card.getAnswer());

        int result = (int) sqLiteDatabase.insert(DataContract.TodoEntry.TABLE_CARDS, null, values);
        Log.d("insert","result: " + result);
    }

    public List<Card> getAllCards(){
        List<Card> cardList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DataContract.TodoEntry.TABLE_CARDS, null, null, null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                String question = cursor.getString(cursor.getColumnIndexOrThrow(DataContract.TodoEntry.COLUMN_QUESTION));
                String answer = cursor.getString(cursor.getColumnIndexOrThrow(DataContract.TodoEntry.COLUMN_ANSWER));

                cardList.add(new Card(question, answer));
            }while (cursor.moveToNext());
        }

        return cardList;
    }
}
