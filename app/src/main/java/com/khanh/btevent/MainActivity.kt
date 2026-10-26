package com.khanh.btevent

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.khanh.btevent.databinding.ActivityMainBinding
import com.khanh.btevent.validator.FormValidator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var lastClickTime: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupEditTextEvents()
        setupRadioEvents()
        setupCheckboxEvents()
        setupRegisterButton()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showToast(@StringRes messageId: Int) {
        showToast(getString(messageId))
    }

    /**
     * Hiển thị Toast khi người dùng bắt đầu thao tác với EditText và xóa lỗi
     * ngay khi nội dung được sửa.
     */
    private fun setupEditTextEvents() {
        configureEditText(
            binding.etFullName,
            binding.tilFullName,
            R.string.label_fullname
        )
        configureEditText(
            binding.etEmail,
            binding.tilEmail,
            R.string.label_email
        )
        configureEditText(
            binding.etPhone,
            binding.tilPhone,
            R.string.label_phone
        )
        configureEditText(
            binding.etStudentId,
            binding.tilStudentId,
            R.string.label_studentid
        )
    }

    private fun configureEditText(
        editText: TextInputEditText,
        inputLayout: TextInputLayout,
        @StringRes fieldLabelId: Int
    ) {
        editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showToast(
                    getString(
                        R.string.toast_editing_field,
                        getString(fieldLabelId)
                    )
                )
            }
        }
        editText.doAfterTextChanged {
            if (inputLayout.error != null) {
                inputLayout.error = null
                inputLayout.isErrorEnabled = false
            }
        }
    }

    /** Hiển thị Toast mỗi khi người dùng chọn giới tính. */
    private fun setupRadioEvents() {
        binding.rgGender.setOnCheckedChangeListener { _, checkedId ->
            val gender = when (checkedId) {
                R.id.rbMale -> getString(R.string.gender_male)
                R.id.rbFemale -> getString(R.string.gender_female)
                R.id.rbOther -> getString(R.string.gender_other)
                else -> return@setOnCheckedChangeListener
            }
            showToast(getString(R.string.toast_gender_selected, gender))
        }
    }

    /**
     * Hiển thị Toast khi chọn/bỏ chọn mạng xã hội. Checkbox điều khoản đồng
     * thời quyết định trạng thái khả dụng của nút Đăng ký theo hướng dẫn Lab 5.
     */
    private fun setupCheckboxEvents() {
        val socialCheckboxes = listOf(
            binding.cbFacebook,
            binding.cbZalo,
            binding.cbTiktok,
            binding.cbInstagram
        )

        socialCheckboxes.forEach { checkBox ->
            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                val messageId = if (isChecked) {
                    R.string.toast_social_selected
                } else {
                    R.string.toast_social_unselected
                }
                showToast(getString(messageId, buttonView.text))
            }
        }

        binding.btnRegister.isEnabled = binding.cbTerms.isChecked
        binding.cbTerms.setOnCheckedChangeListener { _, isChecked ->
            binding.btnRegister.isEnabled = isChecked
            showToast(
                if (isChecked) {
                    R.string.toast_terms_accepted
                } else {
                    R.string.toast_terms_unaccepted
                }
            )
        }
    }

    /** Chống bấm đúp, xác thực form rồi chuyển sang màn hình chi tiết. */
    private fun setupRegisterButton() {
        binding.btnRegister.setOnClickListener {
            val currentTime = SystemClock.elapsedRealtime()
            if (currentTime - lastClickTime < CLICK_DEBOUNCE_DELAY) {
                return@setOnClickListener
            }
            lastClickTime = currentTime

            if (validateForm()) {
                navigateToDetail()
            }
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true
        var firstErrorView: View? = null

        val name = binding.etFullName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val studentId = binding.etStudentId.text.toString().trim()

        if (!FormValidator.isValidName(name)) {
            binding.tilFullName.error = getString(R.string.err_empty_name)
            if (firstErrorView == null) firstErrorView = binding.etFullName
            isValid = false
        }

        if (!FormValidator.isValidEmail(email)) {
            binding.tilEmail.error = getString(R.string.err_invalid_email)
            if (firstErrorView == null) firstErrorView = binding.etEmail
            isValid = false
        }

        if (!FormValidator.isValidPhone(phone)) {
            binding.tilPhone.error = getString(R.string.err_invalid_phone)
            if (firstErrorView == null) firstErrorView = binding.etPhone
            isValid = false
        }

        if (!FormValidator.isValidStudentId(studentId)) {
            binding.tilStudentId.error = getString(R.string.err_empty_studentid)
            if (firstErrorView == null) firstErrorView = binding.etStudentId
            isValid = false
        }

        val selectionError = when {
            binding.rgGender.checkedRadioButtonId == -1 -> R.string.err_gender
            getSelectedSocialList().isEmpty() -> R.string.err_social
            !binding.cbTerms.isChecked -> R.string.err_terms
            else -> null
        }
        selectionError?.let {
            showToast(it)
            isValid = false
        }

        firstErrorView?.requestFocus()
        return isValid
    }

    private fun getSelectedGender(): String {
        return when (binding.rgGender.checkedRadioButtonId) {
            R.id.rbMale -> getString(R.string.gender_male)
            R.id.rbFemale -> getString(R.string.gender_female)
            R.id.rbOther -> getString(R.string.gender_other)
            else -> getString(R.string.value_not_selected)
        }
    }

    private fun getSelectedSocialList(): List<String> = buildList {
        if (binding.cbFacebook.isChecked) add(binding.cbFacebook.text.toString())
        if (binding.cbZalo.isChecked) add(binding.cbZalo.text.toString())
        if (binding.cbTiktok.isChecked) add(binding.cbTiktok.text.toString())
        if (binding.cbInstagram.isChecked) add(binding.cbInstagram.text.toString())
    }

    private fun navigateToDetail() {
        showToast(R.string.toast_registration_success)

        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_FULL_NAME, binding.etFullName.text.toString().trim())
            putExtra(DetailActivity.EXTRA_EMAIL, binding.etEmail.text.toString().trim())
            putExtra(DetailActivity.EXTRA_PHONE, binding.etPhone.text.toString().trim())
            putExtra(DetailActivity.EXTRA_STUDENT_ID, binding.etStudentId.text.toString().trim())
            putExtra(DetailActivity.EXTRA_GENDER, getSelectedGender())
            putExtra(DetailActivity.EXTRA_SOCIAL, getSelectedSocialList().joinToString(", "))
        }
        startActivity(intent)
    }

    private companion object {
        const val CLICK_DEBOUNCE_DELAY = 1_000L
    }
}
