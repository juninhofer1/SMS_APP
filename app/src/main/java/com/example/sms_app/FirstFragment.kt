package com.example.sms_app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sms_app.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private lateinit var mBroadcastReceiver: BroadcastReceiver
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    fun registerBroadcastSMS() {
        mBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                when (intent.action) {
                    BroadcastAction.KEY_SMS -> {
                        val mess = intent.getStringExtra(BundleKey.KEY_CODE_SMS)
                        binding.editTextNumber.setText(mess)
                    }
                }
            }
        }
        val filter = IntentFilter(BroadcastAction.KEY_SMS)
        activity?.registerReceiver(mBroadcastReceiver, filter)
    }

    override fun onResume() {
        registerBroadcastSMS()
        super.onResume()
    }

    override fun onDestroy() {
        activity?.unregisterReceiver(mBroadcastReceiver)
        super.onDestroy()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}