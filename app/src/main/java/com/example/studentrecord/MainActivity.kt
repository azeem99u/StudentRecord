package com.example.studentrecord

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentrecord.arch.MainViewModel
import com.example.studentrecord.database.AppDatabase
import com.example.studentrecord.database.entity.ItemEntity
import com.example.studentrecord.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.init(AppDatabase.getDatabase(this))

        val tableRecyclerView = binding.tableRecyclerView

        val tableRowAdapter = TableRowAdapter(viewModel)
        tableRecyclerView.layoutManager = LinearLayoutManager(this)
        tableRecyclerView.adapter = tableRowAdapter

        viewModel.categoryEntitiesLiveData.observe(this) {
            tableRowAdapter.setData(it as ArrayList<ItemEntity>)
        }


        binding.floatingActionButton.setOnClickListener {

            var isNameFilled = false;
            var isClassFilled = false;
            var isLocationFilled = false;
            var isEmailFilled = false;
            var isDateOfBirthFilled = false;
            val builder = MaterialAlertDialogBuilder(this)
            val context = builder.context;
             val myScroll = ScrollView(context);
            val layout = LinearLayout(context)
            layout.orientation = LinearLayout.VERTICAL
            builder.setIcon(R.drawable.ic_baseline_add_24)
            builder.setTitle("Add Student")

            val nameEt = EditText(this)
            nameEt.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            nameEt.hint = "Enter Name"
            layout.addView(nameEt)

            val classEt = EditText(this)
            classEt.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            classEt.hint = "Enter Class"
            layout.addView(classEt)
            val locationEt = EditText(this)
            locationEt.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            locationEt.hint = "Enter Location"
            layout.addView(locationEt)

            val emailEt = EditText(this)
            emailEt.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            emailEt.hint = "Enter Email"
            layout.addView(emailEt)

            val dateOfBirthEt = EditText(this)
            dateOfBirthEt.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_DATETIME_VARIATION_DATE
            dateOfBirthEt.hint = "Set Date Of Birth"
            dateOfBirthEt.inputType = InputType.TYPE_NULL;
            layout.addView(dateOfBirthEt)
            myScroll.addView(layout)
            builder.setView(myScroll)

            dateOfBirthEt.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        val cldr = Calendar.getInstance()
                        val day = cldr[Calendar.DAY_OF_MONTH]
                        val month = cldr[Calendar.MONTH]
                        val year = cldr[Calendar.YEAR]
                        @SuppressLint("SetTextI18n") val picker = DatePickerDialog(
                            this,
                            { view: DatePicker?, year1: Int, monthOfYear: Int, dayOfMonth: Int ->
                                dateOfBirthEt.setText(
                                    dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year1
                                )
                            }, year, month, day
                        )
                        picker.show()
                    }
                }

            dateOfBirthEt.setOnClickListener {
                val cldr = Calendar.getInstance()
                val day = cldr[Calendar.DAY_OF_MONTH]
                val month = cldr[Calendar.MONTH]
                val year = cldr[Calendar.YEAR]
                @SuppressLint("SetTextI18n") val picker = DatePickerDialog(
                    this,
                    { view: DatePicker?, year1: Int, monthOfYear: Int, dayOfMonth: Int ->
                        dateOfBirthEt.setText(
                            dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year1
                        )
                    }, year, month, day
                )
                picker.show()
            }



            builder.setPositiveButton("OK") { d, _ ->
                val name = nameEt.text.toString();
                val class_ = classEt.text.toString();
                val location = locationEt.text.toString();
                val email = emailEt.text.toString();
                val dateOfBirth = dateOfBirthEt.text.toString();

                viewModel.let {
                    it.insertItem(ItemEntity(name, email, class_, location, dateOfBirth))
                }

            }
            builder.setNegativeButton("CANCEL") { d, _ ->
                d.cancel()
            }
            builder.setCancelable(false)
            val dialog = builder.create();
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
            nameEt.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable) {

                    if (!TextUtils.isEmpty(s)) {
                        isNameFilled = true
                        if (isNameFilled && isEmailFilled && isClassFilled && isDateOfBirthFilled && isLocationFilled) {
                            (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).isEnabled =
                                true
                        }
                    } else {
                        isNameFilled = false;
                        (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).isEnabled =
                            false
                    }


                }
            })


            classEt.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable) {

                    if (!TextUtils.isEmpty(s)) {
                        isClassFilled = true
                        if (isNameFilled && isEmailFilled && isClassFilled && isDateOfBirthFilled && isLocationFilled) {
                            (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).isEnabled =
                                true
                        }
                    } else {
                        isClassFilled = false;
                        (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).isEnabled =
                            false
                    }

                }
            })

            locationEt.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable) {

                    if (!TextUtils.isEmpty(s)) {
                        isLocationFilled = true
                        if (isNameFilled && isEmailFilled && isClassFilled && isDateOfBirthFilled && isLocationFilled) {
                            (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).isEnabled =
                                true
                        }
                    } else {
                        isLocationFilled = false;
                        (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).isEnabled =
                            false
                    }
                }
            })

            emailEt.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable) {

                    if (!TextUtils.isEmpty(s)) {
                        if (!isValidEmail(s.toString())) {
                            emailEt.error = "Enter a valid address";
                        } else {
                            isEmailFilled = true
                            if (isNameFilled && isEmailFilled && isClassFilled && isDateOfBirthFilled && isLocationFilled) {
                                (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).isEnabled =
                                    true
                            }
                        }
                    } else {
                        isEmailFilled = false;
                        (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).isEnabled =
                            false
                    }

                }
            })

            dateOfBirthEt.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable) {
                    if (!TextUtils.isEmpty(s)) {

                        isDateOfBirthFilled = true
                        if (isNameFilled && isEmailFilled && isClassFilled && isDateOfBirthFilled && isLocationFilled) {
                            (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).isEnabled =
                                true
                        }
                    } else {
                        isDateOfBirthFilled = false;
                        (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).isEnabled =
                            false
                    }
                }
            })


        }
    }

    fun isValidEmail(target: String?): Boolean {
        return if (target == null) {
            false
        } else {
            //android Regex to check the email address Validation
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }



}