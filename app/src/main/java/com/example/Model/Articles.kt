package com.example.Model

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle;
import java.io.Serializable

class Articles:Serializable {
    var content: String? = null
    var publishedAt: String? = null
    var author: String? = null
    var urlToImage: String? = null
    var title: String? = null
    var source: Source? = null
    var description: String? = null
    var url: String? = null
    var comments: String? = null
    var likes: String? = null

    override fun toString(): String {
        return "Articles [content = $content, publishedAt = $publishedAt, author = $author, urlToImage = $urlToImage, title = $title, source = $source, description = $description, url = $url]"
    }
}