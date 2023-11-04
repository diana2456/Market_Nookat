package nook.test.market_nookat.ui.fragment.add

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
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
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import nook.test.market_nookat.R
import nook.test.market_nookat.data.result.Status
import nook.test.market_nookat.databinding.FragmentAddBinding
import nook.test.market_nookat.ui.base.BaseFragment
import nook.test.market_nookat.ui.fragment.add.adapter.ImageAdapter
import nook.test.market_nookat.ui.fragment.add.adapter.ImageModel
import nook.test.market_nookat.ui.fragment.add.adapter.SelectedImageAdapter
import nook.test.market_nookat.ui.fragment.filter.AdapterDire
import nook.test.market_nookat.ui.fragment.settings.LocateHelper
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


class AddFragment :  BaseFragment<AddViewModel,FragmentAddBinding>()  {

    companion object {
        const val STORAGE_PERMISSION = 100
    }

    override val viewModel: AddViewModel by viewModel()
    override fun inflateViewBinding(inflater: LayoutInflater,container: ViewGroup?,boolean: Boolean): FragmentAddBinding {
        return FragmentAddBinding.inflate(inflater,container,false)
    }

    private var category = ""
    private var currency = ""
    private var token = ""
    private val imageList = ArrayList<ImageModel>()
    private val parts = mutableListOf<File>()
    private val selectedImageList = arrayListOf<String>()
    private var address = ""
    private lateinit var selectedImageAdapter: SelectedImageAdapter
    private lateinit var imageAdapter: ImageAdapter
    private val projection = arrayOf(MediaStore.MediaColumns.DATA)



    override fun initFragment() {
        initView()
        token()
        click()
        val selectedLanguage = Pref(requireContext()).getSelectedLanguage()
        if (selectedLanguage == "ru") {
            showRuText()
        } else {
            showKgText()
        }
        if (isStoragePermissionGranted()) {
            getAllImages()
            setImageList()
            setSelectedImageList()
        }
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

            Log.i("huji9o", "selectImage:$selectedImageList")
            Log.i("okmk", "selectImage:${imageList[position].image}")
            val imageFile = File(imageList[position].image)
            parts.add(imageFile)
            Log.i("cdvfbg", "selectImage:$parts")
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
                requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION && grantResults.isNotEmpty() &&
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
                    }

                    Status.LOADING -> {}
                    Status.ERROR -> {}
                }
            }
        }
    }


    private fun click() {
        binding.btnAdd.setOnClickListener {
            onLoginClicked()
        }
        binding.ivBackAdd.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun onLoginClicked() {
        if (binding.etMessage.text?.isNotEmpty() == true && binding.etPrice.text?.isNotEmpty() == true && binding.etNumberPhoneTwo.text?.isNotEmpty() == true && binding.etNumberWhatsapp.text?.isNotEmpty() == true && currency.isNotEmpty() && address.isNotEmpty() && category.isNotEmpty()) {
            addData()
        } else {
            showToast(requireContext(), "Заполните все поля!!")
        }
    }
    private fun addData() {
        val tokenWith = "Bearer $token"
        Log.i("asxdcf", "addData:$tokenWith")
        val photoParts = parts.mapIndexed { index, file ->
            val photoRequestBody = file.asRequestBody("application/octet-stream".toMediaType())
            MultipartBody.Part.createFormData("photo[$index]", file.name, photoRequestBody)
        }
        Log.i("nbvcx", "addData:$photoParts")
         lifecycleScope.launch {
            viewModel.addAds(
                tokenWith,
                binding.etMessage.text.toString().toRequestBody("text/plain".toMediaType()),
                binding.etPrice.text.toString().toRequestBody("text/plain".toMediaType()),
                binding.etNumberPhoneTwo.text.toString().toRequestBody("text/plain".toMediaType()),
                binding.etNumberWhatsapp.text.toString().toRequestBody("text/plain".toMediaType()),
                currency.toRequestBody("text/plain".toMediaType()),
                address.toRequestBody("text/plain".toMediaType()),
                category.toRequestBody("text/plain".toMediaType()),
                photoParts
            ).collect { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        showToast(requireContext(), "Данные отправлены!")
                        Log.i("rfgthy", "onViewModel:$result")
                        Log.i("rdfry", "onViewModel:$photoParts")
                    }

                    Status.LOADING -> {}

                    Status.ERROR -> {
                        Log.i("defroiu", "addData:${result.message}")
                        showToast(
                            requireContext(),
                            "Ошибка при отправке данных убедитесь что вы прошли регестрацию!!"
                        )
                    }
                }
            }
        }
    }


    private fun initView(){
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

                    Status.LOADING -> {
                    }

                    Status.ERROR -> {
                    }
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


    private fun showKgText(){
        val context = LocateHelper().setLocale(requireContext(),"kg")
        val resource = context.resources
        binding.tvWrite.text = resource.getString(R.string.write_advertisements)
        binding.tvPho.text = resource.getString(R.string.selected_photos)
        binding.etMessage.hint = resource.getString(R.string.description)
        binding.tvField.text = resource.getString(R.string.filling_conditions)
        binding.tvCategory.hint = resource.getString(R.string.category)
        binding.tvValute.hint = resource.getString(R.string.currency)
        binding.etPrice.hint = resource.getString(R.string.price)
        binding.tvAdress.hint = resource.getString(R.string.address)
        binding.etNumberPhoneTwo.hint = resource.getString(R.string.your_phone_number)
        binding.etNumberWhatsapp.hint = resource.getString(R.string.whatsapp_number)
        binding.btnAdd.text = resource.getString(R.string.publish)
    }

    private fun showRuText(){
        val context = LocateHelper().setLocale(requireContext(),"ru")
        val resource = context.resources
        binding.tvWrite.text = resource.getString(R.string.write_advertisements)
        binding.tvPho.text = resource.getString(R.string.selected_photos)
        binding.etMessage.hint = resource.getString(R.string.description)
        binding.tvField.text = resource.getString(R.string.filling_conditions)
        binding.tvCategory.hint = resource.getString(R.string.category)
        binding.tvValute.hint = resource.getString(R.string.currency)
        binding.etPrice.hint = resource.getString(R.string.price)
        binding.tvAdress.hint = resource.getString(R.string.address)
        binding.etNumberPhoneTwo.hint = resource.getString(R.string.your_phone_number)
        binding.etNumberWhatsapp.hint = resource.getString(R.string.whatsapp_number)
        binding.btnAdd.text = resource.getString(R.string.publish)
    }

    override fun initResume() {}

    override fun initPause() {}

    override fun initDestroy() {}

}