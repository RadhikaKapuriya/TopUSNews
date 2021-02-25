package com.example.Model

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.Serializable

class Response: Serializable {
    var articles: Array<Articles>? = null
    var totalResults: String? = null
    var status: String? = null

    override fun toString(): String {
        return "Response [articles = $articles, totalResults = $totalResults, status = $status]"
    }
}