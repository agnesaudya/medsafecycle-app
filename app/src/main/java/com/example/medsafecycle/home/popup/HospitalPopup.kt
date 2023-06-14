package com.example.medsafecycle.home.popup

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.medsafecycle.AuthResponse
import com.example.medsafecycle.R
import com.example.medsafecycle.createCustomTempFile
import com.example.medsafecycle.limbah.LimbahNotFoundActivity
import com.example.medsafecycle.uriToFile
import com.example.medsafecycle.viewmodel.PopupViewModel
import java.io.File

class HospitalPopup : DialogFragment() {
    private var getFile: File? = null
    private lateinit var bgImage: ImageView
    private lateinit var progressBar: ProgressBar
    private val popupViewModel by viewModels<PopupViewModel>()
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
                    requireActivity(),
                    "Don't get permission.",
                    Toast.LENGTH_SHORT
                ).show()
                requireActivity().finish()
            }
        }
    }

    private fun showResult(res: AuthResponse) {
        if(res.status=="berhasil terupload"){
            Toast.makeText(requireActivity(), res.message, Toast.LENGTH_SHORT).show()


        }else{
            Toast.makeText(requireActivity(), "Tidak berhasil terupload", Toast.LENGTH_SHORT).show()

            redirectResultNotFound()
            // TODO : Tambahan avel buat bikin home. Ubah ajaa gapapa
//            val moveIntent = Intent(requireActivity(), HospitalHomeActivityBase::class.java)
//            startActivity(moveIntent)
//            requireActivity().finish()
        }
    }



    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun redirectResultNotFound() {
        val i = Intent(requireActivity(), LimbahNotFoundActivity::class.java)
        startActivity(i)
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireActivity(), it) == PackageManager.PERMISSION_GRANTED
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.popup_image_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
        val btnGallery: Button? = view.findViewById(R.id.btnGallery)
        val btnCamera = view.findViewById<Button>(R.id.btnCamera)
        val textCancel = view.findViewById<ImageView>(R.id.closeButton)
        progressBar = view.findViewById(R.id.progressBar)
        bgImage = view.findViewById(R.id.scan_img)

        btnGallery?.setOnClickListener {
            startGallery()


        }

        btnCamera?.setOnClickListener {
            startTakePhoto()

        }

        textCancel?.setOnClickListener {
            dismiss()
        }


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
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, requireActivity())
                getFile = myFile
                bgImage.setImageURI(uri)
            }
        }
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireActivity().packageManager)
        createCustomTempFile(requireActivity()).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireActivity(),
                "com.example.medsafecycle",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }


    private lateinit var currentPhotoPath: String
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            val myFile = File(currentPhotoPath)
            myFile.let { file ->
                getFile = file
                bgImage.setImageBitmap((BitmapFactory.decodeFile(file.path)))
            }

        }
    }


}