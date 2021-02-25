package com.example.Model

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.Serializable

class Source: Serializable {
    var id: Any? = null
    var name: String? = null

    override fun toString(): String {
        return "Source [id = $id, name = $name]"
    }
}