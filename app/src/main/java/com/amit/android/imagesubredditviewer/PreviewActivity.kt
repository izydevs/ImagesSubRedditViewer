package com.amit.android.imagesubredditviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.amit.android.imageloader.ImageLoader
import com.amit.android.imagesubredditviewer.R
import kotlinx.android.synthetic.main.activity_preview.*

class PreviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val imageLoader = ImageLoader()

        imageLoader.initImageLoader(this)
        val imageUrl = intent!!.extras!!.getString("image_url")
        if (imageUrl != null) {
            try {
                imageLoader.DisplayImage(
                    imageUrl,
                    imageView
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
