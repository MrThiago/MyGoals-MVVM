package com.mrthiago.mygoal.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.mrthiago.mygoal.R
import com.mrthiago.mygoal.model.Goal
import com.mrthiago.mygoal.viewmodel.GoalViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import java.util.*


class AddEditFragment : Fragment() {

    private val mGoalViewModel: GoalViewModel by viewModels()
    private val args by navArgs<AddEditFragmentArgs>()
    private var goalDate: Date? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView =  inflater.inflate(R.layout.fragment_add, container, false)

        // In Edit Mode
        if (args.isEditMode){
            rootView.titleInputLayout.editText?.setText(args.itemGoal?.title)
            rootView.dateText.text = args.itemGoal?.creationDate.toString()
            rootView.descriptionInputLayout.editText?.setText(args.itemGoal?.description)

            // Update CTA Text
            rootView.saveButton.text = "Update"
        }

        rootView.pickDateBt.setOnClickListener {
            // Create the date picker builder and set the title
            val builder = MaterialDatePicker.Builder.datePicker()

            // create the date picker
            val datePicker = builder.build()

            // set listener when date is selected
            datePicker.addOnPositiveButtonClickListener {
                val calendar = Calendar.getInstance()
                calendar.time = Date(it)
                ("${calendar.get(Calendar.DAY_OF_MONTH)}- " + "${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.YEAR)}")
                    .also { dateSelected -> dateText.text = dateSelected }
                    .also { goalDate = calendar.time }
            }
            datePicker.show(activity?.supportFragmentManager!!, datePicker.toString())
        }

        // Save / Update CTA
        rootView.saveButton.setOnClickListener {
            insertGoalIntoDatabase()
        }

        // Add Delete Menu
        setHasOptionsMenu(args.isEditMode)

        return rootView
    }

    private fun insertGoalIntoDatabase() {
        val goalTitle = titleInputLayout.editText?.text.toString()
        val goalDescription = descriptionInputLayout.editText?.text.toString()

        if(validInput(goalTitle, goalDescription, goalDate)) {
            // Create a Goal obj
            if (args.isEditMode){
                // Update a Goal obj
                val goal = Goal(
                    args.itemGoal!!.id,
                    goalTitle,
                    goalDescription,
                    goalDate
                )
                mGoalViewModel.updateGoal(goal)
                Snackbar.make(requireView(), "Goal Updated", Snackbar.LENGTH_SHORT).show()
            }
            else {
                // Create a new Goal obj
                val goal = Goal(0, goalTitle, goalDescription, goalDate)
                mGoalViewModel.addGoal(goal)

                Snackbar.make(requireView(), "Goal Saved", Snackbar.LENGTH_SHORT).show()
            }

            println("Title: $goalTitle | Description: $goalDescription")

            // Navigate Back
            findNavController().navigateUp()
        }
    }

    private fun validInput(goalTitle: String, goalDescription: String, goalDate: Date?): Boolean{
        return !TextUtils.isEmpty(goalTitle) && !TextUtils.isEmpty(goalDescription) && goalDate != null && !TextUtils.isEmpty(dateText.text.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteGoal()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteGoal() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Delete") { _, _ ->
            args.itemGoal?.let { mGoalViewModel.deleteGoal(it) }
            findNavController().navigateUp()
        }
        builder.setNegativeButton("No") { _, _ ->}
        builder.setTitle("Delete ${args.itemGoal?.title}?")
        builder.setMessage("Are you sure you want to delete ${args.itemGoal?.title}?")
        builder.create().show()
    }
}