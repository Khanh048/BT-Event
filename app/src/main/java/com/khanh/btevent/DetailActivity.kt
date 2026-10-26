package com.khanh.btevent

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.khanh.btevent.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvDetailFullName.text = getExtraOrFallback(EXTRA_FULL_NAME)
        binding.tvDetailEmail.text = getExtraOrFallback(EXTRA_EMAIL)
        binding.tvDetailPhone.text = getExtraOrFallback(EXTRA_PHONE)
        binding.tvDetailStudentId.text = getExtraOrFallback(EXTRA_STUDENT_ID)
        binding.tvDetailGender.text = getExtraOrFallback(EXTRA_GENDER)
        binding.tvDetailSocial.text = getExtraOrFallback(
            EXTRA_SOCIAL,
            R.string.value_no_social_network
        )

        Toast.makeText(
            this,
            R.string.toast_detail_displayed,
            Toast.LENGTH_SHORT
        ).show()

        binding.btnBack.setOnClickListener {
            Toast.makeText(
                this,
                R.string.toast_back_to_registration,
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }

    private fun getExtraOrFallback(
        key: String,
        fallbackId: Int = R.string.value_no_data
    ): String {
        return intent.getStringExtra(key).orEmpty().ifBlank { getString(fallbackId) }
    }

    companion object {
        const val EXTRA_FULL_NAME = "EXTRA_FULL_NAME"
        const val EXTRA_EMAIL = "EXTRA_EMAIL"
        const val EXTRA_PHONE = "EXTRA_PHONE"
        const val EXTRA_STUDENT_ID = "EXTRA_STUDENT_ID"
        const val EXTRA_GENDER = "EXTRA_GENDER"
        const val EXTRA_SOCIAL = "EXTRA_SOCIAL"
    }
}
