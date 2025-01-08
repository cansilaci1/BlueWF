package com.example.BlueWF.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.BlueWF.R

class PredictionAdapter(private val predictions: List<String>) :
    RecyclerView.Adapter<PredictionAdapter.PredictionViewHolder>() {

    class PredictionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val predictionText: TextView = itemView.findViewById(R.id.tvPrediction)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PredictionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_prediction, parent, false)
        return PredictionViewHolder(view)
    }

    override fun onBindViewHolder(holder: PredictionViewHolder, position: Int) {
        holder.predictionText.text = predictions[position]
    }

    override fun getItemCount(): Int {
        return predictions.size
    }
}
