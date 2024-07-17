package com.dk.room_simple_ex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.dk.room_simple_ex.db.TextDatabase
import com.dk.room_simple_ex.db.entity.TextEntity
import com.dk.room_simple_ex.db.entity.TextEntity2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * addMigrations
 * 데이터 구조가 변경되면서 기존 데이터가 삭제 되는것을 막는다.
 * 즉 데이터 구조는 변경되도 기존 데이터는 유지시킨다.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = TextDatabase.getDatabase(this)

        val inputArea = findViewById<EditText>(R.id.textInputArea)

        val insertBtn = findViewById<Button>(R.id.insertBtn)
        val getAllBtn = findViewById<Button>(R.id.getDataBtn)
        val deleteBtn = findViewById<Button>(R.id.deleteBtn)

        insertBtn.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                db.textDao().insert(TextEntity(0, inputArea.text.toString()))
                db.textDao2().insert(TextEntity2(0, inputArea.text.toString(), "this is newTxt"))
                inputArea.setText("")
            }

        }

        getAllBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                Log.d("MAIN", db.textDao().getAllData().toString())
                Log.d("MAIN", db.textDao2().getAllData().toString())
            }
        }

        deleteBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.textDao().deleteAllData()
                db.textDao2().deleteAllData()
            }
        }



    }
}