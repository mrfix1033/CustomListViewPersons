package ru.mrfix1033.customlistview

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.mrfix1033.customlistview.adapters.CustomListAdapter
import ru.mrfix1033.customlistview.data.Person
import ru.mrfix1033.customlistview.enumerations.ResultCode

class MainActivity : AppCompatActivity() {

    private var bitmap: Bitmap? = null
    private lateinit var imageViewAvatar: ImageView
    private lateinit var editTextName: EditText
    private lateinit var editTextAge: EditText
    private lateinit var editTextPhoneNumber: EditText
    private lateinit var buttonSave: Button
    private lateinit var listView: ListView

    private val personsList =  mutableListOf<Person>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()

        imageViewAvatar.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(intent, ResultCode.PICK_FROM_GALLERY)
        }

        buttonSave.setOnClickListener {
            if (!createPerson()) return@setOnClickListener
            setOrUpdateListViewAdapter()
            clearEditFields()
        }
    }

    private fun createPerson(): Boolean {
        val personName = editTextName.text.toString()
        val personAge = editTextAge.text.toString().toIntOrNull()
        if (personAge == null) {
            Toast.makeText(this, "В возрасте введено не число!", Toast.LENGTH_SHORT)
            return false
        }
        val personPhoneNumber = editTextPhoneNumber.text.toString()
        val personImage = bitmap
        val person = Person(personName, personAge, personPhoneNumber, personImage)
        personsList.add(person)
        return true
    }

    private fun setOrUpdateListViewAdapter() {
        val listAdapter = CustomListAdapter(this, personsList)
        listView.adapter = listAdapter
        listAdapter.notifyDataSetChanged()
    }

    private fun clearEditFields() {
        listOf(editTextName, editTextAge, editTextPhoneNumber).forEach { it.text.clear() }
        imageViewAvatar.setImageResource(R.drawable.baseline_account_circle_24)
    }

    private fun init() {
        imageViewAvatar = findViewById(R.id.imageViewAvatar)
        editTextName = findViewById(R.id.editTextName)
        editTextAge = findViewById(R.id.editTextAge)
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber)
        buttonSave = findViewById(R.id.buttonSave)
        listView = findViewById(R.id.listView)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            ResultCode.PICK_FROM_GALLERY -> {
                if (resultCode == RESULT_OK) {
                    val selectedImageUri = data!!.data
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
                    imageViewAvatar.setImageURI(selectedImageUri)
                }
            }
        }
    }
}