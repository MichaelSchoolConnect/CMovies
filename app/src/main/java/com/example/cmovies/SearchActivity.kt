package com.example.cmovies

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager


private lateinit var sharedPreferences: SharedPreferences
private lateinit var editor: SharedPreferences.Editor
private lateinit var urlString: EditText
private lateinit var checkBox: CheckBox

class SearchActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity_main)

        urlString = findViewById(R.id.editTextTextUrl)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sharedPreferences.edit()

        //checkBox = findViewById(R.id.checkBox)
        var button: Button = findViewById(R.id.searchBtn)
        button.setOnClickListener(this)

        checkSharedPref()
    }

    private fun checkSharedPref(){
        var str = sharedPreferences.getString("urlString", "").toString().trim()
        if(str.isEmpty()){
            Log.i("SearchActivity", "sharedPref is empty")
        }else{
            //start SearchResultsActivity
            Log.i("SearchActivity", "SharedPref not empty")
            val i = Intent(applicationContext, SearchResultsActivity::class.java)
            startActivity(i)
        }
    }

    override fun onClick(v: View?) {
        if(v?.id  == R.id.searchBtn){
            if(TextUtils.isEmpty(urlString.text)){
                urlString.error = "Required"
            }else{
                val str = urlString.text.toString().toLowerCase().trim()
                val ch = '+'

                editor.putString("urlString", str.replace(' ', ch))

                editor.apply()

                urlString.text.clear()

                val i = Intent(applicationContext, SearchResultsActivity::class.java)
                startActivity(i)
            }

        }
    }

    //Algorithm to replace space with '+' character.
    //Runs in O(n) time.
    fun replaceSpaceWith(string: String){
        val ch = '+'
        for (i in string.indices) {
            if (string[i] === ' ') {
                string[i] === ch
            }
        }
    }
}