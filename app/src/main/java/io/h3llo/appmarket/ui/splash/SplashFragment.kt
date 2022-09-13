package io.h3llo.appmarket.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import io.h3llo.appmarket.MainMenuActivity
import io.h3llo.appmarket.R
import io.h3llo.appmarket.core.SecurityPreferences
import io.h3llo.appmarket.core.SecurityPreferences.encryptPreferences
import io.h3llo.appmarket.databinding.FragmentSplashBinding
import io.h3llo.appmarket.util.Constantes


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

            // ESTO ES PARA NAVEGAR AL MAINMENUACTIVITY SI YA EXISTE TOKEN EN LAS PREFERNCIAS Y EVITAR LA PANTALLA DEL LOGIN
            val token = SecurityPreferences.getToken(requireContext().encryptPreferences(Constantes.PREFERENCES_TOKEN))
            if(!token.isEmpty()){
                startActivity(Intent(requireContext(), MainMenuActivity::class.java))
            } else{
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_loginFragment)
            }
        },SPLASH_TIME_OUT)


    }

}