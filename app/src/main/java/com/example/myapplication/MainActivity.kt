package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Model.Articles
import com.example.Model.Response
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.Serializable


class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        articles.layoutManager = LinearLayoutManager(this)
        val url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=973163c29a164868b20716a8d64a5851"
        var latestNews:Response
        OkHttpHandler().execute(url)

        articles.addOnItemTouchListener(
            RecyclerItemClickListener(
                applicationContext,
                articles,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        // do whatever
                        Log.d("CCC","CLICK")
                        val i = Intent(this@MainActivity, ShowDetails::class.java)

                        i.putExtra("sampleObject", latestNewsData.articles?.get(position) as Serializable)
                        this@MainActivity.startActivity(i)
                    }

                    override fun onLongItemClick(view: View, position: Int) {
                        // do whatever
                    }
                })
        )
    }




    inner class OkHttpHandler : AsyncTask<String, String, String>() {
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
            println("result: $result")
            val type = object : TypeToken<Response>() {}.type
            val latestNews: Response = Gson().fromJson(result, type)
            latestNewsData = latestNews
            articles.adapter = latestNews.articles?.let { ArticlesAdapter(it) }
        }
    }


    companion object
    {
        lateinit var latestNewsData: Response
    }

    inner class ArticlesAdapter(var articles: Array<Articles>) :     RecyclerView.Adapter<ArticlesAdapter.ArticleHolder>() {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ArticleHolder {
            val view = LayoutInflater.from(p0.context).inflate(R.layout.row_article, p0, false)
            return ArticleHolder(view)
        }

        override fun getItemCount(): Int = articles.size

        override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
            val article = articles[position]
            holder.title.text = article.title
            holder.description.text = article.description
            holder.source.text = "-${article.source?.name}"
            Picasso.get().load(article.urlToImage).into(holder.preview)
        }

        inner class ArticleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var title = itemView.findViewById<TextView>(R.id.title)
            var description = itemView.findViewById<TextView>(R.id.descriptions)
            var source = itemView.findViewById<TextView>(R.id.source)
            var preview = itemView.findViewById<ImageView>(R.id.preview)
        }
    }

}
