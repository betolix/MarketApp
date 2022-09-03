package io.h3llo.appmarket.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import io.h3llo.appmarket.R
import io.h3llo.appmarket.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {

    private var _binding : FragmentSplashBinding? = null
    private val binding get() = _binding!!

    val SPLASH_TIME_OUT:Long = 3000

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(inflater,container, false)
        return binding.root
        //return inflater.inflate(R.layout.fragment_splash, container, false)

        //ViewBindings
        binding

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_loginFragment)
        },SPLASH_TIME_OUT)


    }

}