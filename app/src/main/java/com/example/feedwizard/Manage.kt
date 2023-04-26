package com.example.feedwizard

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import com.example.feedwizard.databinding.ActivityManageBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Manage : AppCompatActivity() {
    private lateinit var binding: ActivityManageBinding

    data class Ingredient(val name: String, val amount: Double)

    private val ingredientsList = mutableListOf<Ingredient>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityManageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startMixButton.setOnClickListener {
            val intent = Intent(this, MixFeed::class.java)
            startActivity(intent)
        }

        binding.addIngredientButton.setOnClickListener {
            val name = binding.ingredientName.text.toString()
            val amount = binding.ingredientAmount.text.toString().toDoubleOrNull()

            if (name.isNotBlank() && amount != null) {
                val ingredient = Ingredient(name, amount)
                ingredientsList.add(ingredient)
                binding.ingredientName.text.clear()
                binding.ingredientAmount.text.clear()

                Toast.makeText(this, "Inventory Item Added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.startMixButton.setOnClickListener {
            val startWeight = binding.startWeightAmount.text.toString()
            val batch = binding.batchName.text.toString()

            if (ingredientsList.isNotEmpty() && startWeight.isNotBlank() && batch.isNotBlank()) {
                val sharedPreferences: SharedPreferences = getSharedPreferences("com.example.app.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                val gson = Gson()

                val ingredientsJson = gson.toJson(ingredientsList)
                editor.putString("ingredientsList", ingredientsJson)
                editor.putString("startWeightAmount", startWeight)
                editor.putString("batchName", batch)
                editor.apply()

                val intent = Intent(this, MixFeed::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please fill in all fields and add at least one ingredient", Toast.LENGTH_LONG).show()
            }
        }
    }
}