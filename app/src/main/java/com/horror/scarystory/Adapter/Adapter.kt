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
        private val listLine: TextView = itemView.findViewById(R.id.list_line)
        private val listNumber: TextView = itemView.findViewById(R.id.list_number)

        val view = view

        fun bind(item: AdapterData) {
            txtName.text = item.Title
            listNumber.text = "${item.position+1}"

            val color = if(item.value) {
                "#858585"
            } else{
                "#EAEAEA"
            }

            txtName.setTextColor(Color.parseColor(color))
            listLine.setTextColor(Color.parseColor(color))
            listNumber.setTextColor(Color.parseColor(color))

            itemView.setOnClickListener {

                val intent = Intent(context, StoryActivity::class.java)
                intent.putExtra("position",item.position)
                intent.putExtra("tag",item.tag)
                intent.run {
                    context.startActivity(this)
                }

                listener?.onItemClick(view)

            }

        }

    }

    interface OnItemClickListener {
        fun onItemClick(v: View)
    }

    private var listener : OnItemClickListener? = null

    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

}
