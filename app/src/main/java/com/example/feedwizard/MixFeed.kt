package com.example.feedwizard

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import com.example.feedwizard.databinding.ActivityMixFeedBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MixFeed : AppCompatActivity() {
    private lateinit var binding: ActivityMixFeedBinding
    data class Ingredient(val name: String, val amount: Double)

    private var ingredients: List<Ingredient> = emptyList()
    private var currentIngredientIndex: Int = 0
    private var startWeight: Double = 0.0
    private var targetWeight: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMixFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.completeMix.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        ingredients = getIngredientsList()
        startWeight = getStartWeight()

        if (ingredients.isNotEmpty()) {
            targetWeight = startWeight + ingredients[currentIngredientIndex].amount
        } else {
            targetWeight = startWeight
        }

        updateUI()

        binding.minus.setOnClickListener {
            if (targetWeight > 0) {
                targetWeight -= 10.0
            }
            updateUI()
        }

        binding.plus.setOnClickListener {
            targetWeight += 10.0
            updateUI()
        }

        binding.nextIngredient.setOnClickListener {
            if (currentIngredientIndex < ingredients.size - 1) {
                currentIngredientIndex++
                targetWeight += ingredients[currentIngredientIndex].amount
                updateUI()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun getIngredientsList(): List<Ingredient> {
        val gson = Gson()
        val sharedPreferences = getSharedPreferences("com.example.app.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
        val ingredientsJson = sharedPreferences.getString("ingredientsList", "")
        val type = object : TypeToken<List<Ingredient>>() {}.type
        return gson.fromJson(ingredientsJson, type) ?: emptyList()
    }

    private fun getStartWeight(): Double {
        val sharedPreferences = getSharedPreferences("com.example.app.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
        return sharedPreferences.getString("startWeightAmount", "0")?.toDouble() ?: 0.0
    }

    private fun updateUI() {
        binding.targetWeightValue.text = String.format("%.1f", targetWeight)
        binding.startWeightValue.text = String.format("%.1f", startWeight)

        if (currentIngredientIndex < ingredients.size) {
            val currentIngredient = ingredients[currentIngredientIndex]
            binding.nowLoadingText.text = "Now Loading: ${currentIngredient.name} (${currentIngredient.amount} lb)"
        } else {
            binding.nowLoadingText.visibility = View.GONE
            binding.nextIngredient.visibility = View.GONE
        }
    }
}
