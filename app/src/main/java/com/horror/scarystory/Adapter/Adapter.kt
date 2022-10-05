package com.horror.scarystory.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.horror.scarystory.R
import com.horror.scarystory.Activity.StoryActivity

class Adapter(private val context: Context) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    var datas = mutableListOf<AdapterData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.word_view,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val txtName: TextView = itemView.findViewById(R.id.list_txt)
        private val list_line: TextView = itemView.findViewById(R.id.list_line)
        private val list_number: TextView = itemView.findViewById(R.id.list_number)

        fun bind(item: AdapterData) {
            txtName.text = item.Title
            list_number.text = "${item.position+1}"

            if(item.value)
            {
                txtName.setTextColor(Color.parseColor("#858585"))
                list_line.setTextColor(Color.parseColor("#858585"))
                list_number.setTextColor(Color.parseColor("#858585"))
            }
            else{
                txtName.setTextColor(Color.parseColor("#EAEAEA"))
                list_line.setTextColor(Color.parseColor("#EAEAEA"))
                list_number.setTextColor(Color.parseColor("#EAEAEA"))
            }

            itemView.setOnClickListener {

                val intent = Intent(context, StoryActivity::class.java)
                intent.putExtra("All", item)
                intent.run {
                    context.startActivity(this)
                }

            }

        }

    }

}
