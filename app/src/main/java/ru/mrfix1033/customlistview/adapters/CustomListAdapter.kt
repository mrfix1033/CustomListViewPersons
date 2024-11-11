package ru.mrfix1033.customlistview.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import org.w3c.dom.Text
import ru.mrfix1033.customlistview.R
import ru.mrfix1033.customlistview.data.Person

class CustomListAdapter(context: Context, personList: MutableList<Person>) :
    ArrayAdapter<Person>(context, R.layout.list_item, personList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val person = getItem(position)!!
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)!!
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val textViewName = view.findViewById<TextView>(R.id.textViewName)
        val textViewAge = view.findViewById<TextView>(R.id.textViewAge)
        val textViewPhoneNumber = view.findViewById<TextView>(R.id.textViewPhoneNumber)
        person.image?.let { imageView.setImageBitmap(it) }
            ?: imageView.setImageResource(R.drawable.baseline_account_circle_24)
        textViewName.text = person.name
        textViewAge.text = person.age.toString()
        textViewPhoneNumber.text = person.phoneNumber
        return view
    }
}