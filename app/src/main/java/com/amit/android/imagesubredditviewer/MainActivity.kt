package com.amit.android.imagesubredditviewer

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.amit.android.imageloader.ImageLoader
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private var viewModel: AppViewModel? = null
    private var myList: ArrayList<ImageDataLink> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
        viewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)
        getSubredditImageData()
        setObservers()
    }

    private fun setObservers() {
        viewModel!!.getImageListData().observe(this, Observer {
            if (it.isNotEmpty()) {
                myList.clear()
                for (i in it.indices) {
                    Log.d("asdf", "image url ${it[i].data}")
                    myList.add(it[i].data)
                }
                redditImageRV!!.adapter!!.notifyDataSetChanged()
            }
        })
    }

    private fun getSubredditImageData() {
        if (allPermissionsGranted()) {
            viewModel!!.getImageList()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun initRecyclerView() {
        val imageLoader = ImageLoader()

        imageLoader.initImageLoader(this)
        redditImageRV.layoutManager = LinearLayoutManager(this)
        redditImageRV.adapter = ImageListAdapter(myList, this, imageLoader)
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                viewModel!!.getImageList()
            } else {
                Toast.makeText(
                    this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                getSubredditImageData()
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 101
    }
}
