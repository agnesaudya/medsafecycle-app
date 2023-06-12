package com.example.medsafecycle.home.guest

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medsafecycle.R
import com.example.medsafecycle.auth.LoginActivity
import com.example.medsafecycle.auth.RegisterActivity
import com.example.medsafecycle.landing.LandingActivity
import com.example.medsafecycle.limbah.HistoryLimbahActivity
import com.example.medsafecycle.limbah.HistoryLimbahAdapter
import com.example.medsafecycle.limbah.LimbahDummy
import java.io.File

//  TODO : Buat guest, pake shared preference aja ya :) soalnya dia gapunya akun, tapi perlu history
class GuestHomeActivity : AppCompatActivity() {
    private lateinit var cameraButton: CardView
    private lateinit var rvLimbah: RecyclerView
    private lateinit var textRedirect: CardView
    private lateinit var registerRedirect: CardView
    private var getFile: File? = null
    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Don't get permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        supportActionBar?.hide()

        setUpButton()
        setUpRecyclerView()

        textRedirect = findViewById(R.id.redirect_nearest_hospital)
        cameraButton = findViewById(R.id.camera_button)
        cameraButton.setOnClickListener{
            showImagePickerDialog()
        }
        textRedirect.setOnClickListener {

            val query = "Rumah Sakit Terdekat"
            val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(query)}")


            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)

            mapIntent.setPackage("com.google.android.apps.maps")
            mapIntent.resolveActivity(packageManager)?.let {
                startActivity(mapIntent)
            }

        }

        registerRedirect = findViewById(R.id.redirect_register)
        registerRedirect.setOnClickListener {
            val i = Intent(this@GuestHomeActivity, RegisterActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun setUpRecyclerView(){
        rvLimbah = findViewById(R.id.recyclerView)
        rvLimbah.setHasFixedSize(true)

        list.addAll(getListDummy())
        showRecyclerList()
    }

    private fun setUpButton(){
        val toHistory: TextView = findViewById(R.id.to_history_button)
        toHistory.setOnClickListener {
            val moveIntent = Intent(this@GuestHomeActivity, HistoryLimbahActivity::class.java)
            startActivity(moveIntent)
        }
    }

    private fun showImagePickerDialog() {

        val dialogView = layoutInflater.inflate(R.layout.popup_image_picker, null)

        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setView(dialogView)

        val  mAlertDialog = dialogBuilder.show()

        val btnGallery: Button? = dialogView.findViewById(R.id.btnGallery)
        val btnCamera = dialogView.findViewById<Button>(R.id.btnCamera)
        val textCancel = dialogView.findViewById<TextView>(R.id.textCancel)


        if (btnGallery != null) {
            btnGallery.setOnClickListener {
                Log.d("test","hei")
                mAlertDialog.dismiss()
                // Handle gallery button click
    //            openGalle ry()


            }
        }

        if (btnCamera != null) {
            btnCamera.setOnClickListener {
                // Handle camera button click
    //            openCamera()

            }
        }

        if (textCancel != null) {
            textCancel.setOnClickListener {
                // Handle cancel button click
                mAlertDialog.dismiss()
            }
        }

        mAlertDialog.show()
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }
    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@AddStoryActivity)
                getFile = myFile
                binding.previewImageView.setImageURI(uri)
            }
        }
    }

//    private fun startTakePhoto() {
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        intent.resolveActivity(packageManager)
//        createCustomTempFile(application).also {
//            val photoURI: Uri = FileProvider.getUriForFile(
//                this@AddStoryActivity,
//                "com.example.mystory",
//                it
//            )
//            currentPhotoPath = it.absolutePath
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
//            launcherIntentCamera.launch(intent)
//        }
//    }


    // TODO : Jangan lupa sesuain ini sama output API, terutama bagian pas nambahin list
    private fun showRecyclerList() {
        rvLimbah.layoutManager = LinearLayoutManager(this)
        val listAdapter = HistoryLimbahAdapter(list)
        rvLimbah.adapter = listAdapter
    }

    // TODO : Ini buat bikin dummy. Nanti hapus ini dan di values.strings.xml

    private val list = ArrayList<LimbahDummy>()
    private fun getListDummy(): ArrayList<LimbahDummy> {

        val dataJenis = resources.getStringArray(R.array.data_jenis)
        val dataTanggal = resources.getStringArray(R.array.data_tanggal)

        val listDummy = ArrayList<LimbahDummy>()
        for (i in dataJenis.indices) {
            val dummy = LimbahDummy(dataJenis[i],dataTanggal[i])
            listDummy.add(dummy)
        }

        // TODO : Di halaman ini cuma perlu max 3, nanti sesuain yaa
        if(listDummy.size > 3){
            return ArrayList(listDummy.subList(0, 3))
        } else {
            return listDummy
        }
    }

}