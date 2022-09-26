package com.example.kotlinapplication.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
//import androidx.fragment.app.activityViewModels
import androidx.lifecycle.*
import com.example.kotlinapplication.R
import com.example.kotlinapplication.databinding.TestFragmentBinding
import kotlinx.coroutines.flow.collect

class TestFragment : Fragment() {

    companion object {
        fun newInstance() = TestFragment()
    }

    private lateinit var binding: TestFragmentBinding
    private lateinit var viewModel: TestViewModel
//    private val viewModel: TestViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.test_fragment, container, false)
        viewModel = ViewModelProvider(this).get(TestViewModel::class.java)
        val userRepository = UserRepository()
//        viewModel = TestViewModel(userRepository)
        binding.viewModel = viewModel
        // TODO: Use the ViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.testBtn.setOnClickListener {
//            viewModel.nameLivedata.postValue("456")
//            viewModel._nameFlow.value = "458"
            refreshText()
        }
        lifecycle.coroutineScope.launchWhenStarted {
            viewModel._nameFlow.collect {
                Log.e("flowState","result = "+it)
                if (it.equals("1")){
                    Toast.makeText(requireContext(),"your winning !",Toast.LENGTH_SHORT).show()
                }
            }
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel.nameFlow.observe(viewLifecycleOwner){
//            binding.message.setText(it)
//        }
    }

    private fun refreshText() {
        viewModel._nameFlow.tryEmit("1")
    }

}