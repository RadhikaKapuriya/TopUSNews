package com.example.myapplication

import android.app.Activity
import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.Model.Articles
import com.example.Model.comment
import com.example.Model.like
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import okhttp3.Request


class ShowDetails : Activity() {
    var showimg: ImageView? = null
    var tvtitle: TextView? = null
    var tvdetails: TextView? = null
    var tvlike: TextView? = null
    var tvcomment: TextView? = null
    lateinit var dene:Articles
    var progressBar: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.showdetails)
        showimg = findViewById(R.id.tvname)
        tvtitle = findViewById(R.id.tvtitle)
        tvdetails = findViewById(R.id.tvdetails)
        tvlike = findViewById(R.id.tvlike)
        tvcomment= findViewById(R.id.tvcomment)
        data
    }


    private val data: Unit
        private get() {
            val i = intent
            dene = i.getSerializableExtra("sampleObject") as Articles

            val mergeurl = dene.urlToImage!!.replace("https://", "").replace("/", "-")
            val likesurl = "https://cn-news-info-api.herokuapp.com/likes/$mergeurl"
            val commentssurl =
                "https://cn-news-info-api.herokuapp.com/comments/$mergeurl"
            OkHttpHandler().execute(likesurl)
            OkHttpHandlercomment().execute(commentssurl)
            Log.d("UUU123", "likesurl=$likesurl")
            Log.d("UUU123", "commentssurl=$commentssurl")
            findViewById<View>(R.id.ivback).setOnClickListener { finish() }
        }

    inner class OkHttpHandlercomment : AsyncTask<String, String, String>() {
        override fun onPreExecute() {
            progressBar = ProgressDialog(this@ShowDetails);
            progressBar?.setMax(100);
            progressBar?.setMessage("Loading....");
            progressBar?.setCanceledOnTouchOutside(false)
            progressBar?.show();
            super.onPreExecute()
        }
        override fun doInBackground(vararg params: String?): String? {

            val client = OkHttpClient()
            val request = params[0]?.let { Request.Builder().url(it).build() }
            try {
                val response = request?.let { client.newCall(it).execute() }
                val result = response?.body?.string()
                return result
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if(progressBar != null)
            {
                progressBar?.dismiss()
            }
            Picasso.get()
                .load(dene.urlToImage) //                .resize(50, 50) // here you resize your image to whatever width and height you like
                .into(showimg)
            tvtitle!!.text = dene.author
            tvdetails!!.text = dene.title
            val type_comment = object : TypeToken<comment>() {}.type
            val latestcomment: comment = Gson().fromJson(result, type_comment)
            if(latestcomment != null)
            {
                println("result: ${latestcomment}")
                tvcomment?.setText("Comments:"+latestcomment.comments)
            }



        }
    }


    inner class OkHttpHandler : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
        }


        override fun doInBackground(vararg params: String?): String? {

            val client = OkHttpClient()
            val request = params[0]?.let { Request.Builder().url(it).build() }
            try {
                val response = request?.let { client.newCall(it).execute() }
                val result = response?.body?.string()
                return result
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val type = object : TypeToken<like>() {}.type
            val latestNews: like = Gson().fromJson(result, type)
            if(latestNews != null)
            {
                println("result: ${latestNews}")
                tvlike?.setText("Likes:" + latestNews.likes)
            }



        }
    }

}