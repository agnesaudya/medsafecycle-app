package com.example.medsafecycle.home.popup

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
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
import com.example.medsafecycle.*
import com.example.medsafecycle.UserPreference
import com.example.medsafecycle.limbah.LimbahNotFoundActivity
import com.example.medsafecycle.viewmodel.HospitalPopupViewModel
import com.example.medsafecycle.viewmodel.guest.PopupViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class HospitalPopup : DialogFragment(), DialogInterface.OnDismissListener{
    private var onDismissListener: DialogInterface.OnDismissListener? = null
    private var getFile: File? = null
    private lateinit var bgImage: ImageView
    private lateinit var progressBar: ProgressBar

    private lateinit var mUserPreference: UserPreference
    private val hospitalPopupViewModel: HospitalPopupViewModel by viewModels()
    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    fun setOnDismissListener(listener: DialogInterface.OnDismissListener) {
        onDismissListener = listener
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissListener?.onDismiss(dialog)
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


    private fun showResult(res: UploadResponse) {
        if(res.message=="berhasil terupload"){
            Toast.makeText(requireActivity(), res.message, Toast.LENGTH_SHORT).show()
            dismiss()


        }else if(res.message=="Terjadi kesalahan"){
            Toast.makeText(requireActivity(), "Tidak berhasil terupload", Toast.LENGTH_SHORT).show()
            redirectResultNotFound()

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


        mUserPreference = UserPreference(requireActivity())

        hospitalPopupViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        hospitalPopupViewModel.scanResponse.observe(this) {
            showResult(it)
        }

        val btnGallery: Button? = view.findViewById(R.id.btnGallery)
        val btnCamera = view.findViewById<Button>(R.id.btnCamera)
        val textCancel = view.findViewById<ImageView>(R.id.closeButton)
        val btnUpload = view.findViewById<Button>(R.id.btnScan)


        progressBar = view.findViewById(R.id.progressBar)
        bgImage = view.findViewById(R.id.scan_img)


        btnUpload?.setOnClickListener{
            scanImage()
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

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


    private fun scanImage() {
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "file",
                file.name,
                requestImageFile
            )
            mUserPreference.getToken()?.let { hospitalPopupViewModel.scan(it,imageMultipart) }



        } else {
            Toast.makeText(requireActivity(), "Silahkan, pilih foto terlebih dahulu.", Toast.LENGTH_SHORT).show()
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