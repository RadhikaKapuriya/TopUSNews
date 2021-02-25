package com.example.Model

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.Serializable

class comment: Serializable {
    var comments: String? = null

    override fun toString(): String {
        return "Source [name = $comments]"
    }
}