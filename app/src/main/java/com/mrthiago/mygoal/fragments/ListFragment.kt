package com.mrthiago.mygoal.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mrthiago.mygoal.R
import com.mrthiago.mygoal.adapters.ListAdapter
import com.mrthiago.mygoal.databinding.FragmentListBinding
import com.mrthiago.mygoal.model.Goal
import com.mrthiago.mygoal.viewmodel.GoalViewModel


class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val mGoalViewModel: GoalViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)

        // Add Goal CTA
        binding.addGoalButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        // List
        val adapter = ListAdapter{ item -> handleListClick(item) }
        val recyclerView = binding.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // GoalViewModel
        mGoalViewModel.getAllData.observe(viewLifecycleOwner, Observer { goal ->
            adapter.setData(goal)

            setHasOptionsMenu(goal.isNotEmpty())
        })

        return binding.root
    }

    private fun handleListClick(item: Any) {
        val goal = item as Goal
        println("Item Selected ${goal.title}")

        val action = ListFragmentDirections.actionListFragmentToAddFragment(goal, true)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteAllGoals()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllGoals() {
        val builder = android.app.AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Delete") { _, _ ->
            mGoalViewModel.deleteAllGoals()
        }
        builder.setNegativeButton("No") {_, _ ->}
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to delete everything?")
        builder.create().show()
    }
}