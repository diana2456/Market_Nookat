package nook.test.market_nookat.ui.fragment.profile.active.edit

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import nook.test.market_nookat.R
import nook.test.market_nookat.data.model.AdsOne
import nook.test.market_nookat.data.result.Status
import nook.test.market_nookat.databinding.FragmentEditActiveBinding
import nook.test.market_nookat.ui.base.BaseFragment
import nook.test.market_nookat.ui.fragment.add.AddFragment
import nook.test.market_nookat.ui.fragment.add.YourViewPagerAdapter
import nook.test.market_nookat.ui.fragment.add.adapter.ImageAdapter
import nook.test.market_nookat.ui.fragment.add.adapter.ImageModel
import nook.test.market_nookat.ui.fragment.add.adapter.SelectedImageAdapter
import nook.test.market_nookat.ui.fragment.filter.AdapterDire
import nook.test.market_nookat.ui.fragment.profile.active.ads_active.AdsActiveFragment.Companion.KO
import nook.test.market_nookat.ui.fragment.util.Pref
import nook.test.market_nookat.ui.fragment.util.showToast
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class EditActiveFragment : BaseFragment<EditActiveViewModel,FragmentEditActiveBinding>() {

    override val viewModel: EditActiveViewModel by viewModel()
    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?, boolean: Boolean): FragmentEditActiveBinding {
        return FragmentEditActiveBinding.inflate(inflater,container,false)
    }

    private val imageList = ArrayList<ImageModel>()
    private lateinit var model: AdsOne
    private var category = ""
    private var currency = ""
    private var token = ""
    private var address = ""
    private val parts = mutableListOf<File>()
    private val selectedImageList = arrayListOf<String>()
    private lateinit var selectedImageAdapter: SelectedImageAdapter
    private lateinit var imageAdapter: ImageAdapter
    private val projection = arrayOf(MediaStore.MediaColumns.DATA)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root
    }

    override fun initFragment() {
        token()
        onViewModel()
        onView()
        if (isStoragePermissionGranted()) {
            getAllImages()
            setImageList()
            setSelectedImageList()
        }
    }

    override fun initResume() {
        TODO("Not yet implemented")
    }

    override fun initPause() {
        TODO("Not yet implemented")
    }

    override fun initDestroy() {
        TODO("Not yet implemented")
    }

        private fun setImageList() {
            imageAdapter = ImageAdapter(requireContext(), imageList).apply {

                setOnItemClickListener(object : ImageAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int, v: View) {
                        when (position) {
                            else -> {
                                try {
                                    if (!imageList[position].isSelected) {
                                        selectImage(position)
                                    } else {
                                        unSelectImage(position)
                                    }
                                } catch (ed: ArrayIndexOutOfBoundsException) {
                                    ed.printStackTrace()
                                }
                            }
                        }
                    }
                })
            }
            binding.rvPhoto.adapter = imageAdapter
        }

        private fun setSelectedImageList() {
            selectedImageAdapter = SelectedImageAdapter(requireContext(), selectedImageList)
            binding.btnAddPhoto.adapter = selectedImageAdapter
        }

        @SuppressLint("Range")
        private fun getAllImages() {
            imageList.clear()
            val cursor: Cursor? = activity?.contentResolver?.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, null
            )
            Log.i("xsdcfvgbh", "getAllImages: $projection")
            cursor?.use {
                while (it.moveToNext()) {
                    val absolutePathOfImage: String =
                        it.getString(it.getColumnIndex(MediaStore.MediaColumns.DATA))
                    val imageModel = ImageModel(
                        image = absolutePathOfImage,
                        title = "",
                        resImg = 0,
                        isSelected = false
                    )
                    imageList.add(imageModel)
                    Log.i("xikjnhgh", "getAllImages: ${imageList}")

                }
            }
        }

        private fun selectImage(position: Int) {
            if (!selectedImageList.contains(imageList[position].image)) {
                imageList[position].isSelected = true
                selectedImageList.add(0, imageList[position].image)
                selectedImageAdapter.notifyDataSetChanged()
                imageAdapter.notifyDataSetChanged()
                val imageFile = File(imageList[position].image)
                parts.add(imageFile)
            }

        }

        private fun unSelectImage(position: Int) {
            val iterator = selectedImageList.iterator()
            while (iterator.hasNext()) {
                val item = iterator.next()
                if (imageList[position].image != null && item == imageList[position].image) {
                    imageList[position].isSelected = false
                    iterator.remove()
                    selectedImageAdapter.notifyDataSetChanged()
                    imageAdapter.notifyDataSetChanged()
                }
            }
        }

        private fun isStoragePermissionGranted(): Boolean {
            val accessExternalStorage = ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            if (accessExternalStorage != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    AddFragment.STORAGE_PERMISSION
                )
                return false
            }
            return true
        }

        override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<out String>, grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            if (requestCode == AddFragment.STORAGE_PERMISSION && grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                getAllImages()
                setImageList()
                setSelectedImageList()
            }
        }


        private fun token() {
            lifecycleScope.launch {
                viewModel.loginUser(
                    Pref(requireContext()).isLogin(),
                    Pref(requireContext()).isPasword()
                ).collect { data ->
                    when (data.status) {
                        Status.SUCCESS -> {
                            token = data.data?.token.toString()
                            Log.i("cdvfbg", "token:$token")
                        }

                        Status.LOADING -> {}
                        Status.ERROR -> {}
                    }
                }
            }
        }


    private fun onView() {
        binding.ivBackAdd.setOnClickListener {
            findNavController().navigateUp()
        }
        lifecycleScope.launch {
            viewModel.getCategory().collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        val adapter = it.data?.data?.let { it1 ->
                            AdapterDire(
                                requireContext(),
                                R.layout.list_item, it1
                            )
                        }
                        binding.tvCategory.setAdapter(adapter)
                        binding.tvCategory.onItemClickListener =
                            AdapterView.OnItemClickListener { _, _, i, _ ->
                                val selectedItem = adapter?.getItem(i)
                                if (selectedItem != null) {
                                    binding.tvCategory.setText(selectedItem.name, false)
                                    category = selectedItem.id.toString()
                                }
                            }
                    }

                    Status.LOADING -> {}
                    Status.ERROR -> {}
                }
            }
        }
        lifecycleScope.launch {
            viewModel.getLocation().collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        val adapter = it.data?.data?.let { it1 ->
                            AdapterDire(
                                requireContext(),
                                R.layout.list_item, it1
                            )
                        }
                        binding.tvAdress.setAdapter(adapter)
                        binding.tvAdress.onItemClickListener =
                            AdapterView.OnItemClickListener { _, _, i, _ ->
                                val selectedItem = adapter?.getItem(i)
                                if (selectedItem != null) {
                                    binding.tvAdress.setText(selectedItem.name, false)
                                    address = selectedItem.id.toString()
                                }
                            }
                    }

                    Status.LOADING -> {}

                    Status.ERROR -> {}
                }
            }
        }
        lifecycleScope.launch {
            viewModel.getCurrency().collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        val adapter = it.data?.data?.let { it1 ->
                            AdapterDire(
                                requireContext(),
                                R.layout.list_item,
                                it1
                            )
                        }
                        binding.tvValute.setAdapter(adapter)
                        binding.tvValute.onItemClickListener =
                            AdapterView.OnItemClickListener { _, _, i, _ ->
                                val selectedItem = adapter?.getItem(i)
                                if (selectedItem != null) {
                                    binding.tvValute.setText(selectedItem.name, false)
                                    currency = selectedItem.id.toString()
                                }
                            }
                    }

                    Status.LOADING -> {}
                    Status.ERROR -> {}
                }
            }
        }
    }


    private fun onViewModel() {
        if (arguments != null) {
            model = arguments?.getSerializable(KO) as AdsOne

            lifecycleScope.launch {
                viewModel.getOneAds(model.id).collect { data ->
                    when (data.status) {
                        Status.SUCCESS -> {
                            binding.tvAdress.setText(data.data?.data?.location, false)
                            binding.etPrice.setText(model.price)
                            binding.tvValute.setText(data.data?.data?.currency, false)
                            binding.etNumberPhoneTwo.setText(model.phone)
                            binding.etNumberWhatsapp.setText(model.whatsapp)
                            binding.etMessage.setText(model.content)
                            binding.tvCategory.setText(data.data?.data?.category, false)
                        }

                        Status.LOADING -> {}
                        Status.ERROR -> {}
                    }
                }
            }

            binding.btnAdd.setOnClickListener {
                val tokenWith = "Bearer $token"
                val photoParts = parts.mapIndexed { _, file ->
                    val photoRequestBody =
                        file.asRequestBody("application/octet-stream".toMediaType())
                    MultipartBody.Part.createFormData("photo[]", file.name, photoRequestBody)
                }
                val put = "PUT"
                lifecycleScope.launch {
                    viewModel.addAds(
                        tokenWith,
                        model.id,
                        put.toRequestBody("text/plain".toMediaType()),
                        binding.etMessage.text.toString().toRequestBody("text/plain".toMediaType()),
                        binding.etPrice.text.toString().toRequestBody("text/plain".toMediaType()),
                        binding.etNumberPhoneTwo.text.toString()
                            .toRequestBody("text/plain".toMediaType()),
                        binding.etNumberWhatsapp.text.toString()
                            .toRequestBody("text/plain".toMediaType()),
                        currency.toRequestBody("text/plain".toMediaType()),
                        address.toRequestBody("text/plain".toMediaType()),
                        category.toRequestBody("text/plain".toMediaType()),
                        photoParts
                    ).collect {
                        when (it.status) {
                            Status.SUCCESS -> {
                                Log.i("rfgthy", "onViewModel:$it")
                                Log.i("rdfry", "onViewModel:$photoParts")
                                showToast(requireContext(), "Данные отправлены!!")
                            }

                            Status.LOADING -> {

                            }
                            Status.ERROR -> {
                                Log.i("ophy", "onViewModel:${it.message}")
                                Log.i("aswey", "onViewModel:$photoParts")

                            }
                        }
                    }
                }
            }
        }
    }
}